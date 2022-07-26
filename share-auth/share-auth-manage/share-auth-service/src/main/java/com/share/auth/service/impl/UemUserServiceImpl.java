package com.share.auth.service.impl;

import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.saas.redis.SassRedisInterface;
import com.google.common.collect.ImmutableMap;
import com.share.auth.constants.CodeFinal;
import com.share.auth.constants.GlobalConstant;
import com.share.auth.domain.UemUserDto;
import com.share.auth.enums.GlobalEnum;
import com.share.auth.model.entity.*;
import com.share.auth.model.querymodels.*;
import com.share.auth.model.vo.OperateResultVO;
import com.share.auth.model.vo.UemUserOperateVO;
import com.share.auth.model.vo.UserAndCompanyVo;
import com.share.auth.service.MsgSendService;
import com.share.auth.service.SysRoleService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.MessageUtil;
import com.share.auth.util.PasswordUtils;
import com.share.message.api.EmailTemplateService;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgSmsApiVO;
import com.share.message.domain.SendEmailVO;
import com.share.message.domain.SendMsgReturnVo;
import com.share.support.model.Company;
import com.share.support.model.Role;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import com.share.support.util.AES128Util;
import com.share.support.util.MD5EnCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xrp
 * @Date 20201021
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class UemUserServiceImpl implements UemUserService {
    /**
     * 配置文件注入
     */
    @Value("${redis.key.prefix.authCode}")
    private String redisKeyPrefixAuthCode;

    /**
     * 过期时间
     */
    @Value("${redis.key.expire.authCode}")
    private Integer authCodeExpireSeconds;

    @Autowired
    private SassRedisInterface sassRedisInterface;
    /**
     * 邮件过期时间30分钟
     */
    private static final Integer MAIL_EXPIRE_TIME = 1800;
    /**
     * 日志提示
     */
    private static final String LOG_PROMPT = "-----------";
    /**
     * 密码安全性不足提示
     */
    private static final String NOT_SECURITY_PROMPT = "密码安全性不足！";
    /**
     * 密码解密失败提示
     */
    private static final String DECODE_FAIL_PROMPT = "密码解密失败！";
    /**
     * 当前用户信息失败提示
     */
    private static final String GET_USER_INFO_FAIL_PROMPT = "用户信息获取失败，请重新登录";

    /**
     * 返回结果代码
     */
    private static final String RESULT_CODE = "resultCode";

    /**
     * 返回结果消息
     */
    private static final String RESULT_MSG = "resultMsg";


    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * 邮箱模板编号
     */
    @Value("${msg.email.template.find_back_pwd}")
    private String emailTemplateCode;

    /**
     * 邮箱IP
     */
    @Value("${msg.httpUrl}")
    private String emailHttpUrl;

    @Value("${msg.sms.template.register}")
    private String registerTemplate;

    @Value("${msg.sms.template.change_mobile}")
    private String changeMobileTemplate;

    @Value("${msg.sms.template.retrieve_password}")
    private String retrievePasswordTemplate;

    @Value("${aes_secret_key}")
    private String aesSecretKey;
    @Autowired
    private MsgApiService msgApiService;

    @Autowired
    private DefaultUserService userService;

    @Autowired
    private MsgSendService msgSendService;
    @Value("${msg.systemCode}")
    private String systemId;
    @Autowired
    DaoServiceClient client;
    @Autowired
    private SysRoleService sysRoleService;
    /**
     *手机号生成验证码
     * @param telephone 手机号
     * @param sign 标识为1的时候是快速注册的验证码，为2的时候是找回密码的验证码，为3的时候是修改绑定手机号
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> generateAuthCodeByTelephone(String telephone, String sign) {
        // 校验参数
        ResultHelper<Object> resultHelper = this.validParams(telephone, sign);
        if (Objects.nonNull(resultHelper) && Boolean.FALSE.equals(resultHelper.getSuccess())) {
            // 校验不通过
            return resultHelper;
        }

        String redisKey = redisKeyPrefixAuthCode + telephone + sign;
        if (StringUtils.isNotEmpty(sassRedisInterface.get(redisKey))) {
            Long time = sassRedisInterface.ttl(redisKey);
            log.info("手机号验证码RedisKey：{}，验证码{}剩余过期时间：{}", redisKey, sassRedisInterface.get(redisKey), time);
            long lastTime = Math.max(authCodeExpireSeconds - Long.parseLong("60"), 0L);
            //用户五分钟后才能发送一次短信
            if(time>0){
                return CommonResult.getFaildResultData("验证码有效期" + authCodeExpireSeconds / 60 + "分钟，获取间隔5分钟，请"+time/60+"分钟后获取！");
            }
//            if (time <= authCodeExpireSeconds && time > lastTime) {
//                return CommonResult.getFaildResultData("验证码有效期" + authCodeExpireSeconds / 60 + "分钟，获取间隔1分钟，请稍后获取！");
//            }
            else{
                sassRedisInterface.del(redisKey);
            }
        }
        String code = MessageUtil.randomNum(6);
        //验证码绑定手机号存储到redis中，并设置过期时间
        log.info(LOG_PROMPT + redisKey);
        sassRedisInterface.set(redisKey, code);
        sassRedisInterface.expire(redisKey, authCodeExpireSeconds);
        // 发送短信
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(telephone);
        // 消息宏参数mgp
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        if(CodeFinal.SIGN_1.equals(sign)){
            // 注册
            msgSmsApiVO.setSmsTemplateCode(registerTemplate);
        }else if (CodeFinal.SIGN_2.equals(sign)){
            // 找回密码
            msgSmsApiVO.setSmsTemplateCode(retrievePasswordTemplate);
        }else {
            // 修改手机号
            msgSmsApiVO.setSmsTemplateCode(changeMobileTemplate);
        }
        content.put("code", code);
        content.put("time", authCodeExpireSeconds/60);
        marco.put(CodeFinal.CONTENT, content);
        log.info("短信消息宏参数传参： " + marco.toString());
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);
        // 调用公共服务接口发送短信
        try{
            SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
            if (CodeFinal.SUCCESSEMAIL.equals(sendMsgReturnVo.getResultCode())){
                //短线发送成功，五分钟后才能发送
                return CommonResult.getSuccessResultData("验证码发送成功,有效期" + authCodeExpireSeconds/60 + "分钟");
            }else {
                // 发送短信失败删除redis中的key
          //      sassRedisInterface.del(redisKey);
                log.info("短信发送失败，失败原因：" + sendMsgReturnVo.getResultMsg());
                return CommonResult.getFaildResultData("短信发送失败，请联系客服！");
            }
        }catch (Exception e){
            log.info("调用推送服务失败"+ e.getMessage());
            // 发送短信失败删除redis中的key
         //   sassRedisInterface.del(redisKey);
            return CommonResult.getFaildResultData("短信发送失败，请联系客服！");
        }
    }

    /**
     * 校验参数
     *
     * @param telephone 电话
     * @param sign      标识为1的时候是快速注册的验证码，为2的时候是找回密码的验证码，为3的时候是修改绑定手机号
     * @return 校验结果
     */
    private ResultHelper<Object> validParams(String telephone, String sign) {
        //用户表
        List<UemUser> uemUserMobileList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.mobile.eq(":mobile"))
                .execute(ImmutableMap.of("mobile", telephone));
        //客服表
        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser
                .select(QSysPlatformUser.sysPlatformUser.fieldContainer())
                .where(QSysPlatformUser.tel.eq(":mobile"))
                .execute(ImmutableMap.of("mobile", telephone));
        //sign用于区别快速注册生成验证码和手机号找回密码生成验证码,1为快速注册,2为找回密码,3为修改绑定手机号
        if (CodeFinal.SIGN_1.equals(sign)) {
            //判断手机是否注册过
            if (CollectionUtils.isNotEmpty(uemUserMobileList) || CollectionUtils.isNotEmpty(sysPlatformUserList)) {
                return CommonResult.getFaildResultData("该手机号已经被注册，短信验证码发送失败");
            }
        } else if (CodeFinal.SIGN_2.equals(sign)) {
            //判断手机是否注册过
            String sign2Validate = this.sign2Validate(uemUserMobileList);
            if (StringUtils.isNotBlank(sign2Validate)) {
                return CommonResult.getFaildResultData(sign2Validate);
            }
        } else if (CodeFinal.SIGN_3.equals(sign)) {
            //手机号重复校验
            String validateResult = sign3Validate(uemUserMobileList, telephone, sysPlatformUserList);
            if (StringUtils.isNotBlank(validateResult)) {
                return CommonResult.getFaildResultData(validateResult);
            }
        } else {
            return CommonResult.getFaildResultData("参数错误，请联系管理员！");
        }

        return CommonResult.getSuccessResultData();
    }

    /**
     * 找回密码校验
     * @param uemUserMobileList 用户列表
     * @return 校验结果
     */
    private String sign2Validate(List<UemUser>uemUserMobileList) {
        if (CollectionUtils.isEmpty(uemUserMobileList)) {
            return "验证不通过";
        }
        if (CollectionUtils.isNotEmpty(uemUserMobileList) && Boolean.FALSE.equals(uemUserMobileList.get(0).getIsValid())) {
            return "绑定该手机号用户已被禁用";
        }
        if (Objects.equals(uemUserMobileList.get(0).getSource(), GlobalEnum.UserSource.NTIP.getCode())) {
            return "国家综合交通运输信息平台用户不允许找回密码";
        }
        return null;
    }

    /**
     * 修改绑定手机号校验
     *
     * @param uemUserMobileList
     * @param telephone
     * @param sysPlatformUserList
     * @return
     * @author huanghwh
     * @date 2021/5/7 上午9:25
     */
    private String sign3Validate(List<UemUser> uemUserMobileList, String telephone, List<SysPlatformUser> sysPlatformUserList) {
        if (CollectionUtils.isNotEmpty(uemUserMobileList)) {
            //获取当前用户信息
            AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
            if (Objects.isNull(user) || Objects.isNull(user.getUemUserId())) {
                return "未获取到登录信息";
            }
            UemUser uemUser = QUemUser.uemUser.selectOne().byId(user.getUemUserId());
            if (telephone.equals(uemUser.getMobile())) {
                return "与当前手机号一致，请重新输入手机号";
            } else {
                return "该手机号已被绑定，请重新输入手机号";
            }
        } else if (CollectionUtils.isNotEmpty(sysPlatformUserList)) {
            return "该手机号已被绑定，请重新输入手机号";
        }
        return null;
    }

    /**
     * 验证验证码
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> verifyAuthCode(UemUserDto uemUserDto) {

        //手机号
        String telephone = uemUserDto.getMobile();
        //验证码
        String authCode = uemUserDto.getAuthCode();

        if(StringUtils.isEmpty(authCode)){
            return CommonResult.getFaildResultData("请输入验证码");
        }
        List<UemUser> uemUserList = QUemUser.uemUser.select()
                .where(QUemUser.mobile.eq(":telephone")
                        .and(QUemUser.isValid.eq$(true)))
                .execute(ImmutableMap.of("telephone",telephone));

        if(CollectionUtils.isEmpty(uemUserList)){
           return CommonResult.getFaildResultData("手机号无效，请重新输入！");
        }
        String redisKey = redisKeyPrefixAuthCode + telephone + uemUserDto.getSign();
        //对比验证码是否失效或者错误验证码
        String redisAuthCode = sassRedisInterface.get(redisKey);
        if(StringUtils.isEmpty(redisAuthCode)){
            return CommonResult.getFaildResultData("验证码已过期，请重新获取");
        }
        if(!authCode.equals(redisAuthCode)){
            return CommonResult.getFaildResultData("验证码输入错误，请重新获取");
        }
        sassRedisInterface.del(redisKey);
        if(CodeFinal.SIGN_2.equals(uemUserDto.getSign())){
            // redis中添加一下当前用户重置密码的标志，过期时间为验证码两倍时间，修改密码时根据updatePwd+手机号/邮箱号获取要重置密码的用户Id
            String updatePwdKey = RandomStringUtils.randomAlphanumeric(32);
            log.info(LOG_PROMPT + updatePwdKey);
            sassRedisInterface.set(updatePwdKey, uemUserList.get(0).getUemUserId().toString());
            sassRedisInterface.expire(updatePwdKey, authCodeExpireSeconds * 2);
            Map<String, Object> result = new HashMap<>(16);
            result.put("data", updatePwdKey);
            result.put("msg", "验证码正确,请修改密码，" + authCodeExpireSeconds *2/60 +"分钟内修改有效！");
            return CommonResult.getSuccessResultData(result);
        }
        return CommonResult.getSuccessResultData("验证码正确");
    }

    /**
     *注册并验证验证码
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> register(UemUserDto uemUserDto){
        //用户名
        String account = uemUserDto.getAccount();
        // 岗位代码
        String staffDutyCode = uemUserDto.getStaffDutyCode();
        //手机号
        String mobile = uemUserDto.getMobile();
        //密码
        String password = uemUserDto.getPassword();
        //验证码
        String authCode = uemUserDto.getAuthCode();
        //是否同意协议(0不同意，1同意)
        Boolean isAgreemeent = uemUserDto.getIsAgreemeent();

        //判断用户是否使用过
        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.account.eq(":account").or(QUemUser.mobile.eq$(mobile)))
                .execute(ImmutableMap.of("account", account));
        //客服表
        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser
                .select(QSysPlatformUser.sysPlatformUser.fieldContainer())
                .where(QSysPlatformUser.account.eq(":account").or(QSysPlatformUser.tel.eq$(mobile)))
                .execute(ImmutableMap.of("account", account));
        AtomicReference<Boolean> hadAccount = new AtomicReference<>(false);
        AtomicReference<Boolean> hadMobile = new AtomicReference<>(false);
        uemUserList.forEach(uemUser -> {
            if (account.equals(uemUser.getAccount())){
                hadAccount.set(true);
            }
            if (mobile.equals(uemUser.getMobile())){
                hadMobile.set(true);
            }
        });
        sysPlatformUserList.forEach(sysPlatformUser -> {
            if (account.equals(sysPlatformUser.getAccount())) {
                hadAccount.set(true);
            }
            if (mobile.equals(sysPlatformUser.getTel())) {
                hadMobile.set(true);
            }
        });

        if (Boolean.TRUE.equals(hadMobile.get())) {
            return CommonResult.getFaildResultData("该手机号已经被注册！");
        }
        if (Boolean.TRUE.equals(hadAccount.get())) {
            return CommonResult.getFaildResultData("该用户名已存在，请重新输入！");
        }
        if (StringUtils.isEmpty(authCode)) {
            return CommonResult.getFaildResultData("请输入验证码");
        }
        //对比验证码是否失效或者错误验证码
        String redisAuthCode = sassRedisInterface.get(redisKeyPrefixAuthCode+mobile + 1);
        if (!uemUserDto.getAuthCodeFlag()) {
            return CommonResult.getFaildResultData("请先获取验证码");
        }
        if(StringUtils.isEmpty(redisAuthCode)){
            return CommonResult.getFaildResultData("验证码已过期，请重新获取");
        }
        if(!authCode.equals(redisAuthCode)){
            return CommonResult.getFaildResultData("验证码输入错误，请重新获取");
        }
        try {
            String decPassword = AES128Util.decrypt(password, aesSecretKey);
            if (!PasswordUtils.matchersPassword(decPassword)) {
                return CommonResult.getFaildResultData(NOT_SECURITY_PROMPT);
            }
            password = MD5EnCodeUtils.MD5EnCode(decPassword).substring(8, 24);
        } catch (Exception e) {
//            e.printStackTrace();
            return CommonResult.getFaildResultData(DECODE_FAIL_PROMPT);
        }
        UemUser uemUser = new UemUser();
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        uemUser.setStaffDutyCode(staffDutyCode);
        uemUser.setAccount(account);
        uemUser.setMobile(mobile);
        // 密码二次加密
        password = MD5EnCodeUtils.MD5EnCode(password);
        uemUser.setPassword(password);
        uemUser.setSource(GlobalEnum.UserSource.REGISTER.getCode());
        uemUser.setUserType(CodeFinal.USER_TYPE_ZERO);
        uemUser.setIsValid(true);
        uemUser.setInvalidTime(new Date());
        uemUser.setIsAgreemeent(isAgreemeent);
        // 获取来源应用
        Long oriApplicationId = MessageUtil.getOriApplicationId();
        // 来源应用不存在时，获取账号系统应id
        if (Objects.isNull(oriApplicationId)) {
            oriApplicationId = MessageUtil.getApplicationId();
        }
        uemUser.setOriApplication(oriApplicationId);
        QUemUser.uemUser.save(uemUser);
        sassRedisInterface.del(redisKeyPrefixAuthCode+mobile + 1);
        //发送短信通知
        msgSendService.registeSuccessSendMsg(uemUser.getAccount(),uemUser.getMobile());
        return CommonResult.getSuccessResultData("注册成功");
    }



    /**
     *更新密码
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> updatePassword(UemUserDto uemUserDto) {
//        //新密码
//        String newPassword = uemUserDto.getNewPassword();
//        if(StringUtils.isEmpty(newPassword)){
//            return CommonResult.getFaildResultData("新密码不能为空");
//        }
//        if (StringUtils.isEmpty(uemUserDto.getUpdatePwdToken())){
//            return CommonResult.getFaildResultData("非法更新密码，请确认！");
//        }
//        try{
//            String  decPassword = AES128Util.decrypt(newPassword, aesSecretKey);
//            if (!PasswordUtils.matchersPassword(decPassword)){
//                return CommonResult.getFaildResultData(NOT_SECURITY_PROMPT);
//            }
//            newPassword = MD5EnCodeUtils.MD5EnCode(decPassword).substring(8,24);
//        }catch (Exception e){
//            return CommonResult.getFaildResultData(DECODE_FAIL_PROMPT);
//        }
//        // 密码二次加密
//        newPassword = MD5EnCodeUtils.MD5EnCode(newPassword);
//        String uemUserId = sassRedisInterface.get(uemUserDto.getUpdatePwdToken());
//        if (StringUtils.isEmpty(uemUserId)){
//            return CommonResult.getFaildResultData("修改密码有效期已过，请重新找回密码！");
//        }
//        UemUser uemUser = QUemUser.uemUser.selectOne().byId(Long.valueOf(uemUserId));
//        if (Objects.nonNull(uemUser) && uemUser.getPassword().equals(newPassword)){
//            return CommonResult.getFaildResultData("该密码与原密码相同");
//        }
//        int updateCount = QUemUser.uemUser.update(QUemUser.password)
//                    .where(QUemUser.uemUserId.eq(":uemUserId"))
//                    .execute(newPassword,uemUserId);
//        if(updateCount > 0){
//            sassRedisInterface.del(uemUserDto.getUpdatePwdToken());
//            return CommonResult.getSuccessResultData("恭喜，新密码已设置成功！");
//        }else{
//            return CommonResult.getFaildResultData("密码更新失败！");
//        }

        return null;
    }

    /**
     *邮箱找回密码
     * @param email 邮箱
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> findPasswordByMail(String email) {

        if(StringUtils.isEmpty(email)){
            return CommonResult.getFaildResultData("邮箱不能为空！");
        }

        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.email.eq(":email"))
                .execute(ImmutableMap.of("email", email));

        if (CollectionUtils.isEmpty(uemUserList)) {
            return CommonResult.getFaildResultData("该邮箱系统中未绑定");
        }
        if (CollectionUtils.isNotEmpty(uemUserList) && Boolean.FALSE.equals(uemUserList.get(0).getIsValid())) {
            return CommonResult.getFaildResultData("绑定该邮箱用户已被禁用");
        }
        if (Objects.equals(uemUserList.get(0).getSource(), GlobalEnum.UserSource.NTIP.getCode())) {
            return CommonResult.getFaildResultData("国家综合交通运输信息平台用户不允许找回密码");
        }
        String updatePwdKey = RandomStringUtils.randomAlphanumeric(32);
        log.info(LOG_PROMPT + updatePwdKey);
        sassRedisInterface.set(updatePwdKey, uemUserList.get(0).getUemUserId().toString());
        sassRedisInterface.expire(updatePwdKey, MAIL_EXPIRE_TIME);

        String linkAddress = emailHttpUrl +"/findPwdByEmail?active=2&updatePwdToken="+ updatePwdKey;
        SendEmailVO sendEmailVO = new SendEmailVO();
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Object> contentMap = new HashMap<>(16);
        map.put(CodeFinal.LINKADDRESS,linkAddress);
        contentMap.put(CodeFinal.CONTENT,map);

        sendEmailVO.setSystemId(MessageUtil.getApplicationCode());
        //邮箱模板编号
        sendEmailVO.setEmailTemplateCode(emailTemplateCode);
        sendEmailVO.setToEmail(email);
        sendEmailVO.setMarcoAndAttachParams(contentMap);
        try {
            Map<String, Object> mapEmail = emailTemplateService.sendEmail(sendEmailVO);
            log.info("mapEmail" + mapEmail);
            if(CodeFinal.SUCCESSEMAIL.equals(mapEmail.get(CodeFinal.RESULTCODE)) || (null == mapEmail.get(RESULT_CODE) && null == mapEmail.get(RESULT_MSG))){
                return CommonResult.getSuccessResultData(sendEmailVO);
            }else{
                // 邮件发送失败删除redis中的key
                sassRedisInterface.del(updatePwdKey);
                log.info("邮件发送失败，失败原因：" + mapEmail.get("resultMsg"));
                return CommonResult.getFaildResultData( "邮件发送失败，请联系客服！");
            }
        }catch (Exception e){
            // 邮件发送失败删除redis中的key
            sassRedisInterface.del(updatePwdKey);
            log.info("调用推送中心邮件发送失败" + e.getMessage());
            return CommonResult.getFaildResultData( "邮件发送失败，请联系客服！");
        }

    }



    /**
     * @Author:chenxf
     * @Description: 根据用户id获取用户信息接口，根据用户id和clientId获取用户角色信息
     * @Date: 15:55 2020/12/10
     * @Param: [uemUserId, clientId]
     * @Return:com.share.auth.domain.User
     *
     */
    @Override
    public User getUserAllInfo(Long uemUserId, String clientId) {
        User user = QUemUser.uemUser.selectOne().mapperTo(User.class).byId(uemUserId);
        if (Objects.nonNull(user)) {
            // 不返回密码信息
            user.setPassword(null);
            if (Objects.nonNull(user.getBlindCompanny())){
                Company company = QUemCompany.uemCompany.selectOne().mapperTo(Company.class).byId(user.getBlindCompanny());
                if(Objects.nonNull(company) && Objects.nonNull(company.getOrganizationType())){
                    SysDictType sysDictType = QSysDictType.sysDictType.selectOne().where(QSysDictType.dictTypeCode.eq$("LOGIN_ORGANIZATION_TYPE").and(QSysDictType.isValid.eq$(true))).execute();
                    SysDictCode sysDictCode = QSysDictCode.sysDictCode.selectOne().where(QSysDictCode.sysDictTypeId.eq$(sysDictType.getSysDictTypeId()).and(QSysDictCode.dictCode.eq$(company.getOrganizationType()).and(QSysDictCode.isValid.eq$(true)))).execute();
                    if (Objects.nonNull(sysDictCode)){
                        company.setOrganizationTypeName(sysDictCode.getDictName());
                    }
                    user.setCompanyName(company.getCompanyNameCn());
                }
                user.setCompany(company);
                if (Objects.nonNull(company) && Objects.nonNull(company.getIsSuperior()) && company.getIsSuperior()){
                    List<Company> companyList = this.queryChildrenCompany(company.getUemCompanyId(), 1);
                    user.setChildrenCompanyList(companyList);
                }
            }
            if (StringUtils.isNotEmpty(clientId)) {
                // 根据clientId查询应用id
                OauthClientDetails oauthClientDetail = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
                // 获取用户角色信息
                getUserRoleList(oauthClientDetail, user);
            }
        }else {
            SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(uemUserId);
            user = new User();
            user.setUemUserId(sysPlatformUser.getSysPlatformUserId());
            user.setAccount(sysPlatformUser.getAccount());
            user.setMobile(sysPlatformUser.getTel());
            user.setEmail(sysPlatformUser.getMail());
            user.setName(sysPlatformUser.getName());
            user.setSysRoleId(CodeFinal.ADMIN_DEFAULT_ROLE_ID);
            user.setSysRoleName("PlateFormUser");
            user.setRoleCode("SYSADMIN");
            Role role = new Role();
            role.setSysRoleId(CodeFinal.ADMIN_DEFAULT_ROLE_ID);
            role.setRoleName("PlateFormUser");
            role.setRoleCode("SYSADMIN");
            user.setRole(role);
            // 将角色添加到角色列表
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            user.setRoleList(roleList);
        }
        user.setClientId(clientId);
        return user;
    }

    private void getUserRoleList(OauthClientDetails oauthClientDetail, User user) {
        if (Objects.nonNull(oauthClientDetail)) {
            List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(QUemUserRole.uemUserRole.fieldContainer()).where(
                    QUemUserRole.uemUserId.eq$(user.getUemUserId())
                            .and(QUemUserRole.sysApplicationId.eq$(oauthClientDetail.getSysApplicationId()))
                            .and(QUemUserRole.isValid.eq$(true))
                            .and(QUemUserRole.uemUserRole.chain(QSysRole.isValid).eq$(true))
            ).execute();
            if (CollectionUtils.isNotEmpty(uemUserRoleList)) {
                // 查询用户的所有角色
                List<Long> roleIds = new ArrayList<>();
                for (UemUserRole uemUserRole : uemUserRoleList) {
                    roleIds.add(uemUserRole.getSysRoleId());
                }
                List<Role> roleList = QSysRole.sysRole.select().mapperTo(Role.class).byId(roleIds);
                // 默认设置第一个角色
                user.setRole(roleList.get(0));
                // 将所有角色添加到列表
                user.setRoleList(roleList);
                // 只有一个角色时，设置角色信息
                if (roleIds.size() == 1) {
                    user.setSysRoleId(roleList.get(0).getSysRoleId());
                    user.setSysRoleName(roleList.get(0).getRoleName());
                    user.setRoleCode(roleList.get(0).getRoleCode());
                }
            }
        }
    }

    /**
     * 根据企业id查询所有下级企业
     * @param uemCompanyId 企业id
     * @param level 递归层级，
     * @return 所有下级企业
     */
    private List<Company> queryChildrenCompany(Long uemCompanyId, int level) {
        List<Company> companyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer())
                .where(QUemCompany.belongCompany.eq$(uemCompanyId)
                        .and(QUemCompany.auditStatus.eq$("1")
                        .and(QUemCompany.isValid.eq$(true)
                        .and(QUemCompany.carrierType.eq$("0").or(QUemCompany.carrierType.isNull())))))
                .mapperTo(Company.class).execute();
        if (CollectionUtils.isEmpty(companyList)) {
            return new ArrayList<>();
        }
        // 递归层级不能大于100
        int maxLevel = 100;
        if (level > maxLevel) {
            return new ArrayList<>();
        }
        level++;
        for (Company company : companyList) {
            List<Company> childrenCompanyList = this.queryChildrenCompany(company.getUemCompanyId(), level);
            company.setChildrenCompanyList(childrenCompanyList);
        }
        return companyList;
    }

    /**
     *根据用户Id获取用户信息
     * @param uemUserId 用户ID
     * @return List<UemUser>
     * @author xrp
     * */
    @Override
    public List<UemUser> queryUemUserByUserId(String uemUserId) {

        return QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer().exclude(QUemUser.password))
                .where(QUemUser.uemUserId.eq(":uemUserId"))
                .execute(ImmutableMap.of("uemUserId", uemUserId));
    }

    /**
     * 根据物流交换代码返回公司信息
     * @param companyCode 物流交换代码
     * @return List<UemCompany>
     * @author xrp
     * */
    @Override
    public List<UemCompany> queryUemUserCompany(String companyCode) {

        return QUemCompany.uemCompany
                .select(QUemCompany.uemCompany.fieldContainer())
                .where(QUemCompany.companyCode.eq(":companyCode"))
                .execute(ImmutableMap.of("companyCode", companyCode));
    }

    /**
     * @Author:chenxf
     * @Description: 校验用户名唯一性
     * @Date: 15:03 2021/1/6
     * @Param: [account]
     * @Return:java.lang.Boolean
     *
     */
    @Override
    public Boolean validateAccount(String account) {
        List<UemUser> uemUserList = QUemUser.uemUser.select(QUemUser.account).where(QUemUser.account.eq$(account)).execute();
        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser.select(QSysPlatformUser.account).where(QSysPlatformUser.account.eq$(account)).execute();
        return CollectionUtils.isEmpty(uemUserList) && CollectionUtils.isEmpty(sysPlatformUserList);
    }

    /**
     * @Author:chenxf
     * @Description: 修改用户信息统一接口
     * @Date: 14:08 2021/1/9
     * @Param: [uemUserDto]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @Override
    public ResultHelper<Object> updateUemUserInfo(UemUserDto uemUserDto) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || Objects.isNull(userInfoModel.getUemUserId())) {
            return CommonResult.getFaildResultData(GET_USER_INFO_FAIL_PROMPT);
        }
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(userInfoModel.getUemUserId());
        if (uemUserDto != null) {
            // 根据传入的字段决定更新哪些信息
            // 用户名校验修改
            String accountValidateResult = accountValidate(uemUserDto, uemUser, userInfoModel);
            if (StringUtils.isNotBlank(accountValidateResult)) {
                return CommonResult.getFaildResultData(accountValidateResult);
            }
            // 密码校验修改
            String passwordValidateResult = passwordValidate(uemUserDto, uemUser);
            if (StringUtils.isNotBlank(passwordValidateResult)) {
                return CommonResult.getFaildResultData(passwordValidateResult);
            }
            uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        }
        int updateNum = QUemUser.uemUser.save(uemUser);
        if (updateNum == CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            // 刷新当前登录人用户信息缓存
            userService.updateCurrentLoginUser();
            return CommonResult.getFaildResultData("更新失败");
        } else {
            return CommonResult.getSuccessResultData("更新成功");
        }
    }

    /**
     * 密码校验修改
     *
     * @param uemUserDto
     * @param uemUser
     * @return
     * @author huanghwh
     * @date 2021/5/7 上午10:19
     */
    private String passwordValidate(UemUserDto uemUserDto, UemUser uemUser) {
        if (uemUserDto.getPassword() != null) {
            String password = uemUserDto.getPassword();
            try {
                String decPassword = AES128Util.decrypt(password, aesSecretKey);
                if (!PasswordUtils.matchersPassword(decPassword)) {
                    return NOT_SECURITY_PROMPT;
                }
                password = MD5EnCodeUtils.MD5EnCode(decPassword).substring(8, 24);
            } catch (Exception e) {
                return DECODE_FAIL_PROMPT;
            }
            // 二次加密
            password = MD5EnCodeUtils.MD5EnCode(password);
            if (password.equals(uemUser.getPassword())) {
                return "新密码与原密码一致，修改失败";
            }
            uemUser.setPassword(password);
        }
        return null;
    }

    /**
     * 用户名校验修改
     *
     * @param uemUserDto
     * @param uemUser
     * @return
     * @author huanghwh
     * @date 2021/5/7 上午10:18
     */
    private String accountValidate(UemUserDto uemUserDto, UemUser uemUser, AuthUserInfoModel userInfoModel) {
        if (uemUserDto.getAccount() != null) {
            List<UemUser> userList = QUemUser.uemUser.select().where(QUemUser.account.eq$(uemUserDto.getAccount()).and(QUemUser.uemUserId.ne$(userInfoModel.getUemUserId()))).execute();
            List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser.select().where(QSysPlatformUser.account.eq$(uemUserDto.getAccount())).execute();
            if (CollectionUtils.isNotEmpty(userList) || CollectionUtils.isNotEmpty(sysPlatformUserList)) {
                return "用户名已存在，修改失败";
            }
            uemUser.setAccount(uemUserDto.getAccount());
        }
        return null;
    }

    /**
     * @Author:chenxf
     * @Description: 个人中心获取当前登录用户信息接口
     * @Date: 14:14 2021/1/9
     * @Param: []
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @Override
    public ResultHelper<UemUserDto> getLoginUserInfo() {
//        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
//        if (Objects.isNull(userInfoModel) || Objects.isNull(userInfoModel.getUemUserId())) {
//            return CommonResult.getFaildResultData(GET_USER_INFO_FAIL_PROMPT);
//        }
//        UemUserDto uemUserDto = QUemUser.uemUser.selectOne().mapperTo(UemUserDto.class).byId(userInfoModel.getUemUserId());
//        if (Objects.nonNull(uemUserDto)) {
//            // 判断缓存中的用户信息与查询到的用户信息是否一致（版本号是否一致）
//            if (!userInfoModel.getRecordVersion().equals(uemUserDto.getRecordVersion())) {
//                userService.updateCurrentLoginUser();
//            }
//            // 不返回登录密码
//            uemUserDto.setPassword(null);
//            if (uemUserDto.getUemIdCardId()!=null){
//                UemIdCard uemIdCard = QUemIdCard.uemIdCard.selectOne().byId(uemUserDto.getUemIdCardId());
//                uemUserDto.setAuditTime(uemIdCard.getAuditTime());
//            }
//            uemUserDto.setHasRole(false);
//            // 用户类型为国交管理员
//            if (Objects.equals(uemUserDto.getUserType(), GlobalEnum.UserType.IMPT_ADMIN.getCode())) {
//                uemUserDto.setIdentity(GlobalEnum.UserIdentity.IMPT_ADMIN.getCode());
//                uemUserDto.setHasBind(false);
//            }
//        } else {
//            SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(userInfoModel.getUemUserId());
//            if (Objects.isNull(sysPlatformUser)) {
//                return CommonResult.getFaildResultData(GET_USER_INFO_FAIL_PROMPT);
//            }
//            uemUserDto = new UemUserDto();
//            uemUserDto.setAccount(sysPlatformUser.getAccount());
//            uemUserDto.setName(sysPlatformUser.getName());
//            uemUserDto.setMobile(sysPlatformUser.getTel());
//            uemUserDto.setEmail(sysPlatformUser.getMail());
//            uemUserDto.setIsValid(sysPlatformUser.getIsValid());
//            // 标志平台客服
//            uemUserDto.setIdentity(GlobalEnum.UserIdentity.ADMIN.getCode());
//        }
//        return CommonResult.getSuccessResultData(uemUserDto);
        return null;
    }

    /**
     * @Author:chenxf
     * @Description: 校验密码和当前登录人是否一致
     * @Date: 18:30 2021/1/11
     * @Param: [password]
     * @Return:java.lang.Boolean
     *
     */
    @Override
    public Boolean validatePassword(String password) {
        password = MD5EnCodeUtils.MD5EnCode(password);
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || Objects.isNull(userInfoModel.getUemUserId())) {
            return true;
        }
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(userInfoModel.getUemUserId());
        return Objects.nonNull(uemUser) && password.equals(uemUser.getPassword());
    }

    /**
     * @Author:chenxf
     * @Description: 赋予用户默认角色
     * @Date: 15:42 2021/1/28
     * @Param: [uemUserId, uemCompanyId]
     * @Return:void
     *
     */
    @Override
    public void defaultUemUserRole(Long uemUserId, Long uemCompanyId){
        log.info("用户：{}的绑定企业为:{}，为该用户设置默认角色", uemUserId, uemCompanyId);
        // 获取企业权限配置表中默认角色数据
        List<UemCompanyRole> uemCompanyRoleList = QUemCompanyRole.uemCompanyRole.select().where(
                QUemCompanyRole.uemCompanyId.eq$(uemCompanyId)
                        .and(QUemCompanyRole.isDefault.eq$(Boolean.TRUE))
                        .and(QUemCompanyRole.uemCompanyRole.chain(QSysApplication.isValid).eq$(Boolean.TRUE))
                        .and(QUemCompanyRole.uemCompanyRole.chain(QSysRole.isValid).eq$(Boolean.TRUE))
        ).execute();

        // 设置用户默认角色
        List<UemUserRole> defaultRoleList = new ArrayList<>();
        for (UemCompanyRole role : uemCompanyRoleList) {
            // 默认赋予用户角色
            UemUserRole uemUserRole = new UemUserRole();
            uemUserRole.setSysApplicationId(role.getSysApplicationId());
            uemUserRole.setUemUserId(uemUserId);
            uemUserRole.setIsValid(true);
            uemUserRole.setInvalidTime(new Date());
            uemUserRole.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            uemUserRole.setSysRoleId(role.getSysRoleId());
            defaultRoleList.add(uemUserRole);
        }
        // 判空
        if (CollectionUtils.isNotEmpty(defaultRoleList)) {
            // 保存默认角色
            QUemUserRole.uemUserRole.save(defaultRoleList);
        }
    }

    /**
     * 根据用户ID集合返回用户和公司信息
     *
     * @param userIdList 用户ID集合
     * @return List<UserAndCompanyVo>
     * @author cxq
     */
    @Override
    public List<UserAndCompanyVo> queryUemUserCompanyByUserId(List<Long> userIdList) {
       List<UserAndCompanyVo> userAndCompanyVos= QUemUser.uemUser.select(QUemUser.uemUserId.as("userId"),QUemUser.name.as("contactName"),QUemUser.mobile.as("contactTel"),QUemUser.uemUser.chain(QUemCompany.organizationCode).as("organizationCode"),
                QUemUser.uemUser.chain(QUemCompany.companyNameCn).as("companyNameCn")).where(QUemUser.uemUserId.in$(userIdList)).mapperTo(UserAndCompanyVo.class).execute();
        return userAndCompanyVos;
    }

    @Override
    public OperateResultVO operateUemUser(UemUserOperateVO uemUserOperateVO) {
        // 校验
        String validStr = this.validOperateUemUser(uemUserOperateVO);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(validStr)) {
            return OperateResultVO.getParamErrorMessage(validStr, uemUserOperateVO.getOptionType(), null, uemUserOperateVO.getLoginNo());
        }
        // 删除操作
        if (Objects.equals(uemUserOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.DELETE.getCode())) {
            // 获取用户
            UemUser sourceUemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).where(QUemUser.source.eq$(GlobalEnum.UserSource.NTIP.getCode()).and(QUemUser.account.eq$(uemUserOperateVO.getLoginNo()).or(QUemUser.account.eq$(GlobalConstant.PREFIX_GJIM + uemUserOperateVO.getLoginNo())))).execute();
            // 删除用户、用户角色
            QUemUser.uemUser.delete().id(sourceUemUser.getUemUserId()).execute();
            QUemUserRole.uemUserRole.delete().where(QUemUserRole.uemUserId.eq$(sourceUemUser.getUemUserId())).execute();
        }
        // 新增操作
        if (Objects.equals(uemUserOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.INSERT.getCode())) {
            // 设置基础信息
            UemUser uemUser = new UemUser();
            this.setUemUserByOperateVO(uemUserOperateVO, uemUser);
            uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            // 保存
            QUemUser.uemUser.save(uemUser);
            // 设置默认角色
            this.insertDefaultRole(uemUser.getUemUserId());
        }
        // 修改操作
        if (Objects.equals(uemUserOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.UPDATE.getCode())) {
            // 获取用户
            UemUser sourceUemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).where(QUemUser.source.eq$(GlobalEnum.UserSource.NTIP.getCode()).and(QUemUser.account.eq$(uemUserOperateVO.getLoginNo()).or(QUemUser.account.eq$(GlobalConstant.PREFIX_GJIM + uemUserOperateVO.getLoginNo())))).execute();
            // 设置基础信息
            UemUser uemUser = new UemUser();
            BeanUtils.copyProperties(sourceUemUser, uemUser);
            this.setUemUserByOperateVO(uemUserOperateVO, uemUser);
            uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            // 保存
            QUemUser.uemUser.save(uemUser);
        }
        return OperateResultVO.getSuccessMessage(uemUserOperateVO.getOptionType(), null, uemUserOperateVO.getLoginNo());
    }

    /**
     * 国家综合交通运输信息平台用户新增默认角色
     * @param uemUserId 用户id
     */
    private void insertDefaultRole(Long uemUserId) {
        if (Objects.isNull(uemUserId)) {
            return;
        }
        // 查询默认角色
        List<SysRole> sysRoleList = QSysRole.sysRole.select(QSysRole.sysRole.fieldContainer()).where(QSysRole.isDefault.eq$(true)).execute();
        if (CollectionUtils.isEmpty(sysRoleList)) {
            return;
        }
        // 用户角色列表
        List<UemUserRole> uemUserRoleList = new ArrayList<>();
        for (SysRole sysRole : sysRoleList) {
            UemUserRole uemUserRole = new UemUserRole();
            // 用户
            uemUserRole.setUemUserId(uemUserId);
            // 应用
            uemUserRole.setSysApplicationId(sysRole.getSysApplicationId());
            // 角色
            uemUserRole.setSysRoleId(sysRole.getSysRoleId());
            // 启用
            uemUserRole.setIsValid(true);
            uemUserRole.setInvalidTime(new Date());
            // 新增状态
            uemUserRole.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            uemUserRoleList.add(uemUserRole);
        }
        QUemUserRole.uemUserRole.save(uemUserRoleList);
    }

    /**
     * 根据用户VO设置用户信息
     * @param uemUserOperateVO 用户VO
     * @param uemUser 用户信息
     */
    private void setUemUserByOperateVO(UemUserOperateVO uemUserOperateVO, UemUser uemUser) {
//        // 新增用户，设置默认新增信息
//        if (Objects.equals(uemUserOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.INSERT.getCode())) {
//            // 用户名是已存在，若已存在，添加前缀 GJIM_
//            UemUser loginNo = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).where(QUemUser.account.eq$(uemUserOperateVO.getLoginNo())).execute();
//            if (Objects.isNull(loginNo)) {
//                uemUser.setAccount(uemUserOperateVO.getLoginNo());
//            } else {
//                uemUser.setAccount(GlobalConstant.PREFIX_GJIM + uemUserOperateVO.getLoginNo());
//            }
//            // 审核状态：默认审核通过
//            uemUser.setAuditStatus(GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode());
//            uemUser.setAuditTime(new Date());
//            // 用户类型-普通用户
//            uemUser.setUserType(GlobalEnum.UserType.GENERAL_USER.getCode());
//            // 同意协议
//            uemUser.setIsAgreemeent(true);
//            // 密码
//            uemUser.setPassword("");
//            // 来源：2
//            uemUser.setSource(GlobalEnum.UserSource.NTIP.getCode());
//        }
//        // 手机号
//        if (StringUtils.isBlank(uemUserOperateVO.getStaffPhone())) {
//            uemUser.setMobile("");
//        } else {
//            uemUser.setMobile(uemUserOperateVO.getStaffPhone());
//        }
//        // 固定电话
//        uemUser.setTelephone(uemUserOperateVO.getFixphone());
//        // 邮箱
//        uemUser.setEmail(uemUserOperateVO.getStaffMail());
//        // 所属组织机构代码
//        uemUser.setOrgCode(uemUserOperateVO.getOrgId());
//        // 所属企业不一致，更新
//        UemCompany uemCompany = QUemCompany.uemCompany.selectOne(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemUserOperateVO.getOrgId())).execute();
//        if (!Objects.equals(uemCompany.getBelongCompany(), uemCompany.getUemCompanyId())) {
//            uemUser.setBlindCompanny(uemCompany.getUemCompanyId());
//            uemUser.setBlindCompannyTime(new Date());
//        }
//
//        // 是否修改启用禁用状态
//        Boolean invalid = GlobalEnum.StaffStatusEnum.getInvalidByCode(uemUserOperateVO.getStaffStatus());
//        if (Objects.nonNull(invalid) && !Objects.equals(invalid, uemUser.getIsValid())) {
//            // 修改
//            uemUser.setIsValid(invalid);
//            uemUser.setInvalidTime(new Date());
//        }
//        // 用户姓名
//        uemUser.setName(uemUserOperateVO.getStaffName());
//        // 员工岗位
//        uemUser.setStaffDuty(uemUserOperateVO.getStaffDuty());
//        // 员工级别
//        uemUser.setStaffLevel(uemUserOperateVO.getStaffLvl());
//        // 排序
//        uemUser.setSeqNo(uemUserOperateVO.getSeqNo());
    }

    /**
     * 校验用户数据非空
     * @param uemUserOperateVO 用户
     * @return 校验结果
     */
    private String validOperateUemUserNotNull(UemUserOperateVO uemUserOperateVO) {
        // 校验必填
        if (StringUtils.isBlank(uemUserOperateVO.getAccessKey())) {
            return "Accesskey不能为空";
        }
        if (StringUtils.isBlank(uemUserOperateVO.getOptionType())) {
            return "OptionType不能为空";
        }
        if (StringUtils.isBlank(uemUserOperateVO.getLoginNo())) {
            return "LoginNo不能为空";
        }
        if (StringUtils.isBlank(uemUserOperateVO.getStaffCode())) {
            return "StaffCode不能为空";
        }
        if (StringUtils.isBlank(uemUserOperateVO.getStaffName())) {
            return "StaffName不能为空";
        }
        if (StringUtils.isBlank(uemUserOperateVO.getOrgId())) {
            return "OrgId不能为空";
        }
        if (StringUtils.isBlank(uemUserOperateVO.getStaffStatus())) {
            return "StaffStatus不能为空";
        }
        if (StringUtils.isBlank(uemUserOperateVO.getSeqNo())) {
            return "SeqNo不能为空";
        }
        if (Objects.isNull(uemUserOperateVO.getTime())) {
            return "Time不能为空";
        }
        return null;
    }

    /**
     *  校验用户数据
     * @param uemUserOperateVO 用户
     * @return 校验结果
     */
    private String validOperateUemUser(UemUserOperateVO uemUserOperateVO) {
        // 校验非空
        String validOperateUemUserNotNull = this.validOperateUemUserNotNull(uemUserOperateVO);
        if (StringUtils.isNotBlank(validOperateUemUserNotNull)) {
            return validOperateUemUserNotNull;
        }
        // 校验AccessKey，OrgCode、Time、Key拼接后的字符串经过MD5加密
        String accessKeyDecode = uemUserOperateVO.getLoginNo() + uemUserOperateVO.getTime() + aesSecretKey;
        String accessKeyEncode = MD5EnCodeUtils.MD5EnCode(accessKeyDecode);
        if (!StringUtils.equals(accessKeyEncode, uemUserOperateVO.getAccessKey())) {
            return "Accesskey值不正确";
        }
        // 校验OptionType
        String optionTypeName = GlobalEnum.OptionTypeEnum.getNameByCode(uemUserOperateVO.getOptionType());
        if (StringUtils.isBlank(optionTypeName)) {
            return "OptionType值不正确";
        }
        // 校验OrgCode
        // 新增组织机构，校验LoginNo唯一、移动电话StaffPhone是否唯一、邮箱StaffMail是否唯一
        String validOperateUemUserInsert = this.validOperateUemUserInsert(uemUserOperateVO);
        if (StringUtils.isNotBlank(validOperateUemUserInsert)) {
            return validOperateUemUserInsert;
        }
        // 修改组织机构，校验LoginNo是否存在、移动电话StaffPhone是否唯一、邮箱StaffMail是否唯一
        String validOperateUemUserUpdate = this.validOperateUemUserUpdate(uemUserOperateVO);
        if (StringUtils.isNotBlank(validOperateUemUserUpdate)) {
            return validOperateUemUserUpdate;
        }
        // 删除组织机构，校验OrgCode是否存在、移动电话StaffPhone是否唯一、邮箱StaffMail是否唯一
        if (Objects.equals(uemUserOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.DELETE.getCode())) {
            List<UemUser> uemUserList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.source.eq$(GlobalEnum.UserSource.NTIP.getCode()).and(QUemUser.account.eq$(uemUserOperateVO.getLoginNo()).or(QUemUser.account.eq$(GlobalConstant.PREFIX_GJIM + uemUserOperateVO.getLoginNo())))).execute();
            if (CollectionUtils.isEmpty(uemUserList)) {
                return "OptionType为3时，LoginNo不存在数据";
            }
            // 因LoginNo值唯一，删除最大记录数量不能大于1
            int deleteCompanyMaxSize = 1;
            if (uemUserList.size() > deleteCompanyMaxSize) {
                return "OptionType为3时，LoginNo存在多条数据";
            }
        }
        // 校验组织代码OrgId
        List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemUserOperateVO.getOrgId())).execute();
        if (CollectionUtils.isEmpty(uemCompanyList)) {
            return "OrgId值不正确，不存在该组织机构";
        }
        // 校验StaffStatus
        String staffStatusName = GlobalEnum.StaffStatusEnum.getNameByCode(uemUserOperateVO.getStaffStatus());
        if (StringUtils.isBlank(staffStatusName)) {
            return "StaffStatus值不正确";
        }
        return null;
    }

    /**
     *  校验用户新增
     * @param uemUserOperateVO 用户
     * @return 校验结果
     */
    private String validOperateUemUserInsert(UemUserOperateVO uemUserOperateVO) {
        // 新增用户，校验LoginNo唯一、移动电话StaffPhone是否唯一、邮箱StaffMail是否唯一
        if (Objects.equals(uemUserOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.INSERT.getCode())) {
            List<UemUser> loginNoList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.account.eq$(uemUserOperateVO.getLoginNo()).or(QUemUser.account.eq$(GlobalConstant.PREFIX_GJIM + uemUserOperateVO.getLoginNo()))).execute();
            if (CollectionUtils.isNotEmpty(loginNoList)) {
                return "OptionType为1时，LoginNo不能重复";
            }
            // 校验手机、邮箱是否唯一
            return this.validPhoneAndMailInsert(uemUserOperateVO);
        }
        return null;
    }

    /**
     * 新增时，校验手机、邮箱是否唯一
     * @param uemUserOperateVO 用户
     * @return 校验结果
     */
    private String validPhoneAndMailInsert(UemUserOperateVO uemUserOperateVO) {
        if (StringUtils.isNotBlank(uemUserOperateVO.getStaffPhone())) {
            List<UemUser> userPhoneList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.mobile.eq$(uemUserOperateVO.getStaffPhone())).execute();
            if (CollectionUtils.isNotEmpty(userPhoneList)) {
                return GlobalConstant.VALID_STAFF_PHONE_DUPLICATE;
            }
            List<SysPlatformUser> platformUserPhoneList = QSysPlatformUser.sysPlatformUser.select(QSysPlatformUser.sysPlatformUser.fieldContainer()).where(QSysPlatformUser.tel.eq$(uemUserOperateVO.getStaffPhone())).execute();
            if (CollectionUtils.isNotEmpty(platformUserPhoneList)) {
                return GlobalConstant.VALID_STAFF_PHONE_DUPLICATE;
            }
        }
        if (StringUtils.isNotBlank(uemUserOperateVO.getStaffMail())) {
            List<UemUser> userMailList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.email.eq$(uemUserOperateVO.getStaffMail())).execute();
            if (CollectionUtils.isNotEmpty(userMailList)) {
                return GlobalConstant.VALID_STAFF_MAIL_DUPLICATE;
            }
            List<SysPlatformUser> platformUserMailList = QSysPlatformUser.sysPlatformUser.select(QSysPlatformUser.sysPlatformUser.fieldContainer()).where(QSysPlatformUser.mail.eq$(uemUserOperateVO.getStaffMail())).execute();
            if (CollectionUtils.isNotEmpty(platformUserMailList)) {
                return GlobalConstant.VALID_STAFF_MAIL_DUPLICATE;
            }
        }
        return null;
    }

    /**
     *  校验用户修改
     * @param uemUserOperateVO 用户
     * @return 校验结果
     */
    private String validOperateUemUserUpdate(UemUserOperateVO uemUserOperateVO) {
        // 修改组织机构，校验LoginNo是否存在、移动电话StaffPhone是否唯一、邮箱StaffMail是否唯一
        if (Objects.equals(uemUserOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.UPDATE.getCode())) {
            List<UemUser> uemUserList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.source.eq$(GlobalEnum.UserSource.NTIP.getCode()).and(QUemUser.account.eq$(uemUserOperateVO.getLoginNo()).or(QUemUser.account.eq$(GlobalConstant.PREFIX_GJIM + uemUserOperateVO.getLoginNo())))).execute();
            if (CollectionUtils.isEmpty(uemUserList)) {
                return "OptionType为2时，LoginNo不存在数据";
            }
            // 因LoginNo值唯一，删除最大记录数量不能大于1
            int updateCompanyMaxSize = 1;
            if (uemUserList.size() > updateCompanyMaxSize) {
                return "OptionType为2时，LoginNo存在多条数据";
            }
            String loginNo = uemUserList.get(0).getAccount();
            return this.validPhoneAndMailUpdate(uemUserOperateVO, loginNo);
        }
        return null;
    }

    /**
     * 修改时，校验手机、邮箱是否唯一
     * @param uemUserOperateVO 用户
     * @return 校验结果
     */
    private String validPhoneAndMailUpdate(UemUserOperateVO uemUserOperateVO, String loginNo) {
        if (StringUtils.isNotBlank(uemUserOperateVO.getStaffPhone())) {
            List<UemUser> staffPhoneList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.account.ne$(loginNo).and(QUemUser.mobile.eq$(uemUserOperateVO.getStaffPhone()))).execute();
            if (CollectionUtils.isNotEmpty(staffPhoneList)) {
                return GlobalConstant.VALID_STAFF_PHONE_DUPLICATE;
            }
            List<SysPlatformUser> platformUserPhoneList = QSysPlatformUser.sysPlatformUser.select(QSysPlatformUser.sysPlatformUser.fieldContainer()).where(QSysPlatformUser.account.ne$(loginNo).and(QSysPlatformUser.tel.eq$(uemUserOperateVO.getStaffPhone()))).execute();
            if (CollectionUtils.isNotEmpty(platformUserPhoneList)) {
                return GlobalConstant.VALID_STAFF_PHONE_DUPLICATE;
            }
        }
        if (StringUtils.isNotBlank(uemUserOperateVO.getStaffMail())) {
            List<UemUser> staffMailList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.account.ne$(loginNo).and(QUemUser.email.eq$(uemUserOperateVO.getStaffMail()))).execute();
            if (CollectionUtils.isNotEmpty(staffMailList)) {
                return GlobalConstant.VALID_STAFF_MAIL_DUPLICATE;
            }
            List<SysPlatformUser> platformUserMailList = QSysPlatformUser.sysPlatformUser.select(QSysPlatformUser.sysPlatformUser.fieldContainer()).where(QSysPlatformUser.account.ne$(loginNo).and(QSysPlatformUser.mail.eq$(uemUserOperateVO.getStaffMail()))).execute();
            if (CollectionUtils.isNotEmpty(platformUserMailList)) {
                return GlobalConstant.VALID_STAFF_MAIL_DUPLICATE;
            }
        }
        return null;
    }

}
