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
@Table(name = "waybill_info")
public class WaybillInfo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**运单号*/
    @Id
    @Column(name = "waybill_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long waybillId;

    /**揽收员工编号*/
    @Column(name = "collect_employee_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long collectEmployeeId;

    /**录入员工姓名*/
    @Column(name = "collect_employee_cname")
    private String collectEmployeeCname;

    /**录入员工手机*/
    @Column(name = "collect_employee_mobile")
    private String collectEmployeeMobile;

    /**采购日期*/
    @Column(name = "collect_datetime")
    private Date collectDatetime;

    /**发货地区编号*/
    @Column(name = "send_area_code")
    private String sendAreaCode;

    /**发货人详址*/
    @Column(name = "send_address_detail")
    private String sendAddressDetail;

    /**发货人中文姓名*/
    @Column(name = "shipper_cname")
    private String shipperCname;

    /**发货人手机号码*/
    @Column(name = "shipper_mobile")
    private String shipperMobile;

    /**收货人姓名*/
    @Column(name = "consignee_cname")
    private String consigneeCname;

    /**收货人手机号码*/
    @Column(name = "consignee_mobile")
    private String consigneeMobile;

    /**货物类型编号*/
    @Column(name = "product_type_code")
    private String productTypeCode;

    /**收货地区编号*/
    @Column(name = "delivery_area_code")
    private String deliveryAreaCode;

    /**收货地址详情*/
    @Column(name = "delivery_address_detail")
    private String deliveryAddressDetail;

    @Column(name = "estimate_delivery_time")
    private Date estimateDeliveryTime;

}