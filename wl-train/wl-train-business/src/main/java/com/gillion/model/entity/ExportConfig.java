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
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "export_config")
public class ExportConfig extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "id")
    @Generator("snowFlakeGenerator")
    private String id;

    @Column(name = "print_mode")
    private Boolean printMode;

    @Column(name = "template")
    private byte[] template;

    @Column(name = "async")
    private Boolean async;

    @Column(name = "dictionary_url")
    private String dictionaryUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "page_size")
    private Integer pageSize;

    @Column(name = "class_name")
    private String className;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "statistics_url")
    private String statisticsUrl;

    @Column(name = "column_setting")
    private String columnSetting;

}