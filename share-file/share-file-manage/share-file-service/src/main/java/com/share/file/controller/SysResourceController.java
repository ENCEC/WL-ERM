package com.share.file.controller;

import com.share.auth.api.ShareAuthInterface;
import com.share.auth.domain.QueryResourceDTO;
import com.share.auth.domain.SysResourceQueryVO;
import com.share.support.result.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 15100
 * @title: SysResourceCotroller
 * @projectName support
 * @description: 查询资源
 * @date 2020/12/89:02
 */
@RestController
@RequestMapping("/file")
public class SysResourceController {

    @Autowired
    ShareAuthInterface shareAuthInterface;

    /**
     * 查询资源
     * @param sysResourceQueryVO
     * @return
     */
    @PostMapping("/queryResource")
    public ResultHelper<List<QueryResourceDTO>> queryResource(@RequestBody SysResourceQueryVO sysResourceQueryVO) {
        return shareAuthInterface.queryResource(sysResourceQueryVO);
    }
}
