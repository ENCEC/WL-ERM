package com.share.auth.center.model.dto;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wengms
 * Created by wengms.
 * Email: wengms@gillion.com.cn
 * Date: 2018/2/9
 * Time: 下午4:44
 * Description:
 */
public interface IUser extends UserDetails {
    /**
     * 获取用户id
     * @return Object
     */
    Object getUserId();

}
