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
import java.lang.Long;
import java.lang.String;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "waybill_route_node")
public class WaybillRouteNode extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**路由节点编号*/
    @Id
    @Column(name = "waybill_route_node_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long waybillRouteNodeId;

    /**运单编号*/
    @Column(name = "waybill_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long waybillId;

    /**路由节点开始物流站点名称*/
    @Column(name = "starting_site_name")
    private String startingSiteName;

    /**路由节点到达站点名称*/
    @Column(name = "arrival_site_name")
    private String arrivalSiteName;

    /**节点在路由中的序号*/
    @Column(name = "site_num")
    private Integer siteNum;

}