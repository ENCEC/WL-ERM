package com.gillion.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.service.StandardDetailService;
import com.gillion.train.api.model.vo.StandardDetailVO;
import com.share.auth.api.StandardEntryInterface;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanjp
 * @Date 2022/8/1 15:38
 */
@RestController
@RequestMapping("/standardDetail")
@Api(value = "规范细则控制器")
public class StandardDetailController {

    @Autowired
    private StandardEntryInterface standardEntryInterface;
    @Autowired
    private StandardDetailService standardDetailService;

    /**
     * 查询
     *
     * @param standardDetailVO 规范细则管理信息封装类
     * @return Page<standardDetailVO>
     * @author tanjp
     * @date 2022/8/1
     */
    @PostMapping("/queryStandardDetail")
    public ResultHelper<Page<StandardDetailVO>> selectStandardDetail(@RequestBody StandardDetailVO standardDetailVO) {
        return standardDetailService.selectStandardDetail(standardDetailVO);
    }

    /**
     *新增项目
     *
     * @param standardDetailVO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @PostMapping("/addStandardDetail")
    public ResultHelper<StandardDetailVO> addStandardDetailVO(@RequestBody StandardDetailVO standardDetailVO) {
        return standardDetailService.addStandardDetailVO(standardDetailVO);
    }

    /**
     *删除
     *
     * @param standardDetailId id
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @GetMapping("/deleteStandardDetail")
    public ResultHelper<?> deleteStandardDetailById(@RequestParam Long standardDetailId) {
        return standardDetailService.deleteStandardDetailById(standardDetailId);
    }

    /**
     * 启动禁止
     *
     * @param standardDetailVO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @PostMapping("standardDetailStartStop")
    public ResultHelper<?> standardDetailStartStop(@RequestBody StandardDetailVO standardDetailVO) {
        return standardDetailService.standardDetailStartStop(standardDetailVO);
    }

    /**
     *修改
     *
     * @param standardDetailVO
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @PostMapping("updateStandardDetail")
    public ResultHelper<?> updateStandardDetail(@RequestBody StandardDetailVO standardDetailVO) {
        return standardDetailService.updateStandardDetail(standardDetailVO);
    }

    /**
     *规范条目种类
     *
     * @param
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @GetMapping("selectEntryNameSpecies")
    public ResultHelper<?> entryNameSpecies() {
        return standardDetailService.entryNameSpecies();
    }

    /**
     *条目类型  种类
     *
     * @param
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @GetMapping("selectItemTypeSpecies")
    public ResultHelper<?> itemTypeSpecies() {
        return  standardDetailService.itemTypeSpecies();
    }
}
