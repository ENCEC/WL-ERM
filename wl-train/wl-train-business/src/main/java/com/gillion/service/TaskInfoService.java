package com.gillion.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.vo.StandardDetailVo;
import com.share.support.result.ResultHelper;

/**
 * 任务信息服务
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
public interface TaskInfoService {

    /**
     * 查询任务信息列表
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page<com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Page<TaskInfoDto>> queryTaskInfoPage(TaskInfoDto taskInfoDto);

    /**
     * 新增任务信息和任务细则
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Object> saveTaskInfo(TaskInfoDto taskInfoDto);

    /**
     * 更新任务信息和任务细则
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Object> updateTaskInfo(TaskInfoDto taskInfoDto);

    /**
     * 获取任务细节和任务信息
     * @param taskInfoId 任务ID
     * @return com.share.support.result.ResultHelper<com.gillion.model.domain.TaskInfoDto>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<TaskInfoDto> getTaskInfoDetail(Long taskInfoId);

    /**
     * 删除任务细节和任务信息
     * @param taskInfoId 任务ID
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Object> deleteTaskInfo(Long taskInfoId);

    /**
     * 根据任务类型分页查询完整规范细则
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page<com.gillion.model.vo.StandardDetailVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Page<StandardDetailVo>> queryStandardFullDetailByTaskType(TaskInfoDto taskInfoDto);
}
