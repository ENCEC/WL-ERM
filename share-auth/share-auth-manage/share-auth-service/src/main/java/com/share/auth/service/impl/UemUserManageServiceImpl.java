package com.share.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.service.UemUserManageService;
import com.share.auth.service.UemUserService;
import com.share.auth.user.DefaultUserService;
import com.share.message.api.MsgApiService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private MsgApiService msgApiService;

    /**
     * 查询用户信息
     *
     * @param uemUserDto 用户信息封装类
     * @return Page<UemUserDto>
     * @date 2022-07-25
     */
    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<UemUserDto> queryUemUser(UemUserDto uemUserDto) {

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

        return QUemUser.uemUser.select(
                QUemUser.uemUserId, QUemUser.account, QUemUser.name,
                QUemUser.mobile, QUemUser.email, QUemUser.isValid
        ).where(
                QUemUser.account.like(":account")
                        .and(QUemUser.name.like(":name"))
                        .and(QUemUser.isValid.eq(":isValid"))
        ).paging(pageNo, pageSize)
        .sorting(QUemUser.createTime.desc())
        .mapperTo(UemUserDto.class)
        .execute(uemUserDto);
    }

    /**
     * 用户管理详情
     *
     * @param uemUserId 用户ID
     * @return List<UemUserDto>
     * @date 2022-07-25
     */
    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UemUserDto getUemUser(Long uemUserId) {

        UemUser uemUser = QUemUser.uemUser
                .selectOne()
                .byId(uemUserId);

        UemUserDto uemUserDto = new UemUserDto();
        BeanUtils.copyProperties(uemUser, uemUserDto);

        return uemUserDto;
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
        String uemUserId = uemUserDto.getUemUserId();
        //是否禁用(0禁用,1启用)
        Boolean isValid = uemUserDto.getIsValid();

        if (StringUtils.isEmpty(uemUserId)) {
            return CommonResult.getFaildResultData("用户ID不能为空");
        }
        UemUser uemUser = new UemUser();
        uemUser.setUemUserId(Long.valueOf(uemUserId));
        uemUser.setIsValid(isValid);
        uemUser.setInvalidTime(new Date());
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int updateCount = QUemUser.uemUser.selective(QUemUser.isValid, QUemUser.invalidTime).execute(uemUser);
        if (updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("启停成功");
        } else {
            return CommonResult.getFaildResultData("启停失败");
        }
    }

    /**
     * 修改用户信息
     *
     * @param uemUserDto 用户信息
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @date 2022-07-25
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> editUemUser(UemUserEditDTO uemUserDto) {
        // 根据主键查询用户信息
        Long uemUserId = uemUserDto.getUemUserId();
        if (Objects.isNull(uemUserId)) {
            return CommonResult.getFaildResultData("用户id不允许为空");
        }
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("查询不到对应的用户信息");
        }
        // 检查手机号是否被占用
        if (!uemUser.getMobile().equals(uemUserDto.getMobile())) {
            List<UemUser> uemUserList = QUemUser.uemUser
                    .select(QUemUser.uemUser.fieldContainer())
                    .where(QUemUser.mobile.eq$(uemUserDto.getMobile()))
                    .execute();
            if(CollectionUtils.isNotEmpty(uemUserList)) {
                return CommonResult.getSuccessResultData("该手机号已经被占用！");
            }
        }
        // 更新用户信息
        BeanUtils.copyProperties(uemUserDto, uemUser);
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int rowCount = QUemUser.uemUser.save(uemUser);
        // 检查是否更新成功
        if (rowCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
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
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        }
        // 逻辑删除
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemUser.setIsValid(false);
        uemUser.setInvalidTime(new Date());
        int rowCount = QUemUser.uemUser
                .selective(QUemUser.isValid, QUemUser.invalidTime)
                .update(uemUser);
        // 检查是否更新成功
        if (rowCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
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
        if(CollectionUtils.isNotEmpty(accountUser)) {
            return CommonResult.getSuccessResultData("该用户名已注册过！");
        }
        // 手机号是否可用
        List<UemUser> uemUserList = QUemUser.uemUser
                .select(QUemUser.uemUser.fieldContainer())
                .where(QUemUser.mobile.eq$(uemUserEditDTO.getMobile()))
                .execute();
        if(CollectionUtils.isNotEmpty(uemUserList)) {
            return CommonResult.getSuccessResultData("该手机号已注册过！");
        }
        // 设置字段
        UemUser uemUser = new UemUser();
        BeanUtils.copyProperties(uemUserEditDTO, uemUser);
        uemUser.setIsValid(true);
        uemUser.setIsLocked(false);
        uemUser.setJobStatus(0L);
        // 设置密码
        String passwordText = RandomUtil.randomString(12);
        String password = MD5EnCodeUtils.MD5EnCode(passwordText);
        password = MD5EnCodeUtils.encryptionPassword(password);
        uemUser.setPassword(password);
        // 新增用户
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        int rowCount = QUemUser.uemUser.save(uemUser);
        // 检查是否新增成功,并异步发送邮件通知
        if (rowCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            sendEmailToNewUser(uemUser, passwordText);
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
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemUserId);
        if (Objects.isNull(uemUser)) {
            return CommonResult.getFaildResultData("用户不存在");
        }
        // 生成新密码
        String passwordText = RandomUtil.randomString(12);
        String password = MD5EnCodeUtils.MD5EnCode(passwordText);
        password = MD5EnCodeUtils.encryptionPassword(password);
        uemUser.setPassword(password);
        // 新增用户
        uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        int rowCount = QUemUser.uemUser.save(uemUser);
        // 检查是否新增成功,并异步发送邮件通知
        if (rowCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            sendEmailToNewUser(uemUser, passwordText);
            return CommonResult.getSuccessResultData("重置密码成功");
        } else {
            return CommonResult.getFaildResultData("重置密码失败");
        }
    }

    /**
     * 新增用户时异步发送短信提醒给新用户
     *
     * @param uemUser 用户信息
     * @param passwordText 新密码
     * @date 2022-07-25
     */
    @Async
    public void sendEmailToNewUser(UemUser uemUser, String passwordText) {
        log.debug("send email, " + uemUser.getAccount() + ", " + passwordText);
    }
}
