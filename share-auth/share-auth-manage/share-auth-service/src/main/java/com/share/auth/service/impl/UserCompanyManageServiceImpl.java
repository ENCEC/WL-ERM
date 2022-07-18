package com.share.auth.service.impl;


import cn.hutool.core.date.DateUtil;
import com.gillion.ds.client.api.queryobject.command.SelectPageFromModelCommand;
import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.ec.core.security.IUser;
import com.gillion.exception.BusinessRuntimeException;
import com.gillion.saas.redis.SassRedisInterface;
import com.google.common.collect.ImmutableMap;
import com.share.auth.constants.CodeFinal;
import com.share.auth.constants.EsConstant;
import com.share.auth.constants.GlobalConstant;
import com.share.auth.domain.UemLogDto;
import com.share.auth.domain.UemUserCompanyDto;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserRoleDto;
import com.share.auth.enums.GlobalEnum;
import com.share.auth.model.entity.*;
import com.share.auth.model.querymodels.*;
import com.share.auth.service.MsgSendService;
import com.share.auth.service.UemUserService;
import com.share.auth.service.UserCompanyManageService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.MessageUtil;
import com.share.auth.util.PasswordUtils;
import com.share.file.api.ShareFileInterface;
import com.share.file.domain.FileInfoVO;
import com.share.message.api.EmailTemplateService;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgSmsApiVO;
import com.share.message.domain.SendEmailVO;
import com.share.message.domain.SendMsgReturnVo;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import com.share.support.util.AES128Util;
import com.share.support.util.MD5EnCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xrp
 * @Date 20201021
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserCompanyManageServiceImpl implements UserCompanyManageService {

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

    /**
     * 邮件过期时间30分钟
     */
    private static final Integer MAIL_EXPIRE_TIME = 1800;
    /**
     * 当前登录用户信息失败提示
     */
    private static final String GET_LOGIN_USER_INFO_FAIL_PROMPT = "获取登录用户信息失败";
    /**
     * 应用名称(字段名称)
     */
    private static final String APPLICATION_NAME = "applicationName";
    /**
     * 账户(字段名称)
     */
    private static final String ACCOUNT = "account";
    /**
     * 手机(字段名称)
     */
    private static final String MOBILE = "mobile";
    /**
     * 关联企业id(字段名称)
     */
    private static final String UEM_COMPANY_ID = "uemCompanyId";
    /**
     * 关联用户id(字段名称)
     */
    private static final String UEM_USER_ID = "uemUserId";
    /**
     * 企业id(字段名称)
     */
    private static final String COMPANY_ID = "companyId";
    /**
     * 用户类型(字段名称)
     */
    private static final String USER_TYPE = "userType";

    /**
     * 结束时间(数据服务查询占位符)
     */
    private static final String END_TIME_PLACEHOLDER = ":endTime";
    /**
     * 关联企业id(数据服务查询占位符)
     */
    private static final String UEM_COMPANY_ID_PLACEHOLDER = ":uemCompanyId";
    /**
     * 关联用户id(数据服务查询占位符)
     */
    private static final String UEM_USER_ID_PLACEHOLDER = ":uemUserId";
    /**
     * 企业id(数据服务查询占位符)
     */
    private static final String COMPANY_ID_PLACEHOLDER = ":companyId";

    @Value("${msg.httpUrl}")
    private String authUrl;

    /**
     * 管理员新增用户短信模版编号
     */
    @Value("${msg.sms.template.manage_add_user}")
    private String manageAddUserTemplate;

    /**
     * 绑定邮箱模版编号
     */
    @Value("${msg.email.template.binding_emil}")
    private String bindEmailTemplate;

    /**
     * 修改邮箱模版编号
     */
    @Value("${msg.email.template.change_email}")
    private String changeEmailTemplate;
    /**
     * 绑定企业审核模版
     */
    @Value("${msg.sms.template.blind_company}")
    private String blindCompanyTemplate;

    @Value("${aes_secret_key}")
    private String aesSecretKey;

    /**
     * 绑定企业审核模版
     */
    @Value("${msg.sms.template.permission_distribute}")
    private String permissionDistribute;

    @Autowired
    private ShareFileInterface shareFileInterface;

    @Autowired
    private EmailTemplateService emailTemplateService;
    @Autowired
    private MsgApiService msgApiService;

    @Autowired
    private SassRedisInterface sassRedisInterface;

    @Autowired
    private DefaultUserService userService;

    @Autowired
    private UemUserService uemUserService;

    @Autowired
    private MsgSendService msgSendService;

    /*@Value("${elasticsearch.enable}")
    private Boolean enableES;*/

    /**
     * 企业用户管理
     *
     * @param uemUserDto 用户表封装类
     * @return Page<UemUserDto>
     * @author xrp
     */
    @Override
    public Page<UemUserDto> queryUserCompanyManage(UemUserDto uemUserDto) {
        //企业ID
        String blindCompanny = uemUserDto.getBlindCompanny();
        if (!StringUtils.isEmpty(uemUserDto.getAccount())) {
            uemUserDto.setAccount("%" + uemUserDto.getAccount() + "%");
        }
        if (!StringUtils.isEmpty(uemUserDto.getName())) {
            uemUserDto.setName("%" + uemUserDto.getName() + "%");
        }
        List<UemUserCompany> uemUserCompanyList = QUemUserCompany.uemUserCompany
                .select(QUemUserCompany.uemUserCompany.fieldContainer())
                .where
                        (QUemUserCompany.uemCompanyId.eq$(Long.valueOf(blindCompanny))
                                .and(QUemUserCompany.entryTime.notNull())
                                .and(QUemUserCompany.quitTime.isNull())
                                .and(QUemUserCompany.auditStatus.ne$(CodeFinal.ACTIVE_TYPE_TWO)))
                .execute();

        log.info("uemUserCompanyList:" + uemUserCompanyList);

        Set<Long> userIdList2;
        if (!CollectionUtils.isEmpty(uemUserCompanyList)) {
            //查出绑定表中用户ID
            userIdList2 = uemUserCompanyList.stream().map(UemUserCompany::getUemUserId).collect(Collectors.toSet());
            AndExpression expression = QUemUser.account.like$(uemUserDto.getAccount())
                    .and(QUemUser.name.like$(uemUserDto.getName()))
                    .and(QUemUser.source.eq$(uemUserDto.getSource()))
                    .and(QUemUser.uemUserCompany.chain(QUemUserCompany.quitTime).isNull())
                    .and(QUemUser.oriApplication.eq$(uemUserDto.getOriApplication()))
                    .and(QUemUser.uemUser.chain(QSysApplication.applicationCode).eq$(uemUserDto.getApplicationName()))
                    .and(QUemUser.isValid.eq$(uemUserDto.getIsValid()))
                    .and(QUemUser.uemUserId.in$(userIdList2));
            log.info("userIdList2:" + userIdList2);
            if (!CollectionUtils.isEmpty(userIdList2)) {
                /*绑定表绑定时间不为空*/
                return QUemUser.uemUser
                        .select(QUemUser.uemUserId,
                                QUemUser.account,
                                QUemUser.name,
                                QUemUser.mobile,
                                QUemUser.email,
                                QUemUser.isValid,
                                QUemUser.invalidTime,
                                QUemUser.sysApplication.chain(QSysApplication.applicationName).as("applicationName"),
                                QUemUser.uemUserCompany.chain(QUemUserCompany.auditStatus).as("blindStatus"),
                                QUemUser.source
                        ).where(expression)
                        .paging((uemUserDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserDto.getPageNo(), (uemUserDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserDto.getPageSize())
                        .sorting(QUemUser.createTime.desc())
                        .mapperTo(UemUserDto.class)
                        .execute();
            }

        }
        return null;

    }

    /**
     * 查询企业解绑用户
     *
     * @param uemUserCompanyDto 解绑表封装类
     * @return Page<UemUserDto>
     * @author xrp
     */
    @Override
    public Page<UemUserCompanyDto> queryUnBindUser(UemUserCompanyDto uemUserCompanyDto) {

        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            // 全局异常处理
            throw new RuntimeException(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        //企业ID
        Long blindCompanny = userInfoModel.getBlindCompanny();
        //用户名
        String account = uemUserCompanyDto.getAccount();
        //姓名
        String name = uemUserCompanyDto.getName();
        if (!StringUtils.isEmpty(account)) {
            uemUserCompanyDto.setAccount("%" + account + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            uemUserCompanyDto.setName("%" + name + "%");
        }

        //绑定表解除绑定时间不为空
        return QUemUserCompany.uemUserCompany
                .select(QUemUserCompany.uemUserCompany.chain(QUemUser.account).as(ACCOUNT),
                        QUemUserCompany.uemUserCompany.chain(QUemUser.name).as("name"),
                        QUemUserCompany.uemUserCompany.chain(QUemUser.mobile).as(MOBILE),
                        QUemUserCompany.uemUserCompany.chain(QUemUser.source).as("source"),
                        QUemUserCompany.uemUserCompany.chain(QUemUser.invalidTime).as("invalidTime"),
                        QUemUserCompany.quitTime,
                        QUemUserCompany.uemUserCompany.chain(QUemUser.uemUser).chain(QSysApplication.applicationName).as(APPLICATION_NAME))
                .where(QUemUserCompany.uemUserCompany.chain(QUemUser.account).like(":account")
                        .and(QUemUserCompany.uemUserCompany.chain(QUemUser.name).like(":name"))
                        .and(QUemUserCompany.quitTime.gt(":startTime"))
                        .and(QUemUserCompany.quitTime.lt(END_TIME_PLACEHOLDER))
                        .and(QUemUserCompany.quitTime.notNull())
                        .and(QUemUserCompany.uemCompanyId.eq$(blindCompanny)))
                .paging((uemUserCompanyDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserCompanyDto.getPageNo(), (uemUserCompanyDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserCompanyDto.getPageSize())
                .sorting(QUemUserCompany.quitTime.desc())
                .mapperTo(UemUserCompanyDto.class)
                .execute(uemUserCompanyDto);

    }

    /**
     * 新增企业用户
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> saveUemUser(UemUserDto uemUserDto) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData("登录用户信息为空，请重新登录");
        }
        //管理员用户ID
        String uemUserId = userInfoModel.getUemUserId().toString();
        if (StringUtils.isEmpty(uemUserId)) {
            return CommonResult.getFaildResultData("管理员用户Id不能为空");
        }

        //手机号
        String mobile = uemUserDto.getMobile();
        //用户名
        String account = uemUserDto.getAccount();
        //密码
        String password = uemUserDto.getPassword();
        //绑定企业
        Long blindCompanny = userInfoModel.getBlindCompanny();
        // 校验
        ResultHelper<Object> resultHelper = this.validUemUserDto(uemUserDto);
        if (Boolean.FALSE.equals(resultHelper.getSuccess())) {
            // 校验失败
            return resultHelper;
        }
        // 解密
        String decPassword = "";
        try {
            decPassword = AES128Util.decrypt(password, aesSecretKey);
        } catch (Exception e) {
            return CommonResult.getFaildResultData("密码解密失败！");
        }
        // 加密
        password = MD5EnCodeUtils.MD5EnCode(decPassword).substring(8, 24);

        UemUser uemUser = new UemUser();
        // 二次加密
        password = MD5EnCodeUtils.MD5EnCode(password);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        uemUser.setAccount(account);
        uemUser.setMobile(mobile);
        uemUser.setPassword(password);
        uemUser.setUserType(CodeFinal.USER_TYPE_ONE);
        uemUser.setSource(GlobalEnum.UserSource.ADMIN_ADD.getCode());
        uemUser.setIsValid(true);
        uemUser.setIsAgreemeent(true);
        uemUser.setBlindCompanny(blindCompanny);
        uemUser.setBlindCompannyTime(new Date());
        uemUser.setInvalidTime(new Date());
        int saveCount = QUemUser.uemUser.save(uemUser);

        UemUserCompany uemUserCompany = new UemUserCompany();
        uemUserCompany.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        uemUserCompany.setUemUserId(uemUser.getUemUserId());
        uemUserCompany.setUemCompanyId(blindCompanny);
        uemUserCompany.setEntryTime(new Date());
        uemUserCompany.setUserRole(false);
        uemUserCompany.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
        int saveUemUserCompany = QUemUserCompany.uemUserCompany.save(uemUserCompany);
        // 赋予默认角色
        uemUserService.defaultUemUserRole(uemUser.getUemUserId(), blindCompanny);
        log.info("用户表ID:" + uemUser.getUemUserId().toString());

        if (saveCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM && saveUemUserCompany > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            // 短信发送vo
            MsgSmsApiVO msgSmsApiVO = this.setMsgSmsApiVO(account, decPassword, mobile, userInfoModel);
            log.info("短信消息宏参数传参： " + msgSmsApiVO.getMarco().toString());
            // 调用公共服务接口发送短信
            SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
            if (CodeFinal.SUCCESSEMAIL.equals(sendMsgReturnVo.getResultCode())) {
                return CommonResult.getSuccessResultData("新增成功");
            } else {
                log.info("短信发送失败，失败原因：" + sendMsgReturnVo.getResultMsg());
                throw new BusinessRuntimeException("短信发送失败，请联系客服！");
            }
        } else {
            return CommonResult.getFaildResultData("新加失败");
        }
    }

    /**
     * 校验用户信息
     *
     * @param uemUserDto 用户信息
     * @return 校验结果
     */
    private ResultHelper<Object> validUemUserDto(UemUserDto uemUserDto) {
        //手机号
        String mobile = uemUserDto.getMobile();
        //用户名
        String account = uemUserDto.getAccount();
        //密码
        String password = uemUserDto.getPassword();
        // 校验手号
        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.mobile.eq(":mobile"))
                .execute(ImmutableMap.of(MOBILE, mobile));
        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser.select().where(
                QSysPlatformUser.tel.eq$(mobile)).execute();

        if (CollectionUtils.isNotEmpty(uemUserList) || CollectionUtils.isNotEmpty(sysPlatformUserList)) {
            return CommonResult.getFaildResultData("该手机号已注册过！");
        }
        // 校验账号
        List<UemUser> accountUser = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(
                QUemUser.account.eq$(account)
        ).execute();
        List<SysPlatformUser> accountSysPlatformUser = QSysPlatformUser.sysPlatformUser.select().where(
                QSysPlatformUser.account.eq$(account)).execute();
        if (CollectionUtils.isNotEmpty(accountUser) || CollectionUtils.isNotEmpty(accountSysPlatformUser)) {
            return CommonResult.getFaildResultData("用户名已存在！");
        }
        // 校验密码
        try {
            String decPassword = AES128Util.decrypt(password, aesSecretKey);
            if (!PasswordUtils.matchersPassword(decPassword)) {
                return CommonResult.getFaildResultData("密码安全性不足！");
            }
        } catch (Exception e) {
            return CommonResult.getFaildResultData("密码解密失败！");
        }
        // 校验通过
        return CommonResult.getSuccessResultData();
    }

    /**
     * 设置短信推送VO内容
     *
     * @param account       账号
     * @param decPassword   解密密码
     * @param mobile        手机号
     * @param userInfoModel 当前用户
     * @return 短信推送VO内容
     */
    private MsgSmsApiVO setMsgSmsApiVO(String account, String decPassword, String mobile, AuthUserInfoModel userInfoModel) {
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(mobile);
        // 消息宏参数mgp
        Map<String, Object> marco = new HashMap<>(16);
        // 短信内容变量
        Map<String, Object> content = new HashMap<>(16);
        // 公司名称
        UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(userInfoModel.getBlindCompanny());
        content.put("companyName", uemCompany.getCompanyNameCn());
        // 管理员真实姓名
        content.put("name", userInfoModel.getName());
        // 用户登录账号
        content.put(ACCOUNT, account);
        // 用户登录密码
        content.put("password", decPassword);
        // 账号权限系统url
        content.put("authUrl", authUrl);
        marco.put(CodeFinal.CONTENT, content);
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);
        msgSmsApiVO.setSmsTemplateCode(manageAddUserTemplate);
        return msgSmsApiVO;
    }

    /**
     * 启停
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> startStop(UemUserDto uemUserDto) {
        //用户ID
        String uemUserId = uemUserDto.getUemUserId();
        //是否禁用(0禁用,1启用)
        Boolean isValid = uemUserDto.getIsValid();

        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(Long.valueOf(uemUserId));
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        if (Objects.isNull(uemUser) || !uemUser.getBlindCompanny().equals(userInfoModel.getBlindCompanny())) {
            return CommonResult.getFaildResultData("启停用户id与当前登录用户不在统一企业，查询失败！");
        }
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setInvalidTime(new Date());
        uemUser.setIsValid(isValid);
        int updateCount = QUemUser.uemUser.save(uemUser);
        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("启停成功");
        } else {
            return CommonResult.getFaildResultData("启停失败");
        }

    }

    /**
     * @Author:chenxf
     * @Description: 个人中心用户解除绑定和企业用户管理解除绑定抽取公共代码
     * @Date: 15:10 2021/1/11
     * @Param: [uemUserId, uemCompanyId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public ResultHelper<Object> unbindUser(String uemUserId, String uemCompanyId) {
        if (uemUserId == null) {
            return CommonResult.getFaildResultData("用户ID为空！");
        }

        if (StringUtils.isEmpty(uemCompanyId)) {
            return CommonResult.getFaildResultData("企业ID为空");
        }
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        UemUser loginUser = QUemUser.uemUser.selectOne().byId(userInfoModel.getUemUserId());
        if (!uemCompanyId.equals(loginUser.getBlindCompanny().toString())) {
            return CommonResult.getFaildResultData("无法解绑其它企业的用户");
        }

        List<UemUserCompany> uemUserCompanies = QUemUserCompany.uemUserCompany
                .select(QUemUserCompany.uemUserCompany.fieldContainer())
                .where(QUemUserCompany.uemCompanyId.eq(UEM_COMPANY_ID_PLACEHOLDER)
                        .and(QUemUserCompany.userRole.eq$(true))
                        .and(QUemUserCompany.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ONE))
                        .and(QUemUserCompany.quitTime.isNull()))
                .execute(ImmutableMap.of(UEM_COMPANY_ID, uemCompanyId));

        if (!CollectionUtils.isEmpty(uemUserCompanies)) {
            List<Long> uemCompanyManagerList1 = uemUserCompanies.stream().map(UemUserCompany::getUemUserId).distinct().collect(Collectors.toList());
            if (CodeFinal.SIZE == uemCompanyManagerList1.size() && uemCompanyManagerList1.contains(Long.valueOf(uemUserId))) {
                return CommonResult.getFaildResultData("该用户是企业唯一管理员，请先通知其他用户成为管理员再解除绑定");
            }
        }

        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(Long.valueOf(uemUserId));
        if (!uemUser.getBlindCompanny().equals(loginUser.getBlindCompanny())) {
            return CommonResult.getFaildResultData("该用户绑定的企业与当前登录用户绑定企业不同，无法解绑");
        }
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setBlindCompanny(null);
        uemUser.setBlindCompannyTime(null);
        uemUser.setUserType(CodeFinal.USER_TYPE_ZERO);
        int updateCount = QUemUser.uemUser.save(uemUser);

        int updateUemUserCompany = QUemUserCompany.uemUserCompany.update(QUemUserCompany.quitTime)
                .where(QUemUserCompany.uemUserId.eq(UEM_USER_ID_PLACEHOLDER)
                        .and(QUemUserCompany.uemCompanyId.eq(UEM_COMPANY_ID_PLACEHOLDER))
                        .and(QUemUserCompany.quitTime.isNull()))
                .execute(new Date(), uemUserId, uemCompanyId);

        QUemUserRole.uemUserRole.delete().where(QUemUserRole.uemUserId.eq$(Long.valueOf(uemUserId))).execute();

        //删除认证函文件
        List<UemCompanyManager> uemCompanyManagers = QUemCompanyManager.uemCompanyManager
                .select(QUemCompanyManager.uemCompanyManager.fieldContainer())
                .where(QUemCompanyManager.uemUserId.eq(UEM_USER_ID_PLACEHOLDER)
                        .and(QUemCompanyManager.uemCompanyId.eq(UEM_COMPANY_ID_PLACEHOLDER))
                        .and(QUemCompanyManager.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ZERO)))
                .execute(ImmutableMap.of(UEM_USER_ID, uemUserId, UEM_COMPANY_ID, uemCompanyId));
        if (!CollectionUtils.isEmpty(uemCompanyManagers)) {
            //使用方系统ID
            String systemId = MessageUtil.getApplicationCode();
            FileInfoVO fileInfoVO = new FileInfoVO();
            fileInfoVO.setSystemId(systemId);
            fileInfoVO.setFileKey(uemCompanyManagers.get(0).getFileUrlId());
            shareFileInterface.deleteFile(fileInfoVO);
        }
        //解除绑定，需要把待审批的管理员申请记录（含上传的认证函）删除

        QUemCompanyManager.uemCompanyManager.delete().where(QUemCompanyManager.uemUserId.eq(UEM_USER_ID_PLACEHOLDER)
                .and(QUemCompanyManager.uemCompanyId.eq(UEM_COMPANY_ID_PLACEHOLDER)))
                .execute(ImmutableMap.of(UEM_USER_ID, uemUserId, UEM_COMPANY_ID, uemCompanyId));

        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM && updateUemUserCompany > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            // 更新缓存中的用户信息
            userService.updateCurrentLoginUser();
            return CommonResult.getSuccessResultData("解绑成功！");
        } else {
            return CommonResult.getFaildResultData("解绑失败！");
        }
    }

    /**
     * 权限分配
     *
     * @param uemUserId 用户表ID
     * @return List<UemUserRoleDto>
     * @author xrp
     */
    @Override
    public ResultHelper<List<UemUserRoleDto>> authorityAssignment(String uemUserId) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            // 全局异常处理
            throw new RuntimeException(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        // 非平台客服、非国交管理员分配权限，需要校验用户企业
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(userInfoModel.getUemUserId());
        if (Objects.isNull(sysPlatformUser) && !Objects.equals(userInfoModel.getUserType(), GlobalEnum.UserType.IMPT_ADMIN.getCode())) {
            UemUser uemUser = QUemUser.uemUser.selectOne().byId(Long.valueOf(uemUserId));
            if (Objects.isNull(uemUser) || !uemUser.getBlindCompanny().equals(userInfoModel.getBlindCompanny())) {
                // 全局异常处理
                throw new RuntimeException("权限分配用户id与当前登录用户不在同一企业，请重新登录！");
            }
        }
        List<UemUserRoleDto> uemUserDtoList = QUemUserRole.uemUserRole
                .select(QUemUserRole.isValid,
                        QUemUserRole.uemUserRoleId,
                        QUemUserRole.sysApplicationId,
                        QUemUserRole.sysRoleId,
                        QUemUserRole.uemUserRole.chain(QSysApplication.sysApplicationId).as("sysApplicationId"),
                        QUemUserRole.invalidTime,
                        QUemUserRole.uemUserRole.chain(QSysApplication.applicationName).as(APPLICATION_NAME),
                        QUemUserRole.uemUserRole.chain(QSysRole.roleName).as("roleName"))
                .where(QUemUserRole.uemUserId.eq(UEM_USER_ID_PLACEHOLDER)
                        .and(QUemUserRole.isValid.eq(":isValid")))
                .mapperTo(UemUserRoleDto.class)
                .execute(ImmutableMap.of(UEM_USER_ID, uemUserId));

        log.info("uemUserDtoList:" + uemUserDtoList);
        return CommonResult.getSuccessResultData(uemUserDtoList);
    }

    /**
     * 权限分配 获取详情
     *
     * @param uemUserRoleId 用户角色表ID
     * @return List<UemUserRoleDto>
     * @author xrp
     */
    @Override
    public List<UemUserRoleDto> getAuthorityAssignment(String uemUserRoleId) {


        List<UemUserRoleDto> uemUserRoleDtoList = QUemUserRole.uemUserRole
                .select(QUemUserRole.uemUserRole.chain(QSysApplication.applicationName).as(APPLICATION_NAME),
                        QUemUserRole.uemUserRole.chain(QSysRole.roleName).as("roleName"))
                .where(QUemUserRole.uemUserRoleId.eq(":uemUserRoleId"))
                .mapperTo(UemUserRoleDto.class)
                .execute(ImmutableMap.of("uemUserRoleId", uemUserRoleId));

        log.info("uemUserRoleDtoList:" + uemUserRoleDtoList);
        return uemUserRoleDtoList;
    }

    /**
     * 权限分配 修改
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> updateAuthorityAssignment(UemUserRoleDto uemUserRoleDto) {
        //角色管理表ID
        String sysRoleId = uemUserRoleDto.getSysRoleId();
        if (StringUtils.isEmpty(sysRoleId)) {
            return CommonResult.getFaildResultData("角色id不能为空");
        }
        if (StringUtils.isEmpty(uemUserRoleDto.getUemUserRoleId())) {
            return CommonResult.getFaildResultData("用户角色表id不能为空");
        }
        UemUserRole uemUserRole = QUemUserRole.uemUserRole.selectOne().byId(Long.valueOf(uemUserRoleDto.getUemUserRoleId()));
        SysRole sysRole = QSysRole.sysRole.selectOne().byId(Long.valueOf(sysRoleId));
        if (sysRoleId.equals(uemUserRole.getSysRoleId().toString())) {
            return CommonResult.getFaildResultData("角色未改变，无需更新");
        }
        if (Objects.isNull(sysRole) || !sysRole.getSysApplicationId().equals(uemUserRole.getSysApplicationId())) {
            return CommonResult.getFaildResultData("新角色不属于当前应用，请确认");
        }
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData("获取当前用户信息失败，请确认");
        }
        // 非平台客服、非国交管理员分配权限，需要校验用户企业
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(userInfoModel.getUemUserId());
        UemUser uemUser = null;
        if (Objects.isNull(sysPlatformUser) && !Objects.equals(userInfoModel.getUserType(), GlobalEnum.UserType.IMPT_ADMIN.getCode())) {
            uemUser = QUemUser.uemUser.selectOne().byId(uemUserRole.getUemUserId());
            if (Objects.isNull(uemUser) || !uemUser.getBlindCompanny().equals(userInfoModel.getBlindCompanny())) {
                return CommonResult.getFaildResultData("当前登录用户与被分配用户不在同一企业，无法分配权限");
            }
        }
        // 查询该用户该应用下是否已经有一样的角色
        List<UemUserRole> sysRoleList = QUemUserRole.uemUserRole.select().where(
                QUemUserRole.uemUserId.eq$(uemUserRole.getUemUserId())
                        .and(QUemUserRole.sysApplicationId.eq$(uemUserRole.getSysApplicationId()))
                        .and(QUemUserRole.sysRoleId.eq$(sysRole.getSysRoleId()))
                        .and(QUemUserRole.uemUserRoleId.ne$(uemUserRole.getUemUserRoleId()))
        ).execute();
        if (CollectionUtils.isNotEmpty(sysRoleList)) {
            return CommonResult.getFaildResultData("该用户该应用下已有该角色，请确认");
        }
        uemUserRole.setSysRoleId(sysRole.getSysRoleId());
        uemUserRole.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QUemUserRole.uemUserRole.save(uemUserRole);
        // 操作者和分配权限是同一个，就刷新当前用户缓存
        if (Objects.nonNull(uemUser) && Objects.equals(uemUser.getUemUserId(), userInfoModel.getUserId())) {
            userService.updateCurrentLoginUser();
        }
        return CommonResult.getSuccessResultData("修改成功");
    }

    /**
     * 权限分配 启停
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> startStopByAuthorityAssignment(UemUserRoleDto uemUserRoleDto) {

        //用户角色表ID
        String uemUserRoleId = uemUserRoleDto.getUemUserRoleId();

        Boolean isValid = uemUserRoleDto.getIsValid();

        String uemUserId = uemUserRoleDto.getUemUserId();
        if (StringUtils.isEmpty(uemUserRoleId)) {
            return CommonResult.getFaildResultData("用户角色表ID不能为空");
        }
        if (StringUtils.isEmpty(uemUserId)) {
            return CommonResult.getFaildResultData("用户表Id不能为空");
        }
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        // 非平台客服、非国交管理员分配权限，需要校验用户企业
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(userInfoModel.getUemUserId());
        if (Objects.isNull(sysPlatformUser) && !Objects.equals(userInfoModel.getUserType(), GlobalEnum.UserType.IMPT_ADMIN.getCode())) {
            UemUser uemUser = QUemUser.uemUser.selectOne().byId(Long.valueOf(uemUserId));
            if (Objects.isNull(uemUser) || !uemUser.getBlindCompanny().equals(userInfoModel.getBlindCompanny())) {
                return CommonResult.getFaildResultData("启停用户id与当前登录用户不在统一企业，操作失败！");
            }
        }

        UemUserRole uemUserRole = QUemUserRole.uemUserRole.selectOne(QUemUserRole.uemUserRole.fieldContainer()).byId(Long.valueOf(uemUserRoleId));
        uemUserRole.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);

        uemUserRole.setInvalidTime(new Date());
        uemUserRole.setIsValid(isValid);
        int updateCount = QUemUserRole.uemUserRole.save(uemUserRole);


        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("启停成功");
        } else {
            return CommonResult.getFaildResultData("启停失败");
        }

    }

    /**
     * 权限分配 新加
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> saveAuthorityAssignment(UemUserRoleDto uemUserRoleDto) {

        //应用管理表ID
        String sysApplicationId = uemUserRoleDto.getSysApplicationId();
        //用户角色表ID
        String uemUserRoleId = uemUserRoleDto.getUemUserRoleId();
        //用户表ID
        String uemUserId = uemUserRoleDto.getUemUserId();
        if (StringUtils.isEmpty(uemUserId)) {
            return CommonResult.getFaildResultData("用户Id不能为空");
        }
        if (StringUtils.isEmpty(uemUserRoleId)) {
            return CommonResult.getFaildResultData("用户角色表ID不能为空");
        }
        if (StringUtils.isEmpty(sysApplicationId)) {
            return CommonResult.getFaildResultData("应用管理表ID不能为空");
        }
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        // 非平台客服、非国交管理员分配权限，需要校验用户企业
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(userInfoModel.getUemUserId());
        if (Objects.isNull(sysPlatformUser) && !Objects.equals(userInfoModel.getUserType(), GlobalEnum.UserType.IMPT_ADMIN.getCode())) {
            UemUser uemUser = QUemUser.uemUser.selectOne().byId(Long.valueOf(uemUserId));
            if (Objects.isNull(uemUser) || !uemUser.getBlindCompanny().equals(userInfoModel.getBlindCompanny())) {
                return CommonResult.getFaildResultData("新增的用户id与当前登录用户不在统一企业，操作失败！");
            }
        }

        List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole
                .select(QUemUserRole.uemUserRole.fieldContainer())
                .where(QUemUserRole.sysApplicationId.eq(":sysApplicationId")
                        .and(QUemUserRole.uemUserId.eq(UEM_USER_ID_PLACEHOLDER))
                        .and(QUemUserRole.sysRoleId.eq(":sysRoleId")))
                .execute(ImmutableMap.of("sysApplicationId", sysApplicationId, UEM_USER_ID, uemUserId, "sysRoleId", uemUserRoleId));

        if (CollectionUtils.isNotEmpty(uemUserRoleList)) {
            return CommonResult.getFaildResultData("该用户该应用下已有该角色，请确认");
        }

        int saveUemUserRoleCount = 0;


        //用户角色表
        UemUserRole uemUserRole = new UemUserRole();
        uemUserRole.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);

        uemUserRole.setSysApplicationId(Long.valueOf(sysApplicationId));
        uemUserRole.setSysRoleId(Long.valueOf(uemUserRoleId));
        uemUserRole.setIsValid(true);
        uemUserRole.setUemUserId(Long.valueOf(uemUserId));
        saveUemUserRoleCount = QUemUserRole.uemUserRole.save(uemUserRole);

        if (saveUemUserRoleCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("新加成功");
        } else {
            return CommonResult.getFaildResultData("新加失败");
        }
    }

    /**
     * 根据手机号生成验证码
     *
     * @param mobile 手机号
     * @return Map<String, Object>
     * @author xrp
     */
    @Deprecated
    @Override
    public ResultHelper generateAuthCodeByMobile(String mobile) {

        if (StringUtils.isEmpty(mobile)) {
            return CommonResult.getFaildResultData("手机号不能为空");
        }

        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.mobile.eq(":mobile"))
                .execute(ImmutableMap.of("mobile", mobile));
        if (!CollectionUtils.isEmpty(uemUserList)) {
            AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
            // 如果是有登录的情况下，判断输入的手机号是否是原手机号
            if (Objects.nonNull(user) && Objects.nonNull(user.getUemUserId()) && uemUserList.stream().anyMatch(uemUser -> uemUser.getUemUserId().equals(user.getUemUserId()))) {
                return CommonResult.getFaildResultData("与当前手机号一致，请重新输入手机号");
            }
            return CommonResult.getFaildResultData("该手机号已被绑定，请重新输入手机号");
        }

        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号存储到redis中，并设置过期时间
        sassRedisInterface.set(redisKeyPrefixAuthCode + mobile, sb.toString());
        sassRedisInterface.expire(redisKeyPrefixAuthCode + mobile, authCodeExpireSeconds);
        return CommonResult.getSuccessResultData(sb.toString());
    }

    /**
     * 验证手机验证码是否正确
     *
     * @param uemUserDto 用户表
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> verifyAuthCodeByMobile(UemUserDto uemUserDto) {

        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        //手机号
        String mobile = uemUserDto.getMobile();
        //验证码
        String authCode = uemUserDto.getAuthCode();
        //用户表ID
        Long uemUserId = userInfoModel.getUemUserId();
        if (StringUtils.isEmpty(authCode)) {
            return CommonResult.getFaildResultData("请输入验证码");
        }
        if (StringUtils.isNotEmpty(mobile) && mobile.equals(userInfoModel.getMobile())) {
            return CommonResult.getFaildResultData("与当前手机号一致，请重新输入！");
        }
        List<UemUser> uemUserList = QUemUser.uemUser.select(QUemUser.isValid)
                .where(QUemUser.mobile.eq$(mobile)
                        .and(QUemUser.isValid.eq$(true))
                        .and(QUemUser.uemUserId.ne$(userInfoModel.getUemUserId())))
                .execute();

        log.info("uemUserList:" + uemUserList);
        if (!CollectionUtils.isEmpty(uemUserList)) {
            return CommonResult.getFaildResultData("手机号无效，请重新输入！");
        }

        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser
                .select(QSysPlatformUser.sysPlatformUser.fieldContainer())
                .where(QSysPlatformUser.tel.eq(":mobile")
                        .and(QSysPlatformUser.isValid.eq(":sign")))
                .execute(ImmutableMap.of(MOBILE, mobile, "sign", Boolean.getBoolean(CodeFinal.IS_VALID_ZERO)));

        if (!CollectionUtils.isEmpty(sysPlatformUserList)) {
            return CommonResult.getFaildResultData("手机号无效，请重新输入！");
        }


        //对比验证码是否失效或者错误验证码
        String redisAuthCode = sassRedisInterface.get(redisKeyPrefixAuthCode + mobile + uemUserDto.getSign());
        if (StringUtils.isEmpty(redisAuthCode)) {
            return CommonResult.getFaildResultData("验证码已过期，请重新获取");
        }
        if (!authCode.equals(redisAuthCode)) {
            return CommonResult.getFaildResultData("验证码输入错误，请重新获取");
        }
        int updateCount = QUemUser.uemUser
                .update(QUemUser.mobile)
                .where(QUemUser.uemUserId.eq(UEM_USER_ID_PLACEHOLDER))
                .execute(mobile, uemUserId);

        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            sassRedisInterface.del(redisKeyPrefixAuthCode + mobile + uemUserDto.getSign());
            return CommonResult.getSuccessResultData("验证码正确");
        } else {
            return CommonResult.getFaildResultData("提交失败");
        }

    }

    /**
     * 根据邮箱生成验证码
     *
     * @param email 邮箱
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> generateAuthCodeByEmail(String email) {
        log.info("绑定/修改邮箱开始时间：{}", System.currentTimeMillis());
        AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
        // 发送绑定邮箱邮件次数
        String sendEmailRedisKey = GlobalConstant.BIND_EMAIL_DAILY_SEND_EMAIL_MAX_TIMES_KEY_PRE + user.getUemUserId();
        String sendEmailRedisValue = sassRedisInterface.get(sendEmailRedisKey);
        int sendEmailTimes = StringUtils.isEmpty(sendEmailRedisValue) ? 0 : Integer.parseInt(sendEmailRedisValue);
        if (sendEmailTimes >= GlobalConstant.BIND_EMAIL_DAILY_SEND_EMAIL_MAX_TIMES) {
            return CommonResult.getFaildResultData("今日绑定/修改邮箱发送邮件已达限制数10条");
        }

        if (StringUtils.isEmpty(email)) {
            return CommonResult.getFaildResultData("邮箱不能为空");
        }

        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.email.eq(":email"))
                .execute(ImmutableMap.of("email", email));

        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser
                .select(QSysPlatformUser.sysPlatformUser.fieldContainer())
                .where(QSysPlatformUser.mail.eq(":email"))
                .execute(ImmutableMap.of("email", email));

        if (CollectionUtils.isNotEmpty(uemUserList)) {
            if (Objects.nonNull(user) && uemUserList.get(0).getUemUserId().equals(user.getUemUserId())) {
                // 如果是有登录的情况下，判断输入的邮箱是否是原邮箱
                return CommonResult.getFaildResultData("与当前邮箱一致，请重新输入邮箱");
            } else {
                return CommonResult.getFaildResultData("该邮箱已被绑定，请重新输入邮箱");
            }
        }
        if (CollectionUtils.isNotEmpty(sysPlatformUserList)) {
            return CommonResult.getFaildResultData("该邮箱已被绑定，请重新输入邮箱");
        }
        // 剩余过期时间，单位秒
        Long ttl = sassRedisInterface.ttl(redisKeyPrefixAuthCode + email);
        int residueSecond = Objects.isNull(ttl) ? 0 : ttl.intValue();
        // 获取验证码间隔一分钟
        int intervalSecond = 60;
        // 获取验证码时间间隔不超过一分钟
        if (MAIL_EXPIRE_TIME - residueSecond < intervalSecond) {
            return CommonResult.getFaildResultData("验证码有效期" + MAIL_EXPIRE_TIME / 60 + "分钟，获取间隔1分钟，请稍后获取！");
        }
        String code = MessageUtil.randomNum(6);
        //验证码绑定手机号存储到redis中，并设置过期时间
        sassRedisInterface.set(redisKeyPrefixAuthCode + email, code);
        sassRedisInterface.expire(redisKeyPrefixAuthCode + email, MAIL_EXPIRE_TIME);
        SendEmailVO sendEmailVO = new SendEmailVO();
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put("code", code);
        marco.put(CodeFinal.CONTENT, content);
        sendEmailVO.setSystemId(MessageUtil.getApplicationCode());
        sendEmailVO.setToEmail(email);
        sendEmailVO.setMarcoAndAttachParams(marco);
        if (Objects.nonNull(user) && !StringUtils.isEmpty(user.getEmail())) {
            sendEmailVO.setEmailTemplateCode(changeEmailTemplate);
        } else {
            sendEmailVO.setEmailTemplateCode(bindEmailTemplate);
        }
        log.info("绑定/修改邮箱-发送邮件开始时间：{}", System.currentTimeMillis());
        Map<String, Object> result = emailTemplateService.sendEmail(sendEmailVO);
        log.info("绑定/修改邮箱-发送邮件结束时间：{}，发送Email返回结果：{}", System.currentTimeMillis(), result);
        // 记录发送邮件的次数
        if (Objects.nonNull(result) && result.get(CodeFinal.RESULTCODE).equals(CodeFinal.SUCCESSEMAIL)) {
            // 记录发送邮件的次数
            this.recordSendEmailTimes(sendEmailRedisKey, user);
            log.info("绑定/修改邮箱结束时间：{}", System.currentTimeMillis());
            return CommonResult.getSuccessResultData("邮件发送成功");
        } else {
            log.info("调用推送中心服务返回结果为null，邮件发送失败");
            // 全局异常处理
            throw new RuntimeException("邮件发送失败，请联系客服！");
        }
    }

    /**
     * 记录发送邮件的次数
     *
     * @param sendEmailRedisKey 发送邮件redis key
     * @param currentLoginUser  当前登录用户
     */
    private void recordSendEmailTimes(String sendEmailRedisKey, AuthUserInfoModel currentLoginUser) {
        // 记录用户每日邮件发送次数
        Long incr = sassRedisInterface.incr(sendEmailRedisKey);
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 明天0点毫秒数
        long tomorrowZero = calendar.getTimeInMillis();
        // 今日剩余时间
        long remainSecond = (tomorrowZero - current) / 1000;
        sassRedisInterface.expire(sendEmailRedisKey, Integer.parseInt(String.valueOf(remainSecond)));
        log.info("用户{}今日修改绑定邮件，发送邮件次数：{}", currentLoginUser.getUemUserId(), incr);
    }

    /**
     * 验证邮箱验证码是否正确
     *
     * @param uemUserDto 用户表
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> verifyAuthCodeByEmail(UemUserDto uemUserDto) {

        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        //邮箱
        String email = uemUserDto.getEmail();
        //验证码
        String authCode = uemUserDto.getAuthCode();
        //用户表ID
        Long uemUserId = userInfoModel.getUemUserId();

        if (StringUtils.isEmpty(authCode)) {
            return CommonResult.getFaildResultData("请输入验证码");
        }
        //对比验证码是否失效或者错误验证码
        String redisAuthCode = sassRedisInterface.get(redisKeyPrefixAuthCode + email);
        if (StringUtils.isEmpty(redisAuthCode)) {
            return CommonResult.getFaildResultData("验证码已过期，请重新获取");
        }
        if (!authCode.equals(redisAuthCode)) {
            return CommonResult.getFaildResultData("验证码输入错误，请重新获取");
        }
        int updateCount = QUemUser.uemUser
                .update(QUemUser.email)
                .where(QUemUser.uemUserId.eq(UEM_USER_ID_PLACEHOLDER))
                .execute(email, uemUserId);
        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("验证码正确");
        } else {
            return CommonResult.getFaildResultData("提交失败");
        }

    }

    /**
     * 登录日志  个人
     *
     * @param uemLogDto 登录日志表
     * @return Page<UemLogDto>
     * @author xrp
     */
    @Override
    public Page<UemLogDto> loginLogIndividual(UemLogDto uemLogDto) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || Objects.isNull(userInfoModel.getUemUserId())) {
            return null;
        }
        //用户表ID
        Long uemUserId = userInfoModel.getUemUserId();

        Integer pageSize = uemLogDto.getPageSize();

        Integer pageNo = uemLogDto.getPageNo();
        //查询的开始时间标识(当标识为0时，为最近100条，当标识为1，为一个月，当标识为2时，三个月)
        String beginTime = uemLogDto.getBeginTime();

        //如果页码为空则初始化页码信息
        if (Objects.isNull(pageNo)) {
            pageNo = CodeFinal.CURRENT_PAGE_DEFAULT;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = CodeFinal.PAGE_SIZE_DEFAULT;
        }

        if (CodeFinal.RECENT_TIME.equals(beginTime)) {
            /*Page<UemLogDto> page = QUemLog.uemLog
                    .select(QUemLog.result,
                            QUemLog.logDate,
                            QUemLog.ipAddress,
                            QUemLog.city,
                            QUemLog.equipment,
                            QUemLog.way)
                    .where(QUemLog.uemUserId.eq(UEM_USER_ID_PLACEHOLDER))
                    .paging(pageNo, pageSize)
                    .sorting(QUemLog.logDate.desc())
                    .mapperTo(UemLogDto.class)
                    .execute(ImmutableMap.of(UEM_USER_ID,uemUserId));*/
            SelectPageFromModelCommand<UemLog> searchPage = QUemLog.uemLog
                    .select(QUemLog.result,
                            QUemLog.logDate,
                            QUemLog.ipAddress,
                            QUemLog.city,
                            QUemLog.equipment,
                            QUemLog.way)
                    .where(QUemLog.uemUserId.eq(UEM_USER_ID_PLACEHOLDER))
                    .paging(pageNo, pageSize);
            /*if (enableES) {
                searchPage.tag(EsConstant.ES_AUTH_TAG);
            }*/
            Page<UemLogDto> page = searchPage.sorting(QUemLog.logDate.desc())
                    .mapperTo(UemLogDto.class)
                    .execute(ImmutableMap.of(UEM_USER_ID, uemUserId));
            if (page.getTotalRecord() > CodeFinal.HUNDRED) {
                page.setTotalRecord(CodeFinal.HUNDRED);
            }
            return page;
        }

        Date startDate = null;
        Date endDate = new Date();
        if (CodeFinal.ONE_MONTH.equals(beginTime)) {
            startDate = DateUtil.offsetMonth(endDate, -1);
        }
        if (CodeFinal.THREE_MONTH.equals(beginTime)) {
            startDate = DateUtil.offsetMonth(endDate, -3);
        }

        if (Objects.nonNull(startDate)) {
/*
            return QUemLog.uemLog
                    .select(QUemLog.result,
                            QUemLog.logDate,
                            QUemLog.ipAddress,
                            QUemLog.city,
                            QUemLog.equipment,
                            QUemLog.way)
                    .where(QUemLog.logDate.between(":beginTime", END_TIME_PLACEHOLDER)
                            .and(QUemLog.uemUserId.eq(UEM_USER_ID_PLACEHOLDER)))
                    .paging((uemLogDto.getPageNo() == null) ? 1 : uemLogDto.getPageNo(), (uemLogDto.getPageSize() == null) ? 10 : uemLogDto.getPageSize())
                    .sorting(QUemLog.logDate.desc())
                    .mapperTo(UemLogDto.class)
                    .execute(ImmutableMap.of("beginTime", startDate, "endTime", endDate, UEM_USER_ID, uemUserId));
*/

            SelectPageFromModelCommand<UemLog> searchPage = QUemLog.uemLog
                    .select(QUemLog.result,
                            QUemLog.logDate,
                            QUemLog.ipAddress,
                            QUemLog.city,
                            QUemLog.equipment,
                            QUemLog.way)
                    .where(QUemLog.logDate.between(":beginTime", END_TIME_PLACEHOLDER)
                            .and(QUemLog.uemUserId.eq(UEM_USER_ID_PLACEHOLDER)))
                    .paging((uemLogDto.getPageNo() == null) ? 1 : uemLogDto.getPageNo(), (uemLogDto.getPageSize() == null) ? 10 : uemLogDto.getPageSize());

            /*if (enableES) {
                searchPage.tag(EsConstant.ES_AUTH_TAG);
            }*/
            return searchPage.sorting(QUemLog.logDate.desc())
                    .mapperTo(UemLogDto.class)
                    .execute(ImmutableMap.of("beginTime", startDate, "endTime", endDate, UEM_USER_ID, uemUserId));
        }

        SelectPageFromModelCommand<UemLog> searchPage = QUemLog.uemLog
                .select(QUemLog.result,
                        QUemLog.logDate,
                        QUemLog.ipAddress,
                        QUemLog.city,
                        QUemLog.equipment,
                        QUemLog.way)
                .where(QUemLog.uemUserId.eq(UEM_USER_ID_PLACEHOLDER))
                .paging(pageNo, pageSize);

        /*if (enableES) {
            searchPage.tag(EsConstant.ES_AUTH_TAG);
        }*/
        return searchPage.sorting(QUemLog.logDate.desc())
                .mapperTo(UemLogDto.class)
                .execute(ImmutableMap.of(UEM_USER_ID, uemUserId));
        /*return QUemLog.uemLog
                .select(QUemLog.result,
                        QUemLog.logDate,
                        QUemLog.ipAddress,
                        QUemLog.city,
                        QUemLog.equipment,
                        QUemLog.way)
                .where(QUemLog.uemUserId.eq(UEM_USER_ID_PLACEHOLDER))
                .paging(pageNo, pageSize)
                .sorting(QUemLog.logDate.desc())
                .mapperTo(UemLogDto.class)
                .execute(ImmutableMap.of(UEM_USER_ID, uemUserId));*/

    }

    /**
     * 登录日志  全部
     *
     * @param uemLogDto 登录日志表
     * @return Page<UemLogDto>
     * @author xrp
     */
    @Override
    public Page<UemLogDto> loginLogAll(UemLogDto uemLogDto) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || Objects.isNull(userInfoModel.getUemUserId())) {
            /// 全局异常处理
            throw new RuntimeException("登录信息获取失败");
        }
        //企业ID,前端必传
        Long companyId = userInfoModel.getBlindCompanny();
        Integer pageNo = uemLogDto.getPageNo();
        //查询的开始时间标识（当标识为0时，为最近100条，当标识为1，为一个月，当标识为2时，三个月)
        String beginTime = uemLogDto.getBeginTime();
        Integer pageSize = uemLogDto.getPageSize();
        //如果页码为空则初始化页码信息
        if (Objects.isNull(pageNo)) {
            pageNo = CodeFinal.CURRENT_PAGE_DEFAULT;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = CodeFinal.PAGE_SIZE_DEFAULT;
        }

        if (CodeFinal.RECENT_TIME.equals(beginTime)) {
            Page<UemLogDto> page = QUemLog.uemLog
                    .select(QUemLog.result,
                            QUemLog.logDate,
                            QUemLog.ipAddress,
                            QUemLog.city,
                            QUemLog.equipment,
                            QUemLog.uemLog.chain(QUemUser.account).as(ACCOUNT),
                            QUemLog.uemLog.chain(QUemUser.name).as("name"),
                            QUemLog.uemLog.chain(QUemUser.userType).as(USER_TYPE),
                            QUemLog.way
                    )
                    .where(QUemLog.companyId.eq(COMPANY_ID_PLACEHOLDER))
                    .paging(pageNo, pageSize)
                    .sorting(QUemLog.logDate.desc())
                    .mapperTo(UemLogDto.class)
                    .execute(ImmutableMap.of(COMPANY_ID, companyId));
            if (page.getTotalRecord() > CodeFinal.HUNDRED) {
                page.setTotalRecord(CodeFinal.HUNDRED);
            }
            return page;
        }
        //开始时间
        Date startDate = null;
        Date endDate = new Date();
        if (CodeFinal.ONE_MONTH.equals(beginTime)) {
            startDate = DateUtil.offsetMonth(endDate, -1);
        }
        if (CodeFinal.THREE_MONTH.equals(beginTime)) {
            startDate = DateUtil.offsetMonth(endDate, -3);
        }

        //查询的结束时间
        //判断开始时间有没有被赋值，如果被赋值了按赋值的条件查询，未被赋值的按分页的条件查询
        if (Objects.nonNull(startDate)) {
            return QUemLog.uemLog
                    .select(QUemLog.result,
                            QUemLog.logDate,
                            QUemLog.ipAddress,
                            QUemLog.city,
                            QUemLog.equipment,
                            QUemLog.uemLog.chain(QUemUser.account).as(ACCOUNT),
                            QUemLog.uemLog.chain(QUemUser.name).as("name"),
                            QUemLog.uemLog.chain(QUemUser.userType).as(USER_TYPE),
                            QUemLog.way
                    )
                    .where(QUemLog.logDate.between(":beginTime", END_TIME_PLACEHOLDER)
                            .and(QUemLog.companyId.eq(COMPANY_ID_PLACEHOLDER)))
                    .paging((uemLogDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemLogDto.getPageNo(), (uemLogDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemLogDto.getPageSize())
                    .sorting(QUemLog.logDate.desc())
                    .mapperTo(UemLogDto.class)
                    .execute(ImmutableMap.of("beginTime", startDate, "endTime", endDate, COMPANY_ID, companyId));
        } else {
            return QUemLog.uemLog
                    .select(QUemLog.result,
                            QUemLog.logDate,
                            QUemLog.ipAddress,
                            QUemLog.city,
                            QUemLog.equipment,
                            QUemLog.uemLog.chain(QUemUser.account).as(ACCOUNT),
                            QUemLog.uemLog.chain(QUemUser.name).as("name"),
                            QUemLog.uemLog.chain(QUemUser.userType).as(USER_TYPE),
                            QUemLog.way
                    )
                    .where(QUemLog.companyId.eq(COMPANY_ID_PLACEHOLDER))
                    .paging(pageNo, pageSize)
                    .sorting(QUemLog.logDate.desc())
                    .mapperTo(UemLogDto.class)
                    .execute(ImmutableMap.of(COMPANY_ID, companyId));
        }
    }

    /**
     * @Author:chenxf
     * @Description: 解除绑定
     * @Date: 19:44 2021/1/13
     * @Param: [uemUserId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public ResultHelper<Object> unbindUemUser(String uemUserId) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        UemUser user = QUemUser.uemUser.selectOne().byId(userInfoModel.getUemUserId());
        return this.unbindUser(uemUserId, user.getBlindCompanny().toString());
    }

    /**
     * @Author:chenxf
     * @Description: 企业用户绑定审核
     * @Date: 10:04 2021/2/23
     * @Param: [uemUserDto]
     * @Return:com.share.support.result.ResultHelper
     */
    @Override
    public ResultHelper<Object> reviewUemUserCompany(UemUserDto uemUserDto) {
        String result = "审核通过成功";
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel) || userInfoModel.getUemUserId() == null) {
            return CommonResult.getFaildResultData(GET_LOGIN_USER_INFO_FAIL_PROMPT);
        }
        UemUserCompany uemUserCompany = QUemUserCompany.uemUserCompany.selectOne().where(
                QUemUserCompany.uemCompanyId.eq$(userInfoModel.getBlindCompanny())
                        .and(QUemUserCompany.uemUserId.eq$(Long.valueOf(uemUserDto.getUemUserId())))
                        .and(QUemUserCompany.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ZERO))
                        .and(QUemUserCompany.quitTime.isNull())
        ).execute();
        UemCompanyManager uemCompanyManager = QUemCompanyManager.uemCompanyManager.selectOne().where(
                QUemCompanyManager.uemCompanyId.eq$(userInfoModel.getBlindCompanny())
                        .and(QUemCompanyManager.uemUserId.eq$(Long.valueOf(uemUserDto.getUemUserId())))
                        .and(QUemCompanyManager.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ZERO))
        ).execute();
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(Long.valueOf(uemUserDto.getUemUserId()));
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        Map<String, Object> marco = new HashMap<>(16);
        if (CodeFinal.AUDIT_STATUS_ONE.equals(uemUserDto.getBlindStatus())) {
            uemUserCompany.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            uemUserCompany.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
            QUemUserCompany.uemUserCompany.save(uemUserCompany);
            uemUser.setUserType(CodeFinal.USER_TYPE_ONE);
            uemUser.setBlindCompanny(uemUserCompany.getUemCompanyId());
            uemUser.setBlindCompannyTime(new Date());
            marco.put("result", "通过");

        } else if (CodeFinal.AUDIT_STATUS_TWO.equals(uemUserDto.getBlindStatus())) {
            QUemUserCompany.uemUserCompany.delete(uemUserCompany);
            if (Objects.nonNull(uemCompanyManager)) {
                QUemCompanyManager.uemCompanyManager.delete(uemCompanyManager);
            }
            marco.put("result", "拒绝");
            result = "审核拒绝成功";
        } else {
            return CommonResult.getFaildResultData("入参审批状态为空，请确认！");
        }

        QUemUser.uemUser.save(uemUser);
        UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(userInfoModel.getBlindCompanny());
        if (CodeFinal.AUDIT_STATUS_ONE.equals(uemUserDto.getBlindStatus())) {
            // 赋予默认用户角色
            uemUserService.defaultUemUserRole(uemUserCompany.getUemUserId(), uemUserCompany.getUemCompanyId());
            if (Objects.nonNull(uemCompanyManager)) {
                msgSendService.notifyAuditCompanyManage(uemCompany.getCompanyNameCn());
            }
        }
        marco.put("companyName", uemCompany.getCompanyNameCn());
        msgSendService.sendMobileMsg(blindCompanyTemplate, marco, uemUser.getMobile());
        return CommonResult.getSuccessResultData(result);
    }

    @Override
    public ResultHelper<Object> sendMessage(Long uemUserId, Long sysApplicationId) {
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户id不能空");
        }
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        }
        if (Objects.isNull(sysApplicationId)) {
            return CommonResult.getFaildResultData("应用id不能为空");
        }
        SysApplication sysApplication = QSysApplication.sysApplication.selectOne().byId(sysApplicationId);
        if (Objects.isNull(sysApplication)) {
            return CommonResult.getFaildResultData("应用不存在");
        }
        // 短信VO
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(uemUser.getMobile());
        // 短信模板
        msgSmsApiVO.setSmsTemplateCode(permissionDistribute);
        // 消息宏参数map
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put("name", uemUser.getName());
        content.put(GlobalConstant.ALIAS_APPLICATION_NAME, sysApplication.getApplicationName());
        marco.put(CodeFinal.CONTENT, content);
        log.info("短信消息宏参数传参： {}", marco.toString());
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);
        // 调用公共服务接口发送短信
        try {
            SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
            if (CodeFinal.SUCCESSEMAIL.equals(sendMsgReturnVo.getResultCode())) {
                log.info("权限开通，短信发送成功");
                return CommonResult.getSuccessResultData();
            } else {
                log.error("权限开通，短信发送失败，失败原因：{}", sendMsgReturnVo.getResultMsg());
                return CommonResult.getSuccessResultData(sendMsgReturnVo.getResultMsg());
            }
        } catch (Exception e) {
            log.error("调用推送服务失败：{}", e.getMessage(), e);
            return CommonResult.getSuccessResultData("调用推送服务失败");
        }
    }
}
