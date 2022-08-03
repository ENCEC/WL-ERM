package com.gillion.service.impl;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.entity.StandardEntry;
import com.gillion.model.querymodels.QStandardEntry;
import com.gillion.service.StandardEntryService;
import com.gillion.train.api.model.vo.StandardEntryDTO;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
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
    /**
     * 查询条目
     * @param standardEntryDTO
     * @return
     */
    @Override
    public ResultHelper<Page<StandardEntryDTO>> queryStandardEntry(StandardEntryDTO standardEntryDTO) {
        Page<StandardEntryDTO> standardEntryDTOS = QStandardEntry.standardEntry.select()
                .where(QStandardEntry.entryName.eq$(standardEntryDTO.getEntryName()).and(QStandardEntry.actionRoleId.eq$(standardEntryDTO.getActionRoleId()).and(QStandardEntry.applyPostId.eq$(standardEntryDTO.getApplyPostId()))))
                .paging(standardEntryDTO.getCurrentPage(),standardEntryDTO.getPageSize())
                .sorting(QStandardEntry.actionSerialNum)
                .mapperTo(StandardEntryDTO.class)
                .execute();
        return CommonResult.getSuccessResultData(standardEntryDTOS);
    }

    /**
     * 新增条目
     * @param standardEntryDTO
     * @return
     */
    @Override
    public ResultHelper<?> saveStandardEntry(StandardEntryDTO standardEntryDTO) {
        List<StandardEntry> standardEntrys = QStandardEntry.standardEntry.select(QStandardEntry.standardEntry.fieldContainer())
                .where(QStandardEntry.entryName.eq$(standardEntryDTO.getEntryName()))
                .execute();
        if (CollectionUtils.isNotEmpty(standardEntrys)) {
            return CommonResult.getFaildResultData("该条目已经存在");
        }
        StandardEntry standardEntry = new StandardEntry();
        standardEntry.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        standardEntry.setEntryName(standardEntryDTO.getEntryName());
        standardEntry.setItemType(standardEntryDTO.getItemType());
        standardEntry.setApplyPostId(standardEntryDTO.getApplyPostId());
        standardEntry.setActionRoleId(standardEntryDTO.getActionRoleId());
        standardEntry.setApplyProfessorId(standardEntryDTO.getApplyProfessorId());
        standardEntry.setActionTime(standardEntryDTO.getActionTime());
        standardEntry.setActionPeriod(standardEntryDTO.getActionPeriod());
        standardEntry.setIsNeed(standardEntryDTO.getIsNeed());
        standardEntry.setOrdinatorId(standardEntryDTO.getOrdinatorId());
        standardEntry.setActionRemark(standardEntryDTO.getActionRemark());
        standardEntry.setActionSerialNum(standardEntryDTO.getActionSerialNum());
        standardEntry.setCreateTime(new DateTime());
        standardEntry.setCreatorName("系统管理员");
        QStandardEntry.standardEntry.save(standardEntry);

        return CommonResult.getSuccessResultData("新增成功");
    }

    public void change(Integer actionSerialNum) {
        StandardEntry standardEntry = new StandardEntry();
        List<StandardEntry> list = QStandardEntry.standardEntry.select(QStandardEntry.actionSerialNum)
                .where(QStandardEntry.standardEntryId.goe$(1L))
                .execute();
//        Object[] array = list.toArray();
        Integer[] strArray = list.toArray(new Integer[list.size()]);
        for (int i = 0; i <strArray.length ; i++) {
            if (actionSerialNum <= strArray[i]) {
                StandardEntry entry = QStandardEntry.standardEntry.selectOne(QStandardEntry.standardEntry.fieldContainer())
                        .where(QStandardEntry.actionSerialNum.eq$(strArray[i]))
                        .execute();
                standardEntry.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                standardEntry.setStandardEntryId(entry.getStandardEntryId());
                standardEntry.setActionSerialNum(strArray[i]+1);
                QStandardEntry.standardEntry.save(standardEntry);
            }
        }
    }
}
