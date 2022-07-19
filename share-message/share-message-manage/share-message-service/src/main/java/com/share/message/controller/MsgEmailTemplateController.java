package com.share.message.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.esotericsoftware.minlog.Log;
import com.share.message.enums.GlobalEnum;
import com.share.message.vo.GetMailTemplateMarcoVO;
import com.share.message.vo.MsgEmailTemplateVO;
import com.share.message.service.MsgEmailTemplateService;
import com.share.message.vo.SendEmailVO;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮件模板配置控制层
 *
 * @author tujx
 * @date 2020/10/20
 */
@Slf4j
@RestController
@RequestMapping("/msgEmailTemplate")
public class MsgEmailTemplateController {

    @Autowired
    private MsgEmailTemplateService msgEmailTemplateService;


    /**
     * 新增邮件模板配置
     *
     * @param msgEmailTemplateVO 邮件模板信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/saveEmailTemplate")
    @ApiOperation(value = "新增邮件模板", notes = "新增邮件模置")
    @ApiImplicitParam(name = "msgEmailTemplateVO", value = "新增邮件模板入参", required = true, dataType = "MsgEmailTemplateVO", paramType = "body")
    public ResultHelper<String> saveEmailTemplate(@RequestBody MsgEmailTemplateVO msgEmailTemplateVO) {
        return msgEmailTemplateService.saveEmailTemplate(msgEmailTemplateVO);
    }


    /**
     * 编辑邮件模板配置
     *
     * @param msgEmailTemplateVO 邮件模板信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/updateEmailTemplate")
    @ApiOperation(value = "编辑邮件模板", notes = "编辑邮件模板")
    public ResultHelper<String> updateEmailTemplate(@RequestBody MsgEmailTemplateVO msgEmailTemplateVO) {
        return msgEmailTemplateService.updateEmailTemplate(msgEmailTemplateVO);
    }


    /**
     * 根据邮件模板编号获取邮件模板的消息宏参数
     *
     * @param getMailTemplateMarcoVO 邮件模板信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/getEmailTemplateByCode")
    @ApiOperation(value = "根据邮件模板编号获取邮件模板的消息宏参数", notes = "根据邮件模板编号获取邮件模板的消息宏参数")
    public Map<String, Object> getEmailTemplateByCode(@RequestBody GetMailTemplateMarcoVO getMailTemplateMarcoVO) {
        String systemId = getMailTemplateMarcoVO.getSystemId();
        String emailTemplateCode = getMailTemplateMarcoVO.getEmailTemplateCode();
        return msgEmailTemplateService.getEmailTemplateMarcoByCode(systemId, emailTemplateCode);
    }


    /**
     * 通过邮件模板发送邮件
     *
     * @param sendEmailVO 邮件发送信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/sendEmail")
    @ApiOperation(value = "邮件发送", notes = "邮件发送")
    public Map<String, Object> sendEmail(@RequestBody SendEmailVO sendEmailVO) {
        return msgEmailTemplateService.sendEmailByTemplate(sendEmailVO);
    }


    /**
     * 携带附件的邮件发送
     *
     * @param sendEmailVO  邮件发送信息
     * @param paramJsonStr 模板参数Json字符串
     * @param files        附件
     * @return
     * @throws
     * @author tujx
     */
    @PostMapping(value = "/sendEmailWithFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "携带附件的邮件发送", notes = "携带附件的邮件发送")
    public Map<String, Object> sendEmailWithFile(SendEmailVO sendEmailVO, String paramJsonStr, @RequestPart("files") List<MultipartFile> files) {
        if (StringUtils.isNotBlank(paramJsonStr)) {
            Map<String, Object> map;
            try {
                map = JSON.parseObject(paramJsonStr, Map.class);
            } catch (JSONException e) {
                //参数格式异常
                Log.error("邮件发送失败：宏参数json字符串转换失败，" + paramJsonStr);
                Map<String, Object> result = new HashMap<>(2);
                result.put("resultCode", GlobalEnum.ErrorCode.FAIL.getResultCode());
                result.put("resultMsg", GlobalEnum.ErrorCode.FAIL.getResultMsg() + ",宏参数json字符串转换失败");
                return result;
            }
            //模板参数
            sendEmailVO.setMarcoAndAttachParams(map);
        }
        //附件
        sendEmailVO.setFileList(files);
        return msgEmailTemplateService.sendEmailByTemplate(sendEmailVO);
    }

}
