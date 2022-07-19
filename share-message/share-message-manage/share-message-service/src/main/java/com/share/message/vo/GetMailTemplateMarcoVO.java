package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tujx
 * @description 根据系统编码和邮件模板编码获取宏参数VO
 * @date 2021/01/05
 */
@ApiModel(value = "根据系统编码和邮件模板编码获取宏参数VO")
@Data
public class GetMailTemplateMarcoVO {

    /**
     * 使用方系统id
     */
    @ApiModelProperty(value = "使用方系统编码")
    private String systemId;

    /**
     * 邮件模板编码
     */
    @ApiModelProperty(value = "邮件模板编码")
    private String emailTemplateCode;


}
