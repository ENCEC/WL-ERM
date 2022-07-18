package com.share.auth.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "uem_file")
public class UemFile extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "uem_file_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemFileId;

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

    /**文件名称*/
    @Column(name = "file_name")
    private String fileName;

    /**文件路径*/
    @Column(name = "file_path")
    private String filePath;

    /**文件类型(0身份证正面，1身份证反面，2企业证件，3管理员认证函)*/
    @Column(name = "file_type")
    private Byte fileType;

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
    @Version
    private Integer recordVersion;

    /**关联用户或企业id*/
    @Column(name = "target_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long targetId;

}