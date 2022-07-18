package com.gillion.model.entity;

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
import java.lang.Integer;
import java.lang.String;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "rule_number_config")
public class RuleNumberConfig extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long id;

    /**单号规则编码*/
    @Column(name = "rule_code")
    private String ruleCode;

    /**所属组织*/
    @Column(name = "group_name")
    private String groupName;

    /**表达式*/
    @Column(name = "expression")
    private String expression;

    /**单号类型*/
    @Column(name = "control_type")
    private Integer controlType;

    @Column(name = "create_time")
    private Date createTime;

    /**最大值*/
    @Column(name = "max_value")
    private Integer maxValue;

    /**重置类型*/
    @Column(name = "reset_type")
    private Integer resetType;

    /**单号规则名*/
    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "update_time")
    private Date updateTime;

    /**步长*/
    @Column(name = "step")
    private Integer step;

    /**初始值*/
    @Column(name = "init_value")
    private Integer initValue;

}