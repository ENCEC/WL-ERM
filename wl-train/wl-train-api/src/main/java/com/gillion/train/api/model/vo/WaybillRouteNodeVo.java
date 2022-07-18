package com.gillion.train.api.model.vo;

import lombok.Data;

/**
 * @author wengms
 * @date 2021/1/11 11:08 上午
 * @email wengms@gillion.com.cn
 */
@Data
public class WaybillRouteNodeVo {
    /**
     * 路由节点编号
     */
    private Long waybillRouteNodeId;

    /**
     * 运单编号
     */
    private Long waybillId;

    /**
     * 路由节点开始物流站点名称
     */
    private String startingSiteName;

    /**
     * 路由节点到达站点名称
     */
    private String arrivalSiteName;

    /**
     * 节点在路由中的序号
     */
    private Integer siteNum;
}
