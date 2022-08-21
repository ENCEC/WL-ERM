package com.gillion.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.domain.TaskDetailInfoDto;
import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.model.entity.TaskInfo;
import com.gillion.model.querymodels.QTaskDetailInfo;
import com.gillion.model.querymodels.QTaskInfo;
import com.gillion.model.vo.StandardDetailVo;
import com.gillion.service.TaskInfoService;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.auth.api.TaskInfoInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/7/29
 */
@Api("任务信息接口")
@RestController
@RequestMapping("/taskInfo")
public class TaskInfoController {

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private TaskInfoInterface taskInfoInterface;

    @PostMapping("/queryTaskInfoPage")
    @ApiOperation("查询任务信息")
    @ApiImplicitParam(name = "taskInfoDto", value = "任务信息DTO", dataType = "TaskInfoDto", paramType = "body")
    public ResultHelper<Page<TaskInfoDto>> queryTaskInfoPage(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.queryTaskInfoPage(taskInfoDto);
    }

    @PostMapping("/saveTaskInfo")
    @ApiOperation("新增条目")
    @ApiImplicitParam(name = "taskInfoDto", value = "任务信息DTO", dataType = "TaskInfoDto", paramType = "body")
    public ResultHelper<Object> saveTaskInfo(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.saveTaskInfo(taskInfoDto);
    }

    @PostMapping("/updateTaskInfo")
    @ApiOperation("更新条目")
    @ApiImplicitParam(name = "taskInfoDto", value = "任务信息DTO", dataType = "TaskInfoDto", paramType = "body")
    public ResultHelper<Object> updateTaskInfo(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.updateTaskInfo(taskInfoDto);
    }

    @PostMapping("/getTaskInfoDetail")
    @ApiOperation("获取任务细节")
    @ApiImplicitParam(name = "taskInfoId", value = "任务信息ID", dataType = "Long", paramType = "body")
    public ResultHelper<TaskInfoDto> getTaskInfoDetail(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.getTaskInfoDetail(taskInfoDto.getTaskInfoId());
    }

    @PostMapping("/deleteTaskInfo")
    @ApiOperation("删除任务细节")
    @ApiImplicitParam(name = "taskInfoId", value = "任务信息ID", dataType = "Long", paramType = "body")
    public ResultHelper<Object> deleteTaskInfo(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.deleteTaskInfo(taskInfoDto.getTaskInfoId());
    }

    @PostMapping("/queryStandardFullDetailByTaskType")
    @ApiOperation("根据任务类型分页查询完整规范细则")
    @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "String", paramType = "body")
    public ResultHelper<Page<StandardDetailVo>> queryStandardFullDetailByTaskType(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.queryStandardFullDetailByTaskType(taskInfoDto);
    }

    @PostMapping("/queryNeedStandardFullDetailByTaskType")
    @ApiOperation("根据任务类型查询全部必须的完整规范细则")
    @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "String", paramType = "body")
    public ResultHelper<List<StandardDetailVo>> queryNeedStandardFullDetailByTaskType(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.queryNeedStandardFullDetailByTaskType(taskInfoDto);
    }

    @PostMapping("/queryNotNeedStandardFullDetailByTaskType")
    @ApiOperation("根据任务类型分页查询非必须的完整规范细则")
    @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "String", paramType = "body")
    public ResultHelper<Page<StandardDetailVo>> queryNotNeedStandardFullDetailByTaskType(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.queryNotNeedStandardFullDetailByTaskType(taskInfoDto);
    }

    @PostMapping("/queryStaffTaskInfo")
    @ApiOperation("查询员工任务信息")
    @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "String", paramType = "body")
    public ResultHelper<Page<TaskInfoDto>> queryStaffTaskInfo(@RequestBody TaskInfoDto taskInfoDto) {
        if (Objects.isNull(taskInfoDto.getPageNo())) {
            taskInfoDto.setPageNo(1);
        }
        if (Objects.isNull(taskInfoDto.getPageSize())) {
            taskInfoDto.setPageSize(10);
        }
        return taskInfoService.queryStaffTaskInfo(taskInfoDto);
    }

    @PostMapping("/queryTaskDetailInfo")
    @ApiOperation("查询员工任务详情信息")
    @ApiImplicitParam(name = "taskInfoDto", value = "任务信息ID", dataType = "TaskInfoDto", paramType = "body")
    public ResultHelper<Page<TaskDetailInfoDto>> queryTaskDetail(@RequestBody TaskInfoDto taskInfoDto) {
        if (Objects.isNull(taskInfoDto.getPageNo())) {
            taskInfoDto.setPageNo(1);
        }
        if (Objects.isNull(taskInfoDto.getPageSize())) {
            taskInfoDto.setPageSize(10);
        }
        return taskInfoService.queryStaffTaskDetail(taskInfoDto);
    }

    @PostMapping("/updateTaskDetailProgress")
    @ApiOperation("更新任务进度")
    @ApiImplicitParam(name = "taskDetailInfoDtoList", value = "任务细则ID和进度数值", dataType = "TaskDetailInfoDto", paramType = "body")
    public ResultHelper<String> updateTaskDetailProgress(@RequestBody List<TaskDetailInfoDto> taskDetailInfoDtoList) {
        return taskInfoService.updateTaskDetailProgress(taskDetailInfoDtoList);
    }

    @PostMapping("/queryLeaderTaskInfo")
    @ApiOperation("查询负责人任务信息")
    @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "String", paramType = "body")
    public ResultHelper<Page<TaskInfoDto>> queryLeaderTaskInfo(@RequestBody TaskInfoDto taskInfoDto) {
        if (Objects.isNull(taskInfoDto.getPageNo())) {
            taskInfoDto.setPageNo(1);
        }
        if (Objects.isNull(taskInfoDto.getPageSize())) {
            taskInfoDto.setPageSize(10);
        }
        return taskInfoService.queryLeaderTaskInfo(taskInfoDto);
    }

    @PostMapping("/updateTaskDetailStatus")
    @ApiOperation("更新任务完成状态")
    @ApiImplicitParam(name = "taskDetailInfoDtoList", value = "任务细则ID和完成状态和完成结果", dataType = "TaskDetailInfoDto", paramType = "body")
    public ResultHelper<String> updateTaskDetailStatus(@RequestBody List<TaskDetailInfoDto> taskDetailInfoDtoList) {
        return taskInfoService.updateTaskDetailStatus(taskDetailInfoDtoList);
    }

    @PostMapping("/queryOrdinatorTaskInfo")
    @ApiOperation("查询统筹人任务信息")
    @ApiImplicitParam(name = "taskType", value = "任务类型", dataType = "String", paramType = "body")
    public ResultHelper<Page<TaskInfoDto>> queryOrdinatorTaskInfo(@RequestBody TaskInfoDto taskInfoDto) {
        if (Objects.isNull(taskInfoDto.getPageNo())) {
            taskInfoDto.setPageNo(1);
        }
        if (Objects.isNull(taskInfoDto.getPageSize())) {
            taskInfoDto.setPageSize(10);
        }
        return taskInfoService.queryOrdinatorTaskInfo(taskInfoDto);
    }

    @GetMapping("/queryPositiveApply")
    @ApiOperation("根据任务id查询该申请人的转正申请")
    public ResultHelper<TaskDetailInfoDto> queryPositiveApply(@RequestParam Long taskInfoId) {
        return taskInfoService.queryPositiveApply(taskInfoId);
    }

    @PostMapping("/queryAllStandardDetail")
    @ApiOperation("查出规范条目所对应的规范细则 用作转正程序")
    public ResultHelper<List<StandardDetailVo>> queryAllStandardDetail() {
        return taskInfoService.queryAllStandardDetail();
    }

    @PostMapping("/savePositiveInfo")
    @ApiOperation("我的任务（项目经历初次审核） 添加基本转正信息")
    public ResultHelper<Object> savePositiveInfo(@RequestBody TaskDetailInfoDto taskDetailInfoDto) {
        return taskInfoService.savePositiveInfo(taskDetailInfoDto);
    }

    @PostMapping("/saveLeaveInfo")
    @ApiOperation("我的任务（项目经历初次审核） 添加离职基本信息")
    public ResultHelper<Object> saveLeaveInfo(@RequestBody TaskDetailInfoDto taskDetailInfoDto) {
        return taskInfoService.saveLeaveInfo(taskDetailInfoDto);
    }

    @GetMapping("/queryLeaveInfo")
    @ApiOperation("我的任务（项目经历初次审核） 查询员工离职原因")
    public ResultHelper<UemUserDto> queryDismissInfo(@RequestParam(value = "dispatchers") Long dispatchers) {
        ResultHelper<UemUserDto> uemUserDtoResultHelper = taskInfoInterface.queryLeaveInfo(dispatchers);
        return CommonResult.getSuccessResultData(uemUserDtoResultHelper);
    }

    @PostMapping("/savePositiveInfoByLeader")
    @ApiOperation("我的任务（部门领导最终审核）添加转正基本信息")
    public ResultHelper<Object> savePositiveInfoByLeader(@RequestBody TaskDetailInfoDto taskDetailInfoDto) {
        return taskInfoService.savePositiveInfoByLeader(taskDetailInfoDto);
    }

    @GetMapping("/queryLeaveInfoByLeader")
    @ApiOperation("我的任务（部门领导最终审核）查看离职信息以及基本信息")
    public ResultHelper<List> queryLeaveInfoByLeader(@RequestParam Long taskInfoId, @RequestParam Long dispatchers) {
        List list = new ArrayList();
        ResultHelper<TaskDetailInfoDto> taskDetailInfoDtoResultHelper = taskInfoService.queryPositiveApply(taskInfoId);
        ResultHelper<UemUserDto> uemUserDtoResultHelper = taskInfoInterface.queryLeaveInfo(dispatchers);
        list.add(taskDetailInfoDtoResultHelper);
        list.add(uemUserDtoResultHelper);
        return CommonResult.getSuccessResultData(list);
    }

    @PostMapping("/saveLeaveInfoByLeader")
    @ApiOperation("我的任务（部门领导最终审核）添加离职基本信息")
    public ResultHelper<Object> saveLeaveInfoByLeader(@RequestBody TaskDetailInfoDto taskDetailInfoDto) {
        return taskInfoService.saveLeaveInfoByLeader(taskDetailInfoDto);
    }

    @PostMapping("/savePositiveInfoByStaff")
    @ApiOperation("员工管理（服务调用） 添加员工转正信息")
    public ResultHelper<Object> savePositiveInfoByStaff(@RequestBody TaskDetailInfoDTO taskDetailInfoDTO) {
        Long uemUserId = taskDetailInfoDTO.getUemUserId();
        UemUserDto positiveUser = taskInfoInterface.queryStaffInfo(String.valueOf(uemUserId));
        Long id = positiveUser.getUemUserId();
        String name = positiveUser.getName();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setDispatchers(id);
        taskInfo.setDispatchersName(name);
        taskInfo.setTaskType("员工转正");
        taskInfo.setTaskTitle(name + "转正申请");
        taskInfo.setStatus(2);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        //关联查出用户id，name 插入任务表，生成新任务
        List<TaskInfo> execute = QTaskInfo.taskInfo.select().where(QTaskInfo.taskTitle.eq$(taskInfo.getTaskTitle())).execute();
        if (execute.size() > 1) {
            return CommonResult.getFaildResultData("该员工已经提交过申请");
        } else {
            QTaskInfo.taskInfo.save(taskInfo);
            Long taskInfoId = taskInfo.getTaskInfoId();
            TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
            taskDetailInfo.setTaskInfoId(taskInfoId);
            taskDetailInfo.setStandardEntryId(6960875859204194304L);
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
            taskDetailInfoDTO.setTaskInfoId(taskInfo.getTaskInfoId());
            taskDetailInfoDTO.setTaskDetailId(taskDetailInfo.getTaskDetailId());
            return taskInfoService.savePositiveInfoByStaff(taskDetailInfoDTO);
        }

    }

    @PostMapping("/saveResignInfo")
    @ApiOperation("员工管理（服务调用） 添加员工离职信息")
    public ResultHelper<?> saveResignInfo(@RequestBody UemUserDto uemUserDto) {
        Long uemUserId = uemUserDto.getUemUserId();
        UemUserDto resignUser = taskInfoInterface.queryStaffInfo(String.valueOf(uemUserId));
        uemUserDto.setUemUserId(uemUserId);
        taskInfoInterface.saveResignInfo(uemUserDto);
        Long id = resignUser.getUemUserId();
        String name = resignUser.getName();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setDispatchers(id);
        taskInfo.setDispatchersName(name);
        taskInfo.setTaskType("员工离职");
        taskInfo.setTaskTitle(name + "离职申请");
        taskInfo.setStatus(2);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        List<TaskInfo> execute = QTaskInfo.taskInfo.select().where(QTaskInfo.taskTitle.eq$(taskInfo.getTaskTitle())).execute();
        //关联查出用户id，name 插入任务表，生成新任务
        if (execute.size() > 1) {
            return CommonResult.getFaildResultData("该员工已经提交过申请");
        } else {
            int taskResult = QTaskInfo.taskInfo.save(taskInfo);
            Long taskInfoId = taskInfo.getTaskInfoId();
            TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
            taskDetailInfo.setTaskInfoId(taskInfoId);
            taskDetailInfo.setStandardEntryId(6960875859204194304L);
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            int taskDetailResult = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
            taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
            taskDetailInfo.setTaskDetailId(taskDetailInfo.getTaskDetailId());
            if ((taskResult == 1) && (taskDetailResult == 1)) {
                return CommonResult.getSuccessResultData("添加成功！");
            } else {
                return CommonResult.getFaildResultData("添加失败！");
            }
        }
    }

    @PostMapping("/saveDismissInfo")
    @ApiOperation("员工管理（服务调用） 添加员工辞退信息")
    public ResultHelper<?> saveDismissInfo(@RequestBody UemUserDto uemUserDto) {
        Long uemUserId = uemUserDto.getUemUserId();
        UemUserDto dismissUser = taskInfoInterface.queryStaffInfo(String.valueOf(uemUserId));
        uemUserDto.setUemUserId(Long.valueOf(uemUserId));
        taskInfoInterface.saveDismissInfo(uemUserDto);
        Long id = dismissUser.getUemUserId();
        String name = dismissUser.getName();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setDispatchers(id);
        taskInfo.setDispatchersName(name);
        taskInfo.setTaskType("员工辞退");
        taskInfo.setTaskTitle(name + "辞退申请");
        taskInfo.setStatus(2);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        //关联查出用户id，name 插入任务表，生成新任务
        int taskResult = QTaskInfo.taskInfo.save(taskInfo);
        Long taskInfoId = taskInfo.getTaskInfoId();
        TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
        taskDetailInfo.setTaskInfoId(taskInfoId);
        taskDetailInfo.setStandardEntryId(6960875859204194304L);
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        int taskDetailResult = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
        taskDetailInfo.setTaskDetailId(taskDetailInfo.getTaskDetailId());
        if ((taskResult == 1) && (taskDetailResult == 1)) {
            return CommonResult.getSuccessResultData("添加成功！");
        } else {
            return CommonResult.getFaildResultData("添加失败！");
        }
    }

    @GetMapping("/deletedApplyByStaff")
    @ApiOperation("我的任务 员工撤回申请")
    public ResultHelper<?> deletedApplyByStaff(@RequestParam Long taskInfoId) {
        return taskInfoService.deletedApplyByStaff(taskInfoId);
    }

    @PostMapping("/queryTaskInfoByPage")
    @ApiOperation("我的任务 分页查询任务信息")
    public ResultHelper<TaskInfoDto> queryTaskInfoByPage(@RequestBody TaskInfoDto taskInfoDto) {
        return taskInfoService.queryTaskInfoByPage(taskInfoDto);
    }

    @GetMapping("/queryPositiveInfo")
    @ApiOperation("员工管理查询员工转正信息")
    public List<TaskInfoDto> queryPositiveInfo(@RequestParam Long dispatchers) {
        return taskInfoService.queryPositiveInfo(dispatchers);
    }

    @GetMapping("/queryPositiveInfoByTaskId")
    @ApiOperation("员工管理查询员工转正信息")
    public TaskDetailInfoDTO queryPositiveInfoByTaskId(@RequestParam Long taskInfoId) {
        return taskInfoService.queryPositiveInfoByTaskId(taskInfoId);
    }

    @GetMapping("/queryTaskInfoByUser")
    @ApiOperation("根据任务id查询该申请人的转正申请以及简历")
    public List queryPositiveApply(@RequestParam Long taskInfoId, @RequestParam Long dispatchers) {
        List list = new ArrayList<>();
        ResultHelper<TaskDetailInfoDto> taskDetailInfoDtoResultHelper = taskInfoService.queryPositiveApply(taskInfoId);
        UemUserDto uemUserDto = taskInfoInterface.queryStaffInfo(String.valueOf(dispatchers));
        //String resume = uemUserDto.getResume();
        list.add(taskDetailInfoDtoResultHelper);
        list.add(uemUserDto);
        return list;
    }
}
