package com.gillion.service.impl;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.entity.StandardEntry;
import com.gillion.model.querymodels.QStandardEntry;
import com.gillion.service.StandardEntryService;
import com.gillion.train.api.model.vo.StandardEntryDTO;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Page<StandardEntryDTO> standardEntryDTOS = QStandardEntry.standardEntry.select()
                .where(QStandardEntry.entryName.eq$(standardEntryDTO.getEntryName()).and(QStandardEntry.actionRoleId.eq$(standardEntryDTO.getActionRoleId()).and(QStandardEntry.applyPostId.eq$(standardEntryDTO.getApplyPostId()))))
                .mapperTo(StandardEntryDTO.class)
                .paging(standardEntryDTO.getCurrentPage(),standardEntryDTO.getPageSize())
                .execute();
        return CommonResult.getSuccessResultData(standardEntryDTOS);
    }

    @Override
    public ResultHelper<?> saveStandardEntry(StandardEntryDTO standardEntryDTO) {
        StandardEntry standardEntry = new StandardEntry();
        standardEntry.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        standardEntry.setEntryName(standardEntryDTO.getEntryName());
        standardEntry.setApplyPostId(standardEntryDTO.getApplyPostId());
        standardEntry.setActionRoleId(standardEntryDTO.getActionRoleId());
        standardEntry.setApplyProfessorId(standardEntryDTO.getApplyProfessorId());
        standardEntry.setActionTime(standardEntryDTO.getActionTime());
        standardEntry.setActionPeriod(standardEntryDTO.getActionPeriod());
        standardEntry.setIsNeed(standardEntryDTO.getIsNeed());
        standardEntry.setOrdinatorId(standardEntryDTO.getOrdinatorId());
        standardEntry.setActionRemark(standardEntryDTO.getActionRemark());
        standardEntry.setActionSerialNum(standardEntryDTO.getActionSerialNum());
        QStandardEntry.standardEntry.save(standardEntry);

        return CommonResult.getSuccessResultData();
    }
}
