package com.share.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.share.message.constants.GlobalConstant;
import com.share.message.dto.*;
import com.share.message.enums.GlobalEnum;
import com.share.message.msg.client.demo.Response;
import com.share.message.msg.client.demo.util.PostUtil;
import com.share.message.model.entity.MsgSmsRecord;
import com.share.message.model.querymodels.QMsgSmsRecord;
import com.share.message.service.MsgApiService;
import com.share.message.util.StringUtil;
import com.share.message.vo.MsgSmsApiVO;
import com.share.support.util.AES128Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 短信接口实现方法
 * @Author nily
 * @Date 2020/11/9
 * @Time 12:53 下午
 */
@Service
public class MsgApiServiceImpl implements MsgApiService {
    Logger logger = LoggerFactory.getLogger(MsgApiServiceImpl.class);

    /**
     * 宏模板配置AES加密
     * 同邮件密码加密秘钥
     */
    @Value("${mail.aes_secret_key}")
    private String passwordSecretKey;


    @Autowired
    private MsgSmsTemplateServiceImpl msgSmsTemplateServiceimpl;

    @Autowired
    private MsgMaecoServiceImpl msgMaecoServiceimpl;

    @Autowired
    private MsgSmsConfigServiceImpl msgsmsConfigServiceimpl;

    @Autowired
    private MsgApiServiceImpl msgApiService;

    /**
     * content属性名称
     */
    private static final String CONTENT_KEY = "content";

    /**
     * @Description 入参验证
     */
    public boolean validateParms(String systemId, String smsTemplateCode) {
        if (StringUtils.isEmpty(systemId)) {
            return false;
        }
        return !StringUtils.isEmpty(smsTemplateCode);
    }


    /**
     * @Description 入参验证
     */
    public Boolean validateMsgSmsTemplateDto(MsgSmsTemplateDto msgSmsTemplateDto) {
        return msgSmsTemplateDto != null;
    }


    /**
     * @Description 调用数据服务短信接口
     */
    public SendMsgReturnVo<String> sendMsgByDataService(MsgSmsConfigDto msgSmsConfigDto, String acceptNo, String msgContent, MsgSmsTemplateDto msgSmsTemplateDto) throws Exception {
        SendMsgReturnVo<String> sendMsgReturnVo = new SendMsgReturnVo<>();
        String appkey = msgSmsConfigDto.getAppKey();
        String appsecret = msgSmsConfigDto.getAppSecret();
        String url = msgSmsConfigDto.getSmsUrl();

        /**
         * 根据服务商类型选择不同发送模板 发送短信接口
         */
        String type = msgSmsConfigDto.getSmsServiceType();
        JSONObject jsonbody = new JSONObject();
        String body = null;

        MsgSmsRecordDto msgSmsRecordDto;
        if (Objects.equals(GlobalEnum.ServiceType.ISP.getCode(), type)) {
            jsonbody.put("mobiles", acceptNo);
            jsonbody.put(CONTENT_KEY, msgContent);
            jsonbody.put("provideCode", msgSmsConfigDto.getIspNo());
            jsonbody.put("account", msgSmsConfigDto.getIspAccount());
            String ispPassword = AES128Util.decrypt(msgSmsConfigDto.getIspPassword(), passwordSecretKey);
            jsonbody.put("password", ispPassword);
            body = jsonbody.toJSONString();
            try {
                Response response = PostUtil.postString(url, body, appkey, appsecret);
                //保存短信日志
                msgSmsRecordDto = saveLogToSmsRecordDto(msgSmsConfigDto, acceptNo, msgContent, msgSmsTemplateDto);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    msgSmsRecordDto.setIsSuccess(true);
                    msgSmsRecordDto.setReason(response.getBody());
                    saveSmsLog(msgSmsRecordDto);
                    sendMsgReturnVo.setResultMsg("发送短信接口 发送成功");
                    sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.SUCCESS.getResultCode());
                    sendMsgReturnVo.setData(msgContent);
                } else {
                    logger.error("发送短信接口 发送失败,返回StatusCode：{},返回body：{}", response.getStatusCode(), response.getBody());
                    msgSmsRecordDto.setIsSuccess(false);
                    msgSmsRecordDto.setReason(response.toString());
                    saveSmsLog(msgSmsRecordDto);
                    sendMsgReturnVo.setResultMsg("发送短信接口 发送失败");
                    sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.FAIL.getResultCode());
                }
                return sendMsgReturnVo;

            } catch (Exception e) {
                logger.info(String.valueOf(e));
                sendMsgReturnVo.setResultMsg("发送短信接口1 发送失败 出现异常");
                sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.FAIL.getResultCode());
                return sendMsgReturnVo;
            }
        } else if (Objects.equals(GlobalEnum.ServiceType.NO_ISP.getCode(), type)) {
            jsonbody.put("mobiles", acceptNo);
            jsonbody.put(CONTENT_KEY, msgContent);
            body = jsonbody.toJSONString();
            try {
                Response response = PostUtil.postString(url, body, appkey, appsecret);
                //保存短信日志
                msgSmsRecordDto = saveLogToSmsRecordDto(msgSmsConfigDto, acceptNo, msgContent, msgSmsTemplateDto);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    msgSmsRecordDto.setIsSuccess(true);
                    saveSmsLog(msgSmsRecordDto);
                    sendMsgReturnVo.setResultMsg("发送短信接口2 发送短信成功");
                    sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.SUCCESS.getResultCode());
                    sendMsgReturnVo.setData(msgContent);
                } else {
                    msgSmsRecordDto.setIsSuccess(false);
                    msgSmsRecordDto.setReason(response.toString());
                    saveSmsLog(msgSmsRecordDto);
                    sendMsgReturnVo.setResultMsg("发送短信接口2 发送短信失败");
                    sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.FAIL.getResultCode());
                }
                return sendMsgReturnVo;

            } catch (Exception e) {
                logger.error("发送失败", e);
                sendMsgReturnVo.setResultMsg("发送短信接口2 发送失败 出现异常");
                sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.FAIL.getResultCode());
                return sendMsgReturnVo;
            }
        }
        logger.error("发送失败,服务商类型：", type);
        sendMsgReturnVo.setResultMsg("发送失败,服务商类型为空");
        sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.FAIL.getResultCode());
        return sendMsgReturnVo;
    }


    /**
     * @Description 验证必填参数
     * @Author nily
     * @Date 2020/11/16
     * @Time 3:01 下午
     */
    public Boolean validateSendMsgParms(String systemId, String smsTemplateCode, String acceptNo) {
        if (StringUtils.isEmpty(systemId)) {
            return false;
        }
        if (StringUtils.isEmpty(smsTemplateCode)) {
            return false;
        }
        return !StringUtils.isEmpty(acceptNo);
    }


    /**
     * @Description 发送短信 接口
     * @Author nily
     * @Date 2020/11/25
     * @Time 9:23 上午
     */
    @Override
    public SendMsgReturnVo sendMsg(MsgSmsApiVO msgSmsApiVO) {
        SendMsgReturnVo<String> sendMsgReturnVo = new SendMsgReturnVo<>();
        String systemId = msgSmsApiVO.getSystemId();
        String smsTemplateCode = msgSmsApiVO.getSmsTemplateCode();
        String acceptNo = msgSmsApiVO.getAcceptNo();
        //1 验证发送参数
        if (Boolean.FALSE.equals(msgApiService.validateSendMsgParms(systemId, smsTemplateCode, acceptNo))) {
            sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultCode());
            sendMsgReturnVo.setResultMsg(GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultMsg());
            return sendMsgReturnVo;
        }
        //  2.获取短信content 内容
        String contentTemplate = null;
        try {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(msgSmsApiVO);
            JSONObject content = jsonObject.getJSONObject("marco").getJSONObject(CONTENT_KEY);
            Map<String, Object> map = (Map<String, Object>) JSON.parse(String.valueOf(content));
            //3 获取短信模板中的消息
            MsgSmsTemplateDto msgSmsTemplateDto = msgSmsTemplateServiceimpl.getSmsTemplateById(smsTemplateCode, systemId);
            if (msgSmsTemplateDto == null) {
                sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultCode());
                sendMsgReturnVo.setResultMsg("msgSmsTemplateDto 短信模板查询为空");
                return sendMsgReturnVo;
            }
            contentTemplate = msgSmsTemplateDto.getContent();

            //4 短信模板替换
            if (!CollectionUtils.isEmpty(map)) {
                StrSubstitutor subjectSub = new StrSubstitutor(map);
                contentTemplate = subjectSub.replace(contentTemplate);
            }

            //5.配置发送短信所需参数 ，调用数据服务短信接口
            List<MsgSmsConfigDto> msgSmsConfigDto = msgsmsConfigServiceimpl.getMsgConfigByBusinessSystemId(systemId);
            for (MsgSmsConfigDto smsConfigDto : msgSmsConfigDto) {
                //6匹配关键字
                if (StringUtils.isEmpty(smsConfigDto.getKeyword()) || contentTemplate.contains(smsConfigDto.getKeyword())) {
                    //7调用发送接口
                    sendMsgReturnVo = msgApiService.sendMsgByDataService(smsConfigDto, acceptNo, contentTemplate, msgSmsTemplateDto);
                    if (sendMsgReturnVo.getResultCode().equals(GlobalEnum.ErrorCode.SUCCESS.getResultCode())) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.FAIL.getResultCode());
            sendMsgReturnVo.setResultMsg("发送短信出现异常");
            return sendMsgReturnVo;
        }
        return sendMsgReturnVo;
    }

    /**
     * @Description 根据短信模板编号获取短信模板的消息宏参数
     * @Author nily
     * @Date 2020/11/25
     * @Time 9:23 上午
     */
    @Override
    public SendMsgReturnVo getMarcoById(MsgSmsApiVO msgSmsApiVO) {
        String systemId = msgSmsApiVO.getSystemId();
        String smsTemplateCode = msgSmsApiVO.getSmsTemplateCode();
        SendMsgReturnVo<Object> sendMsgReturnVo = new SendMsgReturnVo<>();
        //1.参数非空判断
        if (Boolean.FALSE.equals(msgApiService.validateParms(systemId, smsTemplateCode))) {
            sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultCode());
            sendMsgReturnVo.setResultMsg(GlobalEnum.ErrorCode.REQUIRED_PARAMS_EMPTY.getResultMsg());
            return sendMsgReturnVo;
        }

        //2. 根据短信模板 id 和 查询 systemId MsgSmsmTemplate 消息模板对象
        try {
            MsgSmsTemplateDto msgSmsTemplateDto = msgSmsTemplateServiceimpl.getSmsTemplateById(smsTemplateCode, systemId);
            if (Boolean.FALSE.equals(msgApiService.validateMsgSmsTemplateDto(msgSmsTemplateDto))) {
                sendMsgReturnVo.setResultMsg("msgSmsTemplateDto  为 null ");
                return sendMsgReturnVo;
            } else if (Boolean.FALSE.equals(msgSmsTemplateDto.isValid())) {
                sendMsgReturnVo.setResultMsg("msgSmsTemplateDto 该模板已被禁用");
                return sendMsgReturnVo;
            }

            //3  提取 短信内容 {} 中的内容  listContent
            String content = msgSmsTemplateDto.getContent();
            List<String> list = null;
            List<String> listContent = null;
            if (content != null) {
                //提取集合{}中的中文名
                list = StringUtil.extractMessageByRegular(content);
                if (CollectionUtils.isEmpty(list)) {
                    sendMsgReturnVo.setResultMsg("模板内容conetent中 宏marco 为 null ");
                    return sendMsgReturnVo;
                }
                //通过英文名 提取属性名 加入 listContent 集合
                listContent = new ArrayList<>();

                for (String s : list) {
                    MsgMarcoDto msgmarcodto = msgMaecoServiceimpl.getMarcoByEnName(s, systemId);
                    String fieldName = msgmarcodto.getFieldName();
                    listContent.add(fieldName);
                }
            }

            //4 设置返回参数
            if (listContent != null) {
                String[] array = new String[listContent.size()];
                String[] contentArray = listContent.toArray(array);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(CONTENT_KEY, contentArray);
                sendMsgReturnVo.setData(jsonObject);
            }
            return sendMsgReturnVo;
        } catch (Exception e) {
            sendMsgReturnVo.setResultMsg("systemId类型转换失败");
            sendMsgReturnVo.setResultCode(GlobalEnum.ErrorCode.PARAMS_NOT_CONFORM.getResultCode());
            return sendMsgReturnVo;
        }
    }

    /**
     * @Description 保存短信日志 dto
     * @Author nily
     * @Date 2020/12/2
     * @Time 4:15 下午
     */
    private MsgSmsRecordDto saveLogToSmsRecordDto(MsgSmsConfigDto msgSmsConfigDto, String acceptNo, String msgContent, MsgSmsTemplateDto smsTemplateDto) {
        MsgSmsRecordDto msgSmsRecordDto = new MsgSmsRecordDto();
        msgSmsRecordDto.setBusinessSystemId(msgSmsConfigDto.getBusinessSystemId());
        msgSmsRecordDto.setChannelName(msgSmsConfigDto.getChannelName());
        msgSmsRecordDto.setMobile(acceptNo);
        msgSmsRecordDto.setSmsTemplateCode(smsTemplateDto.getSmsTemplateCode());
        msgSmsRecordDto.setSmsTemplateName(smsTemplateDto.getSmsTemplateName());
        msgSmsRecordDto.setContent(msgContent);
        msgSmsRecordDto.setSmsType(smsTemplateDto.getSmsType());
        msgSmsRecordDto.setNationCode("+86");
        return msgSmsRecordDto;
    }

    /**
     * @Description 保存短信日志
     * @Author nily
     * @Date 2020/12/2
     * @Time 4:15 下午
     */
    public boolean saveSmsLog(MsgSmsRecordDto msgSmsRecordDto) {
        MsgSmsRecord msgSmsRecord = new MsgSmsRecord();
        msgSmsRecord.setRowStatus(GlobalConstant.SAVE);
        BeanUtils.copyProperties(msgSmsRecordDto, msgSmsRecord);
        int save = -1;
        try {
            save = QMsgSmsRecord.msgSmsRecord.save(msgSmsRecord);
            if (save == 1) {
                logger.info("保存短信日志成功");
            }
            return true;
        } catch (Exception e) {
            logger.error("----{}-----", msgSmsRecordDto.getMobile());
            logger.error("保存短信日志失败", e);
            return false;
        }


    }

}
