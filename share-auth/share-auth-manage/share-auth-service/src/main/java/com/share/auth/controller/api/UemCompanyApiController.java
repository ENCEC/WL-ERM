package com.share.auth.controller.api;

import com.share.auth.model.vo.OperateResultVO;
import com.share.auth.model.vo.UemCompanyOperateVO;
import com.share.auth.service.UemCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公司API
 * @author chenhy
 * @date 2021-04-19
 */
@Api(tags = "企业API")
@Slf4j
@RestController
@RequestMapping(value = "/api/uemCompany")
public class UemCompanyApiController {

    @Autowired
    private UemCompanyService uemCompanyService;

    /**
     * 国家综合交通运输信息平台增删改组织机构
     * @param uemCompanyOperateVO 组织信息
     * @return 结果
     */
    @ApiOperation(value = "增删改组织机构")
    @PostMapping(value = "/operate")
    public OperateResultVO operateUemCompany(@RequestBody UemCompanyOperateVO uemCompanyOperateVO) {
        try {
            return uemCompanyService.operateUemCompany(uemCompanyOperateVO);
        } catch (Exception e) {
            log.error("国家综合交通运输信息平台增删改组织机构异常：{}", e.getMessage(), e);
            return OperateResultVO.getFailMessage(uemCompanyOperateVO.getOptionType(), uemCompanyOperateVO.getOrgCode(), null);
        }
    }
}
