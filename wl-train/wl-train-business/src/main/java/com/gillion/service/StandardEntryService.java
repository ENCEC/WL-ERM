package com.gillion.service;


import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.entity.StandardEntry;
import com.gillion.train.api.model.vo.StandardEntryDTO;
import com.share.support.result.ResultHelper;

/**
 * @ClassName StandardEntryService
 * @Author weiq
 * @Date 2022/8/1 13:10
 * @Version 1.0
 **/
public interface StandardEntryService {
    /**
     * 查询条目
     * @param standardEntryDTO
     * @return
     */
    ResultHelper<Page<StandardEntryDTO>> queryStandardEntry(StandardEntryDTO standardEntryDTO);

    /**
     * 新增条目
     * @param standardEntryDTO
     * @return
     */
    ResultHelper<?> saveStandardEntry(StandardEntryDTO standardEntryDTO);

    /**
     * 查看一个条目信息
     * @param standardEntryId
     * @return
     */
    ResultHelper<?> queryByStandardEntryId(Long standardEntryId);

    /**
     * 启动/禁用
     * @param standardEntryDTO
     * @return
     */
    ResultHelper<?> updateStatus(StandardEntryDTO standardEntryDTO);

    /**
     * 删除条目
     * @param standardEntryId
     * @return
     */
    ResultHelper<?> deleteStandardEntry(Long standardEntryId);

    /**
     * 编辑条目
     * @param standardEntryDTO
     * @return
     */
    ResultHelper<?> updateStandardEntry(StandardEntryDTO standardEntryDTO);


}
