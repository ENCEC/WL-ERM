package com.share.auth.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.UemProjectDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserProjectDto;
import com.share.auth.model.entity.UemProject;
import com.share.auth.model.entity.UemUser;
import com.share.auth.model.entity.UemUserProject;
import com.share.auth.model.querymodels.QUemProject;
import com.share.auth.model.querymodels.QUemUserProject;
import com.share.auth.model.querymodels.QUemUser;
import com.share.auth.service.UemProjectService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * @author tanjp
 * @Date 2022/7/28 16:36
 */
@Service("UemProjectService")
public class UemProjectServiceImpl implements UemProjectService {

    @Autowired
    private UemProjectService uemProjectService;
    private String viewDetailID;

    /**
     * 查询岗位信息
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return Page<UemProjectDTO>
     * @author tanjp
     * @date 2022/7/29
     */
    @Override
    public ResultHelper<Page<UemProjectDTO>> selectUemProject(UemProjectDTO uemProjectDTO) {

        //项目名称
        String projectName = uemProjectDTO.getProjectName();
        //项目客户
        String customer = uemProjectDTO.getCustomer();

        if (!StringUtils.isEmpty(projectName)) {
            uemProjectDTO.setProjectName("%" + projectName + "%");
        }
        if (!StringUtils.isEmpty(customer)) {
            uemProjectDTO.setCustomer("%" + customer + "%");
        }

        //currentPage:当前页     pageSize:每页显示的条数
        int currentPage = (uemProjectDTO.getCurrentPage() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemProjectDTO.getCurrentPage();
        int pageSize = (uemProjectDTO.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemProjectDTO.getPageSize();

        Page<UemProjectDTO> uemProjectDTOPage = QUemProject.uemProject.select(
                QUemProject.uemProject.fieldContainer()
        ).where(
                QUemProject.projectName.like(":projectName")
                        .and(QUemProject.dutyId.eq(":dutyId"))
                        .and(QUemProject.customer.like(":customer"))
                        .and(QUemProject.status.like(":status"))
                        .and(QUemProject.uemProjectId.goe$(1L))
        ).paging(currentPage,pageSize)
                .sorting(QUemProject.createTime.desc())
                .mapperTo(UemProjectDTO.class)
                .execute(uemProjectDTO);
        return CommonResult.getSuccessResultData(uemProjectDTOPage);
    }

    /**
     *新增项目
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author wq
     * @date 2022/7/29
     */
    @Override
    public ResultHelper<UemProjectDTO> addUemProject(UemProjectDTO uemProjectDTO) {
        if (StrUtil.isEmpty(uemProjectDTO.getProjectName())) {
            return CommonResult.getFaildResultData("项目名称不能为空");
        }
        if (StrUtil.isEmpty(uemProjectDTO.getCustomer())) {
            return CommonResult.getFaildResultData("客户名称不能为空");
        }
        if (Objects.isNull(uemProjectDTO.getFcy())) {
            return CommonResult.getFaildResultData("项目金额不能为空");
        }
        if (Objects.isNull(uemProjectDTO.getStatus())) {
            return CommonResult.getFaildResultData("项目进行状态不能为空");
        }
        if (Objects.isNull(uemProjectDTO.getDutyId())) {
            return CommonResult.getFaildResultData("项目经理不能为空");
        }
        if (StrUtil.isEmpty(uemProjectDTO.getGenDemandUsers())) {
            return CommonResult.getFaildResultData("需求组员不能为空");
        }
        if (StrUtil.isEmpty(uemProjectDTO.getGenDevUsers())) {
            return CommonResult.getFaildResultData("开发组员不能为空");
        }
        if (Objects.isNull(uemProjectDTO.getPlanStartTime())) {
            return CommonResult.getFaildResultData("计划开始时间不能为空");
        }
        if (Objects.isNull(uemProjectDTO.getPlanEndTime())) {
            return CommonResult.getFaildResultData("计划结束时间不能为空");
        }
        UemProject uemProject = new UemProject();
        uemProject.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        uemProject.setProjectName(uemProjectDTO.getProjectName());
        uemProject.setCustomer(uemProjectDTO.getCustomer());
        uemProject.setFcy(uemProjectDTO.getFcy());
        uemProject.setStatus(uemProjectDTO.getStatus());
        uemProject.setChiefId(uemProjectDTO.getChiefId());
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getChiefId());
        uemProject.setChiefName(uemUser.getName());
        uemProject.setDutyId(uemProjectDTO.getDutyId());
        UemUser uemUser1 = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getDutyId());
        uemProject.setDutyName(uemUser1.getName());
        uemProject.setDevDirectorId(uemProjectDTO.getDevDirectorId());
        UemUser uemUser2 = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getDevDirectorId());
        uemProject.setDevDirectorName(uemUser2.getName());
        uemProject.setDemandId(uemProjectDTO.getDemandId());
        UemUser uemUser3 = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getDemandId());
        uemProject.setDemandName(uemUser3.getName());
        uemProject.setGenDevUsers(uemProjectDTO.getGenDevUsers());
        uemProject.setGenDemandUsers(uemProjectDTO.getGenDemandUsers());
        uemProject.setPlanStartTime(uemProjectDTO.getPlanStartTime());
        uemProject.setPlanEndTime(uemProjectDTO.getPlanEndTime());
        int save = QUemProject.uemProject.save(uemProject);
        if (save > 0) {
            return CommonResult.getSuccessResultData("项目新增成功");
        } else {
            return CommonResult.getFaildResultData("项目新增失败");
        }
    }


    /**
     *删除
     *
     * @param uemProjectById 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/27
     */
    @Override
    public ResultHelper<?> deleteUemProjectById(Long uemProjectById) {
        //获取用户
        if (Objects.isNull(uemProjectById)) {
            return CommonResult.getFaildResultData("项目不存在");
        }

        UemProject uemProject = new UemProject();
        //删除
        uemProject.setRowStatus(RowStatusConstants.ROW_STATUS_DELETED);
        int row = QUemProject.uemProject.deleteById(uemProjectById);

        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("删除成功");
        } else {
            return CommonResult.getFaildResultData("删除失败");
        }
    }

    /**
     *查找id
     *
     * @param uemProjectId
     * @return
     * @author tanjp
     * @date 2022/7/29
     */
    public UemProject getUemProjectById(Long uemProjectId) {
        List<UemProject> uemProjectList = QUemProject.uemProject
                .select(QUemProject.uemProject.fieldContainer())
                .where(QUemProject.uemProjectId.eq$(uemProjectId))
                .execute();
        if (uemProjectList.size() == 1) {
            return uemProjectList.get(0);
        }else {
            return null;
        }
    }


    /**
     *项目编辑
     *
     * @param uemProjectDTO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/29
     */
    @Override
    public ResultHelper<UemProjectDTO> updateUemProject(UemProjectDTO uemProjectDTO) {
        //根据主键查询岗位信息
        Long uemProjectDTOId = uemProjectDTO.getUemProjectId();
        if (Objects.isNull(uemProjectDTOId)) {
            return CommonResult.getFaildResultData("id不允许为空");
        }
        UemProject uemProject = this.getUemProjectById(uemProjectDTOId);
        if (Objects.isNull(uemProject)) {
            return CommonResult.getFaildResultData("不存在");
        }
        //更新信息
        uemProject.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        uemProject.setProjectName(uemProjectDTO.getProjectName());
        uemProject.setCustomer(uemProjectDTO.getCustomer());
        uemProject.setFcy(uemProjectDTO.getFcy());
        uemProject.setStatus(uemProjectDTO.getStatus());
        uemProject.setChiefId(uemProjectDTO.getChiefId());
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getChiefId());
        uemProject.setChiefName(uemUser.getName());
        uemProject.setDutyId(uemProjectDTO.getDutyId());
        UemUser uemUser1 = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getDutyId());
        uemProject.setDutyName(uemUser1.getName());
        uemProject.setDevDirectorId(uemProjectDTO.getDevDirectorId());
        UemUser uemUser2 = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getDevDirectorId());
        uemProject.setDevDirectorName(uemUser2.getName());
        uemProject.setDemandId(uemProjectDTO.getDemandId());
        UemUser uemUser3 = QUemUser.uemUser.selectOne().byId(uemProjectDTO.getDemandId());
        uemProject.setDemandName(uemUser3.getName());
        uemProject.setGenDevUsers(uemProjectDTO.getGenDevUsers());
        uemProject.setGenDemandUsers(uemProjectDTO.getGenDemandUsers());
        uemProject.setPlanStartTime(uemProjectDTO.getPlanStartTime());
        uemProject.setPlanEndTime(uemProjectDTO.getPlanEndTime());
        int row = QUemProject.uemProject.save(uemProject);
        if (row > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM) {
            return CommonResult.getSuccessResultData("用户修改成功");
        } else {
            return CommonResult.getFaildResultData("用户修改失败");
        }
    }



    /**
     * 联想控件参数
     *
     * @param uemUserDto 用户
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/7/29
     */
    @Override
    public ResultHelper<Page<UemUserDto>> queryUemUser(UemUserDto uemUserDto) {
        // 姓名
        String name = uemUserDto.getName();

        if (!StringUtils.isEmpty(name)) {
            uemUserDto.setName("%" + name + "%");
        }
        int pageNo = (uemUserDto.getPageNo() == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : uemUserDto.getPageNo();
        int pageSize = (uemUserDto.getPageSize() == null) ? CodeFinal.PAGE_SIZE_DEFAULT : uemUserDto.getPageSize();
        Page<UemUserDto> uemUserDtoPage = QUemUser.uemUser.select(
                        QUemUser.uemUser.fieldContainer()
                ).where(
                        QUemUser.account.like(":account")
                                .and(QUemUser.name.like(":name"))
                                .and(QUemUser.isDeleted.eq$(false))
                ).paging(pageNo, pageSize)
                .sorting(QUemUser.createTime.desc())
                .mapperTo(UemUserDto.class)
                .execute(uemUserDto);

        return CommonResult.getSuccessResultData(uemUserDtoPage);
    }


    /**
     * 根据开发成员和需求成员的id  查找
     *
     * @param   ViewDetailID
     * @return ResultHelper<List<String>>
     * @author tanjp
     * @date 2022/7/29
     */
    @Override
    public ResultHelper<List<String>> selectViewDetailById(String ViewDetailID) {
        //组员ID
        String[] split = ViewDetailID.split(",");

        List list = new ArrayList();
        for (int i = 0; i < split.length; i++) {
            Long  id = Long.valueOf(split[i]);
            list.add(id);

        }
        List<UemUser> uemUserList=QUemUser.uemUser
                .select()
                .byId(list);

        return CommonResult.getSuccessResultData(uemUserList);
    }

    @Override
    public Page<UemUserProjectDto> selectUserProject(int pageNo, int pageSize){
        Page<UemUserProjectDto> uemUserProjects = QUemUserProject.uemUserProject.select(
                QUemUserProject.uemProjectId,QUemUserProject.uemUserId)
                .where(QUemUserProject.creatorId.eq$(999L))
                .paging(pageNo,pageSize)
                .mapperTo(UemUserProjectDto.class)
                .execute();
        return uemUserProjects;
    }

}

