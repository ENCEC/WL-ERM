package com.share.auth.domain;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @date 20201021
 * @author xrp*/
@Api("管理员表")
@Data
public class UemCompanyManageDto {

    /**用户ID*/
    private String uemUserId;

    /**绑定企业*/
    private String blindCompanny;

    /**上传管理员申请认证函*/
    private String fileName;

    /**企业ID*/
    private String uemCompanyId;

    /**公正函上传地址id*/
    private String fileUrlId;
}
