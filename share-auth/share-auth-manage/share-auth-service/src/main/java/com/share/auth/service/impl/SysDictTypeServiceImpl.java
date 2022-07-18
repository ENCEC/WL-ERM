package com.share.auth.service.impl;

import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.google.common.collect.ImmutableMap;
import com.share.auth.constants.CodeFinal;
import com.share.auth.domain.PageDto;
import com.share.auth.domain.SysDictCodeDto;
import com.share.auth.domain.SysDictTypeDto;
import com.share.auth.domain.daoService.SysDictCodeDTO;
import com.share.auth.model.entity.SysDictCode;
import com.share.auth.model.entity.SysDictType;
import com.share.auth.model.querymodels.QSysDictCode;
import com.share.auth.model.querymodels.QSysDictType;
import com.share.auth.service.SysDictTypeService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class SysDictTypeServiceImpl implements SysDictTypeService {

    /**
     * 数据字典类型id(数据服务查询占位符)
     */
    private static final String SYS_DICT_TYPE_ID_PLACEHOLDER = ":sysDictTypeId";
    /**
     * 数据字典codeId(数据服务查询占位符)
     */
    private static final String SYS_DICT_CODE_ID_PLACEHOLDER = ":sysDictCodeId";
    /**
     * 数据字典类型id(字段名称)
     */
    private static final String SYS_DICT_TYPE = "sysDictTypeId";

    /**
     * 基础管理 数据字典 按条件分页查询
     *
     * @param sysDictTypeDto 数据字典表映射表
     * @param pageDto        分页映射
     * @return Page<SysDictType>
     * @author xrp
     */
    @Override
    public Page<SysDictTypeDto> queryByPage(SysDictTypeDto sysDictTypeDto, PageDto pageDto) {

        //字典类型
        String dictTypeCode = sysDictTypeDto.getDictTypeCode();
        //字典名称
        String dictTypeName = sysDictTypeDto.getDictTypeName();

        Integer pageNo = pageDto.getPageNo();

        Integer pageSize = pageDto.getPageSize();

        if(!StringUtils.isEmpty(dictTypeCode)){
            sysDictTypeDto.setDictTypeCode("%" + dictTypeCode + "%");
        }

        if(!StringUtils.isEmpty(dictTypeName)){
            sysDictTypeDto.setDictTypeName("%" + dictTypeName + "%");
        }

        return QSysDictType.sysDictType
                .select(QSysDictType.sysDictTypeId,
                        QSysDictType.dictTypeCode,
                        QSysDictType.dictTypeName,
                        QSysDictType.remark,
                        QSysDictType.isValid,
                        QSysDictType.invalidTime)
                .where(QSysDictType.dictTypeCode.like(":dictTypeCode")
                        .and(QSysDictType.isValid.eq(":isValid"))
                        .and(QSysDictType.dictTypeName.like(":dictTypeName")))
                .paging((pageNo == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : pageNo, (pageSize == null) ? CodeFinal.PAGE_SIZE_DEFAULT : pageSize)
                .sorting(QSysDictType.modifyTime.desc())
                .mapperTo(SysDictTypeDto.class)
                .execute(sysDictTypeDto);
    }

    /**基础管理 数据字典 新增字典
     * @param sysDictTypeDto 数据字典表映射表
     * @return
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> saveSysDictType(SysDictTypeDto sysDictTypeDto) {
        log.info("平台客服新增数据字典");
        //数据字典表ID
        String sysDictTypeId = sysDictTypeDto.getSysDictTypeId();
        //字典类型
        String dictTypeCode = sysDictTypeDto.getDictTypeCode();
        //字典名称
        String dictTypeName = sysDictTypeDto.getDictTypeName();
        //备注
        String remark = sysDictTypeDto.getRemark();

        if(StringUtils.isEmpty(dictTypeCode)){
            log.error("字典类型不能为空");
            return CommonResult.getFaildResultData("字典类型不能为空");
        }
        if(StringUtils.isEmpty(dictTypeName)){
            log.error("字典名称不能为空");
            return CommonResult.getFaildResultData("字典名称不能为空");
        }

        List<SysDictType> sysDictTypeList = QSysDictType.sysDictType
                .select(QSysDictType.sysDictType.fieldContainer())
                .where(QSysDictType.dictTypeCode.eq(":dictTypeCode")
                        .and(QSysDictType.sysDictTypeId.ne(SYS_DICT_TYPE_ID_PLACEHOLDER)))
                .execute(ImmutableMap.of("dictTypeCode", dictTypeCode, SYS_DICT_TYPE, sysDictTypeId));

        if(!CollectionUtils.isEmpty(sysDictTypeList)){
            log.error("字典类型：{}，已存在该字典类型的数据：{}", dictTypeCode, sysDictTypeList.get(0));
            return CommonResult.getFaildResultData("字典类型必须唯一");
        }

        SysDictType sysDictType = new SysDictType();
        sysDictType.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);

        sysDictType.setDictTypeCode(dictTypeCode);
        sysDictType.setDictTypeName(dictTypeName);
        sysDictType.setRemark(remark);
        sysDictType.setIsValid(true);

        log.info("平台客服保存数据字典信息：{}", sysDictType);
        int saveCount = QSysDictType.sysDictType.save(sysDictType);
        log.info("平台客服保存数据字典信息，返回保存行数为：{}", saveCount);
        if(saveCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM){
            return CommonResult.getSuccessResultData("保存成功");
        }else{
            return CommonResult.getFaildResultData("保存失败");
        }
    }

    /**基础管理 数据字典 修改字典
     * @param sysDictTypeDto 数据字典表映射表
     * @return
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> updateSysDictType(SysDictTypeDto sysDictTypeDto) {
        log.info("平台客服修改数据字典");
        //数据字典表ID
        String sysDictTypeId = sysDictTypeDto.getSysDictTypeId();
        //字典类型
        String dictTypeCode = sysDictTypeDto.getDictTypeCode();
        //字典名称
        String dictTypeName = sysDictTypeDto.getDictTypeName();
        //备注
        String remark = sysDictTypeDto.getRemark();
        if (Objects.isNull(sysDictTypeId)) {
            log.error("字典ID不能为空");
            return CommonResult.getFaildResultData("字典ID不能为空");
        }
        if(StringUtils.isEmpty(dictTypeCode)){
            log.error("字典类型不能为空");
            return CommonResult.getFaildResultData("字典类型不能为空");
        }
        if(StringUtils.isEmpty(dictTypeName)){
            log.error("字典名称不能为空");
            return CommonResult.getFaildResultData("字典名称不能为空");
        }

        List<SysDictType> sysDictTypeList = QSysDictType.sysDictType
                .select(QSysDictType.sysDictType.fieldContainer())
                .where(QSysDictType.dictTypeCode.eq(":dictTypeCode")
                        .and(QSysDictType.sysDictTypeId.ne(SYS_DICT_TYPE_ID_PLACEHOLDER)))
                .execute(ImmutableMap.of("dictTypeCode", dictTypeCode, SYS_DICT_TYPE, sysDictTypeId));
        if(!CollectionUtils.isEmpty(sysDictTypeList)){
            log.error("字典类型：{}，已存在该字典类型的数据：{}", dictTypeCode, sysDictTypeList.get(0));
            return CommonResult.getFaildResultData("字典类型必须唯一");
        }

        SysDictType sysDictType = QSysDictType.sysDictType.selectOne(QSysDictType.sysDictType.fieldContainer()).byId(Long.valueOf(sysDictTypeId));
        sysDictType.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        sysDictType.setDictTypeName(dictTypeName);
        sysDictType.setDictTypeCode(dictTypeCode);
        sysDictType.setRemark(remark);
        log.info("平台客服修改数据字典信息为：{}", sysDictType);
        int updateCount = QSysDictType.sysDictType.save(sysDictType);
        log.info("平台客服修改数据字典信息，返回修改行数为：{}", updateCount);
        if(updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM){
            return CommonResult.getSuccessResultData("修改成功");
        }else{
            return CommonResult.getFaildResultData("修改失败");
        }
    }

    /**基础管理 数据字典 详情
     * @param sysDictTypeId 数据字典表ID
     * @return List<SysDictType>
     * @author xrp
     * */
    @Override
    public List<SysDictType> getSysDictType(String sysDictTypeId) {

        return QSysDictType.sysDictType
                .select(QSysDictType.sysDictType.fieldContainer())
                .where(QSysDictType.sysDictTypeId.eq(SYS_DICT_TYPE_ID_PLACEHOLDER))
                .execute(ImmutableMap.of(SYS_DICT_TYPE, sysDictTypeId));
    }

    /**基础管理 数据字典 启停
     * @param sysDictTypeDto 数据字典表映射表
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> updateDictTypeStatus(SysDictTypeDto sysDictTypeDto) {

        //数据字典表ID
        String sysDictTypeId = sysDictTypeDto.getSysDictTypeId();
        //是否禁用（0禁用，1启用）
        Boolean isValid = sysDictTypeDto.getIsValid();

        int updateCount = QSysDictType.sysDictType
                .update(QSysDictType.isValid,
                        QSysDictType.invalidTime)
                .where(QSysDictType.sysDictTypeId.eq(SYS_DICT_TYPE_ID_PLACEHOLDER))
                .execute(isValid,new Date(),sysDictTypeId);

        if(updateCount > CodeFinal.SAVE_OR_UPDATE_FAIL_ROW_NUM){
            return CommonResult.getSuccessResultData("启停成功");
        }else{
            return CommonResult.getFaildResultData("启停失败");
        }
    }

    /**基础管理 数据字典 配置项分页查询
     * @param sysDictCodeDto 数据字典配置项表映射
     * @param pageDto 分页
     * @return Page<SysDictCodeDto>
     * @author xrp
     * */
    @Override
    public Page<SysDictCodeDto> queryByDictTypeId(SysDictCodeDto sysDictCodeDto,PageDto pageDto) {

        //配置项编码
        String dictCode = sysDictCodeDto.getDictCode();

        //配置项名称
        String dictName = sysDictCodeDto.getDictName();


        if(!StringUtils.isEmpty(dictCode)){
            sysDictCodeDto.setDictCode("%" + dictCode + "%");
        }
        if(!StringUtils.isEmpty(dictName)){
            sysDictCodeDto.setDictName("%" + dictName + "%");
        }

        Integer pageNo = pageDto.getPageNo();

        Integer pageSize = pageDto.getPageSize();

        return QSysDictCode.sysDictCode
                .select(QSysDictCode.sysDictCodeId,
                        QSysDictCode.sysDictTypeId,
                        QSysDictCode.dictCode,
                        QSysDictCode.dictName,
                        QSysDictCode.remark,
                        QSysDictCode.isValid,
                        QSysDictCode.invalidTime)
                .where(QSysDictCode.isValid.eq(":isValid")
                        .and(QSysDictCode.dictName.like(":dictName").or(QSysDictCode.dictCode.like(":dictName")))
                        .and(QSysDictCode.sysDictTypeId.eq(SYS_DICT_TYPE_ID_PLACEHOLDER)))
                .paging((pageNo == null) ? CodeFinal.CURRENT_PAGE_DEFAULT : pageNo,(pageSize == null) ? CodeFinal.PAGE_SIZE_DEFAULT : pageSize)
                .sorting(QSysDictCode.modifyTime.desc())
                .mapperTo(SysDictCodeDto.class)
                .execute(sysDictCodeDto);
    }

    /**基础管理 数据字典 配置项新增或者修改
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> saveSysDictCode(SysDictCodeDto sysDictCodeDto) {
        log.info("平台客服新增数据字典配置项");
        //数据字典配置项ID
        String sysDictCodeId = sysDictCodeDto.getSysDictCodeId();
        //关联字典主表id
        Long sysDictTypeId = sysDictCodeDto.getSysDictTypeId();
        //配置项编码
        String dictCode = sysDictCodeDto.getDictCode();
        //配置项名称
        String dictName = sysDictCodeDto.getDictName();
        //备注
        String remark = sysDictCodeDto.getRemark();
        if (Objects.isNull(sysDictTypeId)) {
            return CommonResult.getFaildResultData("字典id不能为空");
        }
        if(StringUtils.isEmpty(dictCode)){
            log.error("配置项编码不能为空");
            return CommonResult.getFaildResultData("配置项编码不能为空");
        }
        if(StringUtils.isEmpty(dictName)){
            log.error("配置项名称不能为空");
            return CommonResult.getFaildResultData("配置项名称不能为空");
        }

        List<SysDictCode> sysDictCodeList = QSysDictCode.sysDictCode
                .select(QSysDictCode.sysDictCode.fieldContainer())
                .where(QSysDictCode.dictCode.eq(":dictCode")
                        .and(QSysDictCode.sysDictTypeId.eq(SYS_DICT_TYPE_ID_PLACEHOLDER))
                        .and(QSysDictCode.sysDictCodeId.ne(SYS_DICT_CODE_ID_PLACEHOLDER)))
                .execute(ImmutableMap.of("dictCode", dictCode, SYS_DICT_TYPE, sysDictTypeId, "sysDictCodeId", sysDictCodeId));

        if(!CollectionUtils.isEmpty(sysDictCodeList)){
            log.error("字典类型id：{}，配置项编码：{}，已存在该配置项编码的数据：{}", sysDictTypeId, dictCode, sysDictCodeList.get(0));
            return CommonResult.getFaildResultData("同一个字典类型下，编码必须唯一 ");
        }

        SysDictCode sysDictCode = new SysDictCode();
        sysDictCode.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        sysDictCode.setDictCode(dictCode);
        sysDictCode.setDictName(dictName);
        sysDictCode.setSysDictTypeId(sysDictTypeId);
        sysDictCode.setRemark(remark);
        sysDictCode.setIsValid(true);
        QSysDictCode.sysDictCode.save(sysDictCode);

        return CommonResult.getSuccessResultData("保存成功");

    }

    /**基础管理 数据字典 配置项新增或者修改
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> updateSysDictCode(SysDictCodeDto sysDictCodeDto) {
        log.info("平台客服修改数据字典配置项");
        //数据字典配置项ID
        String sysDictCodeId = sysDictCodeDto.getSysDictCodeId();
        //关联字典主表id
        Long sysDictTypeId = sysDictCodeDto.getSysDictTypeId();
        //配置项编码
        String dictCode = sysDictCodeDto.getDictCode();
        //配置项名称
        String dictName = sysDictCodeDto.getDictName();
        //备注
        String remark = sysDictCodeDto.getRemark();
        if (Objects.isNull(sysDictCodeId)) {
            log.error("配置项id不能为空");
            return CommonResult.getFaildResultData("配置项id不能为空");
        }
        if (Objects.isNull(sysDictTypeId)) {
            log.error("字典id不能为空");
            return CommonResult.getFaildResultData("字典id不能为空");
        }
        if(StringUtils.isEmpty(dictCode)){
            log.error("配置项编码不能为空");
            return CommonResult.getFaildResultData("配置项编码不能为空");
        }
        if(StringUtils.isEmpty(dictName)){
            log.error("配置项名称不能为空");
            return CommonResult.getFaildResultData("配置项名称不能为空");
        }

        List<SysDictCode> sysDictCodeList = QSysDictCode.sysDictCode
                .select(QSysDictCode.sysDictCode.fieldContainer())
                .where(QSysDictCode.dictCode.eq(":dictCode")
                        .and(QSysDictCode.sysDictTypeId.eq(SYS_DICT_TYPE_ID_PLACEHOLDER))
                        .and(QSysDictCode.sysDictCodeId.ne(SYS_DICT_CODE_ID_PLACEHOLDER)))
                .execute(ImmutableMap.of("dictCode", dictCode, SYS_DICT_TYPE, sysDictTypeId, "sysDictCodeId", sysDictCodeId));
        if(!CollectionUtils.isEmpty(sysDictCodeList)){
            log.error("字典类型id：{}，配置项编码：{}，已存在该配置项编码的数据：{}", sysDictTypeId, dictCode, sysDictCodeList.get(0));
            return CommonResult.getFaildResultData("同一个字典类型下，编码必须唯一 ");
        }

        SysDictCode sysDictCode = QSysDictCode.sysDictCode.selectOne(QSysDictCode.sysDictCode.fieldContainer()).byId(Long.valueOf(sysDictCodeId));
        sysDictCode.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        sysDictCode.setDictName(dictName);
        sysDictCode.setDictCode(dictCode);
        sysDictCode.setRemark(remark);
        QSysDictCode.sysDictCode.save(sysDictCode);
        return CommonResult.getSuccessResultData("修改成功");
    }


    /**基础管理 数据字典 配置项详情
     * @param sysDictCodeId 数据字典配置项ID
     * @return List<SysDictCode>
     * @author xrp
     * */
    @Override
    public List<SysDictCode> getSysDictCode(String sysDictCodeId) {

        return QSysDictCode.sysDictCode
                .select(QSysDictCode.sysDictCode.fieldContainer())
                .where(QSysDictCode.sysDictCodeId.eq(SYS_DICT_CODE_ID_PLACEHOLDER))
                .execute(ImmutableMap.of("sysDictCodeId", sysDictCodeId));
    }

    /**基础管理 数据字典 配置项 启停
     * @param sysDictCodeDto 数据字典配置项表映射
     * @return Map<String, Object>
     * @author xrp
     * */
    @Override
    public ResultHelper<Object> updateDictCodeStatus(SysDictCodeDto sysDictCodeDto) {

        //数据字典配置项ID
        String sysDictCodeId = sysDictCodeDto.getSysDictCodeId();
        //是否禁用（0禁用，1启用）
        Boolean isValid = sysDictCodeDto.getIsValid();

        QSysDictCode.sysDictCode.update(QSysDictCode.isValid,
                QSysDictCode.invalidTime)
                .where(QSysDictCode.sysDictCodeId.eq(SYS_DICT_CODE_ID_PLACEHOLDER))
                .execute(isValid,new Date(),sysDictCodeId);
        return CommonResult.getSuccessResultData("启停成功");
    }


    @Override
    public Map<String, List<SysDictCodeDTO>> selectSysDictCodeMapToCodeList(List<String> codeList) {
        HashMap<String, Object> paramMa = new HashMap<>();
        paramMa.put("dictTypeCodeList",codeList);
        List<SysDictCodeDTO> sysDictCodeDTOList = DSContext.customization("CZT_selectSysDictCode_Auth").select()
                .mapperTo(SysDictCodeDTO.class)
                .execute(paramMa);
        Map<String, List<SysDictCodeDTO>> queryResourceDTOMap = sysDictCodeDTOList.stream().collect(Collectors.groupingBy(SysDictCodeDTO::getDictTypeCode));
        return queryResourceDTOMap;
    }
}
