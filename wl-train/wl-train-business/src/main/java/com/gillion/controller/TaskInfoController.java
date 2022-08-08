package com.gillion.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.vo.StandardDetailVo;
import com.gillion.service.TaskInfoService;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
