package com.share.support.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemProjectId;

    /**
     * 总监ID
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long chiefId;

    /**
     * 项目客户
     */
    private String customer;

    /**
     * 需求组长ID
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long demandId;

    /**
     * 开发经理ID
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long devDirectorId;

    /**
     * 负责人ID（项目经理）
     */
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long dutyId;

    /**
     * 需求组员ID（逗号分隔）
     */
    private String genDemandUsers;

    /**
     * 开发组员ID（逗号分隔）
     */
    private String genDevUsers;

    private String projectName;
}
