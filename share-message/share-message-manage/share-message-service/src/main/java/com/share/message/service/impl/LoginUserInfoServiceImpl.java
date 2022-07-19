package com.share.message.service.impl;

import com.gillion.ds.utils.ResultUtils;
import com.share.message.service.LoginUserInfoService;
import com.share.message.user.DefaultUserService;
import com.share.message.user.MsgUserInfoModel;
import com.share.message.vo.CurrentLoginUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;

/**
 * @author tujx
 * @description 当前登录信息
 * @date 2020/11/26
 */
@Service
public class LoginUserInfoServiceImpl implements LoginUserInfoService {

    @Autowired
    private DefaultUserService defaultUserService;


    /**
     * 获取当前登录用户信息
     *
     * @return Map
     * @throws
     * @author tujx
     */
    @Override
    public Map<String, Object> getCurrentLoginUser() {
        MsgUserInfoModel msgUser = (MsgUserInfoModel) defaultUserService.getCurrentLoginUser();
        if (Objects.isNull(msgUser)) {
            return ResultUtils.getFailedResultData("获取登录信息失败");
        }
        CurrentLoginUserVO currentLoginUser = new CurrentLoginUserVO();
        BeanUtils.copyProperties(msgUser, currentLoginUser);
        //企业名称
        CurrentLoginUserVO.LoginCompany loginCompany = currentLoginUser.new LoginCompany();
        loginCompany.setCompanyNameCn(msgUser.getCompanyName());
        currentLoginUser.setUemCompany(loginCompany);
        return ResultUtils.getSuccessResultData(currentLoginUser);
    }
}
