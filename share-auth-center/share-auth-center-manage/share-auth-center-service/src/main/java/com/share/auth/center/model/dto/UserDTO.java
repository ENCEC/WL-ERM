package com.share.auth.center.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @Author:chenxf
 * @Description: 用户返回封装类
 * @Date: 17:47 2020/11/28
 * @Param: 
 * @Return:
 *
 */
@Data
@NoArgsConstructor
public class UserDTO {

    /** 用户id*/
    private Long id;
    /** 登录名*/
    private String username;
    /** 密码*/
    private String password;
    /** 状态*/
    private Boolean status;
    /** clientId*/
    private String clientId;
    /** 角色集合*/
    private List<GrantedAuthority> roles;

}
