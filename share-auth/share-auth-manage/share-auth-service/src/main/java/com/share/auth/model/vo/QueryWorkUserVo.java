package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/29
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("QueryWorkUserVo 在职用户信息")
public class QueryWorkUserVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String account;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;
}
