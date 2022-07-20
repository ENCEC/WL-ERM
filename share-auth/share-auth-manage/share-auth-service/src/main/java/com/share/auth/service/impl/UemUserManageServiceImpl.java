package com.share.auth.service.impl;

import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.expressions.OrExpression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.exception.BusinessRuntimeException;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.share.auth.constants.CodeFinal;
import com.share.auth.constants.GlobalConstant;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.enums.GlobalEnum;
import com.share.auth.model.entity.SysPlatformUser;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemIdCard;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.querymodels.*;
import com.share.auth.service.UemUserManageService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.MessageUtil;
import com.share.file.api.ShareFileInterface;
import com.share.file.domain.FastDfsUploadResult;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgSmsApiVO;
import com.share.message.domain.SendMsgReturnVo;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import com.share.support.util.MD5EnCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xrp
 * @date 2020/11/3 0003
 */
@Service
@Slf4j
public class UemUserManageServiceImpl implements UemUserManageService {

    @Value("${user.default.cardPositiveFilePath}")
    private String cardPositiveFilePath;
    @Value("${user.default.cardBackFilePath}")
    private String cardBackFilePath;
    @Value("${user.default.managerFilePath}")
    private String managerFilePath;
    @Value("${msg.sms.template.platform_user_reset_user_password}")
    private String platformUserResetUserPassword;
    @Value("${msg.sms.template.platform_user_add_user}")
    private String platformUserAddUserMsgCode;

    @Autowired
    private DefaultUserService defaultUserService;
    @Autowired
    private UemUserService uemUserService;
    @Autowired
    private MsgApiService msgApiService;
    @Autowired
    private ShareFileInterface shareFileInterface;

    /**
     * 用户管理
     *
     * @param uemUserDto 用户表封装类
     * @return Page<UemUserDto>
     * @author xrp
     */
    @Override
    public Page<UemUserDto> queryUemUser(UemUserDto uemUserDto) {

        //用户名
        String account = uemUserDto.getAccount();
        //姓名
        String name = uemUserDto.getName();

        if (!StringUtils.isEmpty(account)) {
            uemUserDto.setAccount("%" + account + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            uemUserDto.setName("%" + name + "%");
        }
        // 手机号
        if (StringUtils.isNotBlank(uemUserDto.getMobile())) {
            uemUserDto.setMobile("%" + uemUserDto.getMobile() + "%");
        } else {
            uemUserDto.setMobile(null);
        }
        // 审核状态
        if (StringUtils.isBlank(uemUserDto.getAuditStatus())) {
            uemUserDto.setAuditStatus(null);
        }
        if (StringUtils.isNotBlank(uemUserDto.getCompanyNameCn())) {
            uemUserDto.setCompanyNameCn("%" + uemUserDto.getCompanyNameCn() + "%");
        }
        // 查询条件
        AndExpression andExpression = QUemUser.account.like(":account")
                        .and(QUemUser.name.like(":name"))
                        .and(QUemUser.userType.eq(":userType"))
                        .and(QUemUser.blindCompanny.eq(":blindCompanny"))
                        .and(QUemUser.isValid.eq(":isValid"))
                        .and(QUemUser.source.eq(GlobalConstant.PLACEHOLDER_SOURCE))
                        .and(QUemUser.oriApplication.eq(":oriApplication"))
                        .and(QUemUser.mobile.like(":mobile"))
                        .and(QUemUser.uemUser.chain(QUemCompany.companyNameCn).like(":companyNameCn"))
                        .and(QUemUser.uemUser.chain(QUemCompany.uemCompanyId).in(":uemCompanyIdList"));
        // 查询未审核时，需要查询未审核数据与未实名数据
        if (Objects.equals(uemUserDto.getAuditStatus(), GlobalEnum.AuditStatusEnum.NO_AUDIT.getCode())) {
            andExpression = andExpression.and(QUemUser.auditStatus.eq(":auditStatus").or(QUemUser.auditStatus.isNull()));
        } else {
            andExpression = andExpression.and(QUemUser.auditStatus.eq(":auditStatus"));
        }

        //分页查询出用户表，企业表，企业用户类型表（这个为筛选条件）
        return QUemUser.uemUser
                .select(QUemUser.uemUserId,
                        QUemUser.account,
                        QUemUser.name,
                        QUemUser.mobile,
                        QUemUser.auditStatus,
                        QUemUser.userType,
                        QUemUser.blindCompanny,
                        QUemUser.uemUser.chain(QSysApplication.applicationName).as("applicationName"),
                        QUemUser.uemUser.chain(QUemCompany.companyNameCn).as("companyNameCn"),
                        QUemUser.uemUser.chain(QUemCompany.auditStatus).as("companyAuditStatus"),
                        QUemUser.source,
                        QUemUser.oriApplication,
                        QUemUser.isValid,
                        QUemUser.invalidTime,
                        QUemUser.score)
                .where(andExpression)
                .paging((uemUserDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserDto.getPageNo(), (uemUserDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserDto.getPageSize())
                .sorting(QUemUser.createTime.desc())
                .mapperTo(UemUserDto.class)
                .execute(uemUserDto);
    }


    /**
     * 用户管理详情
     *
     * @param uemUserId 用户ID
     * @return List<UemUserDto>
     * @author xrp
     */
    @Override
    public List<UemUserDto> getUemUser(String uemUserId) {

        //该接口缺少身份证照片上传的接口
        return QUemUser.uemUser
                .select(QUemUser.uemUserId,
                        QUemUser.blindCompanny,
                        QUemUser.uemUser.chain(QSysApplication.applicationName).as("applicationName"),
                        QUemUser.uemUser.chain(QUemCompany.companyNameCn).as("companyNameCn"),
                        QUemUser.userType,
                        QUemUser.source,
                        QUemUser.oriApplication,
                        QUemUser.account,
                        QUemUser.mobile,
                        QUemUser.email,
                        QUemUser.auditStatus,
                        QUemUser.createTime,
                        QUemUser.name,
                        QUemUser.sex,
                        QUemUser.idCard,
                        QUemUser.cardBackUrlId,
                        QUemUser.telephone,
                        QUemUser.staffCode,
                        QUemUser.staffLevel,
                        QUemUser.staffDuty,
                        QUemUser.orgCode,
                        QUemUser.isValid,
                        QUemUser.seqNo,
                        QUemUser.modifyTime,
                        QUemUser.cardPositiveUrlId)
                .where(QUemUser.uemUserId.eq(":uemUserId"))
                .mapperTo(UemUserDto.class)
                .execute(ImmutableMap.of("uemUserId", uemUserId));
    }

    /**
     * 用户管理 启停
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String       ,               Object>
     * @author xrp
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<Object> uemUserStartStop(UemUserDto uemUserDto) {

        //用户表ID
        String uemUserId = uemUserDto.getUemUserId();
        //是否禁用(0禁用,1启用)
        Boolean isValid = uemUserDto.getIsValid();

        if (StringUtils.isEmpty(uemUserId)) {
            return CommonResult.getFaildResultData("用户ID不能为空");
        }
        UemUser uemUser = new UemUser();
        uemUser.setUemUserId(Long.valueOf(uemUserId));
        uemUser.setIsValid(isValid);
        uemUser.setInvalidTime(new Date());
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int updateCount = QUemUser.uemUser.selective(QUemUser.isValid, QUemUser.invalidTime).execute(uemUser);
        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("启停成功");
        } else {
            return CommonResult.getFaildResultData("启停失败");
        }
    }


    /**
     * 修改用户信息
     *
     * @param uemUserDto
     * @return
     * @throws
     * @author tujx
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<Object> editUemUser(UemUserEditDTO uemUserDto) {
        //获取当前用户信息
        AuthUserInfoModel user = (AuthUserInfoModel) defaultUserService.getCurrentLoginUser();
        String validateStr = uemUserUniqueValidate(uemUserDto);
        if (StringUtils.isNotBlank(validateStr)) {
            return CommonResult.getFaildResultData(validateStr);
        }
        Long uemUserId = uemUserDto.getUemUserId();
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户id不允许为空");
        }
        //根据主键查询用户信息
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("查询不到对应的用户信息");
        }
        //用户信息修改
        BeanUtils.copyProperties(uemUserDto, uemUser);
        //实名认证判断
        if (StringUtils.isNotBlank(uemUserDto.getName()) && Objects.nonNull(uemUserDto.getSex())
                && StringUtils.isNotBlank(uemUserDto.getIdCard()) && StringUtils.isNotBlank(uemUserDto.getCardPositiveUrlId())
                && StringUtils.isNotBlank(uemUserDto.getCardBackUrlId())) {
            //未进行过实名认证或者认证已被拒绝，直接认证通过
            UemIdCard uemIdCard = null;
            if (StringUtils.isBlank(uemUser.getAuditStatus()) || StringUtils.equals(uemUser.getAuditStatus(), CodeFinal.AUDIT_STATUS_TWO)) {
                //未进行过实名认证或审批拒绝
                uemIdCard = new UemIdCard();
                uemIdCard.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            } else if (StringUtils.equals(uemUser.getAuditStatus(), CodeFinal.AUDIT_STATUS_ONE)) {
                //审批通过
                uemIdCard = QUemIdCard.uemIdCard.selectOne().byId(uemUser.getUemIdCardId());
                //设置更新状态值
                setUemIdCardRowStatus(uemIdCard);
            } else if (StringUtils.equals(uemUser.getAuditStatus(), CodeFinal.AUDIT_STATUS_ZERO)) {
                //待审核
                uemIdCard = QUemIdCard.uemIdCard.selectOne().where(QUemIdCard.uemUserId.eq$(uemUserId)).execute();
                if (!(StringUtils.equals(uemUserDto.getName(), uemIdCard.getName()) && Objects.equals(uemUserDto.getSex(), uemIdCard.getSex())
                        && StringUtils.equals(uemUserDto.getIdCard(), uemIdCard.getIdCard())
                        && StringUtils.equals(uemUserDto.getCardPositiveUrlId(), uemIdCard.getCardPositiveUrlId())
                        && StringUtils.equals(uemUserDto.getCardBackUrlId(), uemIdCard.getCardBackUrlId()))) {
                    //认证信息发生变更
                    uemIdCard.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                } else {
                    uemIdCard = null;
                }
            }
            // 实名认证信息更新
            realNameInfoUpdate(uemIdCard, uemUserDto, uemUserId, user, uemUser);
        }
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QUemUser.uemUser.save(uemUser);
        return CommonResult.getSuccessResultData();
    }

    /**
     * 设置实名信息表状态值
     *
     * @param uemIdCard
     * @return
     * @author huanghwh
     * @date 2021/5/6 下午3:06
     */
    private void setUemIdCardRowStatus(UemIdCard uemIdCard) {
        if (Objects.nonNull(uemIdCard)) {
            uemIdCard.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        }
    }

    /**
     * 实名认证信息更新
     *
     * @param uemIdCard
     * @param uemUserDto
     * @param uemUserId
     * @param user
     * @param uemUser
     * @return
     * @author huanghwh
     * @date 2021/5/6 下午2:55
     */
    private void realNameInfoUpdate(UemIdCard uemIdCard, UemUserEditDTO uemUserDto, Long uemUserId, AuthUserInfoModel user, UemUser uemUser) {
        if (Objects.nonNull(uemIdCard)) {
            Date now = new Date();
            //需要进行实名认证
            BeanUtils.copyProperties(uemUserDto, uemIdCard);
            uemIdCard.setUemUserId(uemUserId);
            uemIdCard.setAuditor(user.getUemUserId());
            uemIdCard.setAuditTime(now);
            uemIdCard.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
            //实名认证信息更新
            QUemIdCard.uemIdCard.save(uemIdCard);
            uemUser.setUemIdCardId(uemIdCard.getUemIdCardId());
            uemUser.setAuditor(user.getUemUserId());
            uemUser.setAuditTime(now);
            uemUser.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
        }
    }


    /**
     * 删除用户信息
     *
     * @param uemUserId 用户主键id
     * @return
     * @throws
     * @author tujx
     */
    @Override
    public ResultHelper<Object> deleteUemUser(Long uemUserId) {
        if (Objects.isNull(uemUserId)) {
            CommonResult.getFaildResultData("用户信息主键不能为空");
        }
       //实名信息表
        QUemIdCard.uemIdCard.delete().where(QUemIdCard.uemUserId.eq$(uemUserId)).execute();
        //用户角色表
        QUemUserRole.uemUserRole.delete().where(QUemUserRole.uemUserId.eq$(uemUserId)).execute();
        //用户表
        QUemUser.uemUser.deleteById(uemUserId);
        return CommonResult.getSuccessResultData();
    }


    /**
     * 用户信息唯一性校验
     *
     * @param uemUserDto
     * @return
     * @throws
     * @author tujx
     */
    public String uemUserUniqueValidate(UemUserEditDTO uemUserDto) {
        String resultStr = "";
        String account = uemUserDto.getAccount();
        String mobile = uemUserDto.getMobile();
        String email = uemUserDto.getEmail();
        //判断用户是否使用过
        OrExpression uemUserExpression = QUemUser.account.eq$(account).or(QUemUser.mobile.eq$(mobile));
        OrExpression platformUserExpression = QSysPlatformUser.account.eq$(account).or(QSysPlatformUser.tel.eq$(mobile));
        if (StringUtils.isNotBlank(email)) {
            uemUserExpression = uemUserExpression.or(QUemUser.email.eq$(email));
            platformUserExpression = platformUserExpression.or(QSysPlatformUser.mail.eq$(email));
        }
        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(uemUserExpression)
                .execute();
        //客服表
        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser
                .select(QSysPlatformUser.sysPlatformUser.fieldContainer())
                .where(platformUserExpression)
                .execute();
        AtomicReference<Boolean> hadAccount = new AtomicReference<>(false);
        AtomicReference<Boolean> hadMobile = new AtomicReference<>(false);
        AtomicReference<Boolean> hadEmail = new AtomicReference<>(false);
        uemUserList.forEach(uemUser ->
                // 用户重复性校验
                uemUserValidate(uemUser, hadAccount, hadMobile, hadEmail, uemUserDto)
        );
        sysPlatformUserList.forEach(sysPlatformUser -> {
            if (StringUtils.equals(account, sysPlatformUser.getAccount())) {
                hadAccount.set(true);
            }
            if (StringUtils.equals(mobile, sysPlatformUser.getTel())) {
                hadMobile.set(true);
            }
            if (StringUtils.equals(email, sysPlatformUser.getMail())) {
                hadEmail.set(true);
            }
        });
        List<String> errorItems = new ArrayList<>();
        if (Boolean.TRUE.equals(hadAccount.get())) {
            errorItems.add("【用户名】");
        }
        if (Boolean.TRUE.equals(hadMobile.get())) {
            errorItems.add("【手机号】");
        }
        if (Boolean.TRUE.equals(hadEmail.get())) {
            errorItems.add("【邮箱】");
        }
        if (CollectionUtils.isNotEmpty(errorItems)) {
            resultStr = StringUtils.join(errorItems, "、") + "信息重复";
        }
        return resultStr;
    }

    /**
     * 用户信息重复校验
     *
     * @param uemUser
     * @param hadAccount
     * @param hadMobile
     * @param hadEmail
     * @param uemUserDto
     * @return
     * @author huanghwh
     * @date 2021/5/7 上午8:57
     */
    private void uemUserValidate(UemUser uemUser, AtomicReference<Boolean> hadAccount, AtomicReference<Boolean> hadMobile, AtomicReference<Boolean> hadEmail, UemUserEditDTO uemUserDto) {
        String account = uemUserDto.getAccount();
        String mobile = uemUserDto.getMobile();
        String email = uemUserDto.getEmail();
        if (!Objects.equals(uemUser.getUemUserId(), uemUserDto.getUemUserId())) {
            if (StringUtils.equals(account, uemUser.getAccount())) {
                hadAccount.set(true);
            }
            if (StringUtils.equals(mobile, uemUser.getMobile())) {
                hadMobile.set(true);
            }
            if (StringUtils.equals(email, uemUser.getEmail())) {
                hadEmail.set(true);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultHelper<String> saveUemUser(UemUserDto uemUserDto) {
        // 当前用户为客服用户
        User user = (User)defaultUserService.getCurrentLoginUser();
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(user.getUemUserId());
        // 判空
        if (Objects.isNull(sysPlatformUser)) {
            throw new RuntimeException("当前用户不是平台客服");
        }
        // 参数校验
        this.validSaveUemUserParam(uemUserDto);
        // 生成密码
        String randomPassword = MessageUtil.generateRandomPassword();
        // 加密
        String password = MD5EnCodeUtils.MD5EnCode(randomPassword).substring(8,24);
        // 二次加密
        password = MD5EnCodeUtils.MD5EnCode(password);
        uemUserDto.setPassword(password);
        // 新增用户
        log.info("平台客服新增用户account：{}", uemUserDto.getAccount());
        UemUser uemUser = this.addUemUser(uemUserDto);

        // 实名认证
        if (StringUtils.isNotBlank(uemUserDto.getName())) {
            log.info("平台客服新增用户，进行实名认证name：{}", uemUserDto.getName());
            this.addUemIdCard(uemUserDto, uemUser, sysPlatformUser);
        }

        // 异步发送短信通知
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("platformUserSaveUemUser-sendMsg-pool-%d").build();
        ThreadPoolExecutor threadPoolExecutor
                = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10), nameThreadFactory);
        threadPoolExecutor.execute(() -> this.sendMsgBySaveUemUser(uemUser, randomPassword));
        threadPoolExecutor.shutdown();

        return CommonResult.getSuccessResultData(randomPassword);
    }

    /**
     * 平台客服新增用户，发送短信提醒
     * @param uemUser 用户信息
     * @param randomPassword 随机密码
     */
    private void sendMsgBySaveUemUser(UemUser uemUser, String randomPassword) {
        // 短信VO
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信模板
        msgSmsApiVO.setSmsTemplateCode(platformUserAddUserMsgCode);
        // 消息宏参数map
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put("account", uemUser.getAccount());
        content.put("password", randomPassword);
        marco.put(CodeFinal.CONTENT, content);
        log.info("短信消息宏参数传参： {}", marco);
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);

        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(uemUser.getMobile());
        // 调用公共服务接口发送短信
        try{
            SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
            if (CodeFinal.SUCCESSEMAIL.equals(sendMsgReturnVo.getResultCode())){
                log.info("平台客服新增用户，短信发送成功");
            } else {
                log.error("平台客服新增用户，短信发送失败，失败原因：{}", sendMsgReturnVo.getResultMsg());
            }
        } catch (Exception e){
            log.error("调用推送服务失败：{}", e.getMessage(), e);
        }
    }

    /**
     * 新增实名认证
     * @param uemUserDto 客服新增的用户信息
     * @param uemUser 用户信息
     * @param sysPlatformUser 平台客服
     */
    private void addUemIdCard(UemUserDto uemUserDto, UemUser uemUser, SysPlatformUser sysPlatformUser) {
        // 实名信息
        UemIdCard uemIdCard = new UemIdCard();
        uemIdCard.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        // 用户id
        uemIdCard.setUemUserId(uemUser.getUemUserId());
        // 用户名
        uemIdCard.setName(uemUserDto.getName());
        // 性别：默认false-男
        uemIdCard.setSex(false);
        // 身份证
        uemIdCard.setIdCard(null);
        // 身份证正面
        String cardPositiveUrlId = this.uploadDefaultFile(CodeFinal.FILE_TYPE_ZERO, cardPositiveFilePath);
        uemIdCard.setCardPositiveUrlId(cardPositiveUrlId);
        // 身份证反面
        String cardBackUrlId = this.uploadDefaultFile(CodeFinal.FILE_TYPE_ONE, cardBackFilePath);
        uemIdCard.setCardBackUrlId(cardBackUrlId);
        // 审核信息
        uemIdCard.setAuditStatus(GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode());
        uemIdCard.setAuditTime(new Date());
        uemIdCard.setAuditor(sysPlatformUser.getSysPlatformUserId());
        // 新增实名信息
        QUemIdCard.uemIdCard.save(uemIdCard);

        // 更新用户信息
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        // 实名信息id
        uemUser.setUemIdCardId(uemIdCard.getUemIdCardId());
        // 用户名
        uemUser.setName(uemIdCard.getName());
        // 性别
        uemUser.setSex(uemIdCard.getSex());
        // 身份证
        uemUser.setIdCard(uemIdCard.getIdCard());
        // 身份证正面
        uemUser.setCardPositiveUrlId(uemIdCard.getCardPositiveUrlId());
        // 身份证反面
        uemUser.setCardBackUrlId(uemIdCard.getCardBackUrlId());
        // 审核信息
        uemUser.setAuditStatus(uemIdCard.getAuditStatus());
        uemUser.setAuditRemark(uemIdCard.getAuditRemark());
        uemUser.setAuditTime(uemIdCard.getAuditTime());
        uemUser.setAuditor(uemIdCard.getAuditor());
        // 更新
        QUemUser.uemUser.save(uemUser);
    }

    /**
     * 上传默认文件
     * @param fileType 文件类型
     * @param filePath 文件路径
     * @return 文件id
     */
    private String uploadDefaultFile(String fileType, String filePath) {
        log.info("上传默认文件，文件类型：{}，文件路径：{}", fileType, filePath);
        MultipartFile multipartFile;
        File file = new File(filePath);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error("{}：文件不存在", filePath, e);
            throw new BusinessRuntimeException(filePath + "文件不存在");
        }
        try {
            // 设置文件
            multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (IOException e) {
            log.error("new MockMultipartFile()，设置文件失败", e);
            throw new BusinessRuntimeException("上传文件，设置文件失败");
        }
        try {
            fileInputStream.close();
        } catch (IOException e) {
            log.error("fileInputStream.close()关闭文件流异常");
            throw new BusinessRuntimeException("关闭文件流异常");
        }
        long start = System.currentTimeMillis();
        log.info("uploadDeFaultFile()调用上传文件服务开始,systemId=" + MessageUtil.getApplicationCode() + ",调用开始时间:" + start);
        FastDfsUploadResult result = shareFileInterface.uploadExternalFile(MessageUtil.getApplicationCode(), fileType, file.getName(), multipartFile);
        long end = System.currentTimeMillis();
        log.info("uploadDeFaultFile()调用上传文件服务结束,调用结束时间:" + end + "，花费时间=" + (end-start));
        log.info("result" + result);
        return result.getFileKey();
    }

    /**
     * 新增用户
     * @param uemUserDto 用户信息
     * @return 返回新增的用户信息
     */
    private UemUser addUemUser(UemUserDto uemUserDto) {
        UemUser uemUser = new UemUser();
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        // 账号
        uemUser.setAccount(uemUserDto.getAccount());
        // 姓名
        uemUser.setName(uemUserDto.getName());
        // 手机号
        uemUser.setMobile(uemUserDto.getMobile());
        // 密码
        uemUser.setPassword(uemUserDto.getPassword());
        // 用户类型：普通用户
        uemUser.setUserType(CodeFinal.USER_TYPE_ZERO);
        // 用户来源：客服新增
        uemUser.setSource(GlobalEnum.UserSource.CUSTOMER_SERVICE_ADD.getCode());

        // 启用
        uemUser.setIsValid(true);
        uemUser.setInvalidTime(new Date());
        // 同意协议
        uemUser.setIsAgreemeent(true);
        QUemUser.uemUser.save(uemUser);
        return uemUser;
    }

    /**
     * 校验保存用户信息参数
     * @param uemUserDto 用户信息
     */
    private void validSaveUemUserParam(UemUserDto uemUserDto) {
        // 校验用户名
        if (StringUtils.isBlank(uemUserDto.getAccount())) {
            throw new RuntimeException("用户名不能为空");
        }
        // 账号是否有效
        List<UemUser> accountUser = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.account.eq$(uemUserDto.getAccount())).execute();
        List<SysPlatformUser> accountSysPlatformUser = QSysPlatformUser.sysPlatformUser.select().where(QSysPlatformUser.account.eq$(uemUserDto.getAccount())).execute();
        if(CollectionUtils.isNotEmpty(accountUser) || CollectionUtils.isNotEmpty(accountSysPlatformUser)){
            throw new RuntimeException("该用户名已注册过！");
        }

        // 校验手机号
        if (StringUtils.isBlank(uemUserDto.getMobile())) {
            throw new RuntimeException("手机号不能为空");
        }
        // 手机号是否有效
        List<UemUser> uemUserList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.mobile.eq$(uemUserDto.getMobile())).execute();
        List<SysPlatformUser> sysPlatformUserList = QSysPlatformUser.sysPlatformUser.select().where(QSysPlatformUser.tel.eq$(uemUserDto.getMobile())).execute();
        if(CollectionUtils.isNotEmpty(uemUserList) || CollectionUtils.isNotEmpty(sysPlatformUserList)){
            throw new RuntimeException("该手机号已注册过！");
        }

        // 校验企业是否审核通过、有效、非综合平台、承运商
        if (StringUtils.isNotBlank(uemUserDto.getBlindCompanny())) {
            UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(Long.valueOf(uemUserDto.getBlindCompanny()));
            log.info("平台客服新增用户，绑定企业id：{}，对应的企业信息为：{}", uemUserDto.getBlindCompanny(), uemCompany);
            // 校验企业是否存在
            if (Objects.isNull(uemCompany)) {
                throw new RuntimeException("企业不存在");
            }
            // 是否启用
            if (!Objects.equals(uemCompany.getIsValid(), Boolean.TRUE)) {
                throw new RuntimeException("企业未启用");
            }
            // 校验企业是否审核通过
            if (!Objects.equals(uemCompany.getAuditStatus(), GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode())) {
                throw new RuntimeException("企业未审核通过");
            }
            // 是否综合平台企业
            if (Objects.equals(uemCompany.getDataSource(), CodeFinal.DATA_SOURCE_THREE)) {
                throw new RuntimeException("企业数据来源不能为综合平台");
            }
            // 是否非注册承运商
            if (Objects.equals(uemCompany.getCarrierType(), GlobalEnum.CarrierType.NO_REGISTER.getCode())) {
                throw new RuntimeException("企业为非注册承运商");
            }
        }

        // 校验是否企业管理员
        if (Objects.equals(uemUserDto.getIsCompanyManager(), Boolean.TRUE)) {
            // 为企业管理员，绑定企业id不能为空
            if (Objects.isNull(uemUserDto.getBlindCompanny())) {
                throw new RuntimeException("为企业管理员时，绑定企业不能为空");
            }
        } else {
            uemUserDto.setIsCompanyManager(Boolean.FALSE);
        }
    }

    @Override
    public ResultHelper<String> resetUemUserPassword(Long uemUserId) {
        // 校验用户信息
        UemUser uemUser = this.validUserInfo(uemUserId);
        log.info("平台客服重置用户：{}的密码", uemUserId);
        // 生成密码
        String randomPassword = MessageUtil.generateRandomPassword();
        // 加密
        String password = MD5EnCodeUtils.MD5EnCode(randomPassword).substring(8,24);
        // 二次加密
        password = MD5EnCodeUtils.MD5EnCode(password);
        // 更新用户密码
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setPassword(password);
        QUemUser.uemUser.save(uemUser);

        // 异步发送短信通知
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("platformUserResetUserPassword-sendMsg-pool-%d").build();
        ThreadPoolExecutor threadPoolExecutor
                = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10), nameThreadFactory);
        threadPoolExecutor.execute(() -> this.sendMsgByResetUserPassword(uemUser, randomPassword));
        threadPoolExecutor.shutdown();

        return CommonResult.getSuccessResultData(randomPassword);
    }

    /**
     * 校验用户信息
     * @param uemUserId 用户id
     */
    private UemUser validUserInfo(Long uemUserId) {
        // 当前用户为客服用户
        User user = (User)defaultUserService.getCurrentLoginUser();
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(user.getUemUserId());
        // 判空
        if (Objects.isNull(sysPlatformUser)) {
            throw new RuntimeException("当前用户不是平台客服");
        }
        // 校验用户id
        if (Objects.isNull(uemUserId)) {
            throw new RuntimeException("用户id不能为空");
        }
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        // 判空
        if (Objects.isNull(uemUser)) {
            log.error("平台客服操作的用户id为：{}", uemUserId);
            throw new RuntimeException("用户不存在");
        }
        return uemUser;
    }

    /**
     * 平台客服重置用户密码，发送短信提醒
     * @param uemUser 用户信息
     * @param randomPassword 随机密码
     */
    private void sendMsgByResetUserPassword(UemUser uemUser, String randomPassword) {
        // 短信VO
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信模板
        msgSmsApiVO.setSmsTemplateCode(platformUserResetUserPassword);
        // 消息宏参数map
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put("password", randomPassword);
        marco.put(CodeFinal.CONTENT, content);
        log.info("短信消息宏参数传参： {}", marco);
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);

        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(uemUser.getMobile());
        // 调用公共服务接口发送短信
        try{
            SendMsgReturnVo<String> sendMsgReturnVo = msgApiService.sendMsg(msgSmsApiVO);
            if (CodeFinal.SUCCESSEMAIL.equals(sendMsgReturnVo.getResultCode())){
                log.info("平台客服重置用户密码，短信发送成功");
            } else {
                log.error("平台客服重置用户密码，短信发送失败，失败原因：{}", sendMsgReturnVo.getResultMsg());
            }
        } catch (Exception e){
            log.error("调用推送服务失败：{}", e.getMessage(), e);
        }
    }

}
