package com.gillion.service;

import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.model.entity.TaskInfo;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.support.result.ResultHelper;

import java.util.List;

/**
 * @ClassName TaskDetailInfoService
 * @Author weiq
 * @Date 2022/8/8 18:40
 * @Version 1.0
 **/
public interface TaskDetailInfoService {

    /**
     * 转正申请
     * @param taskDetailInfoDTO
     * @return
     */
    ResultHelper<?> saveOffer(TaskDetailInfoDTO taskDetailInfoDTO);

    /**
     * 离职申请
     * @param taskDetailInfoDTO
     * @return
     */
    ResultHelper<?> saveLeave(TaskDetailInfoDTO taskDetailInfoDTO);

    /**
     * 查看转正评语
     * @param dispatchers
     * @return
     */
    List queryOffer(Long dispatchers);


}
