package com.share.auth.model.vo;

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
 * @author chenxf
 * @date 2021-01-28 14:54
 */
@Data
public class UemCompanyCargoTypeDTO implements Serializable {
    private static final long serialVersionUID = 1;

    /**需方企业经营货物类型表id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyCargoTypeId;

    /**货物数据字典主表code*/
    private String cargoType;

    /**企业经营货物类型选中项code*/
    private String cargoTypeCode;

    /**创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    /**版本号*/
    private Integer recordVersion;

    /**关联企业id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;
}
