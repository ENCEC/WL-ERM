package com.gillion.service;

import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.support.result.ResultHelper;

/**
 * @ClassName TaskDetailInfoService
 * @Author weiq
 * @Date 2022/8/8 18:40
 * @Version 1.0
 **/
public interface TaskDetailInfoService {
    ResultHelper<?> saveOffer(TaskDetailInfoDTO taskDetailInfoDTO);

    ResultHelper<?> saveLeave(TaskDetailInfoDTO taskDetailInfoDTO);
}
