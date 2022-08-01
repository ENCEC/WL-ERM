package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName UemProjectDTO
 * @Author weiq
 * @Date 2022/7/29 15:39
 * @Version 1.0
 **/
@ApiModel("UemProjectDTO 部门项目信息")
@Data
public class UemProjectDTO {

    /**项目id*/
    @ApiModelProperty("id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemProjectId;

    /**实际结束日期*/
    @ApiModelProperty("实际结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualEndTime;

    /**实际开始日期*/
    @ApiModelProperty("实际开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date actualStartTime;

    /**总监ID*/
    @ApiModelProperty("总监ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long chiefId;

    /**总监名称*/
    @ApiModelProperty("总监名称")
    private String chiefName;

    /**创建时间*/
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**创建人id*/
    @ApiModelProperty("创建人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**项目客户*/
    @ApiModelProperty("项目客户")
    private String customer;

    /**需求组长ID*/
    @ApiModelProperty("需求组长ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long demandId;

    /**需求组长名称*/
    @ApiModelProperty("需求组长名称")
    private String demandName;

    /**开发经理ID*/
    @ApiModelProperty("开发经理ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long devDirectorId;

    /**开发经理名称*/
    @ApiModelProperty("开发经理名称")
    private String devDirectorName;

    /**负责人ID（项目经理）*/
    @ApiModelProperty("负责人ID（项目经理）")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long dutyId;

    /**负责人名称（项目经理）*/
    @ApiModelProperty("负责人名称（项目经理）")
    private String dutyName;

    /**项目金额*/
    @ApiModelProperty("项目金额")
    private Integer fcy;

    /**需求组员ID（逗号分隔）*/
    @ApiModelProperty("需求组员ID（逗号分隔）")
    private String genDemandUsers;

    /**开发组员ID（逗号分隔）*/
    @ApiModelProperty("开发组员ID（逗号分隔）")
    private String genDevUsers;

    /**修改人id*/
    @ApiModelProperty("修改人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @ApiModelProperty("修改人名称")
    private String modifierName;

    /**修改时间*/
    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    /**计划结束日期*/
    @ApiModelProperty("计划结束日期*")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date planEndTime;

    /**计划开始日期*/
    @ApiModelProperty("计划开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date planStartTime;

    /**版本号*/
    @ApiModelProperty("版本号")
    private Integer recordVersion;

    /**状态（0：未开始 1：进行中 2：暂停中 3：已完成 4：延期 5：终止）*/
    @ApiModelProperty("状态（0：未开始 1：进行中 2：暂停中 3：已完成 4：延期 5：终止）")
    private Integer status;

    /**项目人数*/
    @ApiModelProperty("项目人数")
    private Integer totalNum;

    /**所属部门ID*/
    @ApiModelProperty("所属部门ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemDeptId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("开发成员名称")
    @JsonProperty("genDevUsersName")
    private String genDevUsersName;

    @ApiModelProperty("需求成员名称")
    @JsonProperty("genDemandUsersName")
    private String genDemandUsersName;

    /** 分页页数 */
    @ApiModelProperty("分页页数")
    private Integer currentPage;

    /** 分页显示条数 */
    @ApiModelProperty("分页显示条数")
    private Integer pageSize;

    private Long uemUserId;

}
