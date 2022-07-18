package com.share.auth.center.service;

import com.share.auth.center.model.dto.UserDTO;
import com.share.auth.center.model.entity.UemLog;
import com.share.auth.center.model.entity.UemUser;
import com.share.support.model.User;
import com.share.support.result.ResultHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chenxf
 * @date 2020-10-10 10:15
 */
public interface UemUserService {
    /**
     * @Author hehao
     * @Description 新增上报仓单信息表
     * @Date  2021/10/23 13:48
     * @Param 【sdrWaybillInfoDto】
     * @return SdrWarehouseWarrantInfoDTO
     **/
    public int saveToEs(UemLog uemLog, UemUser uemUser);

    /**
     * 根据用户名查询用户
     * @Author:chenxf
     * @Description: 根据用户名查询用户
     * @Date: 9:48 2020/11/16
     * @param username 用户名
     * @return 用户
     *
     */
    UserDTO getByUsername(String username);

    /**
     * 根据用户id和应用id返回用户信息
     * @Author:chenxf
     * @Description: 根据用户id和应用id返回用户信息
     * @Date: 9:48 2020/11/16
     * @param uid 用户id
     * @param clientId 应用id
     * @return 用户信息
     *
     */
    User getUserInfo(Long uid, String clientId);

    /**
     * 校验用户是否可以登录
     * @Author:chenxf
     * @Description: 校验用户是否可以登录
     * @Date: 10:14 2020/11/16
     * @param account 用户名
     * @param password 密码（AES加密）
     * @param clientId 应用id
     * @param checkMoveId 滑动解锁图片
     * @param xWidth 滑动距离
     * @param request 请求
     * @param response 响应
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    ResultHelper<Object> validateUser(String account, String password, String clientId, String checkMoveId, Double xWidth, HttpServletRequest request, HttpServletResponse response);


    /**
     * 校验用户是否可以登录
     * @param account 用户名
     * @param password 密码（AES加密）
     * @param clientId 应用id
     * @param checkDigitalId 数字验证码redis的key
     * @param verifyCode 验证码
     * @param request 请求
     * @param response 响应
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     */
    ResultHelper<Object> validateUserByDigitalCode(String account, String password, String clientId, String checkDigitalId, String verifyCode, HttpServletRequest request, HttpServletResponse response);

    /**
     * 锁定账号
     * @param userName 用户名
     */
    void lockedUser(String userName);

    /**
     * 解锁用户
     * @param account 用户账号
     */
    void unlockedUser(String account);

    /**
     * 换取token
     * @param code code
     * @param env 认证中心外网地址
     * @param request request
     * @param response response
     * @return ResultHelper
     */
    ResultHelper<Object> getToken(String code, String env, String clientId, String clientSecret, HttpServletRequest request, HttpServletResponse response);

    /**
     * 校验滑动解锁是否成功接口
     * @Author:chenxf
     * @Description: 校验滑动解锁是否成功接口
     * @Date: 15:42 2021/1/14
     * @param checkMoveId : 滑动解锁图片
     * @param xWidth 滑动距离
     * @return :java.lang.Boolean
     *
     */
    Boolean validateUnlock(String checkMoveId, Double xWidth);

    /**
     * @Author:ecchen
     * @Description: 校验验证码校验是否成功接口
     * @Date: 15:02 2021/1/14
     * @Param: [checkDigitalId, verifyCode]
     * @Return:java.lang.Boolean
     */
    Boolean validateDigitalCode(String checkDigitalId, String verifyCode);

    /**
     * 认证
     * @param clientId clientId
     * @param redirectUri redirectUri
     * @param responseType responseType
     * @param state state
     * @param request request
     * @param response response
     * @return 重定向地址
     */
    String authentication(String clientId, String redirectUri, String responseType, String state, HttpServletRequest request, HttpServletResponse response);

    /**
     * 生成jwt的Cookie
     * @param userInfo 用户信息
     * @return jwt的Cookie列表JSON格式字符串
     */
    String createCredentialCookie(User userInfo);
}
