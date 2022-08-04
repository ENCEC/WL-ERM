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
import com.share.support.result.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import com.share.support.result.ResultHelper;
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
public class StandardDetailServiceImpl implements StandardDetailService  {

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
                .paging(currentPage,pageSize)
                .mapperTo(StandardDetailVO.class)
                .execute(standardDetailVO);

        return CommonResult.getSuccessResultData(standardDetailVOPage);
    }

    /**
     *新增项目
     *
     * @param standardDetailVO 部门项目信息封装类
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<StandardDetailVO> addStandardDetailVO(StandardDetailVO standardDetailVO) {

        //获取执行序号
        int actionSerialNum = standardDetailVO.getActionSerialNum();
        //获取最大的执行序号
        Integer max_actionSerialNum = this.maxActionSerialNum();
        //判断执行序号是否为null ，赋值1
        if (Objects.isNull(actionSerialNum)) {
            actionSerialNum = 1;
            standardDetailVO.setActionSerialNum(actionSerialNum);
        }
        //新增的执行序号<= 最大的执行序号
        if (actionSerialNum <= max_actionSerialNum) {
            List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
                    .select(QStandardDetail.actionSerialNum)
                    .where(QStandardDetail.actionSerialNum.goe$(max_actionSerialNum))
                    .execute();
            for (int i = 0;i < standardDetailList.size();i++) {
                StandardDetail standardDetail1=standardDetailList.get(i);
                standardDetail1.setActionSerialNum(standardDetailList.get(i).getActionSerialNum()+1);
                standardDetail1.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                QStandardDetail.standardDetail.save(standardDetail1);
            }
        }
        //新增的执行序号大于最大的执行序号，新增执行序号=max+1
        if (actionSerialNum > max_actionSerialNum) {
            actionSerialNum = max_actionSerialNum+1;
            standardDetailVO.setActionSerialNum(actionSerialNum);
        }
        standardDetailVO.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        standardDetailVO.setCreateTime(new DateTime());


        //测试数据   后续调用父表的方面
        StandardEntry standardEntry = new StandardEntry();
        standardEntry.setCreateTime(standardDetailVO.getCreateTime());
        standardEntry.setEntryName(standardDetailVO.getEntryName());
        standardEntry.setItemType(standardDetailVO.getItemType());
        standardEntry.setActionSerialNum(standardDetailVO.getActionSerialNum());
        standardEntry.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        QStandardEntry.standardEntry.save(standardEntry);

        standardDetailVO.setStandardEntryId(standardEntry.getStandardEntryId());
        int rowStatus = QStandardDetail.standardDetail.save(standardDetailVO);


        if (rowStatus > 0) {
            return CommonResult.getSuccessResultData("新增成功");
        } else {
            return CommonResult.getFaildResultData("新增失败");
        }
    }

    /**
     *删除
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
            return CommonResult.getFaildResultData("岗位信息不存在");
        }


       //根据standardDetailId 找到 actionSerialNum
        int count = QStandardDetail.standardDetail
                .selectCount()
                .where(QStandardDetail.standardDetailId.eq$(standardDetailId))
                .execute();

        //找出当前比actionSerialNum 大的actionSerialNum
        List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
                .select(QStandardDetail.actionSerialNum)
                .where(QStandardDetail.actionSerialNum.goe$(count))
                .execute();

        //后面的数据递减
        for (int i = 0;i < standardDetailList.size();i++) {
            StandardDetail standardDetail1=standardDetailList.get(i);
            standardDetail1.setActionSerialNum(standardDetailList.get(i).getActionSerialNum()-1);
            standardDetail1.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            QStandardDetail.standardDetail.save(standardDetail1);
        }


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
        Long standardEntryId = standardDetailVO.getStandardEntryId();
        //(0禁用,1启)
        Boolean status = standardDetailVO.getStatus();
        //检查岗位是否存在
        if (Objects.isNull(standardEntryId)) {
            return CommonResult.getFaildResultData("岗位ID不能为空");
        }
        StandardDetail standardDetail = this.getStandardDetailById(standardEntryId);
        if (Objects.isNull(standardDetail)) {
            return CommonResult.getFaildResultData("岗位信息不存在");
        }
        standardDetail.setStandardEntryId(standardEntryId);
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
     *修改
     *
     * @param standardDetailVO
     * @return ResultHelper<?>
     * @author tanjp
     * @date 2022/8/2
     */
    @Override
    public ResultHelper<?> updateStandardDetail(StandardDetailVO standardDetailVO) {
        //根据主键查询岗位信息
        Long standardEntryId = standardDetailVO.getStandardEntryId();
        if (Objects.isNull(standardEntryId)) {
            return CommonResult.getFaildResultData("id不允许为空");
        }
        StandardDetail standardDetail = this.getStandardDetailById(standardEntryId);
        if (Objects.isNull(standardDetail)) {
            return CommonResult.getFaildResultData("id不存在");
        }

        BeanUtils.copyProperties(standardDetailVO, standardDetail);

        //输入的执行序号
        int actionSerialNum = standardDetailVO.getActionSerialNum();
        //获取最大的执行序号
        Integer max_actionSerialNum = this.maxActionSerialNum();
        //判断执行序号是否为null ，赋值1
        if (actionSerialNum <=0) {
            actionSerialNum = 1;
            standardDetailVO.setActionSerialNum(actionSerialNum);
        }
        //新增的执行序号<= 最大的执行序号
        if (actionSerialNum <= max_actionSerialNum) {
            List<StandardDetail> standardDetailList = QStandardDetail.standardDetail
                    .select(QStandardDetail.actionSerialNum)
                    .where(QStandardDetail.actionSerialNum.goe$(max_actionSerialNum))
                    .execute();
            for (int i = 0;i < standardDetailList.size();i++) {
                StandardDetail standardDetail1=standardDetailList.get(i);
                standardDetail1.setActionSerialNum(standardDetailList.get(i).getActionSerialNum()+1);
                standardDetail1.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                QStandardDetail.standardDetail.save(standardDetail1);
            }
        }
        //输入的执行序号大于最大的执行序号，新增执行序号=max+1
        if (actionSerialNum > max_actionSerialNum) {
            actionSerialNum = max_actionSerialNum+1;
            standardDetailVO.setActionSerialNum(actionSerialNum);
        }


        standardDetail.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        //更新时间
        standardDetail.setModifyTime(new DateTime());
        int row = QStandardDetail.standardDetail.save(standardDetail);

        if (row > 1) {
            return CommonResult.getSuccessResultData("修改成功");
        } else {
            return CommonResult.getFaildResultData("修改失败");
        }
    }

    /**
     *规范条目种类
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
        for (int i = 0;i < standardEntryList.size();i++) {
            StandardEntry standardEntry = standardEntryList.get(i);
            list.add(standardEntry.getEntryName());
        }
        return CommonResult.getSuccessResultData(list);
    }

    /**
     *条目类型  种类
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
        for (int i = 0;i < standardEntryList.size();i++) {
            StandardEntry standardEntry = standardEntryList.get(i);
            list.add(standardEntry.getItemType());
        }
        return CommonResult.getSuccessResultData(list);
    }

    /**
     *查找id
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
        }else {
            return null;
        }
    }
    /**
     *最大执行序号
     *
     * @param
     * @return Integer
     * @author tanjp
     * @date 2022/8/3
     */

    Integer maxActionSerialNum() {
        //获取最大的执行序号
        Integer max_actionSerialNum = DSContext
                .customization("selectMax_actionSerialNum")
                .selectValue()
                .mapperTo(Integer.class)
                .execute();
        return max_actionSerialNum;
    }

}
