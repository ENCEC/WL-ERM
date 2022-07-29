package com.share.auth.api;

import com.share.auth.domain.*;
import com.share.support.model.User;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author chenxf
 * @date 2020-10-26 11:32
 */
@Component
@FeignClient(value = "${application.name.auth}")

public interface ShareAuthInterface {

    /**
     * 根据操作类型查询应用数据
     * @Author:chenxf
     * @Description: 根据操作类型查询应用数据
     * @Date: 9:33 2020/11/16
     * @param opType: [opType]
     * @return :java.util.List<com.share.auth.domain.QueryApplicationDTO>
     *
     */
    @RequestMapping(value = "/application/queryApplicationForOpType")
    List<QueryApplicationDTO> queryApplicationForOpType(@RequestParam(value = "opType") String opType);

    /**
     * 根据应用id，用户id获取资源集合
     *
     * @param sysResourceQueryVO: [sysResourceQueryVO]
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 根据应用id，用户id获取资源集合
     * @Date: 17:07 2020/12/7
     */
    @RequestMapping(value = "/sysResource/queryResource")
    ResultHelper<List<QueryResourceDTO>> queryResource(@RequestBody SysResourceQueryVO sysResourceQueryVO);

    /**
     *根据企业Id获取用户信息
     * @param companyId 企业ID
     * @return List<UemUser>
     * @author xrp
     * */
    @RequestMapping(value = "/user/queryUemUserByCompanyId")
    Map<String, Object> queryUemUserByCompanyId(@RequestParam(value = "companyId") String[] companyId);


    /**
     *根据用户Id获取用户信息
     * @param uemUserId 用户ID
     * @return List<UemUser>
     * @author xrp
     * */
    @RequestMapping(value = "/user/queryUemUserByUserId")
    Map<String, Object> queryUemUserByUserId(@RequestParam(value = "uemUserId") String uemUserId);

    /**
     * 根据用户id获取用户信息接口，根据用户id和clientId获取用户角色信息
     * @Author:chenxf
     * @Description: 根据用户id获取用户信息接口，根据用户id和clientId获取用户角色信息
     * @Date: 15:55 2020/12/10
     * @param uid uid
     * @param clientId : [uemUserId, clientId]
     * @return :com.share.auth.domain.User
     *
     */
    @RequestMapping(value = "/user/getUserAllInfo")
    User getUserAllInfo(@RequestParam(value = "uid") Long uid, @RequestParam(value = "clientId",required = false) String clientId);


    /**
     * 根据物流交换代码返回公司信息
     * @param companyCode 物流交换代码
     * @return List<UemCompany>
     * @author xrp
     * */
    @RequestMapping(value = "/user/queryUemUserCompany")
    Map<String, Object> queryUemUserCompany(@RequestParam(value = "companyCode") String companyCode);

    /**
     * 根据调度系统角色code查询用户
     * @Author:chenxf
     * @Description: 根据调度系统角色code查询用户
     * @Date: 16:11 2021/2/3
     * @param roleCode 角色代码
     * @return :java.util.List<com.share.support.model.User>
     *
     */
    @RequestMapping(value = "/user/queryUserByRoleCode")
    List<User> queryUserByRoleCode(@RequestParam(value = "roleCode") String roleCode);

    /**
     *  根据承运商ID集合返回用户和承运商信息（一般调度）
     * @param uemCompanyIdList 企业ID集合
     * @return Map<String, Object>
     * @author cxq
     * */
    @RequestMapping(value = "/user/queryUemUserCompanyById")
    Map<String, Object> queryUemUserCompanyById(@RequestParam(value = "uemCompanyIdList") List<Long>  uemCompanyIdList);
    /**
     * 根据用户ID集合返回用户和公司信息
     * @param userIdList 用户ID集合
     * @return Map<String, Object>
     * @author cxq
     * */
    @RequestMapping(value = "/user/queryUemUserCompanyByUserId")
    Map<String, Object> queryUemUserCompanyByUserId(@RequestParam(value = "userIdList") List<Long> userIdList);


    /**
     * 根据规则查询企业
     * @param companyTypeCode 企业类型代码：LOGIN_LOGISTICS_SUPPLY：承运商
     * @param isMatch 是否匹配，默认false
     * @param itemCodes 企业类型选中代码
     * @return 根据规则匹配的企业
     */
    @GetMapping(value = "/company/queryCompanyByRule")
    List<UemCompanyVO> queryCompanyByRule(@RequestParam(value = "companyTypeCode") String companyTypeCode,
                                          @RequestParam(value = "isMatch") Boolean isMatch,
                                          @RequestParam(value = "itemCodes") List<String> itemCodes);

    /**
     * 根据企业id、企业类型选中项获取下级企业id集合
     * @param companyId 企业id
     * @param selectedItemCodes 企业类型选中项（S7-港口，M3-地市单位，R5-粮企）
     * @return 下级企业id集合
     */
    @GetMapping(value = "/querySubordinateCompanyIds")
    List<Long> querySubordinateCompanyIds(@RequestParam(value = "companyId") Long companyId,
                                          @RequestParam(value = "selectedItemCodes") List<String> selectedItemCodes);

    /**
     * 根据企业id获取上级省厅企业省份
     * @param companyIds 企业id
     * @return 企业id-上级省厅企业省份对应关系
     */
    @GetMapping(value = "/querySuperiorProvinceCompanyProvince")
    Map<Long, String> querySuperiorProvinceCompanyProvince(@RequestParam(value = "companyIds") List<Long> companyIds);


    /**
     * 根据企业id获取下级省厅企业省份、粮企企业省份
     * @param companyId 企业id
     * @return 企业id的下级省厅企业省份、粮企企业省份
     */
    @GetMapping(value = "/querySubordinateProvinceCompanyProvince")
    List<String> querySubordinateProvinceCompanyProvince(@RequestParam(value = "companyId") Long companyId);

    /**
     * @Author hehao
     * @Description 个人中心获取当前登录用户信息接口
     * @Date  2021/9/25 17:33
     * @Param []
     * @return com.share.support.result.ResultHelper<com.share.auth.domain.UemUserDto> 用户信息
     **/
    @ApiOperation("获取当前登录用户信息接口")
    @GetMapping("/user/getLoginUserInfo")
    @ResponseBody
    ResultHelper<UemUserDto> getLoginUserInfo();

    @ApiOperation("获取问好接口")
    @GetMapping("/user/getHelloFlag")
    @ResponseBody
    ResultHelper<String> getHelloFlag();

}
