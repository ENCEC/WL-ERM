package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemCompanyNameDTO;
import com.share.auth.domain.UemCompanyVO;
import com.share.auth.domain.daoService.*;
import com.share.auth.model.vo.UemCompanyNameVO;
import com.share.auth.service.MdCityService;
import com.share.auth.service.MdDistrictService;
import com.share.auth.service.MdProvinceService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @param
 * @Author Linja
 * @return
 * @Description 联想控件查询省 市 区 信息
 * @Date 2021/10/12 20:04
 */

@Api("地区信息控制器")
@RestController
@Slf4j
public class AreaController {
    @Autowired
    MdProvinceService mdProvinceService;

    @Autowired
    MdCityService mdCityService;

    @Autowired
    MdDistrictService mdDistrictService;

    /**
     * @author Linja
     * @param mdProvinceDTO
     * @return
     */
    @PostMapping("/AreaSelect/selectProvince")
    @ResponseBody
    @ApiOperation(value = "根据关键字查省份详细", notes = "根据关键字查省份详细")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public ResultHelper<Page<MdProvinceVO>> queryProvince(@RequestBody MdProvinceDTO mdProvinceDTO){
        Page<MdProvinceVO> mdProvinceVOPage = mdProvinceService.queryProvinceList(mdProvinceDTO);
        return CommonResult.getSuccessResultData(mdProvinceVOPage);
    }

    @PostMapping("/AreaSelect/selectCity")
    @ResponseBody
    @ApiOperation(value = "根据关键字查城市详细", notes = "根据关键字查城市详细")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public ResultHelper<Page<MdCityVO>> queryCity(@RequestBody MdCityDTO mdCityDTO){
        Page<MdCityVO> mdCityVOPage = mdCityService.selectCityByProvinceCode(mdCityDTO);
        return CommonResult.getSuccessResultData(mdCityVOPage);
    }

    @PostMapping("/AreaSelect/selectDistrict")
    @ResponseBody
    @ApiOperation(value = "根据关键字查区县详细", notes = "根据关键字查区县详细")
    @ApiImplicitParam(name = "UemCompanyNameDTO", value = "企业名称信息封装类", dataType = "List<Long>",type = "query")
    public ResultHelper<Page<MdDistrictVO>> queryDistrict(@RequestBody MdDistrictDTO mdDistrictDTO){
        Page<MdDistrictVO> mdDistrictVOPage = mdDistrictService.selectDistrictByCityId(mdDistrictDTO);
        return CommonResult.getSuccessResultData(mdDistrictVOPage);
    }
}
