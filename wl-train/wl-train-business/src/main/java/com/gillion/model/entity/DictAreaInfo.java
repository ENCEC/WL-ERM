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
@Table(name = "dict_area_info")
public class DictAreaInfo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "area_code")
    @Generator("snowFlakeGenerator")
    private String areaCode;

    @Column(name = "area_short_name")
    private String areaShortName;

    @Column(name = "area_full_name")
    private String areaFullName;

}