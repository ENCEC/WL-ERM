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
import java.math.BigDecimal;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "waybill_fee")
public class WaybillFee extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**运单编号*/
    @Id
    @Column(name = "waybill_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long waybillId;

    /**总运费*/
    @Column(name = "total_freight")
    private BigDecimal totalFreight;

    /**保价费*/
    @Column(name = "insurance_fee")
    private BigDecimal insuranceFee;

    /**重量*/
    @Column(name = "weight")
    private BigDecimal weight;

    /**货物体积*/
    @Column(name = "volume")
    private BigDecimal volume;

}