package com.share.auth.task;

import java.lang.annotation.*;

/**
 * @Author:chenxf
 * @Description: 自定义注解，添加redis锁
 * @Date: 14:43 2021/3/2
 * @Param: 
 * @Return:
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisTryLock {

    /**
     * @Author:chenxf
     * @Description: 锁的有效时间长，单位：秒
     * @Date: 14:25 2021/3/2
     * @Param: []
     * @Return:int
     *
     */
    int expireTime() default 10;

    /**
     * @Author:chenxf
     * @Description: 自定义锁的keyName（不用包含namespace，内部已实现）
     * @Date: 14:25 2021/3/2
     * @Param: []
     * @Return:java.lang.String
     *
     */
    String keyName() default "";
}
