package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
@Data
public class SysDictTypeDto implements Serializable {

//    /**数据字典表ID*/
//    private String sysDictTypeId;
//    /**字典类型*/
//    private String dictTypeCode;
//    /**字典名称*/
//    private String dictTypeName;
//    /**备注*/
//    private String remark;
//    /**是否禁用（0禁用，1启用）*/
//    private Boolean isValid;
//    /**启/禁用时间*/
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    private Date invalidTime;

    @Id
    @Column(name = "sys_dict_type_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long sysDictTypeId;

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

    /**字典类型*/
    @Column(name = "dict_type_code")
    private String dictTypeCode;

    /**字典名称*/
    @Column(name = "dict_type_name")
    private String dictTypeName;

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

    /**分页页数*/
    private Integer currentPage;

    /**分页条数*/
    private Integer pageSize;



}
