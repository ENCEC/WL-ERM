package com.gillion.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.entity.StandardEntry;
import com.gillion.train.api.model.vo.StandardDetailVO;
import com.share.support.result.ResultHelper;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tanjp
 * @Date 2022/8/1 13:19
 */
public interface StandardDetailService {

    /**
     * 查询
     *
     * @param standardDetailVO 规范细则管理信息封装类
     * @return Page<standardDetailVO>
     * @author tanjp
     * @date 2022/8/1
     */
    ResultHelper<Page<StandardDetailVO>> selectStandardDetail(StandardDetailVO standardDetailVO);

    /**
     *新增项目
     *
     * @param standardDetailVO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    ResultHelper<StandardDetailVO> addStandardDetailVO(StandardDetailVO standardDetailVO);


    /**
     *删除
     *
     * @param standardDetailId id
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    ResultHelper<?> deleteStandardDetailById(Long standardDetailId);


    /**
     * 启动禁止
     *
     * @param standardDetailVO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    ResultHelper<?> standardDetailStartStop(StandardDetailVO standardDetailVO);

    /**
     *修改
     *
     * @param standardDetailVO
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    ResultHelper<?> updateStandardDetail(StandardDetailVO standardDetailVO);

    /**
     *规范条目种类
     *
     * @param
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    ResultHelper<?> entryNameSpecies();

    /**
     *条目类型  种类
     *
     * @param
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    ResultHelper<?> itemTypeSpecies();
}
