package com.gillion.service.impl;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.entity.StandardDetail;
import com.gillion.model.entity.StandardEntry;
import com.gillion.model.querymodels.QStandardDetail;
import com.gillion.model.querymodels.QStandardEntry;
import com.gillion.service.StandardDetailService;
import com.gillion.train.api.model.vo.StandardDetailVO;
import com.gillion.train.api.model.vo.StandardEntryDTO;
import com.google.common.collect.ImmutableMap;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author tanjp
 * @Date 2022/8/1 13:20
 */
@Service
public class StandardDetailServiceImpl implements StandardDetailService {

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
    @Override
    public ResultHelper<Page<StandardDetailVO>> selectStandardDetail(StandardDetailVO standardDetailVO) {

        //currentPage:当前页     pageSize:每页显示的条数
        int currentPage = (standardDetailVO.getCurrentPage() == null) ? 1 : standardDetailVO.getCurrentPage();
        int pageSize = (standardDetailVO.getPageSize() == null) ? 20 : standardDetailVO.getPageSize();

        //细则名称
        if (!StringUtils.isEmpty(standardDetailVO.getDetailName())) {
            standardDetailVO.setDetailName("%" + standardDetailVO.getDetailName() + "%");
        }
        //条目名称
        if (!StringUtils.isEmpty(standardDetailVO.getEntryName())) {
            standardDetailVO.setEntryName("%" + standardDetailVO.getEntryName() + "%");
        }
        //条目类型
        if (!StringUtils.isEmpty(standardDetailVO.getItemType())) {
            standardDetailVO.setItemType("%" + standardDetailVO.getItemType() + "%");
        }

        Page<StandardDetailVO> standardDetailVOPage = DSContext
                .customization("select_StandardDetail")
                .select()
                .paging(currentPage, pageSize)
                .mapperTo(StandardDetailVO.class)
                .execute(standardDetailVO);

        return CommonResult.getSuccessResultData(standardDetailVOPage);
    }

    /**
     * 新增项目
     *
     * @param standardDetailVO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<StandardDetailVO> addStandardDetailVO(StandardDetailVO standardDetailVO) {

        //查询
        StandardEntry execute = QStandardEntry.standardEntry
                .selectOne(QStandardEntry.standardEntry.fieldContainer())
                .where(QStandardEntry.itemType.eq$(standardDetailVO.getItemType())
                        .and(QStandardEntry.entryName.eq$(standardDetailVO.getEntryName())))
                .execute();

        standardDetailVO.setStandardEntryId(execute.getStandardEntryId());

//        //获取执行序号
//        int actionSerialNum = standardDetailVO.getActionSerialNum();
//        //获取最大的执行序号
//        Integer max_actionSerialNum = this.maxActionSerialNum();
//        //判断执行序号是否为null ，赋值1
//        if (Objects.isNull(actionSerialNum) && actionSerialNum <= 0) {
//            actionSerialNum = 1;
//        }
//        //新增的执行序号<= 最大的执行序号
//        if (actionSerialNum <= max_actionSerialNum) {
//            List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
//                    .select(QStandardDetail.standardDetail.fieldContainer())
//                    .where(QStandardDetail.actionSerialNum.goe$(actionSerialNum))
//                    .execute();
//            for (int i = 0; i < standardDetailList.size(); i++) {
//                StandardDetail standardDetail1 = standardDetailList.get(i);
//                standardDetail1.setActionSerialNum(standardDetailList.get(i).getActionSerialNum() + 1);
//                standardDetail1.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//                QStandardDetail.standardDetail.save(standardDetail1);
//            }
//        }
//        //新增的执行序号大于最大的执行序号，新增执行序号=max+1
//        if (actionSerialNum > max_actionSerialNum) {
//            actionSerialNum = max_actionSerialNum + 1;
//        }

        if (Objects.isNull(standardDetailVO.getStatus())) {
            standardDetailVO.setStatus(true);
        }
        standardDetailVO.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        Integer actionSerialNum = saveActionSerialNum(standardDetailVO.getActionSerialNum());
        if (actionSerialNum == 0) {
            return  CommonResult.getFaildResultData("传入的执行序号要为正整数");
        }
        standardDetailVO.setActionSerialNum(actionSerialNum);


        //测试数据   后续调用父表的新增接口

//        StandardEntryDTO standardEntryDTO = new StandardEntryDTO();

//        StandardEntry standardEntry = new StandardEntry();
//        standardEntry.setCreateTime(standardDetailVO.getCreateTime());
//        standardEntry.setEntryName(standardDetailVO.getEntryName());
//        standardEntry.setItemType(standardDetailVO.getItemType());
//        standardEntry.setActionSerialNum(standardDetailVO.getActionSerialNum());
//        standardEntry.setStatus(standardDetailVO.getStatus());
//        standardEntry.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);

//        StandardEntryServiceImpl standardEntryService = new StandardEntryServiceImpl();
//        standardEntryService.saveStandardEntry(standardEntryDTO);

//        QStandardEntry.standardEntry.save(standardEntry);
//
//
//        standardDetailVO.setStandardEntryId(standardEntry.getStandardEntryId());
        int rowStatus = QStandardDetail.standardDetail.save(standardDetailVO);


        if (rowStatus > 0) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }

    /**
     * 删除
     *
     * @param standardDetailId id
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<?> deleteStandardDetailById(Long standardDetailId) {
        //获取用户
        if (Objects.isNull(standardDetailId)) {
            return CommonResult.getFaildResultData("id不存在");
        }
        StandardDetail standardDetail = this.getStandardDetailById(standardDetailId);
        if (Objects.isNull(standardDetail)) {
            return CommonResult.getFaildResultData("信息不存在");
        }


//        //根据standardDetailId 找到 standardEntryId
//        Long standardEntryId = standardDetail.getStandardEntryId();
        //序列号
        Integer serialNum = standardDetail.getActionSerialNum();

        //获取最大的执行序号
        Integer max_actionSerialNum = this.maxActionSerialNum();

        if (serialNum < max_actionSerialNum) {
            //找出当前比actionSerialNum 大的actionSerialNum
            List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
                    .select(QStandardDetail.standardDetail.fieldContainer())
                    .where(QStandardDetail.actionSerialNum.goe$(serialNum))
                    .execute();

            //后面的数据递减
            for (int i = 0; i < standardDetailList.size(); i++) {
                StandardDetail standardDetail1 = standardDetailList.get(i);
                standardDetail1.setActionSerialNum(standardDetailList.get(i).getActionSerialNum() - 1);
                standardDetail1.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                QStandardDetail.standardDetail.save(standardDetail1);
            }
        }


//        //调用父类删除方法
//        StandardEntryServiceImpl standardEntryService = new StandardEntryServiceImpl();
//        standardEntryService.deleteStandardEntry(standardEntryId);


        standardDetail.setRowStatus(RowStatusConstants.ROW_STATUS_DELETED);
        int row = QStandardDetail.standardDetail.deleteById(standardDetailId);
        if (row > 0) {
            return CommonResult.getSuccessResultData("删除成功");
        } else {
            return CommonResult.getFaildResultData("删除失败");
        }
    }

    /**
     * 启动禁止
     *
     * @param standardDetailVO 岗位信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<?> standardDetailStartStop(StandardDetailVO standardDetailVO) {
        //id
        Long standardDetailId = standardDetailVO.getStandardDetailId();
        //(0禁用,1启)
        Boolean status = standardDetailVO.getStatus();
        //检查岗位是否存在
        if (Objects.isNull(standardDetailId)) {
            return CommonResult.getFaildResultData("ID不能为空");
        }
        StandardDetail standardDetail = this.getStandardDetailById(standardDetailId);
        if (Objects.isNull(standardDetail)) {
            return CommonResult.getFaildResultData("信息不存在");
        }
        standardDetail.setStandardDetailId(standardDetailId);
        standardDetail.setStatus(status);
        standardDetail.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int updateStatus = QStandardDetail.standardDetail.selective(QStandardDetail.status).execute(standardDetail);
        //判断是否成功
        if (updateStatus > 0) {
            return CommonResult.getSuccessResultData("启停成功");
        } else {
            return CommonResult.getFaildResultData("启停失败");
        }
    }

    /**
     * 修改
     *
     * @param standardDetailVO
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<?> updateStandardDetail(StandardDetailVO standardDetailVO) {
        //根据主键查询岗位信息
        Long standardDetailId = standardDetailVO.getStandardDetailId();
        StandardDetail standardDetail = this.getStandardDetailById(standardDetailId);
        if (Objects.isNull(standardDetailId)) {
            return CommonResult.getFaildResultData("id不允许为空");
        }
        if (Objects.isNull(standardDetail.getStandardDetailId())) {
            return CommonResult.getFaildResultData("id不存在");
        }
        //查询
        StandardEntry execute = QStandardEntry.standardEntry
                .selectOne(QStandardEntry.standardEntry.fieldContainer())
                .where(QStandardEntry.itemType.eq$(standardDetailVO.getItemType())
                        .and(QStandardEntry.entryName.eq$(standardDetailVO.getEntryName())))
                .execute();

        standardDetail.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        standardDetailVO.setStandardEntryId(execute.getStandardEntryId());
        Integer actionSerialNum = updateActionSerialNum(standardDetailVO);
        if (actionSerialNum == 0) {
            return  CommonResult.getFaildResultData("执行序号要为正整数");
        }
        standardDetail.setActionSerialNum(actionSerialNum);
        standardDetail.setDetailName(standardDetailVO.getDetailName());
        int count = QStandardDetail.standardDetail.save(standardDetail);
        if (count > 1) {
            return CommonResult.getSuccessResultData("更新成功");
        } else {
            return CommonResult.getFaildResultData("更新失败");
        }


//        //判断是否输入的执行序号
//        int actionSerialNum = 0;
//        if (Objects.isNull(standardDetailVO.getActionSerialNum())) {
//            actionSerialNum = standardDetail.getActionSerialNum();
//        } else {
//            actionSerialNum = standardDetailVO.getActionSerialNum();
//        }
//
//        //原先的执行序号
//        int actionSerial = standardDetail.getActionSerialNum();
//        //获取最大的执行序号
//        Integer max_actionSerialNum = this.maxActionSerialNum();
//        //赋值1
//        if (actionSerialNum <= 0) {
//            actionSerialNum = 1;
//        }
//        //新增的执行序号<= 最大的执行序号
//        if (actionSerialNum <= max_actionSerialNum) {
//            //前移
//            if (actionSerialNum < standardDetail.getActionSerialNum()) {
//                //前移位置(actionSerialNum) 到  (actionSerial-1)  往后移动一格
//                List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
//                        .select(QStandardDetail.standardDetail.fieldContainer())
//                        .where(QStandardDetail.actionSerialNum.between$(actionSerialNum, actionSerial - 1))
//                        .execute();
//                for (int i = 0; i < standardDetailList.size(); i++) {
//                    StandardDetail standardDetail1 = standardDetailList.get(i);
//                    standardDetail1.setActionSerialNum(standardDetailList.get(i).getActionSerialNum() + 1);
//                    standardDetail1.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//                    QStandardDetail.standardDetail.save(standardDetail1);
//                }
//            } else {
//                List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
//                        .select(QStandardDetail.standardDetail.fieldContainer())
//                        .where(QStandardDetail.actionSerialNum.between$(actionSerial + 1, actionSerialNum))
//                        .execute();
//                for (int i = 0; i < standardDetailList.size(); i++) {
//                    StandardDetail standardDetail1 = standardDetailList.get(i);
//                    standardDetail1.setActionSerialNum(standardDetailList.get(i).getActionSerialNum() - 1);
//                    standardDetail1.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//                    QStandardDetail.standardDetail.save(standardDetail1);
//                }
//            }
//
//        }
//        //输入的执行序号大于最大的执行序号，新增执行序号=max+1
//        if (actionSerialNum > max_actionSerialNum) {
//            actionSerialNum = max_actionSerialNum + 1;
//        }
//
//        standardDetail.setActionSerialNum(actionSerialNum);
////        BeanUtils.copyProperties(standardDetail, standardDetailVO);
//
//
//        //先改父表后改子表
//        StandardEntryDTO standardEntryDTO = new StandardEntryDTO();
//        //父表id
//        standardEntryDTO.setStandardEntryId(standardDetail.getStandardEntryId());
//        //条目类型
//        standardEntryDTO.setItemType(standardDetailVO.getItemType());
//        //规范条目
//        standardEntryDTO.setEntryName(standardDetailVO.getEntryName());
//        standardEntryDTO.setActionSerialNum(actionSerialNum);
//        standardEntryDTO.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//
//        StandardEntryServiceImpl standardEntryService = new StandardEntryServiceImpl();
//        //调用父类编辑排序的方法
//        Integer actionSerialNumEntry = standardEntryService.updateActionSerialNum(standardEntryDTO);
//        if (actionSerialNumEntry == 0) {
//            return CommonResult.getFaildResultData("执行序号要为正整数");
//        }
//        standardEntryDTO.setActionSerialNum(actionSerialNumEntry);
//
//        int action = QStandardEntry.standardEntry.save(standardEntryDTO);
//        if (action > 0) {
//            standardDetail.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//            //更新时间
//            standardDetail.setModifyTime(new DateTime());
//            standardDetail.setStatus(standardDetailVO.getStatus());
//            standardDetail.setDetailName(standardDetailVO.getDetailName());
//            int row = QStandardDetail.standardDetail.save(standardDetail);
//
//            if (row > 0) {
//                return CommonResult.getSuccessResultData("子表修改成功");
//            } else {
//                return CommonResult.getFaildResultData("子表修改失败");
//            }
//        }
//        return CommonResult.getSuccessResultData("修改成功");
    }


    /**
     * 规范条目种类
     *
     * @param
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<?> entryNameSpecies() {

        List<StandardEntry> standardEntryList = DSContext
                .customization("selectEntryNameSpecies")
                .select()
                .mapperTo(StandardEntry.class)
                .execute();
        List list = new ArrayList();
        for (int i = 0; i < standardEntryList.size(); i++) {
            StandardEntry standardEntry = standardEntryList.get(i);
            list.add(standardEntry.getEntryName());
        }
        return CommonResult.getSuccessResultData(list);
    }

    /**
     * 条目类型  种类
     *
     * @param
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<?> itemTypeSpecies() {
        List<StandardEntry> standardEntryList = DSContext
                .customization("selectItemTypeSpecies")
                .select()
                .mapperTo(StandardEntry.class)
                .execute();
        List list = new ArrayList();
        for (int i = 0; i < standardEntryList.size(); i++) {
            StandardEntry standardEntry = standardEntryList.get(i);
            list.add(standardEntry.getItemType());
        }
        return CommonResult.getSuccessResultData(list);
    }

    /**
     * 查找id
     *
     * @param standardDetailId
     * @return
     * @author tanjp
     * @date 2022/8/2
     */
    public StandardDetail getStandardDetailById(Long standardDetailId) {
        List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
                .select(QStandardDetail.standardDetail.fieldContainer())
                .where(QStandardDetail.standardDetailId.eq$(standardDetailId))
                .execute();
        if (standardDetailList.size() == 1) {
            return standardDetailList.get(0);
        } else {
            return null;
        }
    }


    /**
     * 最大执行序号
     *
     * @param
     * @return Integer
     * @author tanjp
     * @date 2022/8/3
     */

    private Integer maxActionSerialNum() {
        //获取最大的执行序号
        Integer max_actionSerialNum = DSContext
                .customization("selectMax_actionSerialNum")
                .selectValue()
                .mapperTo(Integer.class)
                .execute();
        return max_actionSerialNum;
    }

    /**
     * 新增时执行序号逻辑
     * @param actionSerialNum
     * @return
     */
    public Integer saveActionSerialNum(Integer actionSerialNum) {
        List<StandardDetail> lists = QStandardDetail.standardDetail.select(QStandardDetail.actionSerialNum)
                .where(QStandardDetail.standardDetailId.goe$(1L))
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
            List<StandardDetailVO> standardDetailVOS = new ArrayList<>();
            for (int i = 0; i < strArray.length; i++) {
                if (actionSerialNum <= strArray[i]) {
                    StandardDetailVO standardDetail = QStandardDetail.standardDetail.selectOne(QStandardDetail.standardDetail.fieldContainer())
                            .where(QStandardDetail.actionSerialNum.eq$(strArray[i]))
                            .mapperTo(StandardDetailVO.class)
                            .execute();
                    standardDetailVOS.add(standardDetail);
                }
            }
            //将比传的值大于或者等于的执行序号加一
            standardDetailVOS.forEach(x -> {x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED); x.setActionSerialNum(x.getActionSerialNum()+1);});
            QStandardDetail.standardDetail.save(standardDetailVOS);
            return actionSerialNum;
        }

    }

    /**
     * 编辑时执行序号逻辑
     * @param standardDetailVO
     * @return
     */
    public Integer updateActionSerialNum(StandardDetailVO standardDetailVO) {
        StandardDetailVO standardDetailVo = QStandardDetail.standardDetail.selectOne(QStandardDetail.standardDetail.fieldContainer())
                .where(QStandardDetail.standardDetailId.eq$(standardDetailVO.getStandardDetailId()))
                .mapperTo(StandardDetailVO.class)
                .execute();
        //获取更新前的执行序号
        Integer actionSerialNum = standardDetailVo.getActionSerialNum();
        //更新时执行序号传空值
        if (standardDetailVO.getActionSerialNum() == null) {
            List<StandardDetail> lists = QStandardDetail.standardDetail.select(QStandardDetail.actionSerialNum)
                    .where(QStandardDetail.standardDetailId.goe$(1L))
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

            List<StandardDetailVO> standardDetailVOS = new ArrayList<>();
            for (int i = 0; i < strArray.length; i++) {
                if (actionSerialNum < strArray[i]) {
                    StandardDetailVO standardDetail = QStandardDetail.standardDetail.selectOne(QStandardDetail.standardDetail.fieldContainer())
                            .where(QStandardDetail.actionSerialNum.eq$(strArray[i]))
                            .mapperTo(StandardDetailVO.class)
                            .execute();
                    standardDetailVOS.add(standardDetail);
                }
            }
            //将比传的值大于执行序号减一
//            standardDetailVOS.forEach(x -> {
//                x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//                x.setActionSerialNum(x.getActionSerialNum() - 1);
//            });
//            QStandardDetail.standardDetail.save(standardDetailVOS);
            for (int i = 0; i < standardDetailVOS.size() ; i++) {
                standardDetailVOS.get(i).setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                standardDetailVOS.get(i).setActionSerialNum(standardDetailVOS.get(i).getActionSerialNum()-1);
                QStandardDetail.standardDetail.save(standardDetailVOS.get(i));
            }
            return maxActionSerialNum;
        }
        //如果更新的执行序号，比以前的小
        else if (standardDetailVO.getActionSerialNum() < actionSerialNum && standardDetailVO.getActionSerialNum() > 0) {
            List<StandardDetail> lists = QStandardDetail.standardDetail.select(QStandardDetail.actionSerialNum)
                    .where(QStandardDetail.actionSerialNum.between(":actionSerialNum1", ":actionSerialNum2"))
                    .execute(ImmutableMap.of("actionSerialNum1", standardDetailVO.getActionSerialNum(), "actionSerialNum2", actionSerialNum));
            Integer[] strArray = new Integer[lists.size()];
            for (int i = 0; i < strArray.length; i++) {
                strArray[i] = lists.get(i).getActionSerialNum();
            }
            List<StandardDetailVO> standardDetailVOS = new ArrayList<>();
            for (int i = 0; i < strArray.length; i++) {
                StandardDetailVO standardDetail = QStandardDetail.standardDetail.selectOne(QStandardDetail.standardDetail.fieldContainer())
                        .where(QStandardDetail.actionSerialNum.eq$(strArray[i]))
                        .mapperTo(StandardDetailVO.class)
                        .execute();
                standardDetailVOS.add(standardDetail);
            }
//            standardDetailVOS.forEach(x -> {
//                x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//                x.setActionSerialNum(x.getActionSerialNum() + 1);
//            });
//            QStandardDetail.standardDetail.save(standardDetailVOS);
            for (int i = 0; i < standardDetailVOS.size() ; i++) {
                standardDetailVOS.get(i).setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                standardDetailVOS.get(i).setActionSerialNum(standardDetailVOS.get(i).getActionSerialNum()+1);
                QStandardDetail.standardDetail.save(standardDetailVOS.get(i));
            }
            return standardDetailVO.getActionSerialNum();
        }
        //如果更新执行序号比以前的大
        else if (standardDetailVO.getActionSerialNum() > actionSerialNum) {
            List<StandardDetail> lists = QStandardDetail.standardDetail.select(QStandardDetail.actionSerialNum)
                    .where(QStandardDetail.actionSerialNum.between(":actionSerialNum1", ":actionSerialNum2"))
                    .execute(ImmutableMap.of("actionSerialNum1", actionSerialNum, "actionSerialNum2", standardDetailVO.getActionSerialNum()));
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

            List<StandardDetailVO> standardDetailVOS = new ArrayList<>();
            for (int i = 0; i < strArray.length; i++) {
                StandardDetailVO standardDetail = QStandardDetail.standardDetail.selectOne(QStandardDetail.standardDetail.fieldContainer())
                        .where(QStandardDetail.actionSerialNum.eq$(strArray[i]))
                        .mapperTo(StandardDetailVO.class)
                        .execute();
                standardDetailVOS.add(standardDetail);
            }
            //将比传的值大于或者等于的执行序号减一
//            standardDetailVOS.forEach(x -> {
//                x.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
//                x.setActionSerialNum(x.getActionSerialNum() - 1);
//            });
//            QStandardDetail.standardDetail.save(standardDetailVOS);
            for (int i = 0; i < standardDetailVOS.size() ; i++) {
                standardDetailVOS.get(i).setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                standardDetailVOS.get(i).setActionSerialNum(standardDetailVOS.get(i).getActionSerialNum()-1);
                QStandardDetail.standardDetail.save(standardDetailVOS.get(i));
            }
            //如果更新的值比最大值大，则返回最大值，否则返回更新值
            if (standardDetailVO.getActionSerialNum() >= maxActionSerialNum) {
                return maxActionSerialNum;
            } else {
                return standardDetailVO.getActionSerialNum();
            }
        }
        //如果更新的执行序号一样大
        else if (standardDetailVO.getActionSerialNum() == actionSerialNum) {
            return standardDetailVO.getActionSerialNum();
        } else {
            return 0;
        }
    }
}


