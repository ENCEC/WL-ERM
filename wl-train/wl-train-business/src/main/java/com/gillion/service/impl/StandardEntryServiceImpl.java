package com.gillion.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.entity.StandardEntry;
import com.gillion.model.querymodels.QStandardEntry;
import com.gillion.service.StandardEntryService;
import com.gillion.train.api.model.vo.StandardEntryDTO;
import com.google.common.collect.ImmutableMap;
import com.share.auth.api.UemUserInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName StandardEntryServiceImpl
 * @Author weiq
 * @Date 2022/8/1 13:10
 * @Version 1.0
 **/
@Service
public class StandardEntryServiceImpl implements StandardEntryService  {

    @Autowired
    private UemUserInterface uemUserInterface;

    /**
     * 查询条目
     * @param standardEntryDTO
     * @return
     */
    @Override
    public ResultHelper<Page<StandardEntryDTO>> queryStandardEntry(StandardEntryDTO standardEntryDTO) {
        Page<StandardEntryDTO> standardEntryDTOS = QStandardEntry.standardEntry.select()
                .where(QStandardEntry.entryName._like$_(standardEntryDTO.getEntryName())
                        .and(QStandardEntry.standardEntryId.goe$(1L))
                        .and(QStandardEntry.actionRoleId.eq$(standardEntryDTO.getActionRoleId())
                                .and(QStandardEntry.status.eq$(standardEntryDTO.getStatus()))
                                .and(QStandardEntry.applyPostId._like$_(standardEntryDTO.getApplyPostId()))))
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
        if (StrUtil.isEmpty(standardEntryDTO.getEntryName())) {
            return CommonResult.getFaildResultData("条目名称不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getApplyPostId())) {
            return CommonResult.getFaildResultData("适合岗位不能为空");
        }
        if (Objects.isNull(standardEntryDTO.getActionRoleId())) {
            return CommonResult.getFaildResultData("执行角色不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getApplyProfessorId())) {
            return CommonResult.getFaildResultData("岗位职称不能为空");
        }
        if (Objects.isNull(standardEntryDTO.getIsNeed())) {
            return CommonResult.getFaildResultData("是否必须不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getOrdinatorId())) {
            return CommonResult.getFaildResultData("统筹人不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getActionRemark())) {
            return CommonResult.getFaildResultData("执行说明不能为空");
        }
        List<StandardEntryDTO> standardEntryDTOS = QStandardEntry.standardEntry
                .select(QStandardEntry.standardEntry.fieldContainer())
                .where(QStandardEntry.entryName.eq$(standardEntryDTO.getEntryName()))
                .mapperTo(StandardEntryDTO.class)
                .execute();
        if (CollectionUtils.isNotEmpty(standardEntryDTOS)) {
            return CommonResult.getFaildResultData("该条目已经存在");
        }
        String [] ordinatorIds = standardEntryDTO.getOrdinatorId().split(",");
        StringBuilder ordinatorNamesBuilder = new StringBuilder();
        for (String ordinatorIdStr : ordinatorIds) {
            Long ordinatorId = Long.parseLong(ordinatorIdStr);
            ResultHelper<UemUserDto> ordinatorResult = uemUserInterface.getUemUser(ordinatorId);
            if (ordinatorResult.getSuccess() && ordinatorResult.getData() != null) {
                String ordinatorName = ordinatorResult.getData().getName();
                if (ordinatorName != null) {
                    ordinatorNamesBuilder.append(ordinatorName);
                    ordinatorNamesBuilder.append(",");
                }
            }
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
        standardEntry.setOrdinatorName(ordinatorNamesBuilder.toString());
        standardEntry.setActionRemark(standardEntryDTO.getActionRemark());
        Integer saveActionSerialNum = saveActionSerialNum(standardEntryDTO.getActionSerialNum());
        if (saveActionSerialNum == 0) {
            return  CommonResult.getFaildResultData("传入的执行序号要为正整数");
        }
        standardEntry.setActionSerialNum(saveActionSerialNum);
        standardEntry.setStatus(Boolean.FALSE);
        int save = QStandardEntry.standardEntry.save(standardEntry);
        if (save >0) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }

    /**
     * 查看一个条目信息
     * @param standardEntryId
     * @return
     */
    @Override
    public ResultHelper<?> queryByStandardEntryId(Long standardEntryId) {
        StandardEntryDTO standardEntry = QStandardEntry.standardEntry.selectOne()
                .where(QStandardEntry.standardEntryId.eq$(standardEntryId))
                .mapperTo(StandardEntryDTO.class)
                .execute();
        return CommonResult.getSuccessResultData(standardEntry);
    }

    /**
     * 启动/禁用
     * @param standardEntryDTO
     * @return
     */
    @Override
    public ResultHelper<?> updateStatus(StandardEntryDTO standardEntryDTO) {
        QStandardEntry.standardEntry.update(QStandardEntry.status)
                .where(QStandardEntry.standardEntryId.eq$(standardEntryDTO.getStandardEntryId()))
                .execute(ImmutableMap.of("status",standardEntryDTO.getStatus()));
        return CommonResult.getSuccessResultData("启动/禁用成功");
    }

    /**
     * 删除条目
     * @param standardEntryId
     * @return
     */
    @Override
    public ResultHelper<?> deleteStandardEntry(Long standardEntryId) {
        StandardEntryDTO standardEntryDTO = QStandardEntry.standardEntry.selectOne(QStandardEntry.actionSerialNum)
                .where(QStandardEntry.standardEntryId.eq$(standardEntryId))
                .mapperTo(StandardEntryDTO.class)
                .execute();
        //获取删除的执行序号
        Integer actionSerialNum = standardEntryDTO.getActionSerialNum();
        QStandardEntry.standardEntry.deleteById(standardEntryId);
        //获取剩余的全部条目
        List<StandardEntry> lists = QStandardEntry.standardEntry.select(QStandardEntry.actionSerialNum)
                .where(QStandardEntry.standardEntryId.goe$(1L))
                .execute();
        Integer[] strArray = new Integer[lists.size()];
        //将剩余的全部条目的执行序号放入数组中
        for (int i = 0; i <strArray.length ; i++) {
            strArray[i] = lists.get(i).getActionSerialNum();
        }
        //将比删除的执行序号的大的条目的执行序号减一
        List<StandardEntryDTO> standardEntryDTOS = new ArrayList<>();
        for (int i = 0; i < strArray.length; i++) {
            if (actionSerialNum < strArray[i]) {
                StandardEntryDTO standardEntry = QStandardEntry.standardEntry.selectOne(QStandardEntry.standardEntry.fieldContainer())
                        .where(QStandardEntry.actionSerialNum.eq$(strArray[i]))
                        .mapperTo(StandardEntryDTO.class)
                        .execute();
                standardEntryDTOS.add(standardEntry);
            }
        }
        standardEntryDTOS.forEach(x -> {x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED); x.setActionSerialNum(x.getActionSerialNum()-1);});
        QStandardEntry.standardEntry.save(standardEntryDTOS);
        return CommonResult.getSuccessResultData("删除成功");
    }

    /**
     * 编辑条目
     * @param standardEntryDTO
     * @return
     */
    @Override
    public ResultHelper<?> updateStandardEntry(StandardEntryDTO standardEntryDTO) {
        if (StrUtil.isEmpty(standardEntryDTO.getEntryName())) {
            return CommonResult.getFaildResultData("条目名称不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getApplyPostId())) {
            return CommonResult.getFaildResultData("适合岗位不能为空");
        }
        if (Objects.isNull(standardEntryDTO.getActionRoleId())) {
            return CommonResult.getFaildResultData("执行角色不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getApplyProfessorId())) {
            return CommonResult.getFaildResultData("岗位职称不能为空");
        }
        if (Objects.isNull(standardEntryDTO.getIsNeed())) {
            return CommonResult.getFaildResultData("是否必须不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getOrdinatorId())) {
            return CommonResult.getFaildResultData("统筹人不能为空");
        }
        if (StrUtil.isEmpty(standardEntryDTO.getActionRemark())) {
            return CommonResult.getFaildResultData("执行说明不能为空");
        }
        String [] ordinatorIds = standardEntryDTO.getOrdinatorId().split(",");
        StringBuilder ordinatorNamesBuilder = new StringBuilder();
        for (String ordinatorIdStr : ordinatorIds) {
            Long ordinatorId = Long.parseLong(ordinatorIdStr);
            ResultHelper<UemUserDto> ordinatorResult = uemUserInterface.getUemUser(ordinatorId);
            if (ordinatorResult.getSuccess() && ordinatorResult.getData() != null) {
                String ordinatorName = ordinatorResult.getData().getName();
                if (ordinatorName != null) {
                    ordinatorNamesBuilder.append(ordinatorName);
                    ordinatorNamesBuilder.append(",");
                }
            }
        }
        StandardEntryDTO standardEntryDto = QStandardEntry.standardEntry
                .selectOne(QStandardEntry.standardEntry.fieldContainer())
                .where(QStandardEntry.standardEntryId.eq$(standardEntryDTO.getStandardEntryId()))
                .mapperTo(StandardEntryDTO.class)
                .execute();
        standardEntryDto.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        standardEntryDto.setEntryName(standardEntryDTO.getEntryName());
        standardEntryDto.setItemType(standardEntryDTO.getItemType());
        standardEntryDto.setApplyPostId(standardEntryDTO.getApplyPostId());
        standardEntryDto.setActionRoleId(standardEntryDTO.getActionRoleId());
        standardEntryDto.setApplyProfessorId(standardEntryDTO.getApplyProfessorId());
        standardEntryDto.setActionTime(standardEntryDTO.getActionTime());
        standardEntryDto.setActionPeriod(standardEntryDTO.getActionPeriod());
        standardEntryDto.setIsNeed(standardEntryDTO.getIsNeed());
        standardEntryDto.setOrdinatorId(standardEntryDTO.getOrdinatorId());
        standardEntryDto.setOrdinatorName(ordinatorNamesBuilder.toString());
        standardEntryDto.setActionRemark(standardEntryDTO.getActionRemark());
        Integer actionSerialNum = updateActionSerialNum(standardEntryDTO);
        if (actionSerialNum == 0) {
            return  CommonResult.getFaildResultData("执行序号要为正整数");
        }
        standardEntryDto.setActionSerialNum(actionSerialNum);
        int save = QStandardEntry.standardEntry.save(standardEntryDto);
        if (save > 0) {
            return CommonResult.getSuccessResultData("编辑成功");
        } else {
            return CommonResult.getFaildResultData("编辑失败");
        }
    }


    /**
     * 新增时执行序号逻辑
     * @param actionSerialNum
     * @return
     */
    public Integer saveActionSerialNum(Integer actionSerialNum) {
        List<StandardEntry> lists = QStandardEntry.standardEntry.select(QStandardEntry.actionSerialNum)
                .where(QStandardEntry.standardEntryId.goe$(1L))
                .execute();
        Integer[] strArray = new Integer[lists.size()];
        for (int i = 0; i <strArray.length ; i++) {
            strArray[i] = lists.get(i).getActionSerialNum();
        }
        int maxActionSerialNum = 0;
        for (Integer integer : strArray) {
            if (integer > maxActionSerialNum) {
                maxActionSerialNum = integer;
            }
        }
        //如果不传值则为最大执行序号+1
        if (actionSerialNum == null) {
            return maxActionSerialNum+1;
        } else if ( actionSerialNum > maxActionSerialNum) {
        //如果传的值大于最大执行序号值，则自动变成最大执行序号+1
            return maxActionSerialNum+1;
        } else if (actionSerialNum<=0){
            return 0 ;
        } else {
         //如果传的值在1-最大执行序号之间
            List<StandardEntryDTO> standardEntryDTOS = new ArrayList<>();
            for (int i = 0; i < strArray.length; i++) {
                if (actionSerialNum <= strArray[i]) {
                    StandardEntryDTO standardEntry = QStandardEntry.standardEntry.selectOne(QStandardEntry.standardEntry.fieldContainer())
                            .where(QStandardEntry.actionSerialNum.eq$(strArray[i]))
                            .mapperTo(StandardEntryDTO.class)
                            .execute();
                    standardEntryDTOS.add(standardEntry);
                }
            }
            //将比传的值大于或者等于的执行序号加一
               standardEntryDTOS.forEach(x -> {x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED); x.setActionSerialNum(x.getActionSerialNum()+1);});
                 QStandardEntry.standardEntry.save(standardEntryDTOS);
            return actionSerialNum;
        }

    }

    /**
     * 编辑时执行序号逻辑
     * @param standardEntryDTO
     * @return
     */
    public Integer updateActionSerialNum(StandardEntryDTO standardEntryDTO) {
        StandardEntryDTO standardEntryDto = QStandardEntry.standardEntry.selectOne(QStandardEntry.standardEntry.fieldContainer())
                .where(QStandardEntry.standardEntryId.eq$(standardEntryDTO.getStandardEntryId()))
                .mapperTo(StandardEntryDTO.class)
                .execute();
        //获取更新前的执行序号
        Integer actionSerialNum = standardEntryDto.getActionSerialNum();
        //获取最大执行序号
        List<StandardEntry> lists = QStandardEntry.standardEntry.select(QStandardEntry.actionSerialNum)
                .where(QStandardEntry.standardEntryId.goe$(1L))
                .execute();
        Integer[] strArray = new Integer[lists.size()];
        for (int i = 0; i < strArray.length; i++) {
            strArray[i] = lists.get(i).getActionSerialNum();
        }
        int maxActionSerialNum = 0;
        for (Integer integer : strArray) {
            if (integer > maxActionSerialNum) {
                maxActionSerialNum = integer;
            }
        }
        //更新时执行序号传空值
        if (standardEntryDTO.getActionSerialNum() == null) {
            List<StandardEntryDTO> standardEntryDTOS = new ArrayList<>();
            for (int i = 0; i < strArray.length; i++) {
                if (actionSerialNum < strArray[i]) {
                    StandardEntryDTO standardEntry = QStandardEntry.standardEntry.selectOne(QStandardEntry.standardEntry.fieldContainer())
                            .where(QStandardEntry.actionSerialNum.eq$(strArray[i]))
                            .mapperTo(StandardEntryDTO.class)
                            .execute();
                    standardEntryDTOS.add(standardEntry);
                }
            }
            //将比传的值大于执行序号减一
            standardEntryDTOS.forEach(x -> {
                x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                x.setActionSerialNum(x.getActionSerialNum() - 1);
            });
            QStandardEntry.standardEntry.save(standardEntryDTOS);
            return maxActionSerialNum;
        }
        //如果更新的执行序号，比以前的小
        else if (standardEntryDTO.getActionSerialNum() < actionSerialNum && standardEntryDTO.getActionSerialNum() > 0) {
            List<StandardEntry> list = QStandardEntry.standardEntry.select(QStandardEntry.actionSerialNum)
                    .where(QStandardEntry.actionSerialNum.between(":actionSerialNum1", ":actionSerialNum2"))
                    .execute(ImmutableMap.of("actionSerialNum1", standardEntryDTO.getActionSerialNum(), "actionSerialNum2", actionSerialNum));
            Integer[] str = new Integer[list.size()];
            for (int i = 0; i < str.length; i++) {
                str[i] = list.get(i).getActionSerialNum();
            }
            List<StandardEntryDTO> standardEntryDTOS = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                StandardEntryDTO standardEntry = QStandardEntry.standardEntry.selectOne(QStandardEntry.standardEntry.fieldContainer())
                        .where(QStandardEntry.actionSerialNum.eq$(str[i]))
                        .mapperTo(StandardEntryDTO.class)
                        .execute();
                standardEntryDTOS.add(standardEntry);
            }
            //在集合中移除更新的数据
            for (int i = 0; i <standardEntryDTOS.size() ; i++) {
                if (standardEntryDTOS.get(i).getActionSerialNum() == actionSerialNum) {
                    standardEntryDTOS.remove(i);
                }
            }
            standardEntryDTOS.forEach(x -> {
                x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                x.setActionSerialNum(x.getActionSerialNum() + 1);
            });
            QStandardEntry.standardEntry.save(standardEntryDTOS);
            return standardEntryDTO.getActionSerialNum();
        }
        //如果更新执行序号比以前的大
        else if (standardEntryDTO.getActionSerialNum() > actionSerialNum) {
            List<StandardEntry> standardEntryList = QStandardEntry.standardEntry.select(QStandardEntry.actionSerialNum)
                    .where(QStandardEntry.actionSerialNum.between(":actionSerialNum1", ":actionSerialNum2"))
                    .execute(ImmutableMap.of("actionSerialNum1", actionSerialNum, "actionSerialNum2", standardEntryDTO.getActionSerialNum()));
            Integer[] arr = new Integer[standardEntryList.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = standardEntryList.get(i).getActionSerialNum();
            }
            List<StandardEntryDTO> standardEntryDTOS = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                StandardEntryDTO standardEntry = QStandardEntry.standardEntry.selectOne(QStandardEntry.standardEntry.fieldContainer())
                        .where(QStandardEntry.actionSerialNum.eq$(arr[i]))
                        .mapperTo(StandardEntryDTO.class)
                        .execute();
                standardEntryDTOS.add(standardEntry);
            }
            //在集合中移除更新的数据
            for (int i = 0; i <standardEntryDTOS.size() ; i++) {
                if (standardEntryDTOS.get(i).getActionSerialNum() == actionSerialNum) {
                    standardEntryDTOS.remove(i);
                }
            }
            //将比传的值大于或者等于的执行序号减一
            standardEntryDTOS.forEach(x -> {
                x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                x.setActionSerialNum(x.getActionSerialNum() - 1);
            });
            QStandardEntry.standardEntry.save(standardEntryDTOS);
            //如果更新的值比最大值大，则返回最大值，否则返回更新值
            if (standardEntryDTO.getActionSerialNum() >= maxActionSerialNum) {
                return maxActionSerialNum;
            } else {
                return standardEntryDTO.getActionSerialNum();
            }
        }
        //如果更新的执行序号一样大
        else if (standardEntryDTO.getActionSerialNum() == actionSerialNum) {
            return standardEntryDTO.getActionSerialNum();
        } else {
            return 0;
        }
    }
}
