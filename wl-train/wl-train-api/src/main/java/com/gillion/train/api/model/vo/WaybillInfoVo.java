package com.gillion.train.api.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wengms
 * @date 2021/1/11 11:06 上午
 * @email wengms@gillion.com.cn
 */
@Data
public class WaybillInfoVo {
    private static final long serialVersionUID = -66221936508038704L;
    /**
     * 运单号
     */
    private Long waybillId;

    /**
     * 揽收员工编号
     */
    private Long collectEmployeeId;

    /**
     * 揽件人姓名
     */
    
    private String collectEmployeeCname;

    /**
     * 揽件人手机
     */
    
    private String collectEmployeeMobile;

    
    private Date collectDatetime;

    /**
     * 发货地区编号
     */
    
    private String sendAreaCode;

    /**
     * 发货人详址
     */
    
    private String sendAddressDetail;

    /**
     * 发货人中文姓名
     */
    
    private String shipperCname;

    /**
     * 发货人手机号码
     */
    
    private String shipperMobile;

    /**
     * 收货人姓名
     */
    
    private String consigneeCname;

    /**
     * 收货人手机号码
     */
    
    private String consigneeMobile;

    /**
     * 货物类型编号
     */
    
    private String productTypeCode;

    /**
     * 收货地区编号
     */
    
    private String deliveryAreaCode;

    /**
     * 收货地址详情
     */
    
    private String deliveryAddressDetail;

    private WaybillFeeVo waybillFee;

    private List<WaybillRouteNodeVo> waybillRouteNodes;
}
