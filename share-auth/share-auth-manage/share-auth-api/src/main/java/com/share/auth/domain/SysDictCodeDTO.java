package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SysDictCodeDTO
 * @Author weiq
 * @Date 2022/8/18 10:26
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictCodeDTO extends BaseModel implements Serializable {
    /**id*/
    @Id
    @Column(name = "sys_dict_code_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long sysDictCodeId;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @Column(name = "creator_name")
    private String creatorName;

    /**配置项编码*/
    @Column(name = "dict_code")
    private String dictCode;

    /**配置项名称*/
    @Column(name = "dict_name")
    private String dictName;

    /**序号*/
    @Column(name = "dict_sort")
    private Integer dictSort;

    /**启/禁用时间*/
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**是否禁用（0禁用，1启用）*/
    @Column(name = "is_valid")
    private Boolean isValid;

    /**修改人id*/
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @Column(name = "modifier_name")
    private String modifierName;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**备注*/
    @Column(name = "remark")
    private String remark;

    /**关联字典主表id*/
    @Column(name = "sys_dict_type_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysDictTypeId;
}
