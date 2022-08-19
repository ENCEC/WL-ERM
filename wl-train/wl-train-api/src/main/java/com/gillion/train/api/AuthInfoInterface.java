package com.gillion.train.api;

import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.gillion.train.api.model.vo.TaskInfoDto;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/taskInfo/queryPositiveInfo")
    List<TaskInfoDto> queryPositiveInfo(@RequestParam Long dispatchers);

    @GetMapping("/taskInfo/queryPositiveInfoByTaskId")
    @ApiOperation("员工管理查询员工转正信息")
    TaskDetailInfoDTO queryPositiveInfoByTaskId(@RequestParam Long taskInfoId);

}
