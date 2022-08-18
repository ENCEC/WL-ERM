package com.gillion.train.api;

import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wzr
 * @date 2021/8/12
 * @description: 员工模块服务调用
 */
@FeignClient(value = "${application.name.train}")
public interface AuthInfoInterface {
    /**
     * 添加转正信息
     *
     * @param taskDetailInfoDto
     * @return
     */
    @PostMapping("/taskInfo/savePositiveInfoByStaff")
    ResultHelper<Object> savePositiveInfoByStaff(@RequestBody TaskDetailInfoDTO taskDetailInfoDto);

    /**
     * 查看转正信息
     *
     * @param
     * @return
     */
    @GetMapping("/taskInfo/queryPositiveApply")
    ResultHelper<Object> queryPositiveApply(@RequestParam Long dispatchers);

}
