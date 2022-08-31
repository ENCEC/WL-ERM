package com.share.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.domain.UemUserRoleDto;
import com.share.auth.model.entity.*;
import com.share.auth.model.querymodels.*;
import com.share.auth.model.vo.QueryWorkUserVo;
import com.share.auth.service.UemUserManageService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.DefaultUserService;
import com.share.file.api.ShareFileInterface;
import com.share.file.domain.FastDfsDownloadResult;
import com.share.file.domain.FastDfsUploadResult;
import com.share.file.domain.FileInfoVO;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ShareFileInterface shareFileInterface;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询用户信息
     *
     * @param uemUserDto 用户信息封装类
     * @return Page<UemUserDto>
     * @date 2022-07-25
     */
    @Override
    public ResultHelper<Page<UemUserDto>> queryUemUser(UemUserDto uemUserDto) {
        String account = uemUserDto.getAccount();
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
     * 根据用户ID列表查询用户信息
     *
     * @param uemUserDto 用户信息封装类
     * @return ResultHelper<List < UemUserDto>>
     * @date 2022-08-31
     */
    @Override
    public ResultHelper<List<UemUserDto>> queryUemUserListById(UemUserDto uemUserDto) {
        List<Long> uemUserIdList = uemUserDto.getUemUserIdList();
        if (uemUserIdList.isEmpty()) {
            return CommonResult.getSuccessResultData(new ArrayList<>());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        for (Long uemUserId : uemUserIdList) {
            stringBuilder.append(uemUserId);
            stringBuilder.append(',');
        }
        stringBuilder.setCharAt(stringBuilder.length() - 1, ')');
        List<UemUserDto> uemUserDtoList = jdbcTemplate.query(
                "SELECT * FROM uem_user WHERE `uem_user_id` IN ? AND `is_deleted=0`",
                BeanPropertyRowMapper.newInstance(UemUserDto.class), stringBuilder.toString());
//        List<UemUserDto> uemUserDtoList = QUemUser.uemUser
//                .select(QUemUser.uemUser.fieldContainer())
//                .where(QUemUser.uemUserId.in$(uemUserIdList).and(QUemUser.isDeleted.eq$(false)))
//                .sorting(QUemUser.createTime.desc())
//                .mapperTo(UemUserDto.class)
//                .execute(uemUserDto);
        return CommonResult.getSuccessResultData(uemUserDtoList);
    }

    /**
     * 根据用户名、姓名查询所有在职用户列表
     *
     * @param uemUserDto 查询入参
     * @return com.share.support.result.ResultHelper<java.util.List < com.share.auth.model.vo.QueryWorkUserVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-29
     */
    @Override
    public ResultHelper<Page<QueryWorkUserVo>> queryAllWorkUserList(UemUserDto uemUserDto) {
        String name = uemUserDto.getName();
        name = StrUtil.isEmpty(name) ? "%" : "%" + name + "%";
        int pageNo = (uemUserDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserDto.getPageNo();
        int pageSize = (uemUserDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserDto.getPageSize();
        int offset = (pageNo - 1) * pageSize;
        Page<QueryWorkUserVo> page = new Page<>();
        int count = jdbcTemplate.queryForList(
                "SELECT COUNT(`uem_user_id`) FROM uem_user " +
                        "WHERE `name` LIKE ? AND `is_deleted`=0 AND `job_status`<>2;", Integer.class, name)
                .get(0);
        List<QueryWorkUserVo> uemUserDtoList = jdbcTemplate.query(
                "SELECT `uem_user_id`, `account`, `name`, `email`, `mobile` FROM uem_user " +
                        "WHERE `name` LIKE ? AND `is_deleted`=0 AND `job_status`<>2 LIMIT ?,?;",
                BeanPropertyRowMapper.newInstance(QueryWorkUserVo.class), name, offset, pageSize);
        page.setRecords(uemUserDtoList);
        page.setTotalRecord(count);
        page.setPageSize(pageSize);
        page.setCurrentPage(pageNo);
        return CommonResult.getSuccessResultData(page);
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
                .paging(1, 1)
                .execute()
                .getRecords();
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
        if (!uemUser.getAccount().equals(uemUserEditDto.getAccount())) {
            List<UemUser> uemUserList = QUemUser.uemUser
                    .select(QUemUser.uemUser.fieldContainer())
                    .where(QUemUser.account.eq$(uemUserEditDto.getAccount()))
                    .execute();
            if (CollectionUtils.isNotEmpty(uemUserList)) {
                return CommonResult.getFaildResultData("该用户名已经被占用！");
            }
        }
        // 检查手机号是否被占用
        if (!uemUser.getMobile().equals(uemUserEditDto.getMobile())) {
            List<UemUser> uemUserList = QUemUser.uemUser
                    .select(QUemUser.uemUser.fieldContainer())
                    .where(QUemUser.mobile.eq$(uemUserEditDto.getMobile()))
                    .execute();
            if (CollectionUtils.isNotEmpty(uemUserList)) {
                return CommonResult.getFaildResultData("该手机号已经被占用！");
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
            return CommonResult.getFaildResultData("该用户名已注册过！");
        }
        // 手机号是否可用
        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.mobile.eq$(uemUserEditDTO.getMobile()))
                .execute();
        if (CollectionUtils.isNotEmpty(uemUserList)) {
            return CommonResult.getFaildResultData("该手机号已注册过！");
        }
        // 设置字段
        UemUser uemUser = new UemUser();
        BeanUtils.copyProperties(uemUserEditDTO, uemUser);
        uemUser.setIsValid(true);
        uemUser.setIsLocked(false);
        uemUser.setJobStatus(0L);
        uemUser.setIsDeleted(false);
        // 设置密码
        String passwordText = RandomUtil.randomString(6);
        String password = MD5EnCodeUtils.encryptionPassword(passwordText);
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
        String passwordText = RandomUtil.randomString(6);
        String password = MD5EnCodeUtils.encryptionPassword(passwordText);
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
        List<Long> roleIdList = new ArrayList<>();
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
            roleIdList.add(sysRoleId);

        }
        List<SysRole> sysRoleList = QSysRole.sysRole
                .select(QSysRole.sysRole.fieldContainer())
                .where(QSysRole.sysRoleId.in$(roleIdList)
                        .and(QSysRole.isDeleted.eq$(false))
                        .and(QSysRole.isValid.eq$(true)))
                .execute();
        for (SysRole sysRole : sysRoleList) {
            UemUserRole uemUserRole = new UemUserRole();
            uemUserRole.setUemUserId(uniqueUemUserId);
            uemUserRole.setSysRoleId(sysRole.getSysRoleId());
            uemUserRole.setIsValid(true);
            uemUserRole.setSysApplicationId(sysRole.getSysApplicationId());
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
        ResultHelper<UemUserDto> loginUser = uemUserService.getLoginUserInfo();
        if (!loginUser.getSuccess() ||
                loginUser.getData() == null ||
                loginUser.getData().getRoleList() == null ||
                loginUser.getData().getRoleList().isEmpty()) {
            return null;
        }
        return QUemUser.uemUser.select(
                        QUemUser.uemUserId,
                        QUemUser.name,
                        QUemUser.sex,
                        QUemUser.mobile,
                        QUemUser.deptName,
                        QUemUser.uemDeptId,
                        QUemUser.staffDutyId,
                        QUemUser.staffDuty,
                        QUemUser.technicalTitleId,
                        QUemUser.technicalName,
                        QUemUser.jobStatus).
                where(QUemUser.name._like$_(uemUserDto.getName())
                        .and(QUemUser.uemDeptId.eq$(uemUserDto.getUemDeptId()))
                        .and(QUemUser.technicalTitleId.eq$(uemUserDto.getTechnicalTitleId()))
                        .and(QUemUser.staffDutyId.eq$(uemUserDto.getStaffDutyId()))
                        .and(QUemUser.jobStatus.eq$(uemUserDto.getJobStatus()))
                        .and(QUemUser.isDeleted.eq$(false)))
                .paging((currentPage == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : currentPage, (pageSize == null)
                        ? CodeFinal.PAGE_SIZE_DEFAULT : pageSize).sorting(QUemUser.createTime.desc()).mapperTo(UemUserDto.class)
                .execute();
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
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户id不能为空");
        }
        String name = uemUserDto.getName();
        Boolean sex = uemUserDto.getSex();
        String birthday = uemUserDto.getBirthday();
        Long jobStatus = uemUserDto.getJobStatus();
        String idCard = uemUserDto.getIdCard();
        String mobile = uemUserDto.getMobile();
        String address = uemUserDto.getAddress();
        String sourceAddress = uemUserDto.getSourceAddress();
        Long maritalStatus = uemUserDto.getMaritalStatus();
        //政治面貌---来源数据字典
        String politicalStatus = uemUserDto.getPoliticalStatus();
        Long education = uemUserDto.getEducation();
        Date graduateDate = uemUserDto.getGraduateDate();
        String graduateSchool = uemUserDto.getGraduateSchool();
        String speciality = uemUserDto.getSpeciality();
        Date entryDate = uemUserDto.getEntryDate();
        Long uemDeptId = uemUserDto.getUemDeptId();
        Long technicalTitleId = uemUserDto.getTechnicalTitleId();
        String email = uemUserDto.getEmail();
        BigDecimal seniority = uemUserDto.getSeniority();
        Long projectId = uemUserDto.getProjectId();
        Long staffDutyId = uemUserDto.getStaffDutyId();
        //根据id查询出对应的员工信息，避免空字段
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        if (uemUser == null) {
            return CommonResult.getFaildResultData("查询结果为空!");
        }
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setUemUserId(uemUserId);
        uemUser.setPoliticalStatus(politicalStatus);
        uemUser.setSex(sex);
        uemUser.setBirthday(birthday);
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
        uemUser.setEmail(email);
        uemUser.setSeniority(seniority);
        SysTechnicalTitle sysTechnicalTitle = QSysTechnicalTitle.sysTechnicalTitle.selectOne(QSysTechnicalTitle.technicalName).byId(technicalTitleId);
        uemUser.setTechnicalName(sysTechnicalTitle.getTechnicalName());
        uemUser.setTechnicalTitleId(technicalTitleId);
        SysPost sysPost = QSysPost.sysPost.selectOne(QSysPost.postName).where(QSysPost.postId.eq$(staffDutyId)).execute();
        uemUser.setStaffDuty(sysPost.getPostName());
        uemUser.setStaffDutyId(staffDutyId);
        UemProject uemProject = QUemProject.uemProject.selectOne(QUemProject.projectName).byId(projectId);
        uemUser.setProjectName(uemProject.getProjectName());
        uemUser.setProjectId(projectId);
        UemDept uemDept = QUemDept.uemDept.selectOne(QUemDept.deptName, QUemDept.uemDeptId).byId(uemDeptId);
        uemUser.setDeptName(uemDept.getDeptName());
        uemUser.setUemDeptId(uemDept.getUemDeptId());
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
     * 转正，离职，辞退---查看信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @Override
    public UemUserDto queryStaffInfo(Long uemUserId) {
        UemUserDto execute = QUemUser.uemUser.selectOne()
                .where(QUemUser.uemUserId.eq$(uemUserId))
                .mapperTo(UemUserDto.class)
                .execute();
        if (execute != null) {
            return execute;
        } else {
            return null;
        }
    }


    /**
     * 添加离职信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @Override
    public ResultHelper<Object> saveResignInfo(UemUserDto uemUserDto) {
        Long uemUserId = uemUserDto.getUemUserId();
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户id不能为空!");
        }
        Date leaveDate = uemUserDto.getLeaveDate();
        String leaveReason = uemUserDto.getLeaveReason();
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        uemUser.setLeaveDate(leaveDate);
        uemUser.setLeaveReason(leaveReason);
        //添加员工离职状态，改为离职员工
        uemUser.setJobStatus(2L);
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
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户id不能为空!");
        }
        Date dismissDate = uemUserDto.getDismissDate();
        String dismissReason = uemUserDto.getDismissReason();
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        uemUser.setDismissDate(dismissDate);
        uemUser.setDismissReason(dismissReason);
        //添加辞退申请，员工状态改为离职员工
        uemUser.setJobStatus(2L);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QUemUser.uemUser.save(uemUser);
        if (result == 1) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }


    /**
     * 查看转正评语部分信息
     *
     * @param uemUserId
     * @return
     */
    @Override
    public UemUserDto queryOfferInfo(Long uemUserId) {
        UemUserDto execute = QUemUser.uemUser.selectOne(
                        QUemUser.uemUserId,
                        QUemUser.name,
                        QUemUser.sex,
                        QUemUser.entryDate,
                        QUemUser.jobStatus,
                        QUemUser.uemDeptId,
                        QUemUser.staffDutyId,
                        QUemUser.staffApplication
                )
                .where(QUemUser.uemUserId.eq$(uemUserId))
                .mapperTo(UemUserDto.class)
                .execute();

        return execute;
    }

    /**
     * 查看离职原因
     *
     * @param uemUserId
     * @return
     */
    @Override
    public ResultHelper<UemUserDto> queryLeaveInfo(Long uemUserId) {
        UemUserDto execute = QUemUser.uemUser.selectOne(
                        QUemUser.uemUserId,
                        QUemUser.name,
                        QUemUser.sex,
                        QUemUser.entryDate,
                        QUemUser.jobStatus,
                        QUemUser.uemDeptId,
                        QUemUser.staffDutyId,
                        QUemUser.leaveDate,
                        QUemUser.leaveReason,
                        QUemUser.creatorId,
                        QUemUser.creatorName
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
     * 查看辞退原因
     *
     * @param uemUserId
     * @return
     */
    @Override
    public ResultHelper<UemUserDto> queryDismissInfo(Long uemUserId) {
        UemUserDto execute = QUemUser.uemUser.selectOne(
                        QUemUser.uemUserId,
                        QUemUser.name,
                        QUemUser.sex,
                        QUemUser.entryDate,
                        QUemUser.jobStatus,
                        QUemUser.uemDeptId,
                        QUemUser.staffDutyId,
                        QUemUser.dismissDate,
                        QUemUser.dismissReason,
                        QUemUser.dismissApplication
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
     * 保存员工信息
     *
     * @param uemUserDto
     * @return
     */
    @Override
    public ResultHelper<?> preservationUemUser(UemUserDto uemUserDto) {
        if (StrUtil.isEmpty(uemUserDto.getIdCard())
                || StrUtil.isEmpty(uemUserDto.getEmail())
                || Objects.isNull(uemUserDto.getEducation())
                || StrUtil.isEmpty(uemUserDto.getGraduateSchool())
                || StrUtil.isEmpty(uemUserDto.getSpeciality())) {
            return CommonResult.getFaildResultData("必填项不能为空");
        }
        Long uemUserId = uemUserDto.getUemUserId();
        String account = uemUserDto.getAccount();
        String name = uemUserDto.getName();
        Boolean sex = uemUserDto.getSex();
        String birthday = uemUserDto.getBirthday();
        Long jobStatus = uemUserDto.getJobStatus();
        String idCard = uemUserDto.getIdCard();
        String mobile = uemUserDto.getMobile();
        String address = uemUserDto.getAddress();
        String sourceAddress = uemUserDto.getSourceAddress();
        Long maritalStatus = uemUserDto.getMaritalStatus();
        String politicalStatus = uemUserDto.getPoliticalStatus();
        String email = uemUserDto.getEmail();
        BigDecimal seniority = uemUserDto.getSeniority();
        Long education = uemUserDto.getEducation();
        Date graduateDate = uemUserDto.getGraduateDate();
        String graduateSchool = uemUserDto.getGraduateSchool();
        String speciality = uemUserDto.getSpeciality();
        Date entryDate = uemUserDto.getEntryDate();
        Long technicalTitleId = uemUserDto.getTechnicalTitleId();
        Long staffDutyId = uemUserDto.getStaffDutyId();
        Long projectId = uemUserDto.getProjectId();
        //根据id查询出对应的员工信息，避免空字段
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).byId(uemUserId);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setUemUserId(uemUserId);
        uemUser.setAccount(account);
        uemUser.setSex(sex);
        uemUser.setBirthday(birthday);
        uemUser.setJobStatus(jobStatus);
        uemUser.setIdCard(idCard);
        uemUser.setName(name);
        uemUser.setMobile(mobile);
        uemUser.setAddress(address);
        uemUser.setSourceAddress(sourceAddress);
        uemUser.setMaritalStatus(maritalStatus);
        uemUser.setPoliticalStatus(politicalStatus);
        uemUser.setEmail(email);
        uemUser.setSeniority(seniority);
        uemUser.setEducation(education);
        uemUser.setGraduateDate(graduateDate);
        uemUser.setGraduateSchool(graduateSchool);
        uemUser.setSpeciality(speciality);
        uemUser.setEntryDate(entryDate);
        SysTechnicalTitle sysTechnicalTitle = QSysTechnicalTitle.sysTechnicalTitle.selectOne(QSysTechnicalTitle.technicalName).byId(technicalTitleId);
        uemUser.setTechnicalName(sysTechnicalTitle.getTechnicalName());
        uemUser.setTechnicalTitleId(technicalTitleId);
        SysPost sysPost = QSysPost.sysPost.selectOne(QSysPost.postName).where(QSysPost.postId.eq$(staffDutyId)).execute();
        uemUser.setStaffDuty(sysPost.getPostName());
        uemUser.setStaffDutyId(staffDutyId);
        UemProject uemProject = QUemProject.uemProject.selectOne(QUemProject.projectName).byId(projectId);
        uemUser.setProjectName(uemProject.getProjectName());
        uemUser.setProjectId(projectId);
        int save = QUemUser.uemUser.save(uemUser);
        if (save > 0) {
            return CommonResult.getSuccessResultData("保存成功!");
        } else {
            return CommonResult.getFaildResultData("保存失败");
        }
    }

    /**
     * 离职申请添加离职理由
     *
     * @param
     * @return
     */
    @Override
    public ResultHelper<?> updateLeaveReason(Long uemUserId, String leaveReason) {
        UemUser uemUser = QUemUser.uemUser.selectOne().where(QUemUser.uemUserId.eq$(uemUserId)).execute();
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setLeaveReason(leaveReason);
        int count = QUemUser.uemUser.save(uemUser);
        if (count == 1) {
            return CommonResult.getSuccessResultData("更新成功");
        } else {
            return CommonResult.getFaildResultData("更新失败");
        }
    }

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
    @Override
    public ResultHelper<?> uploadExternalFile(Long uemUserId, String systemId, String fileType, String fileName, String type, MultipartFile file) {
        FastDfsUploadResult fastDfsUploadResult = shareFileInterface.uploadExternalFile(systemId, fileType, fileName, file);
        String fileKey = fastDfsUploadResult.getFileKey();
        //返回带后缀的文件名称
        String originFile = fileName + "." + fileType;
        //获取fileKey 映射fileName
        HashMap<String, String> map = new HashMap<>(2);
        map.put(fileKey, originFile);
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        if ("个人简历".equals(type)) {
            uemUser.setResume(fileKey);
        }
        if ("转正申请表".equals(type)) {
            uemUser.setStaffApplication(fileKey);
        }
        if ("辞退申请表".equals(type)) {
            uemUser.setDismissApplication(fileKey);
        }
        int count = QUemUser.uemUser.save(uemUser);
        if (count == 1) {
            return CommonResult.getSuccessResultData(map);
            //return CommonResult.getSuccessResultData(fileKey);
        } else {
            return CommonResult.getFaildResultData("上传失败");
        }
    }

    /**
     * 下载文件
     *
     * @param fileInfoVO
     * @return
     */
    @Override
    public ResultHelper<?> downloadExternalFile(FileInfoVO fileInfoVO) {
        FastDfsDownloadResult fastDfsDownloadResult = shareFileInterface.downloadExternalFile(fileInfoVO);
        String file = fastDfsDownloadResult.getFile();
        if (StringUtils.isEmpty(file)) {
            return CommonResult.getFaildResultData("file为空，下载失败！");
        } else {
            return CommonResult.getSuccessResultData(file);
        }
    }

    /**
     * 批量下载文件
     *
     * @param fileInfoVO
     * @return
     */
    @Override
    public ResultHelper<?> batchDownloadFile(FileInfoVO fileInfoVO) {
        String[] fileKeys = fileInfoVO.getFileKeys();
        List<String> files = new ArrayList<>();
        for (String f : fileKeys) {
            fileInfoVO.setFileKey(f);
            FastDfsDownloadResult fastDfsDownloadResult = shareFileInterface.downloadExternalFile(fileInfoVO);
            String file = fastDfsDownloadResult.getFile();
            files.add(file);
        }
        int size = files.size();
        if (size == 0) {
            return CommonResult.getFaildResultData("file为空，下载失败！");
        } else {
            return CommonResult.getSuccessResultData(files);
        }
    }

    /**
     * 下拉框查询所有岗位的信息
     *
     * @return
     */
    @Override
    public List<SysPost> querySysPost() {
        List<SysPost> sysPosts = QSysPost.sysPost.select().where(QSysPost.postId.goe$(1L)).execute();
        return sysPosts;
    }

    /**
     * 下拉框查询所有职称的信息
     *
     * @return
     */
    @Override
    public List<SysTechnicalTitle> querySysTechnicalTitle() {
        List<SysTechnicalTitle> sysTechnicalTitles = QSysTechnicalTitle
                .sysTechnicalTitle.select()
                .where(QSysTechnicalTitle.technicalTitleId.goe$(1L))
                .execute();
        return sysTechnicalTitles;
    }

    /**
     * 下拉框查询所有项目的信息
     *
     * @return
     */
    @Override
    public List<UemProject> queryUemProject() {
        List<UemProject> uemProjects = QUemProject.uemProject.select().where(QUemProject.uemProjectId.goe$(1L)).execute();
        return uemProjects;
    }

    /**
     * 下拉框查询所有部门的信息
     *
     * @return
     */
    @Override
    public List<UemDept> queryUemDept() {
        List<UemDept> uemDepts = QUemDept.uemDept.select().where(QUemDept.uemDeptId.goe$(1L)).execute();
        return uemDepts;
    }

    /**
     * 服务调用（任务模块通过查询用户id 取到name）
     *
     * @param
     * @return
     */
    @Override
    public UemUserDto queryUemUserById(Long uemUserId) {
        UemUserDto result = QUemUser.uemUser.selectOne(
                        QUemUser.uemUserId,
                        QUemUser.name
                )
                .where(QUemUser.uemUserId.eq$(uemUserId))
                .mapperTo(UemUserDto.class)
                .execute();
        return result;

    }

    @Override
    public ResultHelper<?> updateJobStatus(Long uemUserId) {
        UemUser uemUser = QUemUser.uemUser.
                selectOne()
                .byId(uemUserId);
        String account = uemUser.getAccount();
        //点击转正之后更新员工状态为转正员工
        uemUser.setJobStatus(1L);
        uemUser.setAccount(account);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QUemUser.uemUser.save(uemUser);
        if (result == 1) {
            return CommonResult.getSuccessResultData("员工状态修改成功");
        } else {
            return CommonResult.getFaildResultData("员工状态修改失败");
        }
    }

    /**
     * 设置数据库resume为空
     *
     * @param uemUserId
     * @return
     */
    @Override
    public ResultHelper<?> deleteResume(Long uemUserId, String type) {
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        if ("个人简历".equals(type)) {
            uemUser.setResume("");
        }
        if ("转正申请表".equals(type)) {
            uemUser.setStaffApplication("");
        }
        if ("辞退申请表".equals(type)) {
            uemUser.setDismissApplication("");
        }
        int count = QUemUser.uemUser.save(uemUser);
        if (count > 0) {
            return CommonResult.getSuccessResultData("编辑成功");
        } else {
            return CommonResult.getFaildResultData("编辑失败");
        }

    }

    @Override
    public ResultHelper<?> queryPostOfDept() {
        List result = DSContext.customization("WL-ERM_queryPostOfDept").select().execute();
        if (CollectionUtils.isNotEmpty(result)) {
            return CommonResult.getSuccessResultData(result);
        } else {
            return CommonResult.getFaildResultData("查询失败！");
        }
    }

    /**
     * 批量上传文件
     * @param uemUserId
     * @param file
     * @param fileType
     * @param systemId
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public ResultHelper<?> batchUploadFile(Long uemUserId,MultipartFile[] file, String[] fileType, String systemId) throws JsonProcessingException {
        Map<String, Object> map = shareFileInterface.batchUploadFile(file, fileType, systemId);
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(map);
        JsonNode jsonNode = mapper.readTree(result);
        String files="";
        List<String> fileKeys = new ArrayList<>();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            String fileKey = jsonNode.path("data").get(i).path("fileKey").asText();
            files=files+fileKey+",";
            fileKeys.add(fileKey);
        }
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setStaffApplication(files);
        int count = QUemUser.uemUser.save(uemUser);
        if (count > 0) {
            return CommonResult.getSuccessResultData(fileKeys);
        } else {
            return CommonResult.getFaildResultData("上传失败");
        }
    }


}
