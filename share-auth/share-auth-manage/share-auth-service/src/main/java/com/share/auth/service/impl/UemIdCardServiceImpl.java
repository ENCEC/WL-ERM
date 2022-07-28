package com.share.auth.service.impl;


import com.alibaba.fastjson.JSON;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.ds.generator.SnowFlakeGenerator;
import com.gillion.ec.core.exceptions.BusinessRuntimeException;
import com.google.common.collect.ImmutableMap;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.QueryUserIdCardDTO;
import com.share.auth.domain.UemCompanyDto;
import com.share.auth.domain.UemIdCardDto;
import com.share.auth.enums.GlobalEnum;
import com.share.auth.model.entity.SysPlatformUser;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemIdCard;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.querymodels.*;
import com.share.auth.model.vo.UserIdCardQueryVO;
import com.share.auth.service.MsgSendService;
import com.share.auth.service.UemIdCardService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.HttpUtils;
import com.share.auth.util.MessageUtil;
import com.share.file.api.ShareFileInterface;
import com.share.file.domain.*;
import com.share.message.api.MessageService;
import com.share.message.api.MsgApiService;
import com.share.message.domain.MsgMessageVO;
import com.share.message.domain.MsgSmsApiVO;
import com.share.message.domain.UserVO;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author xrp
 * @Date 20201021
 */
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class UemIdCardServiceImpl implements UemIdCardService {

    @Autowired
    private DefaultUserService userService;

    @Autowired
    private ShareFileInterface shareFileInterface;
    /**
     * 审批成功短信模版编号
     */
    @Value("${msg.sms.template.review_success}")
    private String reviewSuccessMsgTemplate;

    /**
     * 审批失败短信模版编号
     */
    @Value("${msg.sms.template.review_failed}")
    private String reviewFailedMsgTemplate;
    @Autowired
    private MsgApiService msgApiService;

    @Autowired
    private UemUserService uemUserService;

    @Autowired
    private MsgSendService msgSendService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SnowFlakeGenerator snowFlakeGenerator;

    /**
     * 账户(字段名称)
     */
    private static final String ACCOUNT = "account";
    /**
     * 手机号(字段名称)
     */
    private static final String MOBILE = "mobile";
    /**
     * 资源(字段名称)
     */
    private static final String SOURCE = "source";
    /**
     * 应用名称(字段名称)
     */
    private static final String ORI_APPLICATION_NAME = "oriApplicationName";

    /**
     * 新增实名认证
     *
     * @param uemIdCardDto 实名信息表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Object> saveUemIdCard(UemIdCardDto uemIdCardDto) {

//        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
//        if (Objects.isNull(userInfoModel) || Objects.isNull(userInfoModel.getUemUserId())) {
//            return CommonResult.getFaildResultData("无法获取当前登录信息，请重新登录");
//        }
//        //名字
//        String name = uemIdCardDto.getName();
//        Boolean sex = uemIdCardDto.getSex();
//        //身份证
//        String idCard = uemIdCardDto.getIdCard();
//        //用户Id
//        Long uemUserId = userInfoModel.getUemUserId();
//        //身份证正面
//        String cardPositiveUrlId = uemIdCardDto.getCardPositiveUrlId();
//        //身份证反面
//        String cardBackUrlId = uemIdCardDto.getCardBackUrlId();
//        UemIdCard uemIdCard = new UemIdCard();
//        uemIdCard.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
//        uemIdCard.setUemUserId(uemUserId);
//        uemIdCard.setName(name);
//        uemIdCard.setSex(sex);
//        uemIdCard.setIdCard(idCard);
//        uemIdCard.setAuditStatus(CodeFinal.AUDIT_STATUS_ZERO);
//        if (!StringUtils.isEmpty(cardPositiveUrlId)) {
//            uemIdCard.setCardPositiveUrlId(cardPositiveUrlId);
//        }
//        if (!StringUtils.isEmpty(cardBackUrlId)) {
//            uemIdCard.setCardBackUrlId(cardBackUrlId);
//        }
//
//        int saveCount = QUemIdCard.uemIdCard.save(uemIdCard);
//        log.info("uemIdCardId" + uemIdCard.getUemIdCardId());
//
//        int updateCount = QUemUser.uemUser.update(QUemUser.name, QUemUser.sex, QUemUser.idCard, QUemUser.auditStatus, QUemUser.uemIdCardId, QUemUser.cardPositiveUrlId, QUemUser.cardBackUrlId)
//                .where(QUemUser.uemUserId.eq(":uemUserId"))
//                .execute(name, sex, idCard, CodeFinal.AUDIT_STATUS_ZERO, uemIdCard.getUemIdCardId(), cardPositiveUrlId, cardBackUrlId, uemUserId);
//
//        if (saveCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM && updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
//            //短信通知平台客服
//            msgSendService.notifyAuditRealNameAuth();
//            return CommonResult.getSuccessResultData("实名认证审核中...");
//        } else {
//            return CommonResult.getFaildResultData("实名认证失败");
//        }
        return null;
    }


    /**
     * 新增企业
     *
     * @param uemCompanyDto 企业信息表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @Override
    public ResultHelper<Map<String, Object>> saveUemCompany(UemCompanyDto uemCompanyDto) {
//        //获取当前用户信息
//        AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
//        // 绑定的企业
//        Long blindCompannyBefore = user.getBlindCompanny();
//        //用户表ID
//        Long uemUserId = user.getUemUserId();
//        //企业中文名称
//        String companyNameCn = uemCompanyDto.getCompanyNameCn();
//        if (StringUtils.isEmpty(uemCompanyDto.getFileUrlId())) {
//            return CommonResult.getFaildResultData("企业证书不能为空");
//        }
//        if (Objects.isNull(uemUserId)) {
//            return CommonResult.getFaildResultData("用户表ID不能为空");
//        }
//        // 保存公司
//        UemCompany uemCompany = this.setUemCompanyInfo(uemCompanyDto);
//        uemCompany.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
//        int savedUemCompany = QUemCompany.uemCompany.save(uemCompany);
//        log.info(uemCompany.getUemCompanyId().toString());
//        QUemCompany.uemCompany.selective(QUemCompany.uemCompanyHistoryId).execute(uemCompany);
//
//        // 绑定公司
//        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
//
//        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//        uemUser.setBlindCompanny(uemCompany.getUemCompanyId());
//        uemUser.setBlindCompannyTime(new Date());
//        uemUser.setUemUserId(uemUserId);
//        QUemUser.uemUser.save(uemUser);

        return CommonResult.getFaildResultData("提交企业失败");
    }

    /**
     * @return void
     * @Author zhuyp
     * @Description 保存消息记录和消息提醒
     * @Date 2021/11/4 16:37
     * @Param UemCompanyDto
     **/
    private void saveMessageRecordAndNotifier(String companyNameCn) {
        MsgMessageVO msgMessageVO = new MsgMessageVO();
        //获取物口办用户信息
        List<UserVO> userList = DSContext.customization("CZT_selectUserFromWKB").
                select().mapperTo(UserVO.class).execute();
        //查询平台客服用户
        List<SysPlatformUser> platformUserList = QSysPlatformUser.sysPlatformUser
                .select(QSysPlatformUser.sysPlatformUserId,
                        QSysPlatformUser.name)
                .where(QSysPlatformUser.isValid.eq$(true).and(QSysPlatformUser.isLocked.eq$(false)))
                .mapperTo(SysPlatformUser.class).execute();
        platformUserList.forEach(user->{
            userList.add(UserVO.builder()
                    .userId(user.getSysPlatformUserId())
                    .userName(user.getName()).build());
        });
        String messageContent = companyNameCn + "企业申请注册平台用户";
        // 主键
        msgMessageVO.setMsgMessageId(snowFlakeGenerator.next());
        //发送方系统id 为空
        msgMessageVO.setBusinessSystemId(null);
        //接收人
        msgMessageVO.setUserVOList(userList);
        //创建时间
        msgMessageVO.setCreateTime(new Date());
        //是否成功
        msgMessageVO.setIsSuccess(true);
        //消息内容
        msgMessageVO.setMessageContent(messageContent);
        //消息标题
        msgMessageVO.setMessageTitle("企业注册提醒");
        //messageType
        msgMessageVO.setMessageType("1");
        //失败原因
        msgMessageVO.setReason(null);
        //接收方系统id
        msgMessageVO.setTargetSystemId("平台门户系统");
        try {
            messageService.saveMsgMessage(msgMessageVO);
        } catch (Exception e) {
            throw new BusinessRuntimeException("保存消息记录、消息提醒失败");
        }
    }

    /**
     * 设置公司信息
     *
     * @param uemCompanyDto 公司信息
     * @return 公司信息
     */
    private UemCompany setUemCompanyInfo(UemCompanyDto uemCompanyDto) {
        UemCompany uemCompany = new UemCompany();
        // 物流交换代码
        uemCompany.setCompanyCode(uemCompanyDto.getCompanyCode());
        // 企业或机构类型
        uemCompany.setOrganizationType(uemCompanyDto.getOrganizationType());
        // 企业中文名称
        uemCompany.setCompanyNameCn(uemCompanyDto.getCompanyNameCn());
        // 企业简称
        uemCompany.setCompanyAbbreviName(uemCompanyDto.getCompanyAbbreviName());
        //法人类型
        uemCompany.setLegalType(uemCompanyDto.getLegalType());
        //法人名称
        uemCompany.setLegalName(uemCompanyDto.getLegalName());
        //法人身份证
        uemCompany.setLegalCard(uemCompanyDto.getLegalCard());
        //企业联系人
        uemCompany.setContact(uemCompanyDto.getContact());
        //联系人手机
        uemCompany.setContactTel(uemCompanyDto.getContactTel());
        //联系人邮箱
        uemCompany.setContactMail(uemCompanyDto.getContactMail());
        //统一社会信用代码或机构代码
        uemCompany.setOrganizationCode(uemCompanyDto.getOrganizationCode());
        //企业证书
        uemCompany.setFileUrlId(uemCompanyDto.getFileUrlId());
        //所在地区 城市
        uemCompany.setLocCityName(uemCompanyDto.getLocCityName());
        //所在地区 省份
        uemCompany.setLocProvinceName(uemCompanyDto.getLocProvinceName());
        //所在地区 地区
        uemCompany.setLocDistrictName(uemCompanyDto.getLocDistrictName());
        //所在地区 国家
        uemCompany.setLocCountryName(uemCompanyDto.getLocCountryName());
        //详细地址
        uemCompany.setLocAddress(uemCompanyDto.getLocAddress());
        //企业电话
        uemCompany.setCompanyTel(uemCompanyDto.getCompanyTel());
        uemCompany.setAuditStatus(CodeFinal.AUDIT_STATUS_ZERO);
        uemCompany.setDataSource(CodeFinal.DATA_SOURCE_ZERO);
        uemCompany.setIsValid(true);
        //上级企业名称
        uemCompany.setBelongCompanyName(uemCompanyDto.getBelongCompanyName());
        //上级企业
        String belongCompany = uemCompanyDto.getBelongCompany();
        if (!StringUtils.isEmpty(belongCompany)) {
            uemCompany.setBelongCompany(Long.valueOf(belongCompany));
        }
        //查看下级数据
        Boolean isSuperior = uemCompanyDto.getIsSuperior();
        if (isSuperior == null) {
            uemCompany.setIsSuperior(false);
        } else {
            uemCompany.setIsSuperior(isSuperior);
        }
        if (Objects.nonNull(uemCompany.getBelongCompany())) {
            UemCompany belongUemCompany = QUemCompany.uemCompany.selectOne().byId(uemCompany.getBelongCompany());
            uemCompany.setTopCompany(belongUemCompany.getTopCompany() == null ? belongUemCompany.getUemCompanyId() : belongUemCompany.getTopCompany());
        }
        //国家code
        uemCompany.setLocCountryCode(uemCompanyDto.getLocCountryCode());
        //省份Code
        uemCompany.setLocProvinceCode(uemCompanyDto.getLocProvinceCode());
        //城市code
        uemCompany.setLocCityCode(uemCompanyDto.getLocCityCode());
        //县区code
        uemCompany.setLocDistrictCode(uemCompanyDto.getLocDistrictCode());
        uemCompany.setCarrierType("0");
        uemCompany.setIsFocusCompany(Objects.isNull(uemCompanyDto.getIsFocusCompany()) ? false : uemCompanyDto.getIsFocusCompany());
        return uemCompany;
    }

    /**
     * 绑定原物流交换代码
     *
     * @param password 密码
     * @return String
     * @aram userid 物流交换代码
     * @author xrp
     */
    @Override
    public String bingOriginalLogisticsSwap(String userid, String password) {


        //服务资源
        String resource = CodeFinal.RESOURCESERVICE;

        String url = CodeFinal.URL;

        Map<String, String> map = new HashMap<>(16);
        map.put(CodeFinal.USERID, userid);
        map.put(CodeFinal.PASS, password);
        map.put(CodeFinal.RESOURCE, resource);
        String responseStr = null;
        try {
            responseStr = HttpUtils.sendGetRequest(url, map, "UTF8");
            log.info("responseStr:", responseStr);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseStr;
    }

    /**
     * 文件上传
     *
     * @param fileType 文件类型
     * @param fileName 文件名称
     * @param file     文件
     * @return FastDfsUploadResult
     * @author xrp
     */
    @Override
    public FastDfsUploadResult upload(String fileType, String fileName,String businessSystemId, MultipartFile file) {
        long start = System.currentTimeMillis();
        //文件限制类型
        String fileCode =  MessageUtil.getApplicationCode();
        if(Objects.nonNull(businessSystemId)){
            fileCode = businessSystemId;
        }
        log.info("接口UemIdCardServiceImpl.upload()调用上传文件服务开始,systemId=" + fileCode + ",调用开始时间:" + start);
        FastDfsUploadResult result = shareFileInterface.uploadExternalFile(fileCode, fileType, fileName, file);
        long end = System.currentTimeMillis();
        log.info("接口UemIdCardServiceImpl.upload()调用上传文件服务结束,调用结束时间:" + end + "，花费时间=" + (end - start));
        log.info("result" + result);
        return result;
    }

    /**
     * 文件下载
     *
     * @param fileKey 文件Id
     * @return FastDfsDownloadResult
     * @author xrp
     */
    @Override
    public void download(String fileKey, HttpServletResponse response) throws IOException {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setSystemId(MessageUtil.getApplicationCode());
        fileInfoVO.setFileKey(fileKey);
        FastDfsDownloadResult result = shareFileInterface.downloadExternalFile(fileInfoVO);
        if (Objects.isNull(result)) {
            log.info("{}文件下载失败，失败原因{}", fileKey, "文件服务调用异常！");
            // 全局异常处理
            throw new RuntimeException("文件服务调用异常！");
        } else {
            int successCode = 200;
            response.setStatus(successCode);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            if (StringUtils.isNotEmpty(result.getFileName())) {
                try (OutputStream outputStream = response.getOutputStream()) {
                    String fileName = URLEncoder.encode(new String(result.getFileName().getBytes(), StandardCharsets.UTF_8), "UTF-8");
                    log.info("用户{}下载了fileKey为{}的文件", userInfoModel.getUemUserId(), result.getFileName());
                    response.setContentType("mutipart/form-data");
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                    response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                    byte[] res = Base64.decodeBase64(result.getFile());
                    int len;
                    InputStream in = new ByteArrayInputStream(res);
                    byte[] bytes = new byte[1024];
                    while ((len = in.read(bytes)) > 0) {
                        outputStream.write(bytes, 0, len);
                    }
                    outputStream.flush();
                } catch (Exception e) {
                    log.error("文件下载失败：" + e.getMessage());
                    PrintWriter out = response.getWriter();
                    out.write(JSON.toJSONString(CommonResult.getFaildResultData("文件下载失败，请联系客服")));
                    out.flush();
                    out.close();
                }
            } else {
                log.info("{}文件下载失败，失败原因{}", fileKey, result);
                PrintWriter out = response.getWriter();
                out.write(JSON.toJSONString(CommonResult.getFaildResultData(result.getResultMsg())));
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 文件删除
     *
     * @param fileKey 文件Id
     * @return FastDfsDownloadResult
     * @author xrp
     */
    @Override
    public FastDfsDeleteResult deleteFile(String fileKey) {
        if (StringUtils.isEmpty(fileKey)) {
            FastDfsDeleteResult fastDfsDeleteResult = new FastDfsDeleteResult();
            String errorCode = "1";
            fastDfsDeleteResult.setResultCode(errorCode);
            fastDfsDeleteResult.setResultMsg("删除失败，文件FileKey为空");
            return fastDfsDeleteResult;
        }
        //使用方系统ID
        String systemId = MessageUtil.getApplicationCode();
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setSystemId(systemId);
        fileInfoVO.setFileKey(fileKey);
        return shareFileInterface.deleteFile(fileInfoVO);
    }

    /**
     * 文件获取完整路径
     *
     * @param fileKey 文件Id
     * @return FastDfsTokenResult
     * @author xrp
     */
    @Override
    public FastDfsTokenResult getFullUrl(String fileKey) {
        if (StringUtils.isEmpty(fileKey)) {
            FastDfsTokenResult fastDfsTokenResult = new FastDfsTokenResult();
            String paramErrorCode = "400";
            fastDfsTokenResult.setResultCode(paramErrorCode);
            fastDfsTokenResult.setResultMsg("获取文件fileKey不能为空");
            return fastDfsTokenResult;
        }
        //使用方系统ID
        String systemId = MessageUtil.getApplicationCode();
        if (StringUtils.isEmpty(systemId)) {
            log.warn("缺少配置当前系统编码的配置项：msg.system");
        }
        FileInfoVO fileInfoVO = new FileInfoVO();
//        fileInfoVO.setSystemId(systemId);
        fileInfoVO.setFileKey(fileKey);
        return shareFileInterface.getFullUrl(fileInfoVO);
    }


    /**
     * 查询文件列表
     *
     * @param fileKey 文件Id
     * @return xrp
     */
    @Override
    public FileInfoReturnVo<Object> selectFilesList(String fileKey) {
        //使用方系统ID
        String systemId = MessageUtil.getApplicationCode();
        if (StringUtils.isEmpty(systemId)) {
            log.warn("缺少配置当前系统编码的配置项：msg.system");
        }
        FileListParamsVo fileListParamsVo = new FileListParamsVo();
        fileListParamsVo.setSystemId(systemId);
        fileListParamsVo.setFileKey(fileKey);
        fileListParamsVo.setPageSize(CodeFinal.SIZE);
        fileListParamsVo.setCurrentPage(CodeFinal.SIZE);
        return shareFileInterface.selectFilesList(fileListParamsVo);
    }


    /**
     * @Author:chenxf
     * @Description: 分页查询用户实名认证记录
     * @Date: 15:56 2020/10/29
     * @Param: [userIdCardQueryVO]
     * @Return:com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.QueryUserIdCardDTO>
     */
    @Override
    public Page<QueryUserIdCardDTO> queryByPage(UserIdCardQueryVO userIdCardQueryVO) {
        // 添加用户名全模糊匹配查询
        if (StringUtils.isNotEmpty(userIdCardQueryVO.getAccount())) {
            userIdCardQueryVO.setAccount("%" + userIdCardQueryVO.getAccount() + "%");
        }
        // 添加姓名全模糊匹配查询
        if (StringUtils.isNotEmpty(userIdCardQueryVO.getName())) {
            userIdCardQueryVO.setName("%" + userIdCardQueryVO.getName() + "%");
        }
        // 手机号模糊匹配
        if (StringUtils.isNotBlank(userIdCardQueryVO.getMobile())) {
            userIdCardQueryVO.setMobile("%" + userIdCardQueryVO.getMobile() + "%");
        } else {
            userIdCardQueryVO.setMobile(null);
        }
        Page<QueryUserIdCardDTO> queryUserIdCardDtoPage;
        if (CodeFinal.AUDIT_STATUS_ALL.equals(userIdCardQueryVO.getAuditStatus())) {
            // 查询所有审批状态的数据
            queryUserIdCardDtoPage =
                    QUemIdCard.uemIdCard.select(
                            QUemIdCard.uemIdCardId,
                            QUemIdCard.uemUserId,
                            QUemIdCard.name,
                            QUemIdCard.auditStatus,
                            QUemIdCard.auditTime,
                            QUemIdCard.createTime,
                            QUemIdCard.uemIdCard.chain(QUemUser.account).as(ACCOUNT),
                            QUemIdCard.uemIdCard.chain(QUemUser.mobile).as(MOBILE),
                            QUemIdCard.uemIdCard.chain(QUemUser.source).as(SOURCE),
                            QUemIdCard.uemIdCard.chain(QUemUser.uemUser).chain(QSysApplication.applicationName).as(ORI_APPLICATION_NAME),
                            QUemIdCard.uemIdCard.chain(QUemUser.uemUser).chain(QUemCompany.companyNameCn).as("companyNameCn")
                    )
                            .where(
                                    QUemIdCard.uemIdCard.chain(QUemUser.account).like(":account")
                                            .and(QUemIdCard.name.like(":name"))
                                            .and(QUemIdCard.uemIdCard.chain(QUemUser.mobile).like(":mobile"))
                            )
                            .paging(userIdCardQueryVO.getCurrentPage(), userIdCardQueryVO.getPageSize())
                            .sorting(QUemIdCard.createTime.desc())
                            .mapperTo(QueryUserIdCardDTO.class)
                            .execute(userIdCardQueryVO);
        } else {
            // 查询指定审批状态的数据
            queryUserIdCardDtoPage =
                    QUemIdCard.uemIdCard.select(
                            QUemIdCard.uemIdCardId,
                            QUemIdCard.uemUserId,
                            QUemIdCard.name,
                            QUemIdCard.auditStatus,
                            QUemIdCard.auditTime,
                            QUemIdCard.createTime,
                            QUemIdCard.uemIdCard.chain(QUemUser.account).as(ACCOUNT),
                            QUemIdCard.uemIdCard.chain(QUemUser.mobile).as(MOBILE),
                            QUemIdCard.uemIdCard.chain(QUemUser.source).as(SOURCE),
                            QUemIdCard.uemIdCard.chain(QUemUser.uemUser).chain(QSysApplication.applicationName).as(ORI_APPLICATION_NAME),
                            QUemIdCard.uemIdCard.chain(QUemUser.uemUser).chain(QUemCompany.companyNameCn).as("companyNameCn")
                    )
                            .where(
                                    QUemIdCard.uemIdCard.chain(QUemUser.account).like(":account")
                                            .and(QUemIdCard.name.like(":name"))
                                            .and(QUemIdCard.auditStatus.eq(":auditStatus"))
                                            .and(QUemIdCard.uemIdCard.chain(QUemUser.mobile).like(":mobile"))
                            )
                            .paging(userIdCardQueryVO.getCurrentPage(), userIdCardQueryVO.getPageSize())
                            .sorting(QUemIdCard.createTime.desc())
                            .mapperTo(QueryUserIdCardDTO.class)
                            .execute(userIdCardQueryVO);
        }
        return queryUserIdCardDtoPage;
    }

    /**
     * @Author:chenxf
     * @Description: 根据实名认证表id查询用户信息
     * @Date: 15:57 2020/10/29
     * @Param: [uemIdCardId]
     * @Return:com.share.auth.domain.QueryUserIdCardDTO
     */
    @Override
    public QueryUserIdCardDTO queryByUemIdCardId(Long uemIdCardId) {
        // 根据实名认证表id查询数据
        List<QueryUserIdCardDTO> queryUserIdCardDTO =
                QUemIdCard.uemIdCard.select(
                        QUemIdCard.uemIdCardId,
                        QUemIdCard.uemIdCard.chain(QUemUser.source).as(SOURCE),
                        QUemIdCard.uemIdCard.chain(QUemUser.uemUser).chain(QSysApplication.applicationName).as(ORI_APPLICATION_NAME),
                        QUemIdCard.uemIdCard.chain(QUemUser.account).as(ACCOUNT),
                        QUemIdCard.uemIdCard.chain(QUemUser.mobile).as(MOBILE),
                        QUemIdCard.uemIdCard.chain(QUemUser.email).as("email"),
                        QUemIdCard.uemIdCard.chain(QUemUser.createTime).as("registeredTime"),
                        QUemIdCard.name,
                        QUemIdCard.sex,
                        QUemIdCard.idCard,
                        QUemIdCard.cardPositiveUrlId,
                        QUemIdCard.cardBackUrlId,
                        QUemIdCard.createTime,
                        QUemIdCard.auditor,
                        QUemIdCard.uemIdCard.chain(QSysPlatformUser.name).as("auditorName"),
                        QUemIdCard.auditStatus,
                        QUemIdCard.auditRemark,
                        QUemIdCard.auditTime
                ).where(QUemIdCard.uemIdCardId.eq(":uemIdCardId")).mapperTo(QueryUserIdCardDTO.class)
                        .execute(ImmutableMap.of("uemIdCardId", uemIdCardId));
        return queryUserIdCardDTO.get(0);
    }

    /**
     * @Author:chenxf
     * @Description: 实名认证审核操作
     * @Date: 16:03 2020/10/29
     * @Param: [queryUserIdCardDTO]
     * @Return:void
     */
    @Override
    public void reviewIdCard(QueryUserIdCardDTO queryUserIdCardDTO) {
        //获取当前用户信息
        AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();

        Date date = new Date();
        // 根据实名认证id查询用户信息，并更新实名认证表信息数据
        UemIdCard uemIdCard = QUemIdCard.uemIdCard.selectOne().byId(queryUserIdCardDTO.getUemIdCardId());
        if (Objects.isNull(uemIdCard)) {
            // 全局异常处理
            throw new RuntimeException("当前实名认证信息不存在，请确认！");
        }
        if (!CodeFinal.AUDIT_STATUS_ZERO.equals(uemIdCard.getAuditStatus())) {
            // 全局异常处理
            throw new RuntimeException("当前实名认证信息已被审核，请确认！");
        }
        uemIdCard.setAuditRemark(queryUserIdCardDTO.getAuditRemark());
        uemIdCard.setAuditTime(date);
        uemIdCard.setAuditor(user.getUemUserId());
        uemIdCard.setAuditStatus(queryUserIdCardDTO.getAuditStatus());
        uemIdCard.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QUemIdCard.uemIdCard.save(uemIdCard);
        // 根据用户id查询用户信息，并更新用户信息数据
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemIdCard.getUemUserId());
        uemUser.setAuditRemark(queryUserIdCardDTO.getAuditRemark());
        uemUser.setAuditTime(date);
        uemUser.setAuditor(user.getUemUserId());
        uemUser.setAuditStatus(queryUserIdCardDTO.getAuditStatus());
//        uemUser.setUemIdCardId(uemIdCard.getUemIdCardId());
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        // 短信发送vo
        MsgSmsApiVO msgSmsApiVO = new MsgSmsApiVO();
        msgSmsApiVO.setSystemId(MessageUtil.getApplicationCode());
        // 短信发送手机号
        msgSmsApiVO.setAcceptNo(uemUser.getMobile());
        // 消息宏参数mgp
        Map<String, Object> marco = new HashMap<>(16);
        Map<String, Object> content = new HashMap<>(16);
        content.put("messageType", "实名认证");
        if (CodeFinal.AUDIT_STATUS_ONE.equals(queryUserIdCardDTO.getAuditStatus())) {
            if (Objects.equals(uemUser.getSource(), GlobalEnum.UserSource.ADMIN_ADD.getCode())) {
                uemUser.setUserType(CodeFinal.USER_TYPE_ONE);
            }
            msgSmsApiVO.setSmsTemplateCode(reviewSuccessMsgTemplate);
        } else {
//            uemUser.setUemIdCardId(null);
            uemUser.setName(null);
            uemUser.setIdCard(null);
            uemUser.setIsDisplayed(null);
            uemUser.setCardBackUrlId(null);
            uemUser.setCardPositiveUrlId(null);
            msgSmsApiVO.setSmsTemplateCode(reviewFailedMsgTemplate);
            if (StringUtils.isNotEmpty(queryUserIdCardDTO.getAuditRemark())) {
                content.put("reviewRemark", "拒绝信息：" + queryUserIdCardDTO.getAuditRemark() + ",");
            } else {
                content.put("reviewRemark", "");
            }
        }
        QUemUser.uemUser.save(uemUser);
        marco.put(CodeFinal.CONTENT, content);
        log.info("短信消息宏参数传参： " + marco.toString());
        // 短信消息宏参数
        msgSmsApiVO.setMarco(marco);
        log.info("实名认证审核，开始发送短信，时间" + System.currentTimeMillis());
        // 调用公共服务接口发送短信
        msgApiService.sendMsg(msgSmsApiVO);
        log.info("实名认证审核，发送短信结束，时间" + System.currentTimeMillis());
    }


}
