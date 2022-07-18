package com.share.auth.controller.api;

import com.share.auth.model.vo.OperateResultVO;
import com.share.auth.model.vo.UemUserOperateVO;
import com.share.auth.service.UemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户API
 * @author chenhy
 * @date 2021-04-19
 */
@Api(tags = "用户API")
@Slf4j
@RestController
@RequestMapping(value = "/api/uemUser")
public class UemUserApiController {

    @Autowired
    private UemUserService uemUserService;

    /**
     * 国家综合交通运输信息平台增删改用户
     * @param uemUserOperateVO 用户
     * @return 结果
     */
    @ApiOperation(value = "增删改用户")
    @PostMapping(value = "/operate")
    public OperateResultVO operateUemUser(@RequestBody UemUserOperateVO uemUserOperateVO) {
        try {
            return uemUserService.operateUemUser(uemUserOperateVO);
        } catch (Exception e) {
            log.error("国家综合交通运输信息平台增删改组织机构异常：{}", e.getMessage(), e);
            return OperateResultVO.getFailMessage(uemUserOperateVO.getOptionType(), null, uemUserOperateVO.getLoginNo());
        }
    }

}
