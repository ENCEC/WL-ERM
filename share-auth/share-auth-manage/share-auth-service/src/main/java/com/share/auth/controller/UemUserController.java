package com.share.auth.controller;

import com.gillion.saas.redis.SassRedisInterface;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.UemUserDto;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.vo.UserAndCompanyVo;
import com.share.support.model.User;
import com.share.auth.service.UemUserService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;


/**
 * @author xrp
 * @date 2020/11/3 0003
 */
@Api("用户控制器")
@Controller
@RequestMapping("user")
@Slf4j
public class UemUserController {

    @Autowired
    private UemUserService uemUserService;
    @Value("${web_domain}")
    private String webDomain;
    @Autowired
    private SassRedisInterface redisInterface;
    /**忘记密码键**/
    public static final String FORGET_KEY = "forget_random";

    /**
     * 手机号生成验证码
     *
     * @param telephone 手机号
     * @param sign      标识为1的时候是快速注册的验证码，为2的时候是找回密码的验证码，为3的时候是修改绑定手机号
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("手机号获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号", required = true, dataType = "String", paramType = "getAuthCode"),
            @ApiImplicitParam(name = "sign", value = "标识(1是快速注册的验证码，2是找回密码的验证码，3是修改绑定手机号)", required = true, dataType = "String", paramType = "getAuthCode")
    })
    @GetMapping("/getAuthCode")
    @ResponseBody
    public ResultHelper<Object> getAuthCode(@RequestParam(required = true) String telephone, @RequestParam(required = true) String sign, @RequestParam(required = true) String code) {
        if (Objects.isNull(code)) {
            return CommonResult.getFaildResultData();
        }
        String redisCode = redisInterface.get(FORGET_KEY);
        if(!code.equals(redisCode)){
            return CommonResult.getFaildResultData();
        } else {
            redisInterface.del(FORGET_KEY);
        }
        return uemUserService.generateAuthCodeByTelephone(telephone, sign);
    }

    /**
     * 验证验证码
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("判断验证码是否正确")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表封装类", required = true, dataType = "UemUserDto", paramType = "verifyAuthCode")
    @PostMapping("/verifyAuthCode")
    @ResponseBody
    public ResultHelper<Object> verifyAuthCode(@RequestBody UemUserDto uemUserDto) {
        return uemUserService.verifyAuthCode(uemUserDto);
    }

    /**
     * 注册并验证验证码
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("用户注册并判断验证码")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表封装类", required = true, dataType = "UemUserDto", paramType = "register")
    @PostMapping("/register")
    @ResponseBody
    public ResultHelper<Object> register(@RequestBody UemUserDto uemUserDto) {
        return uemUserService.register(uemUserDto);
    }

    /**
     * 更新密码
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("更新密码")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表封装类", required = true, dataType = "UemUserDto", paramType = "updatePassword")
    @PostMapping("/updatePassword")
    @ResponseBody
    public ResultHelper<Object> updatePassword(@RequestBody UemUserDto uemUserDto) {
        return uemUserService.updatePassword(uemUserDto);
    }

    /**
     * 邮箱找回密码
     *
     * @param email 邮箱
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("发送邮箱网址找回密码")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "findPasswordByMail")
    @GetMapping("/findPasswordByMail")
    @ResponseBody
    public ResultHelper<Object> findPasswordByMail(@RequestParam String email) {
        return uemUserService.findPasswordByMail(email);
    }


    /**
     * 根据企业Id获取在调度系统有角色的用户信息
     *
     * @param companyId 企业ID
     * @return List<UemUser>
     * @author xrp
     */
//    @ApiOperation("根据企业Id获取用户信息")
//    @ApiImplicitParam(name = "companyId", value = "企业ID", required = true, dataType = "String[]", paramType = "queryUemUserByCompanyId")
//    @PostMapping("/queryUemUserByCompanyId")
//    @ResponseBody
//    public ResultHelper<List<UemUser>> queryUemUserByCompanyId(@RequestParam String[] companyId) {
//        List<UemUser> uemUserDtoList = uemUserService.queryUemUserByCompanyId(companyId);
//        return CommonResult.getSuccessResultData(uemUserDtoList);
//    }


    /**
     * 根据用户Id获取用户信息
     *
     * @param uemUserId 用户ID
     * @return List<UemUser>
     * @author xrp
     */
    @ApiOperation("根据用户Id获取用户信息")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "String", paramType = "queryUemUserByUserId")
    @GetMapping("/queryUemUserByUserId")
    @ResponseBody
    public ResultHelper<UemUser> queryUemUserByUserId(@RequestParam String uemUserId) {
        List<UemUser> uemUserDtoList = uemUserService.queryUemUserByUserId(uemUserId);
        return CommonResult.getSuccessResultData(uemUserDtoList);
    }

    /**
     * @Author:chenxf
     * @Description: Feign接口, 根据用户id获取用户信息接口，根据用户id和clientId获取用户角色信息
     * @Date: 15:55 2020/12/10
     * @Param: [uemUserId, clientId]
     * @Return:com.share.auth.domain.User
     */
    @ApiOperation("根据用户id获取用户信息接口，根据用户id和clientId获取用户角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", required = true, dataType = "Long", paramType = "getUserAllInfo"),
            @ApiImplicitParam(name = "clientId", value = "应用clientId", required = false, dataType = "String", paramType = "getUserAllInfo")
    })
    @GetMapping(value = "/getUserAllInfo")
    @ResponseBody
    public User getUserAllInfo(@RequestParam(value = "uid") Long uid, @RequestParam(value = "clientId", required = false) String clientId) {
        return uemUserService.getUserAllInfo(uid, clientId);
    }


    /**
     * 根据物流交换代码返回公司信息
     *
     * @param companyCode 物流交换代码
     * @return List<UemCompany>
     * @author xrp
     */
    @ApiOperation("根据物流交换代码返回公司信息")
    @ApiImplicitParam(name = "companyCode", value = "物流交换代码", required = true, dataType = "String", paramType = "queryUemUserCompany")
    @GetMapping("/queryUemUserCompany")
    @ResponseBody
    public ResultHelper<List<UemCompany>> queryUemUserCompany(@RequestParam(value = "companyCode") String companyCode) {
        List<UemCompany> uemCompanyList = uemUserService.queryUemUserCompany(companyCode);
        return CommonResult.getSuccessResultData(uemCompanyList);
    }

    /**
     * @Author:chenxf
     * @Description: 校验用户名唯一性
     * @Date: 15:03 2021/1/6
     * @Param: [account]
     * @Return:java.lang.Boolean
     */
    @ApiOperation("校验用户名是否唯一")
    @ApiImplicitParam(name = "account", value = "用户名", required = true, dataType = "String", paramType = "validateAccount")
    @GetMapping("/validateAccount")
    @ResponseBody
    public Boolean validateAccount(@RequestParam(value = "account") String account) {
        return uemUserService.validateAccount(account);
    }

    /**
     * @Author:chenxf
     * @Description: 校验密码和当前登录人是否一致
     * @Date: 15:03 2021/1/6
     * @Param: [account]
     * @Return:java.lang.Boolean
     */
    @ApiOperation("校验密码和当前登录人是否一致")
    @ApiImplicitParam(name = "password", value = "MD5加密后的密码", required = true, dataType = "String", paramType = "validatePassword")
    @GetMapping("/validatePassword")
    @ResponseBody
    public Boolean validatePassword(@RequestParam(value = "password") String password) {
        return uemUserService.validatePassword(password);
    }

    /**
     * @Author:chenxf
     * @Description: 修改用户信息统一接口
     * @Date: 10:39 2021/1/9
     * @Param: [uemUserDto]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @ApiOperation("修改用户信息接口")
    @ApiImplicitParam(name = "uemUserDto", value = "用户信息封装类", required = true, dataType = "UemUserDto", paramType = "updateUemUserInfo")
    @PostMapping("/updateUemUserInfo")
    @ResponseBody
    public ResultHelper<Object> updateUemUserInfo(@RequestBody UemUserDto uemUserDto) {
        return uemUserService.updateUemUserInfo(uemUserDto);
    }

    /**
     * @Author:chenxf
     * @Description: 个人中心获取当前登录用户信息接口
     * @Date: 14:09 2021/1/9
     * @Param: []
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @ApiOperation("获取当前登录用户信息接口")
    @GetMapping("/getLoginUserInfo")
    @ResponseBody
    public ResultHelper<UemUserDto> getLoginUserInfo(HttpServletRequest request, HttpServletResponse response) {
        ResultHelper<UemUserDto> userDtoResultHelper = uemUserService.getLoginUserInfo();
        boolean flag = userDtoResultHelper.getSuccess();
        if (!flag) {
            Cookie cookie = new Cookie("access_token", "");
            cookie.setValue("");
            cookie.setPath("/");
            if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
                cookie.setDomain(webDomain);
            }
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            Cookie uidCookie = new Cookie("uid", "");
            uidCookie.setValue("");
            uidCookie.setPath("/");
            if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
                uidCookie.setDomain(webDomain);
            }
            uidCookie.setSecure(false);
            uidCookie.setMaxAge(0);
            response.addCookie(uidCookie);
        }
        return userDtoResultHelper;
    }

    /**
     * @Author:chenxf
     * @Description: 根据调度系统角色code查询用户
     * @Date: 16:13 2021/2/3
     * @Param: [roleCode]
     * @Return:java.util.List<com.share.support.model.User>
     */
//    @ApiOperation("根据调度系统角色code查询用户")
//    @ApiImplicitParam(name = "roleCode", value = "角色编码", required = true, dataType = "String", paramType = "queryUserByRoleCode")
//    @GetMapping(value = "/queryUserByRoleCode")
//    @ResponseBody
//    public List<User> queryUserByRoleCode(@RequestParam(value = "roleCode") String roleCode) {
//        return uemUserService.queryUserByRoleCode(roleCode);
//    }

    /**
     * 根据承运商ID集合返回用户和承运商信息（一般调度）
     *
     * @param uemCompanyIdList 企业ID集合
     * @return List<UserAndCompanyVo>
     * @author cxq
     */
//    @ApiOperation("根据承运商ID集合返回用户和承运商信息（一般调度）")
//    @ApiImplicitParam(name = "uemCompanyIdList", value = "企业ID", required = true, dataType = "List<Long>", paramType = "queryUemUserCompanyById")
//    @GetMapping("/queryUemUserCompanyById")
//    @ResponseBody
//    public ResultHelper<List<UserAndCompanyVo>> queryUemUserCompanyById(@RequestParam(value = "uemCompanyIdList") List<Long> uemCompanyIdList) {
//        List<UserAndCompanyVo> uemCompanyList = uemUserService.queryUemUserCompanyById(uemCompanyIdList);
//        return CommonResult.getSuccessResultData(uemCompanyList);
//    }

    /**
     * 根据用户ID集合返回用户和公司信息
     *
     * @param userIdList 用户ID集合
     * @return List<UserAndCompanyVo>
     * @author cxq
     */
    @ApiOperation("根据用户ID集合返回用户和公司信息")
    @ApiImplicitParam(name = "userIdList", value = "用户ID集合", required = true, dataType = "List<Long>", paramType = "query")
    @GetMapping("/queryUemUserCompanyByUserId")
    @ResponseBody
    public ResultHelper<List<UserAndCompanyVo>> queryUemUserCompanyByUserId(@RequestParam(value = "userIdList") List<Long> userIdList) {
        List<UserAndCompanyVo> uemCompanyList = uemUserService.queryUemUserCompanyByUserId(userIdList);
        return CommonResult.getSuccessResultData(uemCompanyList);
    }


}
