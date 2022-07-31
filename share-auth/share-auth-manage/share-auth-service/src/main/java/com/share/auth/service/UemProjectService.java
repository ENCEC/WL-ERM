package com.share.auth.service;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemProjectDTO;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;

import java.util.List;

/**
 * @author tanjp
 * @Date 2022/7/28 16:35
 */
public interface UemProjectService {

    /**
     * 查询岗位信息
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return Page<UemProjectDTO>
     * @author tanjp
     * @date 2022/7/29
     */
    ResultHelper<Page<UemProjectDTO>> selectUemProject(UemProjectDTO uemProjectDTO);


    /**
     *新增项目
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/29
     */
    ResultHelper<UemProjectDTO> addUemProject(UemProjectDTO uemProjectDTO);



    /**
     *删除
     *
     * @param uemProjectById id
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    ResultHelper<?> deleteUemProjectById(Long uemProjectById);



    /**
     *项目编辑
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/29
     */
    ResultHelper<UemProjectDTO> updateUemProject(UemProjectDTO uemProjectDTO);

    /**
     * 联想控件参数
     *
     * @param uemUserDto 传入名字
     * @return ResultHelper<Page<UemUserDto>>
     * @author tanjp
     * @date 2022/7/29
     */
    ResultHelper<Page<UemUserDto>> queryUemUser(UemUserDto uemUserDto);


    /**
     * 根据开发成员和需求成员的id  查找
     *
     * @param   ViewDetailID
     * @return ResultHelper<List<String>>
     * @author tanjp
     * @date 2022/7/29
     */
    ResultHelper<List<String>> selectViewDetailById(String ViewDetailID);
}
