package com.share.auth.center.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gillion.utils.CommonResult;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.model.entity.OauthClientDetails;
import com.share.auth.center.model.querymodels.QOauthClientDetails;
import com.share.auth.center.service.ResourcePermissionService;
import com.share.auth.center.service.StaticResourceService;
import com.share.auth.center.util.JsonMapperHolder;
import com.share.auth.center.util.OauthClientUtils;
import com.share.support.constants.UserConstant;
import com.share.support.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

/**
 * @Author:chenxf
 * @Description: 资源权限相关控制器
 * @Date: 15:55 2021/1/18
 * @Param: 
 * @Return:
 *
 */
@Api("资源相关控制器")
@RestController
@RequestMapping("/system/security")
@Slf4j
public class ResourceController {

    @Autowired
    private CredentialProcessor credentialProcessor;

    @Autowired
    private ResourcePermissionService resourcePermissionService;

    @Autowired
    private StaticResourceService staticResourceService;

    /**
     * @Author:chenxf
     * @Description: 前端校验接口
     * @Date: 10:42 2020/12/21
     * @Param: [pageUrl, clientId, request]
     * @Return:com.fasterxml.jackson.databind.node.ObjectNode
     *
     */
    @ApiOperation("前端男权限相关查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageUrl", value = "页面路径url", required = true, dataType = "String", paramType = "getSessionAttrsAndNoPermits"),
            @ApiImplicitParam(name = "clientId", value = "客户端clientId", required = true, dataType = "String", paramType = "getSessionAttrsAndNoPermits")
    })
    @RequestMapping(value = "/getSessionAttrsAndNoPermits", method = RequestMethod.GET)
    public ObjectNode getSessionAttrsAndNoPermits(@RequestParam String pageUrl, @RequestParam String clientId, HttpServletRequest request) {
        // 从session获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserConstant.USER);
        final ObjectNode sessionAttrsAndNoPermits = JsonMapperHolder.createObjectNode();
        //判断是不是在白名单中
        if (resourcePermissionService.isInIgnorePathPattern(pageUrl)) {
            sessionAttrsAndNoPermits.set("noPermits", JsonMapperHolder.convert(""));
            return sessionAttrsAndNoPermits;
        } else {
            return getUnAnnoymousOperateResult(user, sessionAttrsAndNoPermits, pageUrl, clientId);
        }
    }

    /**
     * @Author:chenxf
     * @Description: 非匿名用户登录处理
     * @Date: 10:43 2020/12/21
     * @Param: [user, sessionAttrsAndNoPermits, pageUrl, clientId]
     * @Return:com.fasterxml.jackson.databind.node.ObjectNode
     *
     */
    private ObjectNode getUnAnnoymousOperateResult(User user,ObjectNode sessionAttrsAndNoPermits,String pageUrl,String clientId){
        OauthClientDetails oauthClientDetail = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        // 允许所有用户登录的客户端id
        List<Long> allowAllClientIdList = OauthClientUtils.getAllowAllClientId();
        // 只允许管理员登录的客户端id
        List<Long> allowAdminClientIdList = OauthClientUtils.getAllowAdminClientId();
        if (allowAllClientIdList.contains(oauthClientDetail.getSysApplicationId()) || allowAdminClientIdList.contains(oauthClientDetail.getSysApplicationId())){
            // 附加信息，暂无
            sessionAttrsAndNoPermits.set("sessionAttrs",JsonMapperHolder.createObjectNode());
            return sessionAttrsAndNoPermits;
        }
        // 获取无权限的url
        Collection<String> noPermissionUrlPatterns = staticResourceService.getNoPermissionUrl(user,pageUrl, oauthClientDetail);
        if(CollectionUtils.isNotEmpty(noPermissionUrlPatterns)){
            sessionAttrsAndNoPermits.set("noPermits", JsonMapperHolder.convert(noPermissionUrlPatterns));
        }
        // 附加信息，暂无
        sessionAttrsAndNoPermits.set("sessionAttrs",JsonMapperHolder.createObjectNode());
        return sessionAttrsAndNoPermits;
    }

    /**
     * @Author:chenxf
     * @Description: 后端接口校验（供EDS调用）
     * @Date: 10:43 2020/12/21
     * @Param: [params]
     * @Return:com.share.auth.center.util.CommonResult
     *
     */
    @ApiOperation("后端接口校验")
    @ApiImplicitParam(name = "params", value = "后端接口校验入参", required = true, dataType = "String[]", paramType = "validateUrl")
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult validateUrl(@RequestBody String[] params){ return resourcePermissionService.validate(params);
    }
}
