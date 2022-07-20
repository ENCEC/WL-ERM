package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xrp
 * @date 2020/11/3 0003
 */
@Api("用户管理模块")
public interface UemUserManageService {

    /**用户管理
     * @param uemUserDto 用户表封装类
     * @return Page<UemUserDto>
     * @author xrp
     * */
    Page<UemUserDto> queryUemUser(UemUserDto uemUserDto);

    /**
     * 用户管理详情
     *
     * @param uemUserId 用户ID
     * @return List<UemUserDto>
     * @author xrp
     */
    List<UemUserDto> getUemUser(String uemUserId);

    /**
     * 用户管理 启停
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    ResultHelper<Object> uemUserStartStop(UemUserDto uemUserDto);


    /**
     * 修改用户信息
     *
     * @param uemUserDto
     * @return
     * @throws
     * @author tujx
     */
    ResultHelper<Object> editUemUser(@RequestBody UemUserEditDTO uemUserDto);

    /**
     * 删除用户信息
     *
     * @param uemUserId 用户主键id
     * @return
     * @throws
     * @author tujx
     */
    ResultHelper<Object> deleteUemUser(Long uemUserId);

    /**
     * 平台客服新增用户
     * @param uemUserDto 用户信息
     * @return 新增结果
     */
    ResultHelper<String> saveUemUser(UemUserDto uemUserDto);

    /**
     * 平台客服重置用户密码
     * @param uemUserId 用户id
     * @return 重置结果
     */
    ResultHelper<String> resetUemUserPassword(Long uemUserId);
}
