package com.share.auth.model.entity;

import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "app_nodes")
public class AppNodes extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    @Id
    @Column(name = "id")
    @Generator("snowFlakeGenerator")
    private Integer id;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "node_name")
    private String nodeName;

    @Column(name = "node_num")
    private Integer nodeNum;

}