package com.gillion.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.domain.TaskDetailInfoDto;
import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.vo.StandardDetailVo;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.support.result.ResultHelper;

import java.util.List;

/**
 * 任务信息服务
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
public interface TaskInfoService {

    /**
     * 查询任务信息列表
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Page<TaskInfoDto>> queryTaskInfoPage(TaskInfoDto taskInfoDto);

    /**
     * 新增任务信息和任务细则
     *
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Object> saveTaskInfo(TaskInfoDto taskInfoDto);

    /**
     * 更新任务信息和任务细则
     *
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Object> updateTaskInfo(TaskInfoDto taskInfoDto);

    /**
     * 获取任务细节和任务信息
     *
     * @param taskInfoId 任务ID
     * @return com.share.support.result.ResultHelper<com.gillion.model.domain.TaskInfoDto>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<TaskInfoDto> getTaskInfoDetail(Long taskInfoId);

    /**
     * 删除任务细节和任务信息
     *
     * @param taskInfoId 任务ID
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Object> deleteTaskInfo(Long taskInfoId);

    /**
     * 根据任务类型分页查询完整规范细则
     *
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.vo.StandardDetailVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-03
     */
    ResultHelper<Page<StandardDetailVo>> queryStandardFullDetailByTaskType(TaskInfoDto taskInfoDto);

    /**
     * 根据任务类型查询全部必须的完整规范细则
     *
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.vo.StandardDetailVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    ResultHelper<List<StandardDetailVo>> queryNeedStandardFullDetailByTaskType(TaskInfoDto taskInfoDto);

    /**
     * 根据任务类型分页查询非必须的完整规范细则
     *
     * @param taskInfoDto 任务信息
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.vo.StandardDetailVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    ResultHelper<Page<StandardDetailVo>> queryNotNeedStandardFullDetailByTaskType(TaskInfoDto taskInfoDto);

    /**
     * 查询员工任务信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    ResultHelper<Page<TaskInfoDto>> queryStaffTaskInfo(TaskInfoDto taskInfoDto);

    /**
     * 查询员工任务详情信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-08
     */
    ResultHelper<Page<TaskDetailInfoDto>> queryStaffTaskDetail(TaskInfoDto taskInfoDto);

    /**
     * 更新任务进度
     *
     * @param taskDetailInfoDtoList 任务细则ID和进度数值
     * @return com.share.support.result.ResultHelper<java.lang.String>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-08
     */
    ResultHelper<String> updateTaskDetailProgress(List<TaskDetailInfoDto> taskDetailInfoDtoList);

    /**
     * 查询负责人任务信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    ResultHelper<Page<TaskInfoDto>> queryLeaderTaskInfo(TaskInfoDto taskInfoDto);

    /**
     * 更新任务完成状态
     *
     * @param taskDetailInfoDtoList 查询入参
     * @return com.share.support.result.ResultHelper<java.lang.String>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-08
     */
    ResultHelper<String> updateTaskDetailStatus(List<TaskDetailInfoDto> taskDetailInfoDtoList);

    /**
     * 查询统筹人任务信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    ResultHelper<Page<TaskInfoDto>> queryOrdinatorTaskInfo(TaskInfoDto taskInfoDto);


    /**
     * 我的任务（项目经理初次审核） 根据任务id查询所分配的执行人
     *
     * @param taskInfoId 查询入参
     * @author wzr
     * @date 2022-08-09
     */
    ResultHelper<TaskDetailInfoDto> queryPositiveApply(Long taskInfoId/*, Long taskDetailId*/);

    /**
     * 我的任务（项目经理初次审核） 查出规范条目所对应的规范细则 用作转正程序
     *
     * @author wzr
     * @date 2022-08-09
     */
    ResultHelper<List<StandardDetailVo>> queryAllStandardDetail();

    /**
     * 我的任务（项目经理初次审核） 添加基本转正信息
     *
     * @author wzr
     * @date 2022-08-09
     */
    ResultHelper<Object> savePositiveInfo(TaskDetailInfoDto taskDetailInfoDto);

    /**
     * 我的任务（项目经历初次审核） 添加离职申请基本信息
     *
     * @author wzr
     * @date 2022-08-09
     */
    ResultHelper<Object> saveLeaveInfo(TaskDetailInfoDto taskDetailInfoDto);

    /**
     * 我的任务（部门领导最终审核） 添加转正基本信息
     *
     * @author wzr
     * @date 2022-08-09
     */
    ResultHelper<Object> savePositiveInfoByLeader(TaskDetailInfoDto taskDetailInfoDto);

    /**
     * 我的任务（部门领导最终审核） 添加离职信息
     *
     * @author wzr
     * @date 2022-08-09
     */
    ResultHelper<Object> saveLeaveInfoByLeader(TaskDetailInfoDto taskDetailInfoDto);

    /**
     * 员工管理（服务调用） 添加员工转正信息
     *
     * @author wzr
     * @date 2022-08-11
     */

    ResultHelper<Object> savePositiveInfoByStaff(TaskDetailInfoDTO taskDetailInfoDTO);

    /**
     * 我的任务  撤回员工申请（逻辑删除）
     *
     * @author wzr
     * @date 2022-08-12
     */
    ResultHelper<?> deletedApplyByStaff(Long taskInfoId);

    /**
     * 我的任务  --分页查询任务信息
     *
     * @author wzr
     * @date 2022-08-18
     */
    ResultHelper<TaskInfoDto> queryTaskInfoByPage(TaskInfoDto taskInfoDto);

    List<TaskInfoDto> queryPositiveInfo(Long dispatchers);

    TaskDetailInfoDTO queryPositiveInfoByTaskId(Long taskInfoId);


}
