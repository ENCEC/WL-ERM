package com.share.auth.service;

import com.share.auth.domain.UemUserDto;
import com.share.auth.model.entity.UemCompany;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.vo.OperateResultVO;
import com.share.auth.model.vo.UemUserOperateVO;
import com.share.auth.model.vo.UserAndCompanyVo;
import com.share.message.domain.SendEmailVO;
import com.share.support.model.User;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * @author xrp
 * @date 20201021
 */
@Api("快速注册和找回密码")
public interface UemUserService {

    /**
     * 手机号生成验证码
     *
     * @param telephone 手机号
     * @param sign      标识为1的时候是快速注册的验证码，为2的时候是找回密码的验证码，为3的时候是修改绑定手机号
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> generateAuthCodeByTelephone(String telephone, String sign);


    /**
     * 验证验证码
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> verifyAuthCode(UemUserDto uemUserDto);

    /**
     * 注册并验证验证码
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> register(UemUserDto uemUserDto);

    /**
     * 更新密码
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> updatePassword(UemUserDto uemUserDto);

    /**
     * 邮箱找回密码
     *
     * @param email 邮箱
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> findPasswordByMail(String email);

    /**
     *根据用户Id获取用户信息
     * @param uemUserId 用户ID
     * @return List<UemUser>
     * @author xrp
     * */
    List<UemUser> queryUemUserByUserId(String uemUserId);
    /**
     * 根据用户id获取用户信息接口，根据用户id和clientId获取用户角色信息
     * @Author:chenxf
     * @Description: 根据用户id获取用户信息接口，根据用户id和clientId获取用户角色信息
     * @Date: 15:55 2020/12/10
     * @param uemUserId: 用户id
     * @param clientId: id
     * @return :com.share.auth.domain.User
     *
     */
    User getUserAllInfo(Long uemUserId, String clientId);


    /**
     * 根据物流交换代码返回公司信息
     * @param companyCode 物流交换代码
     * @return List<UemCompany>
     * @author xrp
     * */
    List<UemCompany> queryUemUserCompany(String companyCode);

    /**
     * 校验用户名唯一性
     * @Author:chenxf
     * @Description: 校验用户名唯一性
     * @Date: 15:03 2021/1/6
     * @param account: 账号
     * @return :java.lang.Boolean
     *
     */
    Boolean validateAccount(String account);

    /**
     * 修改用户信息统一接口
     *
     * @param uemUserDto: [uemUserDto]
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 修改用户信息统一接口
     * @Date: 14:08 2021/1/9
     */
    ResultHelper<Object> updateUemUserInfo(UemUserDto uemUserDto);

    /**
     * 个人中心获取当前登录用户信息接口
     *
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 个人中心获取当前登录用户信息接口
     * @Date: 14:14 2021/1/9
     * @Param: []
     */
    ResultHelper<UemUserDto> getLoginUserInfo();

    /**
     * 校验密码和当前登录人是否一致
     * @Author:chenxf
     * @Description: 校验密码和当前登录人是否一致
     * @Date: 18:48 2021/1/11
     * @param password: 密码
     * @return :java.lang.Boolean
     *
     */
    Boolean validatePassword(String password);

    /**
     * 赋予用户默认角色
     * @Author:chenxf
     * @Description: 赋予用户默认角色
     * @Date: 15:44 2021/1/28
     * @param uemUserId 用户id
     * @param uemCompanyId: 公司id
     * @Return:void
     *
     */
    void defaultUemUserRole(Long uemUserId, Long uemCompanyId);

    /**
     * 国家综合交通运输信息平台增删改组织机构
     * @param uemUserOperateVO 组织信息
     * @return  返回操作结果
     */
    OperateResultVO operateUemUser(UemUserOperateVO uemUserOperateVO);
    /**
     * 根据用户ID集合返回用户和公司信息
     * @param userIdList 用户ID集合
     * @return List<UserAndCompanyVo>
     * @author cxq
     * */
    List<UserAndCompanyVo> queryUemUserCompanyByUserId(List<Long> userIdList);
}
