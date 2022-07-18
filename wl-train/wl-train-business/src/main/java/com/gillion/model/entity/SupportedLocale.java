package com.gillion.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.gillion.ec.core.annotations.Generator;
import com.gillion.ds.entity.base.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.String;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "supported_locale")
public class SupportedLocale extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "id")
    @Generator("snowFlakeGenerator")
    private Integer id;

    /**语言*/
    @Column(name = "language")
    private String language;

    /**地区/国家*/
    @Column(name = "country")
    private String country;

    /**变量*/
    @Column(name = "variant")
    private String variant;

    @Column(name = "display")
    private String display;

    @Column(name = "state")
    private String state;

}