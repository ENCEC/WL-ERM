package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.domain.UemUserRoleDto;
import com.share.auth.model.entity.SysPost;
import com.share.auth.model.entity.SysTechnicalTitle;
import com.share.auth.model.entity.UemDept;
import com.share.auth.model.entity.UemProject;
import com.share.support.result.ResultHelper;
import org.springframework.web.multipart.MultipartFile;

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
     *
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
     * @param account      用户名
     * @param email        邮件地址
     * @param passwordText 新密码
     * @param isReset      true:发送重置密码邮件; false:发送新增用户邮件
     * @date 2022-07-25
     */
    void sendEmailWithPassword(String account, String email, String passwordText, boolean isReset);

    /**
     * 根据用户ID获取角色列表
     *
     * @param uemUserDto 用户信息
     * @return com.share.support.result.ResultHelper<java.util.List < com.share.auth.domain.SysRoleDTO>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<List<SysRoleDTO>> queryRoleListByUser(UemUserDto uemUserDto);

    /**
     * 赋予用户角色
     *
     * @param uemUserRoleDtoList 获取uemUserId和sysRoleId
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<?> bindUserAndRole(List<UemUserRoleDto> uemUserRoleDtoList);

    /**
     * 清除一个用户的所有角色
     *
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<?> unbindAllRoleOfUser(Long uemUserId);

    /**
     * 查询员工信息
     *
     * @param uemUserDto
     * @return Page<UemUserDto>
     * @author wzr
     * @date 2022-08-01
     */
    Page<UemUserDto> queryStaffByPage(UemUserDto uemUserDto);


    /**
     * 根据id查询对应员工信息
     *
     * @param uemUserId
     * @author wzr
     * @date 2022-08-02
     */
    UemUserDto queryStaffById(Long uemUserId);

    /**
     * 编辑员工信息
     *
     * @param uemUserDto
     * @author wzr
     * @date 2022-08-02
     */
    ResultHelper<Object> updateStaff(UemUserDto uemUserDto);


    /**
     * 删除员工信息
     *
     * @param uemUserId
     * @author wzr
     * @date 2022-08-02
     */
    ResultHelper<Object> deleteStaff(Long uemUserId);


    /**
     * 转正，离职，辞退---查看信息
     *
     * @param uemUserId
     * @author wzr
     * @date 2022-08-04
     */

    UemUserDto queryStaffInfo(Long uemUserId);


    /**
     * 添加离职信息
     *
     * @param uemUserDto
     * @author wzr
     * @date 2022-08-04
     */
    ResultHelper<Object> saveResignInfo(UemUserDto uemUserDto);

    /**
     * 添加辞退信息
     *
     * @param uemUserDto
     * @author wzr
     * @date 2022-08-04
     */
    ResultHelper<Object> saveDismissInfo(UemUserDto uemUserDto);

    /**
     * 查看转正评语部分信息
     *
     * @param uemUserId
     * @return
     */
    UemUserDto queryOfferInfo(Long uemUserId);

    /**
     * 查看离职原因
     *
     * @param uemUserId
     * @return
     */
    ResultHelper<UemUserDto> queryLeaveInfo(Long uemUserId);

    /**
     * 查看辞退原因
     *
     * @param uemUserId
     * @return
     */
    ResultHelper<UemUserDto> queryDismissInfo(Long uemUserId);

    /**
     * 保存员工信息
     *
     * @param uemUserDto
     * @return
     */
    ResultHelper<?> preservationUemUser(UemUserDto uemUserDto);

    /**
     * 离职申请添加离职理由
     *
     * @param
     * @return
     */
    ResultHelper<?> updateLeaveReason(Long uemUserId, String leaveReason);

    /**
     * 上传文件
     *
     * @param uemUserId
     * @param systemId
     * @param fileType
     * @param fileName
     * @param file
     * @return
     */
    ResultHelper<?> uploadExternalFile(Long uemUserId, String systemId, String fileType, String fileName, String type, MultipartFile file);

    /**
     * 下拉框查询所有岗位的信息
     *
     * @return
     */
    List<SysPost> querySysPost();

    /**
     * 下拉框查询所有职称的信息
     *
     * @return
     */
    List<SysTechnicalTitle> querySysTechnicalTitle();

    /**
     * 下拉框查询所有项目的信息
     *
     * @return
     */
    List<UemProject> queryUemProject();

    /**
     * 下拉框查询所有部门的信息
     *
     * @return
     */
    List<UemDept> queryUemDept();

    /**
     * 服务调用（任务模块通过查询用户id 取到name）
     *
     * @param uemUserId
     * @author wzr
     * @date 2022-08-11
     */

    UemUserDto queryUemUserById(Long uemUserId);

    /**
     * 服务调用（更改员工状态）
     *
     * @param uemUserId
     * @author wzr
     * @date 2022-08-22
     */
    ResultHelper<?> updateJobStatus(Long uemUserId);

    /**
     * 设置数据库resume为空
     *
     * @param uemUserId
     * @return
     */
    ResultHelper<?> deleteResume(Long uemUserId, String type);

}
