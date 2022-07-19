package com.share.message.api;

import com.share.message.domain.MsgEmailTemplateVO;
import com.share.message.domain.SendEmailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author tujx
 * @description 邮件发送接口
 * @date 2020/11/10
 */
@FeignClient(name = "${application.name.message}")
public interface EmailTemplateService {

    /**
     * 邮件发送
     *
     * @param sendEmailVO 邮件发送信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/msgEmailTemplate/sendEmail")
    Map<String, Object> sendEmail(@RequestBody SendEmailVO sendEmailVO);


    /**
     * 根据邮件模板编号获取邮件模板的消息宏参数
     *
     * @param msgEmailTemplateVO 模板信息
     * @return Map
     * @throws
     * @author tujx
     */
    @PostMapping("/msgEmailTemplate/getEmailTemplateByCode")
    Map<String, Object> getEmailTemplateByCode(@RequestBody MsgEmailTemplateVO msgEmailTemplateVO);


    /**
     * 携带附件的邮件发送
     *
     * @param sendEmailVO 邮件信息
     * @param paramJsonStr 模板参数Json字符串
     * @param files 附件
     * @return
     * @throws
     * @author tujx
     */
    @PostMapping(value = "/msgEmailTemplate/sendEmailWithFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String, Object> sendEmailWithFile(@RequestPart(value = "sendEmailVO") SendEmailVO sendEmailVO, @RequestPart(value = "paramJsonStr") String paramJsonStr, @RequestPart("files") List<MultipartFile> files);
}
