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
@Table(name = "shop")
public class Shop extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**礼物店id*/
    @Id
    @Column(name = "shop_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long shopId;

    /**地址*/
    @Column(name = "address")
    private String address;

    @Column(name = "build_time")
    private Date buildTime;

    /**礼物店名*/
    @Column(name = "gift_shop")
    private String giftShop;

    /**乐观锁版本号*/
    @Column(name = "version")
    private Integer version;

}