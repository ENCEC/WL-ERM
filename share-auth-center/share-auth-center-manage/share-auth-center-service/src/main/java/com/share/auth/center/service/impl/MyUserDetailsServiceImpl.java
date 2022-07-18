package com.share.auth.center.service.impl;

import com.alibaba.fastjson.JSON;
import com.share.auth.center.model.dto.UserDTO;
import com.share.auth.center.service.UemUserService;
import com.share.support.model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Chenxf
 * @date 2019-02-11
 */
@Slf4j
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UemUserService uemUserService;

    /**
     * @Author:chenxf
     * @Description: 框架查询用户接口
     * @Date: 11:48 2020/10/21
     * @Param: [username]
     * @Return:org.springframework.security.core.userdetails.UserDetails
     *
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 查询用户是否存在
        UserDTO userDTO = uemUserService.getByUsername(username);
        if (null == userDTO) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }

        MyUser myUser = new MyUser(userDTO.getUsername(), new BCryptPasswordEncoder().encode(userDTO.getPassword()), userDTO.getRoles());
        myUser.setUserId(userDTO.getId());
        log.info("登录成功！用户: {}", JSON.toJSONString(myUser));
        return myUser;
    }

}
