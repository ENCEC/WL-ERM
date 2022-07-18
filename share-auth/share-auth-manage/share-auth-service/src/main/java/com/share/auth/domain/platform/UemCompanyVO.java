package com.share.auth.domain.platform;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @param
 * @author Linja
 * @return
 * @Description
 * @Date 2021/10/6 15:41
 */
@Data
@ApiModel("企业平台VO")
public class UemCompanyVO extends BaseModel implements Serializable {
        /**id*/
        @JsonSerialize(using = Long2String.class)
        @JsonDeserialize(using = String2Long.class)
        private Long uemCompanyId;

        private String organizationType;


}
