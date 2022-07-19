package com.share.file.model.entity;

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

    /**主键*/
    @Id
    @Column(name = "id")
    @Generator("snowFlakeGenerator")
    private Integer id;

    /**应用名称*/
    @Column(name = "app_name")
    private String appName;

    /**节点名称*/
    @Column(name = "node_name")
    private String nodeName;

    /**节点编号*/
    @Column(name = "node_num")
    private Integer nodeNum;

}