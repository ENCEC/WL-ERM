package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.domain.UemUserRoleDto;
import com.share.support.result.ResultHelper;

import java.util.List;

/**
 * 用户管理模块
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-07-25
 */
public interface UemUserManageService {

    /**
     * 查询用户信息
     *
     * @param uemUserDto 用户信息封装类
     * @return Page<UemUserDto>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-25
     */
    ResultHelper<Page<UemUserDto>> queryUemUser(UemUserDto uemUserDto);

    /**
     * 用户管理详情
     *
     * @param uemUserId 用户ID
     * @return UemUserDto
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-25
     */
    ResultHelper<UemUserDto> getUemUser(Long uemUserId);

    /**
     * 用户管理 启停
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<?> uemUserStartStop(UemUserDto uemUserDto);


    /**
     * 修改用户信息
     * @param uemUserEditDTO 用户表封装类
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-25
     */
    ResultHelper<?> editUemUser(UemUserEditDTO uemUserEditDTO);

    /**
     * 用户逻辑删除
     *
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-25
     */
    ResultHelper<?> deleteUemUser(Long uemUserId);

    /**
     * 管理员新增用户
     *
     * @param uemUserEditDTO 用户信息
     * @return 新增结果
     */
    ResultHelper<?> saveUemUser(UemUserEditDTO uemUserEditDTO);

    /**
     * 管理员重置用户密码
     *
     * @param uemUserId 用户id
     * @return 重置结果
     */
    ResultHelper<?> resetUemUserPassword(Long uemUserId);

    /**
     * 新增用户时异步发送短信提醒给新用户
     *
     * @param account 用户名
     * @param email 邮件地址
     * @param passwordText 新密码
     * @param isReset true:发送重置密码邮件; false:发送新增用户邮件
     * @date 2022-07-25
     */
    void sendEmailWithPassword(String account, String email, String passwordText, boolean isReset);

    /**
     * 根据用户ID获取角色列表
     * @param uemUserDto 用户信息
     * @return com.share.support.result.ResultHelper<java.util.List<com.share.auth.domain.SysRoleDTO>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<List<SysRoleDTO>> queryRoleListByUser(UemUserDto uemUserDto);

    /**
     * 赋予用户角色
     * @param uemUserRoleDtoList 获取uemUserId和sysRoleId
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<?> bindUserAndRole(List<UemUserRoleDto> uemUserRoleDtoList);

    /**
     * 清除一个用户的所有角色
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<?> unbindAllRoleOfUser(Long uemUserId);
}
