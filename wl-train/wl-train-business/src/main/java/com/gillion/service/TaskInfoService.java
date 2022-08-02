package com.gillion.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.domain.TaskInfoDto;
import com.share.support.result.ResultHelper;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 任务信息服务
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
public interface TaskInfoService {

    ResultHelper<Page<TaskInfoDto>> queryTaskInfoPage(@RequestBody TaskInfoDto taskInfoDto);

    ResultHelper<Object> saveTaskInfo(@RequestBody TaskInfoDto taskInfoDto);

    ResultHelper<Object> updateTaskInfo(@RequestBody TaskInfoDto taskInfoDto);

    ResultHelper<TaskInfoDto> getTaskInfoDetail(@RequestBody Long taskInfoId);

    ResultHelper<Object> deleteTaskInfo(@RequestBody Long taskInfoId);
}
