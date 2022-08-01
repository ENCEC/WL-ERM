package com.gillion.service.impl;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.model.entity.StandardEntry;
import com.gillion.model.querymodels.QStandardEntry;
import com.gillion.service.StandardEntryService;
import com.gillion.train.api.model.vo.StandardEntryDTO;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.springframework.stereotype.Service;

/**
 * @ClassName StandardEntryServiceImpl
 * @Author weiq
 * @Date 2022/8/1 13:10
 * @Version 1.0
 **/
@Service
public class StandardEntryServiceImpl implements StandardEntryService {
    @Override
    public ResultHelper<Page<StandardEntryDTO>> queryStandardEntry(StandardEntryDTO standardEntryDTO) {
//        Page<StandardEntryDTO> pages = QStandardEntry.standardEntry.select()
//                .where(QStandardEntry.entryName.eq$(standardEntryDTO.ge))
//                .mapperTo(StandardEntryDTO.class);
        return CommonResult.getSuccessResultData();
    }
}
