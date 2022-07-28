package com.share.auth.service.impl;

import com.share.auth.domain.UemProjectDTO;
import com.share.auth.model.entity.UemProject;
import com.share.auth.model.querymodels.QUemProject;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.service.UemProjectService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author tanjp
 * @Date 2022/7/28 16:36
 */
public class UemProjectServiceImpl implements UemProjectService {

    @Autowired
    private UemProjectService uemProjectService;

    /**
     *新增项目
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/28
     */
    @Override
    public ResultHelper<?> saveUemProject(UemProjectDTO uemProjectDTO) {
        //判断项目名是否可用
        List<UemProject> uemProjectList = QUemProject.uemProject
                .select(QUemProject.uemProject.fieldContainer())
                .where(QUemUser.projectName.eq$(uemProjectDTO.getProjectName()))
                .execute();
        if(CollectionUtils.isNotEmpty(uemProjectList)) {
            return CommonResult.getSuccessResultData("该用户名已注册过！");
        }
        //团队成员 name  id  是否和 用户表对应

        // uemProjectDTO.getDemandId() || uemProjectDTO.getChiefId() || uemProjectDTO.getDevDirectorId() || uemProjectDTO.getDemandId() || uemProjectDTO.getGenDevUsers()  || uemProjectDTO.getGenDemandUsers()

        return null;
    }
}
