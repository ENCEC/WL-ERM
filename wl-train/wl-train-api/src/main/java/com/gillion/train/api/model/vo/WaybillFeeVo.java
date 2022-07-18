package com.gillion.train.api.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wengms
 * @date 2021/1/11 11:07 上午
 * @email wengms@gillion.com.cn
 */
@Data
public class WaybillFeeVo {
    /**
     * 运单编号
     */

    private Long waybillId;

    /**
     * 总运费
     */
    private BigDecimal totalFreight;

    /**
     * 保价费
     */
    private BigDecimal insuranceFee;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal volume;
}
