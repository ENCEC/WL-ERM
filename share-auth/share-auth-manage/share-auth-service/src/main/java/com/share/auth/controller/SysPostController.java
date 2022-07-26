package com.share.auth.controller;

import com.gillion.ec.core.utils.ResultUtils;
import com.share.auth.model.entity.SysPost;
import com.share.auth.service.SysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

/**
 * @author tanjp
 * @Date 2022/7/26 11:46
 */
@RestController
@RequestMapping("SysPost")
public class SysPostController {
    @Autowired
    private SysPostService sysPostService;

    @PostMapping("addSysPost")
    public Map<String, Object> addSysPost(@RequestBody SysPost sysPost){
        sysPostService.addSysPost(sysPost);
        return ResultUtils.getSuccessResultData(true);
    }
    @GetMapping("Page/{page}/{size}")
    public Map<String, Object>  findPage(@PathVariable int page, @PathVariable int size){
        sysPostService.findPage(page, size);
        return ResultUtils.getSuccessResultData(true);
    }
    @GetMapping("Page1/{page}/{size}")
    public Map<String, Object> findPage(@RequestBody SysPost sysPost,@PathVariable int page, @PathVariable int size){
        sysPostService.findPage(sysPost,page,size);
        return ResultUtils.getSuccessResultData(true);
    }
    @GetMapping("Page1/{page}/{size}/{status}")
    public Map<String, Object> findPage(@RequestBody SysPost sysPost,@PathVariable int page, @PathVariable int size, @PathVariable String status){
        sysPostService.findPage(sysPost,page,size,status);
        return ResultUtils.getSuccessResultData(true);
    }
    @PutMapping("updateSysPost")
    public Map<String, Object> updateSysPost(@RequestBody SysPost sysPost) {
        sysPostService.updateSysPost(sysPost);
        return ResultUtils.getSuccessResultData(true);
    }
    @PutMapping("Prohibit")
    public Map<String, Object> Prohibit(@RequestBody SysPost sysPost){
        sysPostService.Prohibit(sysPost);
        return ResultUtils.getSuccessResultData(true);
    }
    @DeleteMapping("deleteSysPost/{id}")
    public Map<String, Object> deleteEmployeeId(@PathVariable Long id){
        sysPostService.deleteEmployeeId(id);
        return ResultUtils.getSuccessResultData(true);
    }
}
