package com.share.auth.domain.daoService;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.Api;
import lombok.Data;

import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/26 20:04
 */
@Api("企业信息表")
@Data
public class CargoTypeVO  extends BaseModel implements Serializable{
    /**需方企业经营货物类型表id*/
    private Long uemCompanyCargoTypeId;

    /**货物数据字典主表code*/
    private String cargoType;

    /**企业经营货物类型选中项code*/
    private String cargoTypeCode;

    /**创建时间*/
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    private Date modifyTime;

    /**版本号*/
    @Version
    private Integer recordVersion;

    /**关联企业id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;
}
