package com.gillion.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
@Data
public class StandardDetailVo {

    /**规范条目ID*/
    @Column(name = "standard_entry_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long standardEntryId;

    /**执行周期*/
    @Column(name = "action_period")
    private Integer actionPeriod;

    /**执行说明*/
    @Column(name = "action_remark")
    private String actionRemark;

    /**执行角色ID*/
    @Column(name = "action_role_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long actionRoleId;

    /**执行序号*/
    @Column(name = "action_serial_num")
    private Integer actionSerialNum;

    /**执行时间*/
    @Column(name = "action_time")
    private Integer actionTime;

    /**适用岗位ID*/
    @Column(name = "apply_post_id")
    private String applyPostId;

    /**适用岗位职称*/
    @Column(name = "apply_professor_id")
    private String applyProfessorId;

    /**条目名称*/
    @Column(name = "entry_name")
    private String entryName;

    /**是否必须（0：是 1：否）*/
    @Column(name = "is_need")
    private Boolean isNeed;

    /**条目类型*/
    @Column(name = "item_type")
    private String itemType;

    /**统筹人ID*/
    @Column(name = "ordinator_id")
    private String ordinatorId;

    /**统筹人姓名*/
    private String ordinatorName;

    /**规范细则ID*/
    @Column(name = "standard_detail_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long standardDetailId;

    /**执行序号*/
    @Column(name = "detail_action_serial_num")
    private Integer detailActionSerialNum;

    /**细则名称*/
    @Column(name = "detail_name")
    private String detailName;
}
