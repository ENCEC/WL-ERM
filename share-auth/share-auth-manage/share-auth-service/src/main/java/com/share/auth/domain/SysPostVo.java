package com.share.auth.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SysPostVo
 * @Author weiqi
 * @Date 2022/9/5 15:27
 * @Version 1.0
 **/
@Data
public class SysPostVo implements Serializable {
    /*岗位名称*/
    private String PostName;
    /*岗位的人数*/
    private Integer number;

}
