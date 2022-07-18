package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
@Data
public class SysDictCodeDto implements Serializable {

    /**数据字典配置项ID*/
    private String sysDictCodeId;
    /**关联字典主表id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysDictTypeId;
    /**配置项编码*/
    private String dictCode;
    /**配置项名称*/
    private String dictName;
    /**备注*/
    private String remark;
    /**是否禁用（0禁用，1启用）*/
    private Boolean isValid;
    /**启/禁用时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date invalidTime;
}
