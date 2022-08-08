package com.share.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.nacos.common.util.ClassUtils;
import com.fr.web.core.A.C;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.command.FluentSelectOneCommand;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.domain.UemUserRoleDto;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.entity.UemUserRole;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.model.querymodels.QUemUserRole;
import com.share.auth.service.UemUserManageService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.DefaultUserService;
import com.share.message.api.EmailTemplateService;
import com.share.message.domain.SendEmailVO;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import com.share.support.util.MD5EnCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户信息管理
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-07-25
 */
@Service
@Slf4j
public class UemUserManageServiceImpl implements UemUserManageService {

    @Autowired
    private DefaultUserService defaultUserService;

    @Autowired
    private UemUserService uemUserService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * 查询用户信息
     *
     * @param uemUserDto 用户信息封装类
     * @return Page<UemUserDto>
     * @date 2022-07-25
     */
    @Override
    public ResultHelper<Page<UemUserDto>> queryUemUser(UemUserDto uemUserDto) {

        // 用户名
        String account = uemUserDto.getAccount();

        // 姓名
        String name = uemUserDto.getName();

        if (!StringUtils.isEmpty(account)) {
            uemUserDto.setAccount("%" + account + "%");
        }

        if (!StringUtils.isEmpty(name)) {
            uemUserDto.setName("%" + name + "%");
        }

        int pageNo = (uemUserDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserDto.getPageNo();
        int pageSize = (uemUserDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserDto.getPageSize();

        Page<UemUserDto> uemUserDtoPage = QUemUser.uemUser.select(
                        QUemUser.uemUser.fieldContainer()
                ).where(
                        QUemUser.account.like(":account")
                                .and(QUemUser.name.like(":name"))
                                .and(QUemUser.isValid.eq(":isValid"))
                                .and(QUemUser.isDeleted.eq$(false))
                ).paging(pageNo, pageSize)
                .sorting(QUemUser.createTime.desc())
                .mapperTo(UemUserDto.class)
                .execute(uemUserDto);

        return CommonResult.getSuccessResultData(uemUserDtoPage);
    }

    /**
     * 查询用户信息实体类
     *
     * @param uemUserId 用户ID
     * @return com.share.auth.model.entity.UemUser
     * @date 2022-07-26
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UemUser getUemUserById(Long uemUserId) {
        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.uemUserId.eq$(uemUserId).and(QUemUser.isDeleted.eq$(false)))
                .execute();
        if (uemUserList.size() == 1) {
            return uemUserList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取用户信息
     *
     * @param uemUserId 用户ID
     * @return List<UemUserDto>
     * @date 2022-07-25
     */
    @Override
    public ResultHelper<UemUserDto> getUemUser(Long uemUserId) {
        UemUser uemUser = this.getUemUserById(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        } else {
            UemUserDto uemUserDto = new UemUserDto();
            BeanUtils.copyProperties(uemUser, uemUserDto);
            return CommonResult.getSuccessResultData(uemUserDto);
        }
    }

    /**
     * 用户启用/禁用
     *
     * @param uemUserDto 用户表封装类
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> uemUserStartStop(UemUserDto uemUserDto) {
        //用户表ID
        Long uemUserId = uemUserDto.getUemUserId();
        //是否禁用(0禁用,1启用)
        Boolean isValid = uemUserDto.getIsValid();
        // 检查用户是否存在
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户ID不能为空");
        }
        UemUser uemUser = this.getUemUserById(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        }
        // 设置参数
        uemUser.setUemUserId(uemUserId);
        uemUser.setIsValid(isValid);
        uemUser.setInvalidTime(new Date());
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int updateCount = QUemUser.uemUser.selective(QUemUser.isValid, QUemUser.invalidTime).execute(uemUser);
        // 检查更新是否成功
        if (updateCount == 1) {
            return CommonResult.getSuccessResultData("启停成功");
        } else {
            return CommonResult.getFaildResultData("启停失败");
        }
    }

    /**
     * 修改用户信息
     *
     * @param uemUserEditDto 用户信息
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @date 2022-07-25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> editUemUser(UemUserEditDTO uemUserEditDto) {
        // 根据主键查询用户信息
        Long uemUserId = uemUserEditDto.getUemUserId();
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户id不允许为空");
        }
        UemUser uemUser = this.getUemUserById(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        }
        // 检查手机号是否被占用
        if (!uemUser.getMobile().equals(uemUserEditDto.getMobile())) {
            List<UemUser> uemUserList = QUemUser.uemUser
                    .select(QUemUser.uemUser.fieldContainer())
                    .where(QUemUser.mobile.eq$(uemUserEditDto.getMobile()))
                    .execute();
            if (CollectionUtils.isNotEmpty(uemUserList)) {
                return CommonResult.getSuccessResultData("该手机号已经被占用！");
            }
        }
        // 更新用户信息
        BeanUtils.copyProperties(uemUserEditDto, uemUser);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int rowCount = QUemUser.uemUser.save(uemUser);
        // 检查是否更新成功
        if (rowCount == 1) {
            return CommonResult.getSuccessResultData("用户修改成功");
        } else {
            return CommonResult.getFaildResultData("用户修改失败");
        }
    }

    /**
     * 用户逻辑删除
     *
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> deleteUemUser(Long uemUserId) {
        // 获取用户
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户信息主键不能为空");
        }
        UemUser uemUser = this.getUemUserById(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        }
        // 逻辑删除
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_DELETED);
        uemUser.setIsDeleted(true);
        int rowCount = QUemUser.uemUser.save(uemUser);
        System.err.println(rowCount);
        // 检查是否更新成功
        if (rowCount == 1) {
            return CommonResult.getSuccessResultData("用户删除成功");
        } else {
            return CommonResult.getFaildResultData("用户删除失败");
        }
    }

    /**
     * 管理员新增用户,发送邮件通知
     *
     * @param uemUserEditDTO 用户信息新增修改接口入参
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> saveUemUser(UemUserEditDTO uemUserEditDTO) {
        // 用户名是否可用
        List<UemUser> accountUser = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.account.eq$(uemUserEditDTO.getAccount()))
                .execute();
        if (CollectionUtils.isNotEmpty(accountUser)) {
            return CommonResult.getSuccessResultData("该用户名已注册过！");
        }
        // 手机号是否可用
        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.mobile.eq$(uemUserEditDTO.getMobile()))
                .execute();
        if (CollectionUtils.isNotEmpty(uemUserList)) {
            return CommonResult.getSuccessResultData("该手机号已注册过！");
        }
        // 设置字段
        UemUser uemUser = new UemUser();
        BeanUtils.copyProperties(uemUserEditDTO, uemUser);
        uemUser.setIsValid(true);
        uemUser.setIsLocked(false);
        uemUser.setJobStatus(0L);
        uemUser.setIsDeleted(false);
        // 设置密码
        String passwordText = RandomUtil.randomString(12);
        String password = MD5EnCodeUtils.encryptionPassword(passwordText);
//        password = MD5EnCodeUtils.encryptionPassword(password);
        uemUser.setPassword(password);
        // 新增用户
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        int rowCount = QUemUser.uemUser.save(uemUser);
        // 检查是否新增成功,并异步发送邮件通知
        if (rowCount == 1) {
            UemUserManageService service = SpringUtil.getBean(UemUserManageService.class);
            service.sendEmailWithPassword(uemUser.getAccount(), uemUser.getEmail(), passwordText, false);
            return CommonResult.getSuccessResultData("用户新增成功");
        } else {
            return CommonResult.getFaildResultData("用户新增失败");
        }
    }

    /**
     * 管理员重置用户密码,并发送邮件给用户
     *
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<java.lang.String>
     * @date 2022-07-25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> resetUemUserPassword(Long uemUserId) {
        // 检查用户是否存在
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户名为空");
        }
        UemUser uemUser = this.getUemUserById(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        }
        // 生成新密码
        String passwordText = RandomUtil.randomString(12);
        String password = MD5EnCodeUtils.encryptionPassword(passwordText);
//        password = MD5EnCodeUtils.encryptionPassword(password);
        // 更新用户
        uemUser.setPassword(password);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int rowCount = QUemUser.uemUser.save(uemUser);
        // 检查是否更新成功,并异步发送邮件通知
        if (rowCount == 1) {
            UemUserManageService service = SpringUtil.getBean(UemUserManageService.class);
            service.sendEmailWithPassword(uemUser.getAccount(), uemUser.getEmail(), passwordText, true);
            return CommonResult.getSuccessResultData("重置密码成功");
        } else {
            return CommonResult.getFaildResultData("重置密码失败");
        }
    }

    /**
     * 新增用户时异步发送短信提醒给新用户
     *
     * @param account      用户名
     * @param email        邮件地址
     * @param passwordText 新密码
     * @param isReset      true:发送重置密码邮件; false:发送新增用户邮件
     * @date 2022-07-25
     */
    @Async
    @Override
    public void sendEmailWithPassword(String account, String email, String passwordText, boolean isReset) {
        // 设置模板参数
        Map<String, Object> params = new HashMap<>(10);
        Map<String, String> contentParams = new HashMap<>(10);
        contentParams.put("account", account);
        contentParams.put("password", passwordText);
        params.put("content", contentParams);
        // 设置邮件参数
        SendEmailVO sendEmailVO = new SendEmailVO();
        sendEmailVO.setToEmail(email);
        sendEmailVO.setEmailTemplateCode(isReset ? "RESET_PASSWORD" : "NEW_ACCOUNT");
        sendEmailVO.setSystemId("YYDM200013");
        sendEmailVO.setMarcoAndAttachParams(params);
        // 发送邮件
        log.warn("send email, " + account + ", " + passwordText);
        emailTemplateService.sendEmail(sendEmailVO);
    }

    /**
     * 根据用户ID获取角色列表
     *
     * @param uemUserDto 用户信息
     * @return ResultHelper<List < SysRoleDTO>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @Override
    public ResultHelper<List<SysRoleDTO>> queryRoleListByUser(UemUserDto uemUserDto) {
        if (Objects.isNull(uemUserDto) || Objects.isNull(uemUserDto.getUemUserId())) {
            CommonResult.getFaildResultData("用户ID不能为空");
        }
        List<SysRoleDTO> sysRoleDTOList = DSContext
                .customization("WL-ERM_queryRoleListByUser")
                .select()
                .mapperTo(SysRoleDTO.class)
                .execute(uemUserDto);
        return CommonResult.getSuccessResultData(sysRoleDTOList);
    }

    /**
     * 赋予用户角色
     *
     * @param uemUserRoleDtoList 获取uemUserId和sysRoleId
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> bindUserAndRole(List<UemUserRoleDto> uemUserRoleDtoList) {
        List<UemUserRole> uemUserRoles = new LinkedList<>();
        if (uemUserRoleDtoList.size() <= 0) {
            return CommonResult.getFaildResultData("列表不能为空");
        }
        long uniqueUemUserId = Long.parseLong(uemUserRoleDtoList.get(0).getUemUserId());
        for (UemUserRoleDto uemUserRoleDto : uemUserRoleDtoList) {
            if (Objects.isNull(uemUserRoleDto.getUemUserId())) {
                return CommonResult.getFaildResultData("用户ID不能为空");
            }
            if (Objects.isNull(uemUserRoleDto.getSysRoleId())) {
                return CommonResult.getFaildResultData("角色ID不能为空");
            }
            Long uemUserId = Long.parseLong(uemUserRoleDto.getUemUserId());
            Long sysRoleId = Long.parseLong(uemUserRoleDto.getSysRoleId());
            if (uemUserId != uniqueUemUserId) {
                return CommonResult.getFaildResultData("只能同时修改一个用户的绑定关系");
            }
            UemUserRole uemUserRole = new UemUserRole();
            uemUserRole.setUemUserId(uniqueUemUserId);
            uemUserRole.setSysRoleId(sysRoleId);
            uemUserRole.setIsValid(true);
            uemUserRole.setSysApplicationId(1L);
            uemUserRole.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            uemUserRoles.add(uemUserRole);
        }
        this.unbindAllRoleOfUser(uniqueUemUserId);
        int rowCount = QUemUserRole.uemUserRole.save(uemUserRoles);
        if (rowCount == uemUserRoleDtoList.size()) {
            return CommonResult.getSuccessResultData("绑定成功");
        } else {
            return CommonResult.getFaildResultData("绑定失败");
        }
    }

    /**
     * 清除一个用户的所有角色
     *
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> unbindAllRoleOfUser(Long uemUserId) {
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户ID不能为空");
        }
        QUemUserRole.uemUserRole
                .delete()
                .where(QUemUserRole.uemUserId.eq$(uemUserId))
                .execute();
        return CommonResult.getSuccessResultData("删除成功");
    }

    /**
     * 分页带条件查询员工信息
     *
     * @param uemUserDto
     * @author wzr
     * @date 2022-08-01
     */
    @Override
    public Page<UemUserDto> queryStaffByPage(UemUserDto uemUserDto) {
        Integer currentPage = uemUserDto.getPageNo();
        Integer pageSize = uemUserDto.getPageSize();
        return QUemUser.uemUser.select(
                        QUemUser.uemUserId,
                        QUemUser.name,
                        QUemUser.sex,
                        QUemUser.mobile,
                        QUemUser.deptName,
                        QUemUser.uemDeptId,
                        QUemUser.staffDutyCode,
                        QUemUser.staffDuty,
                        QUemUser.technicalTitleId,
                        QUemUser.technicalName,
                        QUemUser.jobStatus).
                where(QUemUser.name._like$_(uemUserDto.getName())
                        .and(QUemUser.uemDeptId.eq$(uemUserDto.getUemDeptId()))
                        .and(QUemUser.technicalTitleId.eq$(uemUserDto.getTechnicalTitleId()))
                        .and(QUemUser.staffDutyCode.eq$(uemUserDto.getStaffDutyCode()))
                        .and(QUemUser.jobStatus.eq$(uemUserDto.getJobStatus())))
                .paging((currentPage == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : currentPage, (pageSize == null)
                        ? CodeFinal.PAGE_SIZE_DEFAULT : pageSize).mapperTo(UemUserDto.class)
                .execute();
    }

    /**
     * 下拉框获取所有部门信息
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public List<UemUserDto> queryDepartmentBySelect() {
        return QUemUser.uemUser.select(
                        QUemUser.uemDeptId,
                        QUemUser.deptName
                ).where(QUemUser.isDeleted.eq$(false)
                        .and(QUemUser.uemDeptId.notNull()))
                .mapperTo(UemUserDto.class).execute();
    }

    /**
     * 下拉框获取所有岗位信息
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public List<UemUserDto> queryStaffDutyBySelect() {
        return QUemUser.uemUser.select(
                        QUemUser.staffDutyCode,
                        QUemUser.staffDuty
                ).where(QUemUser.isDeleted.eq$(false)
                        .and(QUemUser.staffDutyCode.notNull()))
                .mapperTo(UemUserDto.class).execute();
    }

    /**
     * 下拉框获取所有职称信息
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public List<UemUserDto> queryTechnicalNameBySelect() {
        return QUemUser.uemUser.select(
                        QUemUser.technicalTitleId,
                        QUemUser.technicalName
                ).where(QUemUser.isDeleted.eq$(false)
                        .and(QUemUser.technicalTitleId.notNull()))
                .mapperTo(UemUserDto.class).execute();
    }

    /**
     * 下拉框获取所有项目信息
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public List<UemUserDto> queryProjectNameBySelect() {
        return QUemUser.uemUser.select(
                        QUemUser.projectId,
                        QUemUser.projectName
                ).where(QUemUser.isDeleted.eq$(false)
                        .and(QUemUser.projectId.notNull()))
                .mapperTo(UemUserDto.class).execute();
    }

    /**
     * 根据id查询员工信息
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public UemUserDto queryStaffById(Long uemUserId) {
        return QUemUser.uemUser.selectOne().mapperTo(UemUserDto.class).byId(uemUserId);
    }

    /**
     * 编辑员工信息
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public ResultHelper<Object> updateStaff(UemUserDto uemUserDto) {
        Long uemUserId = uemUserDto.getUemUserId();
        // String account = uemUserDto.getAccount();
        String name = uemUserDto.getName();
        Boolean sex = uemUserDto.getSex();
        String date = uemUserDto.getBirthday();
        Long jobStatus = uemUserDto.getJobStatus();
        String idCard = uemUserDto.getIdCard();
        String mobile = uemUserDto.getMobile();
        String address = uemUserDto.getAddress();
        String sourceAddress = uemUserDto.getSourceAddress();
        Long maritalStatus = uemUserDto.getMaritalStatus();
        //政治面貌暂时不写---来源数据字典
        Long education = uemUserDto.getEducation();
        Date graduateDate = uemUserDto.getGraduateDate();
        String graduateSchool = uemUserDto.getGraduateSchool();
        String speciality = uemUserDto.getSpeciality();
        Date entryDate = uemUserDto.getEntryDate();
        Long uemDeptId = uemUserDto.getUemDeptId();
        String staffDutyCode = uemUserDto.getStaffDutyCode();
        Long technicalTitleId = uemUserDto.getTechnicalTitleId();
        String email = uemUserDto.getEmail();
        BigDecimal seniority = uemUserDto.getSeniority();
        Long projectId = uemUserDto.getProjectId();
        //根据id查询出对应的员工信息，避免空字段
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setUemUserId(uemUserId);
        //  uemUser.setAccount(account);
        uemUser.setSex(sex);
        uemUser.setBirthday(date);
        uemUser.setJobStatus(jobStatus);
        uemUser.setIdCard(idCard);
        uemUser.setName(name);
        uemUser.setMobile(mobile);
        uemUser.setAddress(address);
        uemUser.setSourceAddress(sourceAddress);
        uemUser.setMaritalStatus(maritalStatus);
        uemUser.setEducation(education);
        uemUser.setGraduateDate(graduateDate);
        uemUser.setGraduateSchool(graduateSchool);
        uemUser.setSpeciality(speciality);
        uemUser.setEntryDate(entryDate);
        uemUser.setUemDeptId(uemDeptId);
        uemUser.setStaffDutyCode(staffDutyCode);
        uemUser.setTechnicalTitleId(technicalTitleId);
        uemUser.setEmail(email);
        uemUser.setSeniority(seniority);
        uemUser.setProjectId(projectId);
        QUemUser.uemUser.save(uemUser);
        return CommonResult.getSuccessResultData("修改成功!");
    }

    /**
     * 根据id删除员工信息
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public ResultHelper<Object> deleteStaff(Long uemUserId) {
        int result = QUemUser.uemUser.deleteById(uemUserId);
        if (result == 1) {
            return CommonResult.getSuccessResultData("删除成功!");
        } else {
            return CommonResult.getFaildResultData("删除失败");
        }
    }

    /**
     * 员工简历编辑
     *
     * @author wzr
     * @date 2022-08-03
     */
    @Override
    public ResultHelper<?> uploadStaffFile(MultipartFile mFile) {
        if (mFile.getSize() < 1) {
            return CommonResult.getFaildResultData("文件为空");
        }
        //获取文件名
        String orgFileName = mFile.getOriginalFilename();
//        String dateTimeStr = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
        StringBuilder path = new StringBuilder("D:\\\\uploadFile\\");
        path.append(orgFileName);
        String filePath = path.toString();

        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            mFile.transferTo(file);
            //把上传文件路径存进数据库

        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.getFaildResultData("上传失败");
        }
        return CommonResult.getSuccessResultData("上传成功");
    }


    /**
     * 转正，离职，辞退---查看信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @Override
    public ResultHelper<UemUserDto> queryStaffInfo(Long uemUserId) {
        UemUserDto execute = QUemUser.uemUser.selectOne(
                        QUemUser.uemUserId,
                        QUemUser.uemDeptId,
                        QUemUser.staffDutyCode,
                        QUemUser.name,
                        QUemUser.sex,
                        QUemUser.entryDate,
                        QUemUser.jobStatus,
                        QUemUser.deptName,
                        QUemUser.staffDuty,
                        QUemUser.offerDate,
                        QUemUser.positiveType
                )
                .where(QUemUser.uemUserId.eq$(uemUserId))
                .mapperTo(UemUserDto.class)
                .execute();
        if (execute == null) {
            return CommonResult.getFaildResultData("对象信息为空!查询失败");
        } else {
            return CommonResult.getSuccessResultData(execute);
        }
    }

    /**
     * 添加转正信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @Override
    public ResultHelper<Object> savePositiveInfo(UemUserDto uemUserDto) {
        //主键数组--添加基本信息，添加面试人评语，添加审批人评语，都需要主键
        List<String> uemUserIds = uemUserDto.getUemUserIds();
        int i = 0;
        for (String uemUserId : uemUserIds) {
            String infoId = uemUserIds.get(i);
            if (i == 0) {
                //第一个数组值为主键，执行添加基本信息操作
                Date offerDate = uemUserDto.getOfferDate();
                Long positiveType = uemUserDto.getPositiveType();
                Long defenseScore = uemUserDto.getDefenseScore();
                UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(Long.valueOf(infoId));
                uemUser.setOfferDate(offerDate);
                uemUser.setPositiveType(positiveType);
                uemUser.setDefenseScore(defenseScore);
                uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                int result = QUemUser.uemUser.save(uemUser);
                if (result == 1) {
                    i = ++i;
                    continue;
                } else {
                    return CommonResult.getSuccessResultData("出错啦!");
                }
            }
            //第二个数组值为面谈人id,执行添加面谈人评语操作
            else {
                String interviewId = uemUserIds.get(i);
                if (i == 1) {
                    String interviewComments = uemUserDto.getInterviewComments();
                    UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(Long.valueOf(interviewId));
                    uemUser.setInterviewComments(interviewComments);
                    uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                    int result = QUemUser.uemUser.save(uemUser);
                    if (result == 1) {
                        i = ++i;
                        continue;
                    } else {
                        return CommonResult.getSuccessResultData("出错啦!");
                    }
                }
                //第三个数组值为审批人id，执行添加转正评语操作
                else {
                    String positiveId = uemUserIds.get(i);
                    if (i == 2) {
                        String positiveComments = uemUserDto.getPositiveComments();
                        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(Long.valueOf(positiveId));
                        uemUser.setPositiveComments(positiveComments);
                        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                        int result = QUemUser.uemUser.save(uemUser);
                        if (result == 1) {
                            continue;
                        } else {
                            return CommonResult.getSuccessResultData("出错啦!");
                        }
                    }
                }
            }
        }
        return CommonResult.getSuccessResultData("新增成功!");
    }
     /*   Long uemUserId = uemUserDto.getUemUserId();
        Date offerDate = uemUserDto.getOfferDate();
        Long positiveType = uemUserDto.getPositiveType();
        Long defenseScore = uemUserDto.getDefenseScore();
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        uemUser.setOfferDate(offerDate);
        uemUser.setPositiveType(positiveType);
        uemUser.setDefenseScore(defenseScore);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QUemUser.uemUser.save(uemUser);
        if (result == 1) {
            return CommonResult.getSuccessResultData("新增成功!");
        } else {
            return CommonResult.getFaildResultData("新增失败！");
        }*/

    /**
     * 添加离职信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @Override
    public ResultHelper<Object> saveResignInfo(UemUserDto uemUserDto) {
        Long uemUserId = uemUserDto.getUemUserId();
        Date leaveDate = uemUserDto.getLeaveDate();
        String leaveReason = uemUserDto.getLeaveReason();
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        uemUser.setLeaveDate(leaveDate);
        uemUser.setLeaveReason(leaveReason);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QUemUser.uemUser.save(uemUser);
        if (result == 1) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }

    /**
     * 添加辞退信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @Override
    public ResultHelper<Object> saveDismissInfo(UemUserDto uemUserDto) {
        Long uemUserId = uemUserDto.getUemUserId();
        Date dismissDate = uemUserDto.getDismissDate();
        String dismissReason = uemUserDto.getDismissReason();
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        uemUser.setLeaveDate(dismissDate);
        uemUser.setLeaveReason(dismissReason);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QUemUser.uemUser.save(uemUser);
        if (result == 1) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }

}
