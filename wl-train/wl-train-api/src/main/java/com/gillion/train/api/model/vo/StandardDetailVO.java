package com.gillion.train.api.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity

public class StandardDetailVO extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**规范细则ID*/
    @Id
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long standardDetailId;

    /**执行序号*/
    private Integer actionSerialNum;

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

    /**细则名称*/
    private String detailName;

    /**条目类型*/
    private String itemType;

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

    /**规范条目ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long standardEntryId;

    /**状态（0：启用 1：禁用）*/
    private Boolean status;

    /**规范条目*/
    private String entryName;

    /** 分页页数 */
    private Integer currentPage;

    /** 分页显示条数 */
    private Integer pageSize;

}