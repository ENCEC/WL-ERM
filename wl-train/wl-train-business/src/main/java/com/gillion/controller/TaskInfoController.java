package com.gillion.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.domain.TaskDetailInfoDto;
import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.vo.StandardDetailVo;
import com.gillion.service.TaskInfoService;
import com.share.auth.api.TaskInfoInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    public ResultHelper<TaskDetailInfoDto> queryPositiveApply(@RequestParam Long taskInfoId, @RequestParam Long taskDetailId) {
        return taskInfoService.queryPositiveApply(taskInfoId, taskDetailId);
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
    public ResultHelper<UemUserDto> queryDismissInfo(@RequestParam(value = "uemUserId") Long uemUserId) {
        ResultHelper<UemUserDto> uemUserDtoResultHelper = taskInfoInterface.queryLeaveInfo(uemUserId);
        return CommonResult.getSuccessResultData(uemUserDtoResultHelper);
    }

    @PostMapping("/savePositiveInfoByLeader")
    @ApiOperation("我的任务（部门领导最终审核）添加转正基本信息")
    public ResultHelper<Object> savePositiveInfoByLeader(@RequestBody TaskDetailInfoDto taskDetailInfoDto) {
        return taskInfoService.savePositiveInfoByLeader(taskDetailInfoDto);
    }

    @GetMapping("/queryLeaveInfoByLeader")
    @ApiOperation("我的任务（部门领导最终审核）查看离职信息以及基本信息")
    public ResultHelper<List> queryLeaveInfoByLeader(@RequestParam Long taskInfoId, @RequestParam Long uemUserId, @RequestParam Long taskDetailId) {
        List list = new ArrayList();
        ResultHelper<TaskDetailInfoDto> taskDetailInfoDtoResultHelper = taskInfoService.queryPositiveApply(taskInfoId, taskDetailId);
        ResultHelper<UemUserDto> uemUserDtoResultHelper = taskInfoInterface.queryLeaveInfo(uemUserId);
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
    public ResultHelper<Object> savePositiveInfoByStaff(@RequestBody TaskDetailInfoDto taskDetailInfoDto) {
        return taskInfoService.savePositiveInfoByStaff(taskDetailInfoDto);
    }

}
