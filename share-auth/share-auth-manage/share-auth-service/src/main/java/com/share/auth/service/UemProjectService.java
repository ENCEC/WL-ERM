package com.share.auth.service;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.UemProjectDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserProjectDto;
import com.share.auth.model.entity.UemUserProject;
import com.share.support.result.ResultHelper;

import java.util.List;
import java.util.Map;

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
    /**
     * @Description: 数据权限测试例子
     * @Author: ecchen
     * @Date: 2022/8/21 17:41
     * @Param: [pageNo, pageSize]
     * @Return: com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.UemUserProjectDto>
     **/
    Page<UemUserProjectDto> selectUserProject(int pageNo, int pageSize);

    /**
     * 仪表盘项目人员配置情况
     * @return
     */
    ResultHelper<Map<String,Object>> queryProjectStaff();
    /**
     * 仪表盘项目人员详细配置情况
     * @return
     */
    ResultHelper<?> queryProjectDetailedStaff();

}
