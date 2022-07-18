package com.gillion.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
@Data
public class EmployeeVO implements Serializable {

    private Long employeeId;

    /**
     * 员工用户名
     */
    private String username;

    /**
     * 员工中文名称
     */
    private String cname;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 地址
     */
    private String address;

    /**
     * 从属部门编号
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;
}
