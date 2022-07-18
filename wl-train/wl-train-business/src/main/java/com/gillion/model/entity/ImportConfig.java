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
import java.lang.String;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "import_config")
public class ImportConfig extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "id")
    @Generator("snowFlakeGenerator")
    private String id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "template")
    private byte[] template;

    @Column(name = "dictionary_url")
    private String dictionaryUrl;

    @Column(name = "deal_service")
    private String dealService;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "validate_head")
    private Boolean validateHead;

    @Column(name = "validate_method")
    private String validateMethod;

    @Column(name = "async")
    private Boolean async;

    @Column(name = "allow_parameter")
    private Boolean allowParameter;

    @Column(name = "primary_columns")
    private String primaryColumns;

    @Column(name = "define_column")
    private String defineColumn;

    @Column(name = "children_table")
    private String childrenTable;

}