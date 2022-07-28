package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */

@Data
public class UemProjectVO extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**项目id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemProjectId;

    /**实际结束日期*/
    private Date actualEndTime;

    /**实际开始日期*/
    private Date actualStartTime;

    /**总监ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long chiefId;

    /**总监名称*/
    private String chiefName;

    /**创建时间*/
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**项目客户*/
    private String customer;

    /**需求组长ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long demandId;

    /**需求组长名称*/
    private String demandName;

    /**开发经理ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long devDirectorId;

    /**开发经理名称*/
    private String devDirectorName;

    /**负责人ID（项目经理）*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long dutyId;

    /**负责人名称（项目经理）*/
    private String dutyName;

    /**项目金额*/
    private Integer fcy;

    /**需求组员ID（逗号分隔）*/
    private String genDemandUsers;

    /**开发组员ID（逗号分隔）*/
    private String genDevUsers;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    private Date modifyTime;

    /**计划结束日期*/
    private Date planEndTime;

    /**计划开始日期*/
    private Date planStartTime;

    /**版本号*/
    private Integer recordVersion;

    /**状态（0：未开始 1：进行中 2：暂停中 3：已完成 4：延期 5：终止）*/
    private Integer status;

    /**项目人数*/
    private Integer totalNum;

    /**所属部门ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemDeptId;

}