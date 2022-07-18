package com.share.auth.domain.platform;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * @param
 * @author Linja
 * @return
 * @Description
 * @Date 2021/10/6 15:27
 */
@Data
@ApiModel("企业平台DTO")
public class UemCompanyDTO extends BaseModel implements Serializable {
    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

    private String organizationType;

    /**当前页数**/
    @ApiModelProperty("当前页数")
    private Integer currentPage;

    @ApiModelProperty(" 当前显示条数")
    private Integer pageSize;

}
