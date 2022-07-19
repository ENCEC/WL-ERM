package com.share.message.service.impl;

import com.fr.base.Parameter;
import com.fr.base.operator.common.CommonOperator;
import com.fr.config.activator.BaseDBActivator;
import com.fr.config.activator.ConfigurationActivator;
import com.fr.env.operator.CommonOperatorImpl;
import com.fr.general.I18nResource;
import com.fr.io.TemplateWorkBookIO;
import com.fr.io.exporter.ImageExporter;
import com.fr.io.exporter.PDFExporter;
import com.fr.io.exporter.WordExporter;
import com.fr.io.exporter.excel.stream.StreamExcel2007Exporter;
import com.fr.main.impl.WorkBook;
import com.fr.module.Module;
import com.fr.module.tool.ActivatorToolBox;
import com.fr.stable.WriteActor;
import com.fr.workspace.simple.SimpleWork;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.exception.BusinessRuntimeException;
import com.google.common.collect.ImmutableMap;
import com.share.message.constants.GlobalConstant;
import com.share.message.dto.EmailContentDTO;
import com.share.message.dto.SendEmailDTO;
import com.share.message.enums.DataLengthEnum;
import com.share.message.enums.GlobalEnum;
import com.share.message.model.entity.MsgEmailAttachment;
import com.share.message.model.entity.MsgEmailConfig;
import com.share.message.model.entity.MsgEmailRecord;
import com.share.message.model.entity.MsgEmailTemplate;
import com.share.message.model.querymodels.QMsgEmailAttachment;
import com.share.message.model.querymodels.QMsgEmailConfig;
import com.share.message.model.querymodels.QMsgEmailRecord;
import com.share.message.model.querymodels.QMsgEmailTemplate;
import com.share.message.vo.MsgEmailAttachmentVO;
import com.share.message.vo.MsgEmailTemplateVO;
import com.share.message.service.MsgEmailTemplateService;
import com.share.support.util.AES128Util;
import com.share.message.vo.SendEmailVO;
//import com.share.portal.api.PortalDictService;
//import com.share.portal.domain.DictCodeVO;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Address;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 邮件模板配置实现类
 *
 * @author tujx
 * @date 2020/10/20
 */
@Slf4j
@Service
public class MsgEmailTemplateServiceImpl implements MsgEmailTemplateService {

//    @Autowired
//    private PortalDictService portalDictService;

    /**
     * 帆软工程路径
     */
    @Value("${fr.envpath}")
    private String envpath;

    /**
     * 邮件密码加密秘钥
     */
    @Value("${mail.aes_secret_key}")
    private String passwordSecretKey;

    @Value("${fr.export_file_path}")
    private String exportFilePath;

    /**
     * 数据字典-邮件协议
     */
    private static final String DICT_EMAIL_PROTOCOL = "LOGINK_EMAIL_PROTOCOL";

    /**
     * 参数-系统代码
     */
    private static final String PARAM_SYSTEM_ID = "systemId";

    /**
     * 参数-邮件模板代码
     */
    private static final String PARAM_EMAIL_TEMPLATE_CODE = "emailTemplateCode";

    /**
     * 数据服务占位符-邮件模版
     */
    private static final String EMAIL_TEMPLATE_CODE_PLACEHOLDER = ":emailTemplateCode";

    /**
     * 返回结果代码
     */
    private static final String RESULT_CODE = "resultCode";

    /**
     * 返回结果消息
     */
    private static final String RESULT_MSG = "resultMsg";

    /**
     * 邮件附件名称过长导致客户端附件异常
     */
    static {
        System.setProperty("mail.mime.splitlongparameters", "false");
    }


    /**
     * 新增邮件模板
     *
     * @param msgEmailTemplateVO 邮件模板信息VO
     * @return Map
     * @author tujx
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<String> saveEmailTemplate(MsgEmailTemplateVO msgEmailTemplateVO) {
        //数据校验
        if (StringUtils.isBlank(msgEmailTemplateVO.getSystemId())) {
            return CommonResult.getFaildResultData("新增失败，使用方系统编码不能为空");
        }
        List<String> validateFailStrs = emailTemplateValidate(msgEmailTemplateVO);
        if (!CollectionUtils.isEmpty(validateFailStrs)) {
            return CommonResult.getFaildResultData("新增失败，" + StringUtils.join(validateFailStrs, "，"));
        }
        String applicationCode = msgEmailTemplateVO.getSystemId();
        MsgEmailTemplate msgEmailTemplate = new MsgEmailTemplate();
        BeanUtils.copyProperties(msgEmailTemplateVO, msgEmailTemplate);
        //启用
        msgEmailTemplate.setIsValid(true);
        msgEmailTemplate.setBusinessSystemId(applicationCode);
        msgEmailTemplate.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        //保存模板表数据
        QMsgEmailTemplate.msgEmailTemplate.tag(PARAM_EMAIL_TEMPLATE_CODE).save(msgEmailTemplate);
        if (!CollectionUtils.isEmpty(msgEmailTemplateVO.getMsgEmailAttachment())) {
            List<MsgEmailAttachment> attachmentList = new ArrayList<>();
            MsgEmailAttachment attachment;
            for (MsgEmailAttachmentVO attachmentVO : msgEmailTemplateVO.getMsgEmailAttachment()) {
                attachment = new MsgEmailAttachment();
                attachment.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
                attachment.setMsgEmailTemplateId(msgEmailTemplate.getMsgEmailTemplateId());
                attachment.setBusinessSystemId(applicationCode);
                attachment.setAttachmentType(attachmentVO.getAttachmentType());
                attachment.setAttachmentUrl(attachmentVO.getAttachmentUrl());
                attachmentList.add(attachment);
            }
            //保存附件表数据
            QMsgEmailTemplate.msgEmailAttachment.save(attachmentList);
        }
        return CommonResult.getSuccessResultData("新增成功");
    }

    /**
     * 编辑邮件模板
     *
     * @param msgEmailTemplateVO 邮件模板信息VO
     * @return Map
     * @author tujx
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<String> updateEmailTemplate(MsgEmailTemplateVO msgEmailTemplateVO) {
        //数据校验
        if (StringUtils.isBlank(msgEmailTemplateVO.getSystemId())) {
            return CommonResult.getFaildResultData("保存失败，使用方系统编码不能为空");
        }
        List<String> validateFailStrs = emailTemplateValidate(msgEmailTemplateVO);
        if (!CollectionUtils.isEmpty(validateFailStrs)) {
            return CommonResult.getFaildResultData("保存失败，" + StringUtils.join(validateFailStrs, "，"));
        }
        MsgEmailTemplate msgEmailTemplate = new MsgEmailTemplate();
        BeanUtils.copyProperties(msgEmailTemplateVO, msgEmailTemplate);
        msgEmailTemplate.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        //保存模板
        QMsgEmailTemplate.msgEmailTemplate.selective(QMsgEmailTemplate.emailTemplateName, QMsgEmailTemplate.msgEmailConfigId
                , QMsgEmailTemplate.description, QMsgEmailTemplate.subject, QMsgEmailTemplate.content).update(msgEmailTemplate);
        QMsgEmailTemplate.msgEmailTemplate.save(msgEmailTemplate);
        Long msgEmailTemplateId = msgEmailTemplate.getMsgEmailTemplateId();
        //查询出当前模板的所有附件，删除未提交的
        List<MsgEmailAttachment> msgEmailAttachmentList = QMsgEmailAttachment.msgEmailAttachment
                .select(QMsgEmailAttachment.msgEmailAttachmentId)
                .where(QMsgEmailTemplate.msgEmailTemplateId.eq(":magEmailTemplateId"))
                .mapperTo(MsgEmailAttachment.class)
                .execute(ImmutableMap.of("magEmailTemplateId", msgEmailTemplateId));
        List<Long> msgEmailAttachmentIdList = msgEmailAttachmentList.stream().map(MsgEmailAttachment::getMsgEmailAttachmentId).collect(Collectors.toList());
        //更新的附件Id
        List<Long> updateAttachmentList = new ArrayList<>();
        List<MsgEmailAttachment> attachmentList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(msgEmailTemplateVO.getMsgEmailAttachment())) {
            MsgEmailAttachment attachment;
            for (MsgEmailAttachmentVO attachmentVO : msgEmailTemplateVO.getMsgEmailAttachment()) {
                attachment = new MsgEmailAttachment();
                //需要判断是新增还是保存
                attachment.setMsgEmailTemplateId(msgEmailTemplateId);
                attachment.setAttachmentType(attachmentVO.getAttachmentType());
                attachment.setAttachmentUrl(attachmentVO.getAttachmentUrl());
                if (Objects.nonNull(attachmentVO.getMsgEmailAttachmentId())) {
                    //更新
                    attachment.setMsgEmailAttachmentId(attachmentVO.getMsgEmailAttachmentId());
                    attachment.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                    updateAttachmentList.add(attachmentVO.getMsgEmailAttachmentId());
                } else {
                    //新增
                    attachment.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
                }
                attachmentList.add(attachment);
            }
        }
        if (!CollectionUtils.isEmpty(msgEmailAttachmentIdList)) {
            // 取差集 (msgEmailAttachmentIdList - updateAttachmentList)，获得需要删除的附件
            List<Long> deleteAttachmentList = msgEmailAttachmentIdList.stream().filter(item -> !updateAttachmentList.contains(item)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(deleteAttachmentList)) {
                //删除附件
                QMsgEmailAttachment.msgEmailAttachment.deleteById(deleteAttachmentList);
            }
        }
        //新增或更新附件
        QMsgEmailAttachment.msgEmailAttachment.save(attachmentList);
        return CommonResult.getSuccessResultData("保存成功");
    }

    /**
     * 根据使用方系统id以及邮件模板编码获取模板的宏参数
     *
     * @param systemCode        使用方系统code
     * @param emailTemplateCode 邮件模板编码
     * @return Map
     * @author tujx
     */
    @Override
    public Map<String, Object> getEmailTemplateMarcoByCode(String systemCode, String emailTemplateCode) {
        Map<String, Object> result = new HashMap<>(3);
        String resultCode;
        String resultMsg;
        Map<String, Object> data = new HashMap<>(3);
        //必填参数校验
        if (StringUtils.isBlank(systemCode) || StringUtils.isBlank(emailTemplateCode)) {
            resultCode = GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultCode();
            resultMsg = GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultMsg() + "【";
            List<String> paramNameList = new ArrayList<>();
            if (StringUtils.isBlank(systemCode)) {
                paramNameList.add(PARAM_SYSTEM_ID);
            }
            if (StringUtils.isBlank(emailTemplateCode)) {
                paramNameList.add(PARAM_EMAIL_TEMPLATE_CODE);
            }
            resultMsg = resultMsg + StringUtils.join(paramNameList, "，") + "】";
        } else {
            //获取模板对象
            MsgEmailTemplate emailTemplate = QMsgEmailTemplate.msgEmailTemplate.selectOne().where(QMsgEmailTemplate.businessSystemId.eq(":systemCode").
                    and(QMsgEmailTemplate.emailTemplateCode.eq(EMAIL_TEMPLATE_CODE_PLACEHOLDER)).and(QMsgEmailTemplate.isValid.eq$(true)))
                    .mapperTo(MsgEmailTemplate.class)
                    .execute(ImmutableMap.of("systemCode", systemCode, PARAM_EMAIL_TEMPLATE_CODE, emailTemplateCode));
            if (Objects.isNull(emailTemplate)) {
                resultCode = GlobalEnum.ErrorCode.FAIL.getResultCode();
                resultMsg = GlobalEnum.ErrorCode.FAIL.getResultMsg() + "【未查询到相关模板】";
            } else {
                //主题中的宏参数
                List<String> subjectList = getMarcoFromContent(emailTemplate.getSubject());
                //内容中的宏参数
                List<String> contentList = getMarcoFromContent(emailTemplate.getContent());
                //获取附件
                List<MsgEmailAttachment> attachmentList = QMsgEmailAttachment.msgEmailAttachment.select(QMsgEmailAttachment.attachmentUrl).where(QMsgEmailAttachment.msgEmailTemplateId.eq(":emailTemplateId"))
                        .mapperTo(MsgEmailAttachment.class)
                        .execute(ImmutableMap.of("emailTemplateId", emailTemplate.getMsgEmailTemplateId()));
                List<String> attachmentParameterList = null;
                try {
                    attachmentParameterList = getMarcoFromAttachment(attachmentList);
                    //去重
                    attachmentParameterList = attachmentParameterList.stream().distinct().collect(Collectors.toList());
                } catch (Exception e) {
                    log.error("附件参数获取失败：", e);
                    resultCode = GlobalEnum.ErrorCode.FAIL.getResultCode();
                    resultMsg = GlobalEnum.ErrorCode.FAIL.getResultMsg() + "【附件参数获取失败】";
                    result.put(RESULT_CODE, resultCode);
                    result.put(RESULT_MSG, resultMsg);
                    return result;
                }
                data.put("subjectMarco", subjectList);
                data.put("contentMarco", contentList);
                data.put("attachmentParams", attachmentParameterList);
                resultCode = GlobalEnum.ErrorCode.SUCCESS.getResultCode();
                resultMsg = GlobalEnum.ErrorCode.SUCCESS.getResultMsg();
            }
        }
        result.put(RESULT_CODE, resultCode);
        result.put(RESULT_MSG, resultMsg);
        result.put("data", data);
        return result;
    }

    /**
     * 从文本内容中获取宏
     *
     * @param content 文本内容
     * @return List<String> 宏
     * @author tujx
     */
    public List<String> getMarcoFromContent(String content) {
        List<String> resultList = new ArrayList<>();
        //包裹宏的特殊字符
        String regex = "[$][{](.*?)[}]";
        Pattern pattern = Pattern.compile(regex);
        if (!StringUtils.isEmpty(content)) {
            Matcher m = pattern.matcher(content);
            while (m.find()) {
                resultList.add(m.group(1));
            }
        }
        return resultList;
    }

    /**
     * 获取帆软模板的参数
     *
     * @param attachmentList 附件列表
     * @return Map
     * @throws
     * @author tujx
     */
    public List<String> getMarcoFromAttachment(List<MsgEmailAttachment> attachmentList) throws Exception {
        List<String> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(attachmentList)) {
            //定义报表运行环境,用于执行报表
            Module module = ActivatorToolBox.simpleLink(new BaseDBActivator(),
                    new ConfigurationActivator());
            SimpleWork.supply(CommonOperator.class, new CommonOperatorImpl());
            SimpleWork.checkIn(envpath);
            I18nResource.getInstance();
            module.start();
            WorkBook workbook;
            //参数对象
            Parameter[] parameters;
            //参数名称
            List<String> parameterNameList;
            for (MsgEmailAttachment attachment : attachmentList) {
                // 定义输出的模板路径，以reportlets为根目录
                workbook = (WorkBook) TemplateWorkBookIO.readTemplateWorkBook(attachment.getAttachmentUrl());
                parameters = workbook.getParameters();
                parameterNameList = Arrays.stream(parameters).map(Parameter::getName).collect(Collectors.toList());
                result.addAll(parameterNameList);
            }
            module.stop();
        }
        return result;
    }

    /**
     * 根据模板发送邮件
     *
     * @param sendEmailVO 邮件发送信息
     * @return Map
     * @author tujx
     */
    @Override
    public Map<String, Object> sendEmailByTemplate(SendEmailVO sendEmailVO) {
        Map<String, Object> result = new HashMap<>(2);
        MsgEmailTemplate emailTemplate;
        Map<String, String> validateResult = sendEmailByTemplateValidate(sendEmailVO);
        String resultCode = MapUtils.getString(validateResult, RESULT_CODE);
        String resultMsg = MapUtils.getString(validateResult, RESULT_MSG);
        if (StringUtils.isBlank(resultCode)) {
            try {
                emailTemplate = QMsgEmailTemplate.msgEmailTemplate.selectOne().where(QMsgEmailTemplate.businessSystemId.eq(":systemId").
                        and(QMsgEmailTemplate.emailTemplateCode.eq(EMAIL_TEMPLATE_CODE_PLACEHOLDER)))
                        .mapperTo(MsgEmailTemplate.class)
                        .execute(ImmutableMap.of(PARAM_SYSTEM_ID, sendEmailVO.getSystemId(), PARAM_EMAIL_TEMPLATE_CODE, sendEmailVO.getEmailTemplateCode()));
                MsgEmailConfig msgEmailConfig = QMsgEmailConfig.msgEmailConfig.selectOne().byId(emailTemplate.getMsgEmailConfigId());
                //数据字典-邮件协议
                /*ResultHelper<List<DictCodeVO>> protocolDictResult = portalDictService.getDictCodeByCode(DICT_EMAIL_PROTOCOL);
                if (Boolean.TRUE.equals(protocolDictResult.getSuccess()) && !CollectionUtils.isEmpty(protocolDictResult.getData())) {
                    //邮件协议
                    Optional<DictCodeVO> dictCodeVO = protocolDictResult.getData().stream().filter(p -> StringUtils.equals(p.getDictCode(), msgEmailConfig.getProtocol())).findFirst();
                    if (!dictCodeVO.isPresent()) {
                        throw new UnsupportedOperationException();
                    }
                    DictCodeVO protocol = dictCodeVO.get();
                    if (StringUtils.isNotBlank(protocol.getDictName())) {
                        msgEmailConfig.setProtocol(protocol.getDictName());
                    }
                }*/
                msgEmailConfig.setProtocol("smtp");
                EmailContentDTO emailContentDTO = new EmailContentDTO();
                String subject = emailTemplate.getSubject();
                String content = emailTemplate.getContent();
                Map<String, Object> marcoAndAttachParamsMap = sendEmailVO.getMarcoAndAttachParams();
                if (!CollectionUtils.isEmpty(marcoAndAttachParamsMap)) {
                    Map<?, ?> subjectMarcoMap = MapUtils.getMap(marcoAndAttachParamsMap, "subject");
                    Map<?, ?> contentMarcoMap = MapUtils.getMap(marcoAndAttachParamsMap, "content");
                    Map<String, Object> attachParamMap = (Map<String, Object>) MapUtils.getMap(marcoAndAttachParamsMap, "attach");
                    if (!CollectionUtils.isEmpty(subjectMarcoMap)) {
                        StrSubstitutor subjectSub = new StrSubstitutor(subjectMarcoMap);
                        //获取正确的邮件主题
                        subject = subjectSub.replace(subject);
                    }
                    emailContentDTO.setSubject(subject);
                    if (!CollectionUtils.isEmpty(contentMarcoMap)) {
                        StrSubstitutor contentSub = new StrSubstitutor(contentMarcoMap);
                        //获取正确的邮件正文
                        content = contentSub.replace(content);
                    }
                    emailContentDTO.setContent(content);
                    String attachmentParentPath = uploadAttachment(emailTemplate.getMsgEmailTemplateId(), attachParamMap, sendEmailVO.getFileList());
                    emailContentDTO.setAttachmentParentPath(attachmentParentPath);
                }
                SendEmailDTO sendEmailDTO = new SendEmailDTO();
                sendEmailDTO.setSendEmailVO(sendEmailVO);
                sendEmailDTO.setEmailContentDTO(emailContentDTO);
                sendEmailDTO.setEmailConfig(msgEmailConfig);
                sendEmailDTO.setEmailTemplate(emailTemplate);
                sendMail(sendEmailDTO);
                resultCode = GlobalEnum.ErrorCode.SUCCESS.getResultCode();
                resultMsg = GlobalEnum.ErrorCode.SUCCESS.getResultMsg();


            } catch (Exception e) {
                log.error("邮件发送失败：", e);
                result.put(RESULT_CODE, resultCode);
                result.put(RESULT_MSG, resultMsg);
                return result;
            }
        }
        result.put(RESULT_CODE, resultCode);
        result.put(RESULT_MSG, resultMsg);
        return result;
    }

    /**
     * 根据模板生成附件
     *
     * @param attachmentList 报表信息
     * @param attachParamMap 报表参数
     * @return String 附件所在文件夹名称
     * @throws
     * @author tujx
     */
    public String makeTemplates(List<MsgEmailAttachment> attachmentList, Map<String, Object> attachParamMap) throws Exception {
        //定义报表运行环境,用于执行报表
        Module module = ActivatorToolBox.simpleLink(new BaseDBActivator(),
                new ConfigurationActivator());
        SimpleWork.supply(CommonOperator.class, new CommonOperatorImpl());
        SimpleWork.checkIn(envpath);
        I18nResource.getInstance();
        module.start();
        // 定义输出流
        String outputUrl = exportFilePath;
        String attachmentName;
        String attachmentLocalUrl;
        String uuidStr;
        uuidStr = UUID.randomUUID().toString().replace("-", "");
        outputUrl = outputUrl + uuidStr + File.separator;
        File outfile = new File(outputUrl);
        if (!outfile.exists()) {
            outfile.mkdirs();
        }

        for (int i = 0; i < attachmentList.size(); i++) {
            attachmentName = attachmentList.get(i).getAttachmentUrl().split("\\.")[0];
            //定义模板路径
            WorkBook workbook = (WorkBook) TemplateWorkBookIO.readTemplateWorkBook(attachmentList.get(i).getAttachmentUrl());
            switch (attachmentList.get(i).getAttachmentType()) {
                case GlobalConstant.EMAIL_ATTACHMENT_TYPE_XLSX:
                    //定义输出路径
                    attachmentLocalUrl = outputUrl + attachmentName + "." + GlobalConstant.EMAIL_ATTACHMENT_TYPE_XLSX;
                    try (FileOutputStream outputStream = new FileOutputStream(new File(attachmentLocalUrl))) {
                        StreamExcel2007Exporter<Object> excelExport = new StreamExcel2007Exporter<>();
                        //生成对应格式文件
                        excelExport.export(outputStream, workbook.execute(attachParamMap, new WriteActor()));
                    }
                    break;
                case GlobalConstant.EMAIL_ATTACHMENT_TYPE_WORD:
                    attachmentLocalUrl = outputUrl + attachmentName + "." + GlobalConstant.EMAIL_ATTACHMENT_TYPE_WORD;
                    try (OutputStream outputStream = new FileOutputStream(new File(attachmentLocalUrl))) {
                        WordExporter wordExport = new WordExporter();
                        wordExport.export(outputStream, workbook.execute(attachParamMap, new WriteActor()));
                    }
                    break;
                case GlobalConstant.EMAIL_ATTACHMENT_TYPE_PDF:
                    attachmentLocalUrl = outputUrl + attachmentName + "." + GlobalConstant.EMAIL_ATTACHMENT_TYPE_PDF;
                    try (OutputStream outputStream = new FileOutputStream(new File(attachmentLocalUrl))) {
                        PDFExporter pdfExport = new PDFExporter();
                        pdfExport.export(outputStream, workbook.execute(attachParamMap, new WriteActor()));
                    }
                    break;
                case GlobalConstant.EMAIL_ATTACHMENT_TYPE_PNG:
                    attachmentLocalUrl = outputUrl + attachmentName + "." + GlobalConstant.EMAIL_ATTACHMENT_TYPE_PNG;
                    try (OutputStream outputStream = new FileOutputStream(new File(attachmentLocalUrl))) {
                        ImageExporter imageExporter = new ImageExporter();
                        imageExporter.export(outputStream, workbook.execute(attachParamMap, new WriteActor()));
                    }
                    break;
                default:
                    break;
            }
        }
        return uuidStr;
    }


    /**
     * 模板数据校验
     *
     * @param msgEmailTemplateVO 提交的模板信息
     * @return List<String> 校验提示
     * @author tujx
     */
    public List<String> emailTemplateValidate(MsgEmailTemplateVO msgEmailTemplateVO) {
        List<String> validateFailStrs = new ArrayList<>();
        if (StringUtils.isBlank(msgEmailTemplateVO.getEmailTemplateName())) {
            validateFailStrs.add("模板名称不能为空");
        } else if (StringUtils.length(msgEmailTemplateVO.getEmailTemplateName()) > DataLengthEnum.EmailTemplate.EMAIL_TEMPLATE_NAME.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailTemplate.EMAIL_TEMPLATE_NAME.getTipsStr());
        } else {
            //名称唯一性
            List<MsgEmailTemplate> sameNameEmailTemplates;
            String systemCode = msgEmailTemplateVO.getSystemId();
            if (Objects.isNull(msgEmailTemplateVO.getMsgEmailTemplateId())) {
                //新增
                sameNameEmailTemplates = QMsgEmailTemplate.msgEmailTemplate.select()
                        .where(QMsgEmailTemplate.emailTemplateName.eq$(msgEmailTemplateVO.getEmailTemplateName()).and(QMsgEmailTemplate.businessSystemId.eq$(systemCode)))
                        .mapperTo(MsgEmailTemplate.class)
                        .execute();
            } else {
                //编辑
                sameNameEmailTemplates = QMsgEmailTemplate.msgEmailTemplate.select()
                        .where(QMsgEmailTemplate.emailTemplateName.eq$(msgEmailTemplateVO.getEmailTemplateName())
                                .and(QMsgEmailTemplate.msgEmailTemplateId.ne$(msgEmailTemplateVO.getMsgEmailTemplateId())).and(QMsgEmailTemplate.businessSystemId.eq$(systemCode)))
                        .mapperTo(MsgEmailTemplate.class)
                        .execute();
            }
            if (!CollectionUtils.isEmpty(sameNameEmailTemplates)) {
                validateFailStrs.add("模板名称重复");
            }
        }
        if (StringUtils.length(msgEmailTemplateVO.getDescription()) > DataLengthEnum.EmailTemplate.DESCRIPTION.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailTemplate.DESCRIPTION.getTipsStr());
        }
        if (Objects.isNull(msgEmailTemplateVO.getMsgEmailConfigId())) {
            validateFailStrs.add("模板发送配置不能为空");
        }
        if (StringUtils.isBlank(msgEmailTemplateVO.getSubject())) {
            validateFailStrs.add("模板主题不能为空");
        } else if (StringUtils.length(msgEmailTemplateVO.getSubject()) > DataLengthEnum.EmailTemplate.SUBJECT.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailTemplate.SUBJECT.getTipsStr());
        }
        if (StringUtils.isBlank(msgEmailTemplateVO.getContent())) {
            validateFailStrs.add("模板内容不能为空");
        } else if (StringUtils.length(msgEmailTemplateVO.getContent()) > DataLengthEnum.EmailTemplate.CONTENT.getLength()) {
            validateFailStrs.add(DataLengthEnum.EmailTemplate.CONTENT.getTipsStr());
        }
        for (MsgEmailAttachmentVO msgEmailAttachment : msgEmailTemplateVO.getMsgEmailAttachment()) {
            if (StringUtils.length(msgEmailAttachment.getAttachmentUrl()) > DataLengthEnum.EmailTemplate.ATTACHMENT_URL.getLength()) {
                validateFailStrs.add(DataLengthEnum.EmailTemplate.ATTACHMENT_URL.getTipsStr());
                break;
            }
        }
        return validateFailStrs;
    }


    /**
     * 邮件发送，如果收件人或者抄送人中有错误地址，则会剔除错误账号，再进行二次发送
     *
     * @param sendEmailDTO
     * @throws
     * @author tujx
     */
    public void sendMail(SendEmailDTO sendEmailDTO) throws RuntimeException {
        //邮件收件账号
        SendEmailVO sendEmailVO = sendEmailDTO.getSendEmailVO();
        //邮件主题、正文以及附件信息
        EmailContentDTO emailContentDTO = sendEmailDTO.getEmailContentDTO();
        //发送记录
        MsgEmailRecord emailRecord = new MsgEmailRecord();
        //发送人账号，二次发送使用
        List<String> to = new ArrayList<>(Arrays.asList(sendEmailVO.getToEmail().split(",")));
        //抄送人账号，二次发送使用
        List<String> cc;
        try {
            //发送记录信息填充
            emailRecord = initEmailRecord(sendEmailDTO);
            //发送
            send(sendEmailDTO);
        } catch (Exception e) {
            //发送失败记录
            emailRecord.setIsSuccess(false);
            if (e instanceof MailSendException) {
                log.error("邮件发送失败，", e);
                for (Exception exception : ((MailSendException) e).getFailedMessages().values()) {
                    if (exception instanceof SendFailedException) {
                        //发送失败邮箱地址
                        List<String> toFailList = new ArrayList<>();
                        List<String> ccFailList = new ArrayList<>();
                        String failAddress;
                        cc = new ArrayList<>(Arrays.asList(sendEmailVO.getCcEmail().split(",")));
                        for (Address address : ((SendFailedException) exception).getInvalidAddresses()) {
                            failAddress = address.toString();
                            //从收件人、抄送人里去除发送失败的邮箱地址
                            if (to.contains(failAddress)) {
                                to.remove(failAddress);
                                toFailList.add(failAddress);
                            }
                            if (cc.contains(failAddress)) {
                                cc.remove(failAddress);
                                ccFailList.add(failAddress);
                            }
                            log.info("邮箱地址不存在,{}", failAddress);
                        }
                        emailRecord.setReason("邮箱地址不存在");
                        emailRecord.setCcEmail(StringUtils.join(ccFailList, ","));
                        emailRecord.setToEmail(StringUtils.join(toFailList, ","));
                        if (!CollectionUtils.isEmpty(to)) {
                            //进行二次发送
                            sendEmailVO.setToEmail(StringUtils.join(to, ","));
                            sendEmailVO.setCcEmail(StringUtils.join(cc, ","));
                            sendEmailDTO.setSendEmailVO(sendEmailVO);
                            sendEmailDTO.setEmailContentDTO(emailContentDTO);
                            sendMail(sendEmailDTO);
                        } else {
                            //没有一个正确的收件箱，发送失败
                            throw new MailSendException("接收邮箱错误");
                        }
                    } else {
                        emailRecord.setReason(e.getMessage());
                        throw new MailSendException(e.getMessage());
                    }
                }
            } else {
                emailRecord.setReason(e.getMessage());
                throw new MailSendException(e.getMessage());
            }
        } finally {
            //保存发送记录
            QMsgEmailRecord.msgEmailRecord.save(emailRecord);
        }
    }


    /**
     * 邮件附件保存处理
     *
     * @param msgEmailTemplateId
     * @param attachParamMap
     * @param attachmentFileList
     * @return
     * @throws
     * @author tujx
     */
    public String uploadAttachment(Long msgEmailTemplateId, Map<String, Object> attachParamMap, List<MultipartFile> attachmentFileList) {
        String attachmentParentPath = null;
        //模板附件处理
        if (!CollectionUtils.isEmpty(attachParamMap) && Objects.nonNull(msgEmailTemplateId)) {
            //获取邮件模板对应的报表模板文件
            List<MsgEmailAttachment> attachmentList = QMsgEmailAttachment.msgEmailAttachment.select().where(QMsgEmailAttachment.msgEmailTemplateId.eq$(msgEmailTemplateId))
                    .mapperTo(MsgEmailAttachment.class).execute();
            if (!CollectionUtils.isEmpty(attachmentList)) {
                try {
                    attachmentParentPath = makeTemplates(attachmentList, attachParamMap);
                } catch (Exception e) {
                    throw new BusinessRuntimeException(e);
                }
            }
        }
        //发送方携带附件处理
        if (!CollectionUtils.isEmpty(attachmentFileList)) {
            if (StringUtils.isBlank(attachmentParentPath)) {
                attachmentParentPath = UUID.randomUUID().toString().replace("-", "");
            }
            //附件文件夹
            File tempFile = new File(exportFilePath + attachmentParentPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            for (MultipartFile file : attachmentFileList) {
                try (
                        InputStream inputStream = file.getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(tempFile.getPath() + File.separator + file.getOriginalFilename())
                ) {
                    //附件保存
                    byte[] buffer = new byte[1024 * 1024];
                    int byteread = 0;
                    while ((byteread = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, byteread);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    log.error("邮件附件保存失败", e);
                }
            }
        }
        return attachmentParentPath;
    }


    /**
     * 根据模板发送邮件的数据校验
     *
     * @param sendEmailVO
     * @return
     * @throws
     * @author tujx
     */
    public Map<String, String> sendEmailByTemplateValidate(SendEmailVO sendEmailVO) {
        Map<String, String> resultMap = new HashMap<>(2);
        String resultCode = null;
        String resultMsg = null;
        MsgEmailTemplate emailTemplate;
        //必填参数校验
        if (StringUtils.isBlank(sendEmailVO.getSystemId()) || StringUtils.isBlank(sendEmailVO.getEmailTemplateCode()) || StringUtils.isBlank(sendEmailVO.getToEmail())) {
            resultCode = GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultCode();
            resultMsg = GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultMsg() + "【";
            List<String> paramNameList = new ArrayList<>();
            if (StringUtils.isBlank(sendEmailVO.getSystemId())) {
                paramNameList.add(PARAM_SYSTEM_ID);
            }
            if (StringUtils.isBlank(sendEmailVO.getEmailTemplateCode())) {
                paramNameList.add(PARAM_EMAIL_TEMPLATE_CODE);
            }
            if (StringUtils.isBlank(sendEmailVO.getToEmail())) {
                paramNameList.add("toEmail");
            }
            resultMsg = resultMsg + StringUtils.join(paramNameList, "，") + "】";
        } else {
            String[] toEmails = sendEmailVO.getToEmail().split(",");
            Matcher emailMatcher;
            List<String> validateFailEmail = new ArrayList<>();
            for (String toEmail : toEmails) {
                emailMatcher = GlobalConstant.EMAIL_PATTERN.matcher(toEmail);
                if (!emailMatcher.matches()) {
                    validateFailEmail.add(toEmail);
                }
            }
            if (!CollectionUtils.isEmpty(validateFailEmail)) {
                resultMsg = GlobalEnum.ErrorCode.FAIL.getResultMsg() + "【" + StringUtils.join(validateFailEmail, ",") + "邮箱格式不正确】";
                resultCode = GlobalEnum.ErrorCode.FAIL.getResultCode();
            } else {
                //数据校验通过标识
                Boolean dataValidateFlag = true;
                //获取模板对象
                emailTemplate = QMsgEmailTemplate.msgEmailTemplate.selectOne().where(QMsgEmailTemplate.businessSystemId.eq(":systemId").
                        and(QMsgEmailTemplate.emailTemplateCode.eq(EMAIL_TEMPLATE_CODE_PLACEHOLDER)))
                        .mapperTo(MsgEmailTemplate.class)
                        .execute(ImmutableMap.of(PARAM_SYSTEM_ID, sendEmailVO.getSystemId(), PARAM_EMAIL_TEMPLATE_CODE, sendEmailVO.getEmailTemplateCode()));
                if (Objects.isNull(emailTemplate)) {
                    resultMsg = GlobalEnum.ErrorCode.FAIL.getResultMsg() + "【未查询到相关邮件模板】";
                    resultCode = GlobalEnum.ErrorCode.FAIL.getResultCode();
                } else if (Boolean.FALSE.equals(emailTemplate.getIsValid())) {
                    resultMsg = GlobalEnum.ErrorCode.FAIL.getResultMsg() + "【邮件模板已被禁用】";
                    resultCode = GlobalEnum.ErrorCode.FAIL.getResultCode();
                } else {
                    //获取模板对应邮件发送设置
                    MsgEmailConfig msgEmailConfig = QMsgEmailConfig.msgEmailConfig.selectOne().byId(emailTemplate.getMsgEmailConfigId());
                    //数据字典-邮件协议
                    /*ResultHelper<List<DictCodeVO>> protocolDictResult = portalDictService.getDictCodeByCode(DICT_EMAIL_PROTOCOL);
                    if (Boolean.TRUE.equals(protocolDictResult.getSuccess()) && !CollectionUtils.isEmpty(protocolDictResult.getData())) {
                        //邮件协议
                        Optional<DictCodeVO> dictCodeVO = protocolDictResult.getData().stream().filter(p -> StringUtils.equals(p.getDictCode(), msgEmailConfig.getProtocol())).findFirst();
                        if (!dictCodeVO.isPresent()) {
                            throw new UnsupportedOperationException();
                        }
                        DictCodeVO protocol = dictCodeVO.get();
                        if (Objects.nonNull(protocol) && StringUtils.isNotBlank(protocol.getDictName())) {
                            msgEmailConfig.setProtocol(protocol.getDictName());
                        } else {
                            dataValidateFlag = false;
                        }
                    } else {
                        dataValidateFlag = false;
                    }*/
                    msgEmailConfig.setProtocol("smtp");
//                    if (Boolean.FALSE.equals(dataValidateFlag)) {
//                        resultMsg = GlobalEnum.ErrorCode.FAIL.getResultMsg() + "【未查询到邮件协议的字典数据】";
//                        resultCode = GlobalEnum.ErrorCode.FAIL.getResultCode();
//                    }
                }
            }
        }
        resultMap.put(RESULT_MSG, resultMsg);
        resultMap.put(RESULT_CODE, resultCode);
        return resultMap;
    }


    /**
     * 邮件发送
     *
     * @param sendEmailDTO
     * @return
     * @throws
     * @author tujx
     */
    public void send(SendEmailDTO sendEmailDTO) throws Exception {
        MsgEmailConfig emailConfig = sendEmailDTO.getEmailConfig();
        SendEmailVO sendEmailVO = sendEmailDTO.getSendEmailVO();
        EmailContentDTO emailContentDTO = sendEmailDTO.getEmailContentDTO();
        //创建邮件发送服务器
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol(emailConfig.getProtocol());
        javaMailSender.setHost(emailConfig.getHost());
        javaMailSender.setPort(emailConfig.getPort());
        javaMailSender.setUsername(emailConfig.getEmailAddress());
        String sendMailPwd = AES128Util.decrypt(emailConfig.getEmailPassword(), passwordSecretKey);
        javaMailSender.setPassword(sendMailPwd);
        //加认证机制
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.timeout", 5000);
        javaMailSender.setJavaMailProperties(javaMailProperties);

        MimeMessage message = javaMailSender.createMimeMessage();
        //创建MimeMessageHelper对象，处理MimeMessage的辅助类
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        //使用辅助类MimeMessage设定参数
        helper.setFrom(emailConfig.getEmailAddress(), emailConfig.getEmailUser());
        InternetAddress[] sendTo = InternetAddress.parse(sendEmailVO.getToEmail());
        helper.setTo(sendTo);
        if (StringUtils.isNotBlank(sendEmailVO.getCcEmail())) {
            InternetAddress[] ccTo = InternetAddress.parse(sendEmailVO.getCcEmail());
            helper.setCc(ccTo);
        }
        helper.setSubject(emailContentDTO.getSubject());
        helper.setText(emailContentDTO.getContent(), true);

        //添加附件
        if (StringUtils.isNotBlank(emailContentDTO.getAttachmentParentPath())) {
            File parentFile = new File(exportFilePath + emailContentDTO.getAttachmentParentPath());
            for (File file : parentFile.listFiles()) {
                helper.addAttachment(file.getName(), file);
            }
        }
        //发送
        javaMailSender.send(message);
    }


    /**
     * 发送记录信息填充
     *
     * @param sendEmailDTO
     * @return
     * @throws
     * @author tujx
     */
    public MsgEmailRecord initEmailRecord(SendEmailDTO sendEmailDTO) {
        //发送记录
        MsgEmailRecord emailRecord = new MsgEmailRecord();
        //发送记录信息填充
        SendEmailVO sendEmailVO = sendEmailDTO.getSendEmailVO();
        EmailContentDTO emailContentDTO = sendEmailDTO.getEmailContentDTO();
        MsgEmailTemplate emailTemplate = sendEmailDTO.getEmailTemplate();
        emailRecord.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        emailRecord.setEmailTemplateCode(emailTemplate.getEmailTemplateCode());
        emailRecord.setEmailTemplateName(emailTemplate.getEmailTemplateName());
        emailRecord.setBusinessSystemId(sendEmailVO.getSystemId());
        emailRecord.setSubject(emailContentDTO.getSubject());
        emailRecord.setContent(emailContentDTO.getContent());
        emailRecord.setAttachmentUrl(emailContentDTO.getAttachmentParentPath());
        if (StringUtils.isBlank(emailContentDTO.getSendBatch())) {
            //初次调用，生成发送标识，如果是二次调用，则使用上次生成的
            String sendBatch = StringUtils.substring(emailContentDTO.getSubject(), 0, 5);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            sendBatch += simpleDateFormat.format(System.currentTimeMillis());
            emailContentDTO.setSendBatch(sendBatch);
        }
        emailRecord.setSendBatch(emailContentDTO.getSendBatch());
        emailRecord.setCcEmail(sendEmailVO.getCcEmail());
        emailRecord.setToEmail(sendEmailVO.getToEmail());
        //发送成功记录
        emailRecord.setIsSuccess(true);
        return emailRecord;
    }
}
