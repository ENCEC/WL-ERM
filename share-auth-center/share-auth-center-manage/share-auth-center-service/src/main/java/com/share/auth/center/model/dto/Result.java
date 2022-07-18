package com.share.auth.center.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author:chenxf
 * @Description: 返回结果model
 * @Date: 11:46 2020/10/21
 * @Param: 
 * @Return:
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;

    public static <T> Result<T> succeed(String msg) {
        return of(null, 0, msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return of(model, 0, msg);
    }

    public static <T> Result<T> succeed(T model) {
        return of(model, 1, "");
    }

    public static <T> Result<T> of(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return of(null, 1, msg);
    }

    public static <T> Result<T> failed(T model, String msg) {
        return of(model, 1, msg);
    }
}
