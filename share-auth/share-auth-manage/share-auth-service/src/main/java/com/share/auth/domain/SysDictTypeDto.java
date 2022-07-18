package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
@Data
public class SysDictTypeDto implements Serializable {

    /**数据字典表ID*/
    private String sysDictTypeId;
    /**字典类型*/
    private String dictTypeCode;
    /**字典名称*/
    private String dictTypeName;
    /**备注*/
    private String remark;
    /**是否禁用（0禁用，1启用）*/
    private Boolean isValid;
    /**启/禁用时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date invalidTime;

}
