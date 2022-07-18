package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemLogDto;
import com.share.auth.domain.UemUserCompanyDto;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserRoleDto;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;

import java.util.List;


/**
 * @author xrp
 * @date 20201021
 */
@Api("企业用户管理模块")
public interface UserCompanyManageService {

    /**企业用户管理
     * @param uemUserDto 用户表封装类
     * @return Page<UemUserDto>
     * @author xrp
     * */
    Page<UemUserDto> queryUserCompanyManage(UemUserDto uemUserDto);

    /**
     * 查询企业解绑用户
     * @param uemUserCompanyDto 解绑表封装类
     * @return Page<UemUserDto>
     * @author xrp
     * */
    Page<UemUserCompanyDto> queryUnBindUser(UemUserCompanyDto uemUserCompanyDto);


    /**
     * 新增企业用户
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> saveUemUser(UemUserDto uemUserDto);

    /**
     * 启停
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> startStop(UemUserDto uemUserDto);

    /**
     * 用户与企业解除绑定
     *
     * @param uemUserId    用户id
     * @param uemCompanyId 公司id
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 用户与企业解除绑定
     * @Date: 15:10 2021/1/11
     */
    ResultHelper<Object> unbindUser(String uemUserId, String uemCompanyId);

    /**权限分配
     * @param uemUserId 用户表ID
     * @return List<UemUserRoleDto>
     * @author xrp
     * */
    ResultHelper<List<UemUserRoleDto>> authorityAssignment(String uemUserId);


    /**
     * 权限分配 获取详情
     *
     * @param uemUserRoleId 用户角色表ID
     * @return List<UemUserRoleDto>
     * @author xrp
     */
    List<UemUserRoleDto> getAuthorityAssignment(String uemUserRoleId);

    /**
     * 权限分配 修改
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> updateAuthorityAssignment(UemUserRoleDto uemUserRoleDto);

    /**
     * 权限分配 启停
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> startStopByAuthorityAssignment(UemUserRoleDto uemUserRoleDto);

    /**
     * 权限分配 新加
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> saveAuthorityAssignment(UemUserRoleDto uemUserRoleDto);

    /**
     * 根据手机号生成验证码
     * @param mobile 手机号
     * @return Map<String, Object>
     * @author xrp
     * */
    @Deprecated
    ResultHelper generateAuthCodeByMobile(String mobile);

    /**
     * 验证手机验证码是否正确
     *
     * @param uemUserDto 用户表
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> verifyAuthCodeByMobile(UemUserDto uemUserDto);

    /**
     * 根据邮箱生成验证码
     *
     * @param email 邮箱
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> generateAuthCodeByEmail(String email);

    /**
     * 验证邮箱验证码是否正确
     *
     * @param uemUserDto 用户表
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> verifyAuthCodeByEmail(UemUserDto uemUserDto);



    /**
     * 登录日志  个人
     * @param uemLogDto 登录日志表
     * @return Page<UemLogDto>
     * @author xrp
     * */
    Page<UemLogDto> loginLogIndividual(UemLogDto uemLogDto);


    /**
     * 登录日志  全部
     *
     * @param uemLogDto 登录日志表
     * @return Page<UemLogDto>
     * @author xrp
     */
    Page<UemLogDto> loginLogAll(UemLogDto uemLogDto);


    /**
     * 解除绑定
     *
     * @param uemUserId 用户id
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 解除绑定
     * @Date: 19:44 2021/1/13
     */
    ResultHelper<Object> unbindUemUser(String uemUserId);

    /**
     * 企业用户绑定审核
     *
     * @param uemUserDto: [uemUserDto]
     * @return :com.share.support.result.ResultHelper
     * @Author:chenxf
     * @Description: 企业用户绑定审核
     * @Date: 14:58 2021/2/23
     */
    ResultHelper<Object> reviewUemUserCompany(UemUserDto uemUserDto);

    /**
     * 权限开通发送短信
     * @param uemUserId 用户id
     * @param sysApplicationId 开通应用id
     * @return 发送短信结果
     */
    ResultHelper<Object> sendMessage(Long uemUserId, Long sysApplicationId);


}
