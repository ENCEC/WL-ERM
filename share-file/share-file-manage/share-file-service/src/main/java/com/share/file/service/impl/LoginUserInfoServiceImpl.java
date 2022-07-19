package com.share.file.service.impl;

import com.gillion.ds.utils.ResultUtils;
import com.share.file.service.LoginUserInfoService;
import com.share.file.user.DefaultUserServiceImpl;
import com.share.file.user.SupplyUserInfoModel;
import com.share.file.vo.CurrentLoginUserVO;
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
    private DefaultUserServiceImpl defaultUserService;

    /**
     * 获取当前登录用户信息
     *
     * @return Map
     * @throws
     * @author tujx
     */
    @Override
    public Map<String, Object> getCurrentLoginUser() {
        SupplyUserInfoModel spUser = (SupplyUserInfoModel) defaultUserService.getCurrentLoginUser();
        if (Objects.isNull(spUser)) {
            return ResultUtils.getFailedResultData("获取登录信息失败");
        }
        CurrentLoginUserVO currentLoginUser = new CurrentLoginUserVO();
        BeanUtils.copyProperties(spUser, currentLoginUser);
        //企业名称
        CurrentLoginUserVO.LoginCompany loginCompany = currentLoginUser.new LoginCompany();
        loginCompany.setCompanyNameCn(spUser.getCompanyName());
        currentLoginUser.setUemCompany(loginCompany);
        return ResultUtils.getSuccessResultData(currentLoginUser);
    }
}
