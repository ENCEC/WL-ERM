package com.share.auth.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.gillion.ec.core.annotations.Generator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import com.gillion.ds.entity.base.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "uem_project")
public class UemProject extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**项目id*/
    @Id
    @Column(name = "uem_project_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemProjectId;

    /**实际结束日期*/
    @Column(name = "actual_end_time")
    private Date actualEndTime;

    /**实际开始日期*/
    @Column(name = "actual_start_time")
    private Date actualStartTime;

    /**总监ID*/
    @Column(name = "chief_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long chiefId;

    /**总监名称*/
    @Column(name = "chief_name")
    private String chiefName;

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

    /**项目客户*/
    @Column(name = "customer")
    private String customer;

    /**需求组长ID*/
    @Column(name = "demand_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long demandId;

    /**需求组长名称*/
    @Column(name = "demand_name")
    private String demandName;

    /**开发经理ID*/
    @Column(name = "dev_director_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long devDirectorId;

    /**开发经理名称*/
    @Column(name = "dev_director_name")
    private String devDirectorName;

    /**负责人ID（项目经理）*/
    @Column(name = "duty_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long dutyId;

    /**负责人名称（项目经理）*/
    @Column(name = "duty_name")
    private String dutyName;

    /**项目金额*/
    @Column(name = "fcy")
    private Integer fcy;

    /**需求组员ID（逗号分隔）*/
    @Column(name = "gen_demand_users")
    private String genDemandUsers;

    /**开发组员ID（逗号分隔）*/
    @Column(name = "gen_dev_users")
    private String genDevUsers;

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

    /**计划结束日期*/
    @Column(name = "plan_end_time")
    private Date planEndTime;

    /**计划开始日期*/
    @Column(name = "plan_start_time")
    private Date planStartTime;

    /**负责名称*/
    @Column(name = "project_name")
    private String projectName;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**状态（0：未开始 1：进行中 2：暂停中 3：已完成 4：延期 5：终止）*/
    @Column(name = "status")
    private Integer status;

    /**项目人数*/
    @Column(name = "total_num")
    private Integer totalNum;

    /**所属部门ID*/
    @Column(name = "uem_dept_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemDeptId;

}