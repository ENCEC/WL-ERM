package com.share.auth.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
import com.gillion.ds.client.api.queryobject.expressions.Expression;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.ec.core.security.IUser;
import com.gillion.exception.BusinessRuntimeException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.share.auth.constants.CodeFinal;
import com.share.auth.constants.GlobalConstant;
import com.share.auth.domain.*;
import com.share.auth.domain.platform.UemCompanyDTO;
import com.share.auth.enums.GlobalEnum;
import com.share.auth.model.entity.*;
import com.share.auth.model.querymodels.*;
import com.share.auth.model.vo.*;
import com.share.auth.model.vo.UemUserVO;
import com.share.auth.service.UemCompanyService;
import com.share.auth.user.AuthUserInfoModel;
import com.share.auth.user.DefaultUserService;
import com.share.auth.util.MessageUtil;
import com.share.auth.util.QueryResultUtils;
import com.share.file.api.ShareFileInterface;
import com.share.file.domain.FastDfsUploadResult;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import com.share.support.util.DateUtils;
import com.share.support.util.MD5EnCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author chenxf
 * @date 2020-10-26 10:44
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UemCompanyServiceImpl implements UemCompanyService {

    /**
     * 承运商
     */
    private static final String CARRIER = "cys";
    /**
     * 企业用户类型-物流提供方
     */
    private static final String LOGIN_LOGISTICS_SUPPLY = "LOGIN_LOGISTICS_SUPPLY";
    /**
     * 企业用户类型-物流需求方
     */
    private static final String LOGIN_LOGISTICS_REQUIREMENT = "LOGIN_LOGISTICS_REQUIREMENT";
    /**
     * 是否禁用(数据服务查询占位符)
     */
    private static final String IS_VALID_PLACEHOLDER = ":isValid";
    /**
     * 审核状态(数据服务查询占位符)
     */
    private static final String AUDIT_STATUS_PLACEHOLDER = ":auditStatus";

    @Autowired
    private DefaultUserService userService;

    @Autowired
    private ShareFileInterface shareFileInterface;


    @Value("${aes_secret_key}")
    private String aesSecretKey;


    @Autowired
    private DaoServiceClient client;

    @Autowired
    private DefaultUserService defaultUserService;

    /**
     * @Author:chenxf
     * @Description: 查询承运商企业信息
     * @Date: 11:27 2020/10/26
     * @Param: [opType] 企业类型，“cys”：承运商；
     * @Return:java.lang.String
     */
    @Override
    public Map<String, Object> queryUemCompanyForCustomerType(String opType) {
        String companyTypeCode;
        if (CARRIER.equals(opType)) {
            companyTypeCode = LOGIN_LOGISTICS_SUPPLY;
        } else {
            companyTypeCode = LOGIN_LOGISTICS_REQUIREMENT;
        }
        List<UemCustomerType> uemCustomerTypeList = QUemCustomerType.uemCustomerType.select(QUemCustomerType.uemCustomerType.fieldContainer())
                .where(QUemCustomerType.companyTypeCode.eq(":companyTypeCode")
                        .and(QUemCustomerType.selectedItemCode.in$("S1", "S2", "S3", "S4", "S5", "S6")))
                .execute(ImmutableMap.of("companyTypeCode", companyTypeCode));
        Set<Long> uemCompanyIdSet = uemCustomerTypeList.stream().map(UemCustomerType::getUemCompanyId).collect(Collectors.toSet());
        List<QueryCompanyDTO> queryCompanyDTOList = QUemCompany.uemCompany.select(
                QUemCompany.uemCompanyId,
                QUemCompany.companyCode,
                QUemCompany.organizationType,
                QUemCompany.companyNameCn,
                QUemCompany.companyAbbreviName,
                QUemCompany.companyNameEn,
                QUemCompany.organizationCode,
                QUemCompany.memoryCode,
                QUemCompany.legalType,
                QUemCompany.legalName,
                QUemCompany.legalCard,
                QUemCompany.contact,
                QUemCompany.contactTel,
                QUemCompany.locCountryName,
                QUemCompany.locCityName,
                QUemCompany.locDistrictName,
                QUemCompany.locAddress
        ).where(
                QUemCompany.uemCompanyId.in$(uemCompanyIdSet)
                        .and(QUemCompany.isValid.eq$(true))
                        .and(QUemCompany.auditStatus.eq$(CodeFinal.AUDIT_STATUS_ONE)
                                .and(QUemCompany.carrierType.eq$("0").or(QUemCompany.carrierType.isNull()))
                                .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull())))
        ).mapperTo(QueryCompanyDTO.class).execute();
        return QueryResultUtils.getSuccessData(QueryResultUtils.QUERY_SUCCESS, queryCompanyDTOList);
    }

    /**
     * @Author:chenxf
     * @Description: 企业管理树形表格数据查询接口
     * @Date: 14:59 2020/11/3
     * @Param: [companyTreeTableQueryVO]
     * @Return:com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.model.vo.QueryCompanyTreeTableDTO>
     */
    @Override
    public Page<QueryCompanyTreeTableDTO> queryByTreeTable(CompanyTreeTableQueryVO companyTreeTableQueryVO) {
        // 查询参数处理
        queryParamHandler(companyTreeTableQueryVO);
        Page<QueryCompanyTreeTableDTO> queryCompanyTreeTableDtoPage;
        companyTreeTableQueryVO.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
        if (StringUtils.isNotEmpty(companyTreeTableQueryVO.getCompanyNameCn()) || StringUtils.isNotEmpty(companyTreeTableQueryVO.getCompanyCode())) {
            List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(
                    QUemCompany.uemCompany.fieldContainer()
            ).where(
                    QUemCompany.companyNameCn.like(":companyNameCn")
                            .and(QUemCompany.companyCode.like(":companyCode"))
                            .and(QUemCompany.auditStatus.eq(AUDIT_STATUS_PLACEHOLDER))
                            .and(QUemCompany.isValid.eq(IS_VALID_PLACEHOLDER))
                            .and(QUemCompany.dataSource.eq(GlobalConstant.PLACEHOLDER_DATASOURCE))
                            .and(QUemCompany.orgCode.like(":orgCode"))
            ).execute(companyTreeTableQueryVO);
            Set<Long> uemCompanyIdList = new HashSet<>();
            for (UemCompany uemCompany : uemCompanyList) {
                if (Objects.nonNull(uemCompany.getTopCompany()) && Objects.nonNull(uemCompany.getBelongCompany())) {
                    uemCompanyIdList.add(uemCompany.getTopCompany());
                } else {
                    uemCompanyIdList.add(uemCompany.getUemCompanyId());
                }
            }
            if (CollectionUtils.isNotEmpty(uemCompanyIdList)) {
                AndExpression expression = QUemCompany.uemCompanyId.in$(uemCompanyIdList).and(QUemCompany.isValid.eq(IS_VALID_PLACEHOLDER));
                queryCompanyTreeTableDtoPage = QUemCompany.uemCompany.select(
                        QUemCompany.uemCompany.fieldContainer()
                ).where(expression).paging(companyTreeTableQueryVO.getCurrentPage(), companyTreeTableQueryVO.getPageSize())
                        .sorting(QUemCompany.auditTime.desc())
                        .mapperTo(QueryCompanyTreeTableDTO.class).execute();
            } else {
                queryCompanyTreeTableDtoPage = new Page<>();
            }
        } else {
            // 分页查询企业列表（不包含下级企业）
            queryCompanyTreeTableDtoPage = QUemCompany.uemCompany.select(
                    QUemCompany.uemCompany.fieldContainer()
            ).where(
                    QUemCompany.companyNameCn.like(":companyNameCn")
                            .and(QUemCompany.companyCode.like(":companyCode"))
                            .and(QUemCompany.auditStatus.eq(AUDIT_STATUS_PLACEHOLDER))
                            .and(QUemCompany.isValid.eq(IS_VALID_PLACEHOLDER))
                            .and(QUemCompany.belongCompany.isNull())
                            .and(QUemCompany.dataSource.eq(GlobalConstant.PLACEHOLDER_DATASOURCE))
                            .and(QUemCompany.dataSource.eq(GlobalConstant.PLACEHOLDER_DATASOURCE))
            ).paging(companyTreeTableQueryVO.getCurrentPage(), companyTreeTableQueryVO.getPageSize())
                    .sorting(QUemCompany.auditTime.desc())
                    .mapperTo(QueryCompanyTreeTableDTO.class)
                    .execute(companyTreeTableQueryVO);

        }
        // 循环处理查询每个企业的下级企业
        if (CollectionUtils.isNotEmpty(queryCompanyTreeTableDtoPage.getRecords())) {
            searchChildrenCompany(queryCompanyTreeTableDtoPage.getRecords(), companyTreeTableQueryVO);
        }
        return queryCompanyTreeTableDtoPage;
    }

    /**
     * 查询参数处理
     *
     * @param companyTreeTableQueryVO
     * @return
     * @author huanghwh
     * @date 2021/4/29 下午5:18
     */
    private void queryParamHandler(CompanyTreeTableQueryVO companyTreeTableQueryVO) {
        if (StringUtils.isNotEmpty(companyTreeTableQueryVO.getCompanyNameCn())) {
            companyTreeTableQueryVO.setCompanyNameCn("%" + companyTreeTableQueryVO.getCompanyNameCn() + "%");
        }
        if (StringUtils.isNotEmpty(companyTreeTableQueryVO.getCompanyCode())) {
            companyTreeTableQueryVO.setCompanyCode("%" + companyTreeTableQueryVO.getCompanyCode() + "%");
        }
        if (StringUtils.isNotEmpty(companyTreeTableQueryVO.getOrgCode())) {
            companyTreeTableQueryVO.setOrgCode("%" + companyTreeTableQueryVO.getOrgCode() + "%");
        }
    }

    /**
     * @Author:chenxf
     * @Description: 管理员新增保存企业
     * @Date: 11:17 2020/11/13
     * @Param: [queryCompanyTreeTableDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public ResultHelper<Object> adminSaveCompany(QueryCompanyTreeTableDTO queryCompanyTreeTableDTO) {
        //获取当前用户信息
        AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
        log.info("平台客服新增企业信息，企业id设置空");
        queryCompanyTreeTableDTO.setUemCompanyId(null);
        queryCompanyTreeTableDTO.setCompanyCode("");
        queryCompanyTreeTableDTO.setDataSource(CodeFinal.DATA_SOURCE_TWO);

        if (queryCompanyTreeTableDTO.getIsSuperior() == null) {
            queryCompanyTreeTableDTO.setIsSuperior(false);
        } else {
            queryCompanyTreeTableDTO.setIsSuperior(queryCompanyTreeTableDTO.getIsSuperior());
        }

        // 企业信息
        UemCompany uemCompany = new UemCompany();
        // 获取新增/修改后的企业信息
        BeanUtils.copyProperties(queryCompanyTreeTableDTO, uemCompany);
        // 获取新增/修改后的企业用户类型信息
        List<UemCustomerTypeDTO> uemCustomerTypeDTOList = queryCompanyTreeTableDTO.getUemCustomerTypeList();
        List<UemCustomerType> uemCustomerTypeList = Lists.newArrayList();

        // 如果是新增，及企业管理员新增设置审批信息
        uemCompany.setAuditor(user.getUemUserId());
        uemCompany.setAuditTime(new Date());
        uemCompany.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
        uemCompany.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        uemCompany.setIsValid(true);
        uemCompany.setDataSource(CodeFinal.DATA_SOURCE_TWO);

        if (Objects.nonNull(uemCompany.getBelongCompany())) {
            UemCompany belongCompany = QUemCompany.uemCompany.selectOne().byId(uemCompany.getBelongCompany());
            uemCompany.setTopCompany(belongCompany.getTopCompany() == null ? belongCompany.getUemCompanyId() : belongCompany.getTopCompany());
        }
        uemCompany.setCarrierType(GlobalEnum.CarrierType.REGISTER.getCode());
        // 新增企业信息
        log.info("新增企业信息：{}", uemCompany);
        QUemCompany.uemCompany.save(uemCompany);
        // 重新插入企业用户类型表数据
        reInsertEnterpriseUserTypeTableData(uemCustomerTypeDTOList, uemCustomerTypeList, uemCompany, queryCompanyTreeTableDTO);
        // 新增一条企业历史记录表数据
        UemCompanyHistory uemCompanyHistory = new UemCompanyHistory();
        BeanUtils.copyProperties(uemCompany, uemCompanyHistory);
        uemCompanyHistory.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
        uemCompanyHistory.setAuditTime(new Date());
        uemCompanyHistory.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        uemCompanyHistory.setIsValid(true);
        uemCompanyHistory.setUemCompanyId(uemCompany.getUemCompanyId());
        // 新增记录时需要设置历史记录表id为空，因为上面copy的时候会copy这个字段
        uemCompanyHistory.setUemCompanyHistoryId(null);
        if (org.apache.commons.lang3.StringUtils.isEmpty(uemCompany.getCompanyCode())) {
            uemCompanyHistory.setCompanyCode(getMaxCompanyCode());
            QUemCompanyHistory.uemCompanyHistory.save(uemCompanyHistory);
        } else {
            QUemCompanyHistory.uemCompanyHistory.save(uemCompanyHistory);
        }
        // 保存历史记录表数据后更新企业表uemCompanyHistoryId字段
        log.info("更新uemCompany的uemCompanyHistoryId，companyCode");
        QUemCompany.uemCompany.update(QUemCompany.uemCompanyHistoryId, QUemCompany.companyCode)
                .where(QUemCompany.uemCompanyId.eq(":uemCompanyId"))
                .execute(uemCompanyHistory.getUemCompanyHistoryId(), uemCompanyHistory.getCompanyCode(), uemCompany.getUemCompanyId());
        // 新增企业权限配置
        this.addUemCompanyRole(queryCompanyTreeTableDTO.getUemCompanyRoleVoList(), uemCompany.getUemCompanyId());
        return CommonResult.getSuccessResultData("保存成功");
    }

    /**
     * @Author:chenxf
     * @Description: 管理员修改保存企业
     * @Date: 11:17 2020/11/13
     * @Param: [queryCompanyTreeTableDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public ResultHelper<Object> adminUpdateCompany(QueryCompanyTreeTableDTO queryCompanyTreeTableDTO) {
        log.info("平台客服修改企业信息");
        //获取当前用户信息
        AuthUserInfoModel user = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(queryCompanyTreeTableDTO.getUemCompanyId())) {
            log.error("修改企业，传入企业id为空");
            return CommonResult.getFaildResultData("修改失败，企业id不能为空");
        }
        if (queryCompanyTreeTableDTO.getIsSuperior() == null) {
            queryCompanyTreeTableDTO.setIsSuperior(false);
        } else {
            queryCompanyTreeTableDTO.setIsSuperior(queryCompanyTreeTableDTO.getIsSuperior());
        }
        queryCompanyTreeTableDTO.setIsFocusCompany(Objects.isNull(queryCompanyTreeTableDTO.getIsFocusCompany()) ? false : queryCompanyTreeTableDTO.getIsFocusCompany());

        // 企业原始信息
        UemCompany sourceUemCompany = QUemCompany.uemCompany.selectOne().byId(queryCompanyTreeTableDTO.getUemCompanyId());

        // 企业信息
        UemCompany uemCompany = new UemCompany();
        // 获取新增/修改后的企业信息
        BeanUtils.copyProperties(queryCompanyTreeTableDTO, uemCompany);
        // 获取新增/修改后的企业用户类型信息
        List<UemCustomerTypeDTO> uemCustomerTypeDTOList = queryCompanyTreeTableDTO.getUemCustomerTypeList();
        List<UemCustomerType> uemCustomerTypeList = Lists.newArrayList();

        // 如果是修改,需要把原来的企业用户类型和货物数据删除
        uemCompany.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QUemCustomerType.uemCustomerType.delete().where(QUemCustomerType.uemCompanyId.eq$(uemCompany.getUemCompanyId())).execute();
        QUemCompanyCargoType.uemCompanyCargoType.delete().where(QUemCompanyCargoType.uemCompanyId.eq$(uemCompany.getUemCompanyId())).execute();

        if (Objects.nonNull(uemCompany.getBelongCompany())) {
            UemCompany belongCompany = QUemCompany.uemCompany.selectOne().byId(uemCompany.getBelongCompany());
            uemCompany.setTopCompany(belongCompany.getTopCompany() == null ? belongCompany.getUemCompanyId() : belongCompany.getTopCompany());
        }
        uemCompany.setCarrierType(sourceUemCompany.getCarrierType());
        // 更新企业信息
        log.info("修改企业信息：{}", uemCompany);
        QUemCompany.uemCompany.save(uemCompany);

        // 修改企业名称，更新下级企业的上级企业名称
        if (!Objects.equals(sourceUemCompany.getCompanyNameCn(), uemCompany.getCompanyNameCn())) {
            this.updateBelongCompanyName(uemCompany);
        }
        // 修改上级企业时，更新所有下级企业的最上级企业id
        if (!Objects.equals(sourceUemCompany.getTopCompany(), uemCompany.getTopCompany())) {
            this.updateTopCompany(uemCompany, CodeFinal.RECURSION_START_LEVEL);
        }

        // 重新插入企业用户类型表数据
        reInsertEnterpriseUserTypeTableData(uemCustomerTypeDTOList, uemCustomerTypeList, uemCompany, queryCompanyTreeTableDTO);
        // 新增一条企业历史记录表数据
        UemCompanyHistory uemCompanyHistory = new UemCompanyHistory();
        BeanUtils.copyProperties(uemCompany, uemCompanyHistory);
        uemCompanyHistory.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
        uemCompanyHistory.setAuditTime(new Date());
        uemCompanyHistory.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        uemCompanyHistory.setIsValid(true);
        uemCompanyHistory.setUemCompanyId(uemCompany.getUemCompanyId());
        // 新增记录时需要设置历史记录表id为空，因为上面copy的时候会copy这个字段
        uemCompanyHistory.setUemCompanyHistoryId(null);
        if (org.apache.commons.lang3.StringUtils.isEmpty(uemCompany.getCompanyCode())) {
            QUemCompanyHistory.uemCompanyHistory.tag("CompanyCode").save(uemCompanyHistory);
        } else {
            QUemCompanyHistory.uemCompanyHistory.save(uemCompanyHistory);
        }
        // 保存历史记录表数据后更新企业表uemCompanyHistoryId字段
        log.info("更新uemCompany的uemCompanyHistoryId，companyCode");
        QUemCompany.uemCompany.update(QUemCompany.uemCompanyHistoryId, QUemCompany.companyCode)
                .where(QUemCompany.uemCompanyId.eq(":uemCompanyId"))
                .execute(uemCompanyHistory.getUemCompanyHistoryId(), uemCompanyHistory.getCompanyCode(), uemCompany.getUemCompanyId());

        // 删除企业已配置的权限
        QUemCompanyRole.uemCompanyRole.delete().where(QUemCompanyRole.uemCompanyId.eq$(uemCompany.getUemCompanyId())).execute();
        // 新增企业权限配置
        this.addUemCompanyRole(queryCompanyTreeTableDTO.getUemCompanyRoleVoList(), uemCompany.getUemCompanyId());
        return CommonResult.getSuccessResultData("修改成功");
    }

    /**
     * 重新插入企业用户类型表数据
     *
     * @param uemCustomerTypeDTOList
     * @param uemCustomerTypeList
     * @param uemCompany
     * @param queryCompanyTreeTableDTO
     * @return
     * @author huanghwh
     * @date 2021/5/6 上午10:45
     */
    private void reInsertEnterpriseUserTypeTableData(List<UemCustomerTypeDTO> uemCustomerTypeDTOList, List<UemCustomerType> uemCustomerTypeList, UemCompany uemCompany, QueryCompanyTreeTableDTO queryCompanyTreeTableDTO) {
        for (UemCustomerTypeDTO uemCustomerTypeDTO : uemCustomerTypeDTOList) {
            UemCustomerType uemCustomerType = new UemCustomerType();
            BeanUtils.copyProperties(uemCustomerTypeDTO, uemCustomerType);
            uemCustomerType.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            uemCustomerType.setUemCompanyId(uemCompany.getUemCompanyId());
            uemCustomerTypeList.add(uemCustomerType);
        }
        if (CollectionUtils.isNotEmpty(uemCustomerTypeList)) {
            QUemCustomerType.uemCustomerType.save(uemCustomerTypeList);
            //重新插入货物类型数据
            if (CodeFinal.LOGIN_LOGISTICS_REQUIREMENT.equals(uemCustomerTypeList.get(0).getCompanyTypeCode())) {
                List<UemCompanyCargoTypeDTO> uemCompanyCargoTypeDTOList = queryCompanyTreeTableDTO.getUemCompanyCargoTypeDTOList();
                List<UemCompanyCargoType> uemCompanyCargoTypeList = org.apache.commons.compress.utils.Lists.newArrayList();
                UemCompanyCargoType uemCompanyCargoType;
                for (UemCompanyCargoTypeDTO uemCompanyCargoDTO : uemCompanyCargoTypeDTOList) {
                    uemCompanyCargoType = new UemCompanyCargoType();
                    BeanUtils.copyProperties(uemCompanyCargoDTO, uemCompanyCargoType);
                    uemCompanyCargoType.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
                    uemCompanyCargoType.setUemCompanyId(uemCompany.getUemCompanyId());
                    uemCompanyCargoType.setUemCompanyCargoTypeId(null);
                    uemCompanyCargoTypeList.add(uemCompanyCargoType);
                }
                if (CollectionUtils.isNotEmpty(uemCompanyCargoTypeList)) {
                    QUemCompanyCargoType.uemCompanyCargoType.save(uemCompanyCargoTypeList);
                }
            }
        }
    }

    /**
     * @Author:chenxf
     * @Description: 查询企业信息
     * @Date: 18:41 2020/11/28
     * @Param: [uemCompanyId]
     * @Return:com.share.auth.domain.ReviewCompanyDTO
     */
    @Override
    public ReviewCompanyDTO queryCompanyById(Long uemCompanyId) {
        AuthUserInfoModel userInfoModel = (AuthUserInfoModel) userService.getCurrentLoginUser();
        if (Objects.isNull(userInfoModel)) {
            log.error("获取登录信息为空");
            throw new BusinessRuntimeException("获取登录信息失败");
        }
        if (userInfoModel.getUemUserId() == null) {
            log.error("获取登录信息的用户id为空");
            throw new BusinessRuntimeException("获取登录信息失败");
        }
        ReviewCompanyDTO reviewCompanyDTO = QUemCompany.uemCompany.selectOne(QUemCompany.uemCompany.fieldContainer()).mapperTo(ReviewCompanyDTO.class).byId(uemCompanyId);
        if (Objects.isNull(reviewCompanyDTO)) {
            log.error("uemCompanyId：{}，无法查询到uem_company表的企业信息", uemCompanyId);
            throw new BusinessRuntimeException("未查询到企业信息");
        }
        if (Objects.nonNull(reviewCompanyDTO.getAuditor())) {
            SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(reviewCompanyDTO.getAuditor());
            if (Objects.nonNull(sysPlatformUser)) {
                reviewCompanyDTO.setAuditorName(sysPlatformUser.getName());
            }
        }
        List<UemCustomerTypeDTO> uemCustomerTypeDTOList = QUemCustomerType.uemCustomerType.select(QUemCustomerType.uemCustomerType.fieldContainer()).where(
                QUemCustomerType.uemCompanyId.eq$(uemCompanyId)).mapperTo(UemCustomerTypeDTO.class).execute();
        reviewCompanyDTO.setUemCustomerTypeList(uemCustomerTypeDTOList);
        List<UemCompanyCargoTypeDTO> uemCompanyCargoTypeDTOList = QUemCompanyCargoType.uemCompanyCargoType.select().where(QUemCompanyCargoType.uemCompanyId.eq$(uemCompanyId)).mapperTo(UemCompanyCargoTypeDTO.class).execute();
        reviewCompanyDTO.setUemCompanyCargoTypeDTOList(uemCompanyCargoTypeDTOList);

        // 获取企业权限配置信息
        this.getUemCompanyRoleInfo(reviewCompanyDTO);

        // 创建人是否实名认证
        if (reviewCompanyDTO.getCreatorName() == null) {
            // 获取创建人信息
            UemUser uemUser = QUemUser.uemUser.selectOne().byId(reviewCompanyDTO.getCreatorId());
            // 是否实名认证
            if (uemUser.getName() != null && !uemUser.getName().isEmpty()) {
                reviewCompanyDTO.setCreatorName(uemUser.getName());
            } else {
                reviewCompanyDTO.setCreatorName(uemUser.getAccount());
            }
        }
        return reviewCompanyDTO;
    }

    /**
     * 获取企业权限配置信息
     *
     * @param reviewCompanyDTO 企业信息
     */
    private void getUemCompanyRoleInfo(ReviewCompanyDTO reviewCompanyDTO) {
        // 获取所有可配置角色
        List<UemCompanyRoleVO> allCompanyRoleList = this.queryAllConfigurableRole();
        // 获取企业已配置的角色
        List<UemCompanyRole> uemCompanyRoleList = QUemCompanyRole.uemCompanyRole.select().where(QUemCompanyRole.uemCompanyId.eq$(reviewCompanyDTO.getUemCompanyId())).execute();

        // 设置可配置角色是否已选择，是否默认角色
        for (UemCompanyRoleVO uemCompanyRoleVO : allCompanyRoleList) {
            // 默认未选择角色
            uemCompanyRoleVO.setIsSelected(Boolean.FALSE);
            uemCompanyRoleVO.setIsDefault(Boolean.FALSE);

            // 获取已配置的角色
            for (UemCompanyRole uemCompanyRole : uemCompanyRoleList) {
                // 根据已配置的角色设置信息
                if (Objects.equals(uemCompanyRole.getSysRoleId(), uemCompanyRoleVO.getSysRoleId())) {
                    uemCompanyRoleVO.setUemCompanyRoleId(uemCompanyRole.getUemCompanyRoleId());
                    uemCompanyRoleVO.setUemCompanyId(uemCompanyRole.getUemCompanyId());
                    uemCompanyRoleVO.setIsSelected(Boolean.TRUE);
                    uemCompanyRoleVO.setIsDefault(uemCompanyRole.getIsDefault());
                    break;
                }
            }
        }

        // 按应用名称进行分组
        Map<String, List<UemCompanyRoleVO>> allApplicationRoleMap = allCompanyRoleList.stream().collect(Collectors.groupingBy(UemCompanyRoleVO::getApplicationName));
        // 获取权限配置列表
        List<UemCompanyConfigPermissionVO> allPermissionList = new ArrayList<>();
        for (Map.Entry<String, List<UemCompanyRoleVO>> entry : allApplicationRoleMap.entrySet()) {
            UemCompanyConfigPermissionVO permissionVO = new UemCompanyConfigPermissionVO();
            permissionVO.setApplicationName(entry.getKey());
            permissionVO.setUemCompanyRoleList(entry.getValue());
            allPermissionList.add(permissionVO);
        }
        reviewCompanyDTO.setAllPermissionList(allPermissionList);

        // 获取已选择的角色，按应用名称分组
        Map<String, List<UemCompanyRoleVO>> selectedApplicationRoleMap = allCompanyRoleList.stream().filter(UemCompanyRoleVO::getIsSelected).collect(Collectors.groupingBy(UemCompanyRoleVO::getApplicationName));
        // 获取权限配置列表
        List<UemCompanyConfigPermissionVO> selectedPermissionList = new ArrayList<>();
        for (Map.Entry<String, List<UemCompanyRoleVO>> entry : selectedApplicationRoleMap.entrySet()) {
            UemCompanyConfigPermissionVO permissionVO = new UemCompanyConfigPermissionVO();
            permissionVO.setApplicationName(entry.getKey());
            permissionVO.setUemCompanyRoleList(entry.getValue());
            selectedPermissionList.add(permissionVO);
        }
        reviewCompanyDTO.setSelectedPermissionList(selectedPermissionList);
    }

    /**
     * @Author:chenxf
     * @Description: 保存疫苗企业信息接口
     * @Date: 17:50 2021/1/20
     * @Param: [vaccineCompanyDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> saveCompanyForVaccineCompany(VaccineCompanyDTO vaccineCompanyDTO) {
        if (Objects.isNull(vaccineCompanyDTO)) {
            return QueryResultUtils.getFailData("入参为空，保存失败");
        }
        if (Objects.isNull(vaccineCompanyDTO.getCompanyNameCn())) {
            return QueryResultUtils.getFailData("企业名称为空，保存失败");
        }
        if (Objects.isNull(vaccineCompanyDTO.getOrganizationCode())) {
            return QueryResultUtils.getFailData("统一信用代码为空为空，保存失败");
        }
        if (Objects.isNull(vaccineCompanyDTO.getFileUrlId())) {
            return QueryResultUtils.getFailData("企业证书为空，保存失败");
        }
        List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select().where(
                QUemCompany.organizationCode.eq$(vaccineCompanyDTO.getOrganizationCode())
                        .and(QUemCompany.companyNameCn.eq$(vaccineCompanyDTO.getCompanyNameCn()))).execute();
        if (CollectionUtils.isNotEmpty(uemCompanyList)) {
            return QueryResultUtils.getFailData("企业名称、统一信用代码不唯一，保存失败");
        }
        UemCompany uemCompany = new UemCompany();
        uemCompany.setCompanyNameCn(vaccineCompanyDTO.getCompanyNameCn());
        uemCompany.setCompanyNameEn(vaccineCompanyDTO.getCompanyNameEn());
        uemCompany.setFileUrlId(vaccineCompanyDTO.getFileUrlId());
        uemCompany.setOrganizationCode(vaccineCompanyDTO.getOrganizationCode());
        uemCompany.setContact(vaccineCompanyDTO.getContact());
        uemCompany.setContactTel(vaccineCompanyDTO.getContactTel());
        uemCompany.setInternationalBusinessFileUrl(vaccineCompanyDTO.getInternationalBusinessFileUrl());
        uemCompany.setAuditStatus(CodeFinal.AUDIT_STATUS_ONE);
        uemCompany.setIsValid(true);
        // 设置承运商企业类型-非注册
        uemCompany.setCarrierType(GlobalEnum.CarrierType.NO_REGISTER.getCode());
        uemCompany.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        QUemCompany.uemCompany.save(uemCompany);
        return QueryResultUtils.getSuccessData("保存成功", null);
    }

    /**
     * @Author:chenxf
     * @Description: 调用账号权限服务上传文件接口
     * @Date: 21:01 2021/1/21
     * @Param: [file, fileType]
     * @Return:java.lang.String
     */
    @Override
    public ResultHelper<Object> uploadCompanyFile(MultipartFile file, String fileType) {
        try {
            FastDfsUploadResult result = shareFileInterface.uploadExternalFile(MessageUtil.getApplicationCode(), fileType, file.getName(), file);
            if (Objects.nonNull(result)) {
                String successCode = "0";
                if (successCode.equals(result.getResultCode())) {
                    log.info("调度账号权限系统文件上传" + result.getFileKey());
                    return CommonResult.getSuccessResultData(result.getFileKey());
                } else {
                    return CommonResult.getFaildResultData(result.getResultMsg());
                }
            }
        } catch (Exception e) {
            log.info("账号权限系统调度文件上传失败" + e.getMessage());
            return CommonResult.getFaildResultData("账号权限系统调度文件上传失败");
        }
        log.info("调用账号权限系统文件上传返回null");
        return CommonResult.getFaildResultData("调用账号权限系统文件上传返回null");
    }

    /**
     * @Author:chenxf
     * @Description: 疫苗承运商企业查询接口
     * @Date: 17:08 2021/2/7
     * @Param: [companyNameCn]
     * @Return:java.util.List<com.share.auth.domain.VaccineCompanyDTO>
     */
    @Override
    public List<VaccineCompanyDTO> queryVaccineCompany(String companyNameCn) {
        return QUemCompany.uemCompany.select(
                QUemCompany.companyNameCn,
                QUemCompany.companyNameEn,
                QUemCompany.organizationCode,
                QUemCompany.fileUrlId,
                QUemCompany.contact,
                QUemCompany.contactTel,
                QUemCompany.uemCompanyId
        ).where(
                QUemCompany.companyNameCn._like$_(companyNameCn)
        ).mapperTo(VaccineCompanyDTO.class).execute();
    }

    /**
     * @Author:chenxf
     * @Description: 递归方法，将企业下级企业全部查询出来，包括下级企业的下级企业
     * @Date: 14:58 2020/11/3
     * @Param: [queryCompanyTreeTableDTO]
     * @Return:void
     */
    @Deprecated
    private void searchChildrenCompany(QueryCompanyTreeTableDTO queryCompanyTreeTableDTO, CompanyTreeTableQueryVO companyTreeTableQueryVO) {
        companyTreeTableQueryVO.setUemCompanyId(queryCompanyTreeTableDTO.getUemCompanyId());
        List<QueryCompanyTreeTableDTO> queryCompanyTreeTableDTOList = QUemCompany.uemCompany.select(
                QUemCompany.uemCompany.fieldContainer()
        ).where(
                QUemCompany.belongCompany.eq(":uemCompanyId")
                        .and(QUemCompany.auditStatus.eq(":auditStatus"))
                        .and(QUemCompany.isValid.eq(":isValid"))
        ).mapperTo(QueryCompanyTreeTableDTO.class)
                .execute(companyTreeTableQueryVO);
        if (CollectionUtils.isNotEmpty(queryCompanyTreeTableDTOList)) {
            for (QueryCompanyTreeTableDTO childrenCompanyTreeTableDTO : queryCompanyTreeTableDTOList) {
                searchChildrenCompany(childrenCompanyTreeTableDTO, companyTreeTableQueryVO);
            }
            queryCompanyTreeTableDTO.setHaveChildCompany(true);
            queryCompanyTreeTableDTO.setChildrenList(queryCompanyTreeTableDTOList);
        } else {
            queryCompanyTreeTableDTO.setHaveChildCompany(false);
        }
    }

    /**
     * @Author:chenxf
     * @Description: 处理查询到的企业的下级企业
     * @Date: 17:41 2020/12/2
     * @Param: [queryCompanyTreeTableDTOList, companyTreeTableQueryVO]
     * @Return:java.util.List<com.share.auth.model.vo.QueryCompanyTreeTableDTO>
     */
    private List<QueryCompanyTreeTableDTO> searchChildrenCompany(List<QueryCompanyTreeTableDTO> queryCompanyTreeTableDTOList, CompanyTreeTableQueryVO companyTreeTableQueryVO) {
        List<QueryCompanyTreeTableDTO> allCompanyList = QUemCompany.uemCompany.select(
                QUemCompany.uemCompany.fieldContainer()
        ).where(
                QUemCompany.auditStatus.eq(AUDIT_STATUS_PLACEHOLDER)
                        .and(QUemCompany.isValid.eq(IS_VALID_PLACEHOLDER))
        ).mapperTo(QueryCompanyTreeTableDTO.class)
                .execute(companyTreeTableQueryVO);
        Map<Long, List<QueryCompanyTreeTableDTO>> map = new HashMap<>(16);
        allCompanyList.stream().forEach(
                queryCompanyTreeTableDTO -> {
                    if (Objects.nonNull(queryCompanyTreeTableDTO.getBelongCompany())) {
                        List<QueryCompanyTreeTableDTO> list = map.get(queryCompanyTreeTableDTO.getBelongCompany()) == null ? Lists.newArrayList() : map.get(queryCompanyTreeTableDTO.getBelongCompany());
                        list.add(queryCompanyTreeTableDTO);
                        map.put(queryCompanyTreeTableDTO.getBelongCompany(), list);
                    }
                }
        );
        dealWithCompany(queryCompanyTreeTableDTOList, map);
        return queryCompanyTreeTableDTOList;
    }

    /**
     * @Author:chenxf
     * @Description: 递归方法，循环处理子企业的子企业信息
     * @Date: 17:41 2020/12/2
     * @Param: [queryCompanyTreeTableDTOList, map]
     * @Return:java.util.List<com.share.auth.model.vo.QueryCompanyTreeTableDTO>
     */
    private List<QueryCompanyTreeTableDTO> dealWithCompany(List<QueryCompanyTreeTableDTO> queryCompanyTreeTableDTOList, Map<Long, List<QueryCompanyTreeTableDTO>> map) {
        queryCompanyTreeTableDTOList.stream().forEach(parent -> {
            List<QueryCompanyTreeTableDTO> children = map.get(parent.getUemCompanyId());
            if (CollectionUtils.isNotEmpty(children)) {
                children = dealWithCompany(children, map);
                parent.setChildrenList(children);
                parent.setHaveChildCompany(true);
            } else {
                parent.setHaveChildCompany(false);
            }
        });
        return queryCompanyTreeTableDTOList;
    }

    /**
     * @param companyNameCn
     * @Author:cxq
     * @Description: 疫苗企业根据名称查询接口
     * @Date: 18:15 2021/1/30
     * @Param: companyNameCn
     * @Return:List<VaccineCompanyDTO>
     */
    @Override
    public List<VaccineCompanyDTO> getVaccineCompanyByCompanyNameCn(String companyNameCn) {
        return QUemCompany.uemCompany.select(
                QUemCompany.companyNameCn,
                QUemCompany.companyNameEn,
                QUemCompany.organizationCode,
                QUemCompany.fileUrlId,
                QUemCompany.contact,
                QUemCompany.contactTel,
                QUemCompany.uemCompanyId).where(QUemCompany.carrierType.eq$("1").and(QUemCompany.isValid.eq$(true).and(QUemCompany.companyNameCn.eq$(companyNameCn)))).mapperTo(VaccineCompanyDTO.class).execute();
    }

    /**
     * @Author:chenxf
     * @Description: 根据统一社会信用代码查询疫苗承运商企业
     * @Date: 10:01 2021/2/4
     * @Param: [organizationCode]
     * @Return:com.share.auth.domain.VaccineCompanyDTO
     */
    @Override
    public VaccineCompanyDTO getVaccineCompanyByOrgCode(String organizationCode) {
        return QUemCompany.uemCompany.selectOne(
                QUemCompany.companyNameCn,
                QUemCompany.companyNameEn,
                QUemCompany.organizationCode,
                QUemCompany.fileUrlId,
                QUemCompany.contact,
                QUemCompany.contactTel,
                QUemCompany.uemCompanyId
        ).where(
                QUemCompany.isValid.eq$(true)
                        .and(QUemCompany.organizationCode.eq$(organizationCode))
        ).mapperTo(VaccineCompanyDTO.class).execute();
    }

    @Override
    public OperateResultVO operateUemCompany(UemCompanyOperateVO uemCompanyOperateVO) {
        // 校验
        String validStr = this.validOperateUemCompany(uemCompanyOperateVO);
        if (StringUtils.isNotBlank(validStr)) {
            return OperateResultVO.getParamErrorMessage(validStr, uemCompanyOperateVO.getOptionType(), uemCompanyOperateVO.getOrgCode(), null);
        }
        // 删除操作
        if (Objects.equals(uemCompanyOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.DELETE.getCode())) {
            // 获取企业信息
            UemCompany sourceUemCompany = QUemCompany.uemCompany.selectOne(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemCompanyOperateVO.getOrgCode())).execute();
            // 删除企业、企业用户类型
            QUemCompany.uemCompany.delete().id(sourceUemCompany.getUemCompanyId()).execute();
            QUemCustomerType.uemCustomerType.delete().where(QUemCustomerType.uemCompanyId.eq$(sourceUemCompany.getUemCompanyId())).execute();
        }
        // 新增操作
        if (Objects.equals(uemCompanyOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.INSERT.getCode())) {
            // 设置基础信息
            UemCompany uemCompany = new UemCompany();
            this.setUemCompanyByOperateVO(uemCompanyOperateVO, uemCompany);
            uemCompany.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            QUemCompany.uemCompany.save(uemCompany);
            // 新增企业类型
            this.insertM1CustomerType(uemCompany.getUemCompanyId());
        }
        // 修改操作
        if (Objects.equals(uemCompanyOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.UPDATE.getCode())) {
            // 获取企业信息
            UemCompany sourceUemCompany = QUemCompany.uemCompany.selectOne(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemCompanyOperateVO.getOrgCode())).execute();
            UemCompany uemCompany = new UemCompany();
            BeanUtils.copyProperties(sourceUemCompany, uemCompany);
            // 设置基础信息
            this.setUemCompanyByOperateVO(uemCompanyOperateVO, uemCompany);
            // 更新企业
            uemCompany.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            QUemCompany.uemCompany.save(uemCompany);
            // 修改企业名称，更新下级企业的上级企业名称
            if (!Objects.equals(sourceUemCompany.getCompanyNameCn(), uemCompany.getCompanyNameCn())) {
                this.updateBelongCompanyName(uemCompany);
            }
            // 修改上级企业时，更新所有下级企业的最上级企业id
            if (!Objects.equals(sourceUemCompany.getTopCompany(), uemCompany.getTopCompany())) {
                this.updateTopCompany(uemCompany, CodeFinal.RECURSION_START_LEVEL);
            }
        }
        return OperateResultVO.getSuccessMessage(uemCompanyOperateVO.getOptionType(), uemCompanyOperateVO.getOrgCode(), null);
    }

    /**
     * 更新下级企业的上级企业名称
     *
     * @param uemCompany 上级企业信息
     */
    private void updateBelongCompanyName(UemCompany uemCompany) {
        // 下级企业
        List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.belongCompany.eq$(uemCompany.getUemCompanyId())).execute();
        if (CollectionUtils.isEmpty(uemCompanyList)) {
            return;
        }
        // 更新上级企业名称
        for (UemCompany company : uemCompanyList) {
            company.setBelongCompanyName(uemCompany.getCompanyNameCn());
            company.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        }
        QUemCompany.uemCompany.save(uemCompanyList);
    }

    /**
     * 更新所有下级企业的顶级上级企业id
     *
     * @param uemCompany 上级企业信息
     * @param level      递归层级
     */
    private void updateTopCompany(UemCompany uemCompany, int level) {
        // 下级企业
        List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.belongCompany.eq$(uemCompany.getUemCompanyId())).execute();
        if (CollectionUtils.isEmpty(uemCompanyList)) {
            return;
        }
        // 递归层数加1
        level++;
        // 最大递归层数小于100，避免内存溢出
        if (level == CodeFinal.RECURSION_END_LEVEL) {
            return;
        }
        // 更新上级企业id
        for (UemCompany company : uemCompanyList) {
            company.setTopCompany(Objects.isNull(uemCompany.getTopCompany()) ? uemCompany.getUemCompanyId() : uemCompany.getTopCompany());
            company.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            this.updateTopCompany(company, level);
        }
        // 更新
        QUemCompany.uemCompany.save(uemCompanyList);
    }

    /**
     * 新增组织机构类型(LOGIN_MECHANISM_TYPE)、部委组织（M1）
     *
     * @param uemCompanyId 企业id
     */
    private void insertM1CustomerType(Long uemCompanyId) {
        UemCustomerType uemCustomerType = new UemCustomerType();
        uemCustomerType.setUemCompanyId(uemCompanyId);
        uemCustomerType.setCompanyTypeCode("LOGIN_MECHANISM_TYPE");
        uemCustomerType.setSelectedItemCode("M1");
        uemCustomerType.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        QUemCustomerType.uemCustomerType.save(uemCustomerType);
    }

    /**
     * 根据组织机构VO设置企业信息
     *
     * @param uemCompanyOperateVO 组织机构VO
     * @param uemCompany          企业信息
     */
    private void setUemCompanyByOperateVO(UemCompanyOperateVO uemCompanyOperateVO, UemCompany uemCompany) {
        // 新增，设置默认信息信息
        if (Objects.equals(uemCompanyOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.INSERT.getCode())) {
            // 默认值，数据来源国家综合交通运输信息平台、默认审批通过
            uemCompany.setDataSource(CodeFinal.DATA_SOURCE_THREE);
            uemCompany.setIsSuperior(true);
            uemCompany.setAuditStatus(GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode());
            uemCompany.setAuditTime(new Date());
        }
        // 组织机构代码
        uemCompany.setOrgCode(uemCompanyOperateVO.getOrgCode());
        // 组织机构名称
        uemCompany.setCompanyNameCn(uemCompanyOperateVO.getOrgName());
        // 组织机构顺序
        uemCompany.setOrgSeq(uemCompanyOperateVO.getOrgSeq());
        // 上级企业
        String superiorCode = "root";
        if (!StringUtils.equals(superiorCode, uemCompanyOperateVO.getParentOrgCode())) {
            UemCompany superior = QUemCompany.uemCompany.selectOne(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemCompanyOperateVO.getParentOrgCode())).execute();
            uemCompany.setBelongCompany(superior.getUemCompanyId());
            uemCompany.setBelongCompanyName(superior.getCompanyNameCn());
            // 是否最上级企业
            if (Objects.isNull(superior.getBelongCompany())) {
                // 最上级企业
                uemCompany.setTopCompany(superior.getUemCompanyId());
            } else {
                // 赋值上级企业的最上级企业
                uemCompany.setTopCompany(superior.getTopCompany());
            }
        }

        // 修改启用/禁用状态
        Boolean invalid = GlobalEnum.OrgStatusEnum.getInvalidByCode(uemCompanyOperateVO.getOrgStatus());
        if (!Objects.equals(invalid, uemCompany.getIsValid())) {
            uemCompany.setIsValid(invalid);
            uemCompany.setInvalidTime(new Date());
        }
    }


    /**
     * 校验组织机构数据
     *
     * @param uemCompanyOperateVO 组织机构
     * @return 校验结果
     */
    private String validOperateUemCompany(UemCompanyOperateVO uemCompanyOperateVO) {
        String validOperateUemCompanyNotNull = this.validOperateUemCompanyNotNull(uemCompanyOperateVO);
        if (StringUtils.isNotBlank(validOperateUemCompanyNotNull)) {
            return validOperateUemCompanyNotNull;
        }
        // 校验AccessKey，OrgCode、Time、Key拼接后的字符串经过MD5加密
        String accessKeyDecode = uemCompanyOperateVO.getOrgCode() + uemCompanyOperateVO.getTime() + aesSecretKey;
        String accessKeyEncode = MD5EnCodeUtils.MD5EnCode(accessKeyDecode);
        if (!StringUtils.equals(accessKeyEncode, uemCompanyOperateVO.getAccessKey())) {
            return "Accesskey值不正确";
        }
        // 校验OptionType
        String optionTypeName = GlobalEnum.OptionTypeEnum.getNameByCode(uemCompanyOperateVO.getOptionType());
        if (StringUtils.isBlank(optionTypeName)) {
            return "OptionType值不正确";
        }
        // 校验OrgCode
        // 新增组织机构，校验OrgCode唯一
        if (Objects.equals(uemCompanyOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.INSERT.getCode())) {
            List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemCompanyOperateVO.getOrgCode())).execute();
            if (CollectionUtils.isNotEmpty(uemCompanyList)) {
                return "OptionType为1时，OrgCode不能重复";
            }
        }
        // 校验修改组织机构
        String validOperateUemCompanyUpdate = this.validOperateUemCompanyUpdate(uemCompanyOperateVO);
        if (StringUtils.isNotBlank(validOperateUemCompanyUpdate)) {
            return validOperateUemCompanyUpdate;
        }
        // 校验删除组织机构数据
        String validOperateUemCompanyDelete = this.validOperateUemCompanyDelete(uemCompanyOperateVO);
        if (StringUtils.isNotBlank(validOperateUemCompanyDelete)) {
            return validOperateUemCompanyDelete;
        }
        // 校验上级组织代码ParentOrgCode，最上级组织代码root，不需要校验
        String superiorCode = "root";
        if (!StringUtils.equals(superiorCode, uemCompanyOperateVO.getParentOrgCode())) {
            // 上级组织是否存在
            List<UemCompany> superiorList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemCompanyOperateVO.getParentOrgCode())).execute();
            if (CollectionUtils.isEmpty(superiorList)) {
                return "ParentOrgCode值不正确，不存在该上级组织机构";
            }
        }
        // 校验OrgStatus
        String name = GlobalEnum.OrgStatusEnum.getNameByCode(uemCompanyOperateVO.getOrgStatus());
        if (StringUtils.isBlank(name)) {
            return "OrgStatus值不正确";
        }
        return null;
    }

    /**
     * 校验组织机构数据参数非空
     *
     * @param uemCompanyOperateVO 组织机构
     * @return 校验结果
     */
    private String validOperateUemCompanyNotNull(UemCompanyOperateVO uemCompanyOperateVO) {
        // 校验必填
        if (StringUtils.isBlank(uemCompanyOperateVO.getAccessKey())) {
            return "Accesskey不能为空";
        }
        if (StringUtils.isBlank(uemCompanyOperateVO.getOptionType())) {
            return "OptionType不能为空";
        }
        if (StringUtils.isBlank(uemCompanyOperateVO.getOrgCode())) {
            return "OrgCode不能为空";
        }
        if (StringUtils.isBlank(uemCompanyOperateVO.getOrgName())) {
            return "OrgName不能为空";
        }
        if (StringUtils.isBlank(uemCompanyOperateVO.getParentOrgCode())) {
            return "ParentOrgCode不能为空";
        }
        if (StringUtils.isBlank(uemCompanyOperateVO.getOrgStatus())) {
            return "OrgStatus不能为空";
        }
        if (StringUtils.isBlank(uemCompanyOperateVO.getOrgSeq())) {
            return "OrgSeq不能为空";
        }
        if (Objects.isNull(uemCompanyOperateVO.getTime())) {
            return "Time不能为空";
        }
        return null;
    }

    /**
     * 校验修改组织机构数据
     *
     * @param uemCompanyOperateVO 组织机构
     * @return 校验结果
     */
    private String validOperateUemCompanyUpdate(UemCompanyOperateVO uemCompanyOperateVO) {
        // 修改组织机构，校验OrgCode是否存在
        if (Objects.equals(uemCompanyOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.UPDATE.getCode())) {
            List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemCompanyOperateVO.getOrgCode())).execute();
            if (CollectionUtils.isEmpty(uemCompanyList)) {
                return "OptionType为2时，OrgCode不存在数据";
            }
            // 因OrgCode值唯一，删除最大记录数量不能大于1
            int updateCompanyMaxSize = 1;
            if (uemCompanyList.size() > updateCompanyMaxSize) {
                return "OptionType为2时，OrgCode存在多条数据";
            }
        }
        return null;
    }

    /**
     * 校验删除组织机构数据
     *
     * @param uemCompanyOperateVO 组织机构
     * @return 校验结果
     */
    private String validOperateUemCompanyDelete(UemCompanyOperateVO uemCompanyOperateVO) {
        // 删除组织机构，校验OrgCode是否存在
        if (Objects.equals(uemCompanyOperateVO.getOptionType(), GlobalEnum.OptionTypeEnum.DELETE.getCode())) {
            List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.orgCode.eq$(uemCompanyOperateVO.getOrgCode())).execute();
            if (CollectionUtils.isEmpty(uemCompanyList)) {
                return "OptionType为3时，OrgCode不存在数据";
            }
            // 因OrgCode值唯一，删除最大记录数量不能大于1
            int deleteCompanyMaxSize = 1;
            if (uemCompanyList.size() > deleteCompanyMaxSize) {
                return "OptionType为3时，OrgCode存在多条数据";
            }
            // 查询下级组织
            List<UemCompany> subordinateList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer()).where(QUemCompany.belongCompany.eq$(uemCompanyList.get(0).getUemCompanyId())).execute();
            if (CollectionUtils.isNotEmpty(subordinateList)) {
                return "OptionType为3时，OrgCode存在下级组织机构";
            }
            // 查询绑定用户
            List<UemUser> bindUserList = QUemUser.uemUser.select(QUemUser.uemUser.fieldContainer()).where(QUemUser.blindCompanny.eq$(uemCompanyList.get(0).getUemCompanyId())).execute();
            if (CollectionUtils.isNotEmpty(bindUserList)) {
                return "OptionType为3时，OrgCode存在绑定用户";
            }
        }
        return null;
    }

    @Override
    public List<UemCompanyVO> queryCompanyByRule(String companyTypeCode, Boolean isMatch, List<String> itemCodes) {
        if (StringUtils.isBlank(companyTypeCode)) {
            log.info("根据规则查询企业，参数companyTypeCode不能为空");
            return null;
        }
        // 默认false
        if (Objects.isNull(isMatch)) {
            isMatch = false;
        }
        if (CollectionUtils.isEmpty(itemCodes)) {
            log.info("根据规则查询企业，参数itemCodes不能为空");
            return null;
        }
        log.info("根据规则查询企业查询参数companyTypeCode：{}，isMatch：{}， itemCodes：{}", companyTypeCode, isMatch, itemCodes.toString());
        // 匹配的企业信息
        List<UemCompanyVO> matchCompanyList = QUemCompany.uemCompany
                .select(QUemCompany.uemCompany.fieldContainer())
                .where(QUemCompany.auditStatus.eq$(GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode())
                        .and(QUemCompany.isValid.eq$(true))
                        .and(QUemCompany.carrierType.eq$(GlobalEnum.CarrierType.REGISTER.getCode()).or(QUemCompany.carrierType.isNull()))
                        .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull()))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).eq$(companyTypeCode))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).in$(itemCodes)))
                .groupBy(QUemCompany.uemCompanyId.getFieldName())
                .mapperTo(UemCompanyVO.class)
                .execute();
        // 查询匹配信息
        if (isMatch) {
            return matchCompanyList;
        }
        // 匹配的企业id
        List<Long> companyId = matchCompanyList.stream().map(UemCompanyVO::getUemCompanyId).collect(Collectors.toList());
        // 查询不匹配信息
        return QUemCompany.uemCompany
                .select(QUemCompany.uemCompany.fieldContainer())
                .where(QUemCompany.auditStatus.eq$(GlobalEnum.AuditStatusEnum.AUDIT_PASS.getCode())
                        .and(QUemCompany.isValid.eq$(true))
                        .and(QUemCompany.carrierType.eq$(GlobalEnum.CarrierType.REGISTER.getCode()).or(QUemCompany.carrierType.isNull()))
                        .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull()))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).eq$(companyTypeCode))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).in$("S1", "S2", "S3", "S4", "S5", "S6"))
                        .and(QUemCompany.uemCompanyId.notIn(":companyId")))
                .groupBy(QUemCompany.uemCompanyId.getFieldName())
                .mapperTo(UemCompanyVO.class)
                .execute(ImmutableMap.of("companyId", companyId));
    }


    @Override
    public Page<UemCompanyVO> queryUemCompanyByCompanyType(QueryUemCompanyConditionVO queryUemCompanyConditionVO) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryUemCompanyConditionVO.getKeyword())) {
            queryUemCompanyConditionVO.setKeyword("%" + queryUemCompanyConditionVO.getKeyword() + "%");
        }
        return QUemCompany.uemCompany
                .select(QUemCompany.uemCompany.fieldContainer())
                .where(QUemCompany.auditStatus.eq$("1")
                        .and(QUemCompany.isValid.eq$(true))
                        .and(QUemCompany.carrierType.eq$("0").or(QUemCompany.carrierType.isNull()))
                        .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull()))
                        .and(QUemCompany.companyNameCn.like(":keyword"))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).eq(":companyTypeCode"))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).in(":itemCodes"))
                        .and(QUemCompany.uemCompanyId.notIn(":notInUemCompanyIds")))
                .groupBy(QUemCompany.uemCompanyId)
                .paging(Objects.isNull(queryUemCompanyConditionVO.getCurrentPage()) ? 1 : queryUemCompanyConditionVO.getCurrentPage(), Objects.isNull(queryUemCompanyConditionVO.getPageSize()) ? 10 : queryUemCompanyConditionVO.getPageSize())
                .mapperTo(UemCompanyVO.class)
                .execute(queryUemCompanyConditionVO);
    }

    @Override
    public List<Long> querySubordinateCompanyIds(Long companyId, List<String> selectedItemCodes) {
        // 判空
        if (Objects.isNull(companyId)) {
            log.error("入参companyId企业id不能为空");
            throw new BusinessRuntimeException("入参companyId企业id不能为空");
        }
        // 判空
        if (CollectionUtils.isEmpty(selectedItemCodes)) {
            log.error("selectedItemCodes企业类型选中项不能为空");
            throw new BusinessRuntimeException("selectedItemCodes企业类型选中项不能为空");
        }

        // 下级企业查询条件
        QuerySubordinateCompanyIdsConditionVO conditionVO = new QuerySubordinateCompanyIdsConditionVO();
        // 下级企业id集合
        List<Long> subordinateCompanyIds = new ArrayList<>();
        // 港口、地市单位获取下级企业id
        if (selectedItemCodes.contains(CodeFinal.LOGIN_LOGISTICS_SUPPLY_S7) || selectedItemCodes.contains(CodeFinal.LOGIN_MECHANISM_TYPE_M3)) {
            // 递归获取下级企业
            List<UemCompany> subordinateCompany = new ArrayList<>();
            this.querySubordinateCompany(companyId, subordinateCompany, 0);
            // 判空
            if (CollectionUtils.isNotEmpty(subordinateCompany)) {
                subordinateCompanyIds = subordinateCompany.stream().map(UemCompany::getUemCompanyId).collect(Collectors.toList());
            }
            // 查询当前企业
            subordinateCompanyIds.add(companyId);
            conditionVO.setCompanyIds(subordinateCompanyIds);
        }
        // 查询港口
        if (selectedItemCodes.contains(CodeFinal.LOGIN_LOGISTICS_SUPPLY_S7)) {
            conditionVO.setSupplyCode(CodeFinal.LOGIN_LOGISTICS_SUPPLY);
            conditionVO.setS7(CodeFinal.LOGIN_LOGISTICS_SUPPLY_S7);
        }
        // 查询地市单位
        if (selectedItemCodes.contains(CodeFinal.LOGIN_MECHANISM_TYPE_M3)) {
            conditionVO.setMechanismCode(CodeFinal.LOGIN_MECHANISM_TYPE);
            conditionVO.setM3(CodeFinal.LOGIN_MECHANISM_TYPE_M3);
        }
        // 查询粮企
        if (selectedItemCodes.contains(CodeFinal.LOGIN_LOGISTICS_REQUIREMENT_R5)) {
            conditionVO.setRequirementCode(CodeFinal.LOGIN_LOGISTICS_REQUIREMENT);
            conditionVO.setR5(CodeFinal.LOGIN_LOGISTICS_REQUIREMENT_R5);
        }

        // 查询企业
        List<UemCompany> uemCompanyList = client.customCommand("querySubordinateCompanyIds")
                .tag("query")
                .mapperTo(UemCompany.class)
                .execute(conditionVO);
        return uemCompanyList.stream().map(UemCompany::getUemCompanyId).collect(Collectors.toList());
    }

    @Override
    public Map<Long, String> querySuperiorProvinceCompanyProvince(List<Long> companyIds) {
        // 判空
        if (CollectionUtils.isEmpty(companyIds)) {
            log.error("companyIds企业id集合不能为空");
            throw new BusinessRuntimeException("companyIds企业id集合不能为空");
        }
        // 查询企业
        List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer())
                .where(QUemCompany.uemCompanyId.in$(companyIds)).execute();
        // 判断企业id是否都存在企业信息
        if (CollectionUtils.isEmpty(uemCompanyList)) {
            log.error("companyIds企业id集合，不存在对应企业信息");
            throw new BusinessRuntimeException("companyIds企业id集合，不存在对应企业信息");
        }
        // 查询为粮企的企业
        List<CompanyCustomTypeVO> r5CompanyList = this.queryR5CompanyList(companyIds);
        // 粮企企业id集合
        Set<Long> r5CompanyIdSet = r5CompanyList.stream().map(CompanyCustomTypeVO::getUemCompanyId).collect(Collectors.toSet());

        // 企业id-上级省厅企业省份对应关系
        Map<Long, String> companyIdProvinceMap = new HashMap<>(16);
        // 获取上级省厅企业
        for (UemCompany uemCompany : uemCompanyList) {
            // 粮企企业，获取当前企业的省份
            if (r5CompanyIdSet.contains(uemCompany.getUemCompanyId())) {
                companyIdProvinceMap.put(uemCompany.getUemCompanyId(), uemCompany.getLocProvinceName());
                continue;
            }
            // 其他企业，递归获取上级省厅企业
            CompanyCustomTypeVO companyCustomTypeVO = this.getSuperiorProvinceCompany(uemCompany.getBelongCompany(), 0);
            companyIdProvinceMap.put(uemCompany.getUemCompanyId(), Objects.isNull(companyCustomTypeVO) ? null : companyCustomTypeVO.getLocProvinceName());
        }
        return companyIdProvinceMap;
    }

    /**
     * 查询企业类型为粮企的企业
     *
     * @param companyIds 企业id
     * @return 粮企企业
     */
    private List<CompanyCustomTypeVO> queryR5CompanyList(List<Long> companyIds) {
        // 判空
        if (Objects.isNull(companyIds)) {
            companyIds = new ArrayList<>();
        }
        return QUemCompany.uemCompany.select(
                QUemCompany.uemCompanyId,
                QUemCompany.belongCompany,
                QUemCompany.belongCompanyName,
                QUemCompany.topCompany,
                QUemCompany.locProvinceCode,
                QUemCompany.locProvinceName,
                QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).as("companyTypeCode"),
                QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).as("selectedItemCode")
        ).where(
                QUemCompany.auditStatus.eq$("1")
                        .and(QUemCompany.isValid.eq$(true))
                        .and(QUemCompany.carrierType.eq$("0").or(QUemCompany.carrierType.isNull()))
                        .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull()))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).eq$(CodeFinal.LOGIN_LOGISTICS_REQUIREMENT))
                        .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).eq$(CodeFinal.LOGIN_LOGISTICS_REQUIREMENT_R5))
                        .and(QUemCompany.uemCompanyId.in(":companyIds"))
        ).mapperTo(CompanyCustomTypeVO.class).execute(ImmutableMap.of("companyIds", companyIds));

    }

    /**
     * 递归获取上级省厅企业
     *
     * @param superiorCompanyId 上级企业id
     * @param level             递归层级（最多100层）
     * @return 上级省厅企业
     */
    private CompanyCustomTypeVO getSuperiorProvinceCompany(Long superiorCompanyId, int level) {
        // 上级企业id为空
        if (Objects.isNull(superiorCompanyId)) {
            log.info("上级企业id为空");
            return null;
        }
        int maxLevel = 100;
        if (level == maxLevel) {
            return null;
        }
        level++;
        // 查询上级企业类型
        List<CompanyCustomTypeVO> companyList = QUemCompany.uemCompany.select(
                QUemCompany.uemCompanyId,
                QUemCompany.belongCompany,
                QUemCompany.belongCompanyName,
                QUemCompany.topCompany,
                QUemCompany.locProvinceCode,
                QUemCompany.locProvinceName,
                QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).as("companyTypeCode"),
                QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).as("selectedItemCode")
        ).where(
                QUemCompany.auditStatus.eq$("1")
                        .and(QUemCompany.isValid.eq$(true))
                        .and(QUemCompany.carrierType.eq$("0").or(QUemCompany.carrierType.isNull()))
                        .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull()))
                        .and(QUemCompany.uemCompanyId.eq$(superiorCompanyId)))
                .mapperTo(CompanyCustomTypeVO.class)
                .execute();
        // 判空
        if (CollectionUtils.isEmpty(companyList)) {
            return null;
        }
        // 判断是否省厅企业
        List<CompanyCustomTypeVO> provinceCompanyList = companyList.stream()
                .filter(vo -> Objects.equals(vo.getCompanyTypeCode(), CodeFinal.LOGIN_MECHANISM_TYPE) && Objects.equals(vo.getSelectedItemCode(), CodeFinal.LOGIN_MECHANISM_TYPE_M2))
                .collect(Collectors.toList());
        // 返回省厅企业
        if (CollectionUtils.isNotEmpty(provinceCompanyList)) {
            return provinceCompanyList.get(0);
        }
        // 上级企业不是省厅企业，递归查询上级企业
        return this.getSuperiorProvinceCompany(companyList.get(0).getBelongCompany(), level);
    }


    @Override
    public List<String> querySubordinateProvinceCompanyProvince(Long companyId) {
        if (Objects.isNull(companyId)) {
            log.error("companyId企业id不能为空");
            throw new BusinessRuntimeException("companyId企业id不能为空");
        }
        // 递归获取下级企业
        List<UemCompany> subordinateCompany = new ArrayList<>();
        this.querySubordinateCompany(companyId, subordinateCompany, 0);
        // 企业列表
        List<CompanyCustomTypeVO> provinceCompanyList;
        // 判空
        if (CollectionUtils.isNotEmpty(subordinateCompany)) {
            // 下级企业id
            List<Long> subordinateCompanyId = subordinateCompany.stream().map(UemCompany::getUemCompanyId).collect(Collectors.toList());
            // 获取下级企业为省厅的企业
            provinceCompanyList = QUemCompany.uemCompany.select(
                    QUemCompany.uemCompanyId,
                    QUemCompany.belongCompany,
                    QUemCompany.belongCompanyName,
                    QUemCompany.topCompany,
                    QUemCompany.locProvinceCode,
                    QUemCompany.locProvinceName,
                    QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).as("companyTypeCode"),
                    QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).as("selectedItemCode")
            ).where(
                    QUemCompany.auditStatus.eq$("1")
                            .and(QUemCompany.isValid.eq$(true))
                            .and(QUemCompany.carrierType.eq$("0").or(QUemCompany.carrierType.isNull()))
                            .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull()))
                            .and(QUemCompany.uemCompanyId.in$(subordinateCompanyId))
                            .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.companyTypeCode).eq$(CodeFinal.LOGIN_MECHANISM_TYPE))
                            .and(QUemCompany.uemCustomerType.chain(QUemCustomerType.selectedItemCode).eq$(CodeFinal.LOGIN_MECHANISM_TYPE_M2))
            ).mapperTo(CompanyCustomTypeVO.class).execute();
        } else {
            provinceCompanyList = new ArrayList<>();
        }

        // 查询为粮企的企业
        List<CompanyCustomTypeVO> r5CompanyList = this.queryR5CompanyList(null);
        provinceCompanyList.addAll(r5CompanyList);
        // 获取企业省份
        return provinceCompanyList.stream()
                .map(CompanyCustomTypeVO::getLocProvinceName)
                .filter(org.apache.commons.lang3.StringUtils::isNotBlank)
                .distinct()
                .sorted((v1, v2) -> Collator.getInstance(Locale.CHINA).compare(v1, v2))
                .collect(Collectors.toList());
    }

    /**
     * 查询下级企业
     *
     * @param companyId          企业id
     * @param subordinateCompany 所有下级企业
     * @param level              递归层级
     */
    private void querySubordinateCompany(Long companyId, List<UemCompany> subordinateCompany, int level) {
        // 企业id为空
        if (Objects.isNull(companyId)) {
            log.info("企业id为空");
            return;
        }
        int maxLevel = 100;
        if (level == maxLevel) {
            return;
        }
        level++;
        // companyId的下级企业
        List<UemCompany> uemCompanyList = QUemCompany.uemCompany.select(QUemCompany.uemCompany.fieldContainer())
                .where(
                        QUemCompany.auditStatus.eq$("1")
                                .and(QUemCompany.isValid.eq$(true))
                                .and(QUemCompany.carrierType.eq$("0").or(QUemCompany.carrierType.isNull()))
                                .and(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_ZERO).or(QUemCompany.dataSource.eq$(CodeFinal.DATA_SOURCE_TWO)).or(QUemCompany.dataSource.isNull()))
                                .and(QUemCompany.belongCompany.eq$(companyId))
                )
                .execute();
        subordinateCompany.addAll(uemCompanyList);
        // 递归查询下级企业
        for (UemCompany uemCompany : uemCompanyList) {
            this.querySubordinateCompany(uemCompany.getUemCompanyId(), subordinateCompany, level);
        }
    }

    @Override
    public List<UemCompanyConfigPermissionVO> queryCompanyConfigurablePermissions() {
        // 获取所有可配置角色
        List<UemCompanyRoleVO> uemCompanyRoleList = this.queryAllConfigurableRole();
        // 默认未选择、不是默认角色
        for (UemCompanyRoleVO uemCompanyRoleVO : uemCompanyRoleList) {
            uemCompanyRoleVO.setIsSelected(Boolean.FALSE);
            uemCompanyRoleVO.setIsDefault(Boolean.FALSE);
        }
        // 按应用名称进行分组
        Map<String, List<UemCompanyRoleVO>> applicationRoleMap = uemCompanyRoleList.stream().collect(Collectors.groupingBy(UemCompanyRoleVO::getApplicationName));
        // 获取权限配置列表
        List<UemCompanyConfigPermissionVO> permissionList = new ArrayList<>();
        for (Map.Entry<String, List<UemCompanyRoleVO>> entry : applicationRoleMap.entrySet()) {
            UemCompanyConfigPermissionVO permissionVO = new UemCompanyConfigPermissionVO();
            permissionVO.setApplicationName(entry.getKey());
            permissionVO.setUemCompanyRoleList(entry.getValue());
            permissionList.add(permissionVO);
        }
        return permissionList;
    }

    /**
     * 查询所有可配置的权限
     *
     * @return 所有可配置的权限
     */
    private List<UemCompanyRoleVO> queryAllConfigurableRole() {
        return QSysRole.sysRole.select(
                QSysRole.sysRoleId,
                QSysRole.roleName,
                QSysRole.sysApplicationId,
                QSysRole.sysRole.chain(QSysApplication.applicationName).as("applicationName")
        ).where(
                QSysRole.isValid.eq$(Boolean.TRUE)
                        .and(QSysRole.sysRole.chain(QSysApplication.isValid).eq$(Boolean.TRUE))
        ).mapperTo(UemCompanyRoleVO.class).execute();
    }

    /**
     * 新增企业权限配置
     *
     * @param uemCompanyRoleVoList 企业权限配置列表
     * @param companyId            企业id
     */
    @Override
    public void addUemCompanyRole(List<UemCompanyRoleVO> uemCompanyRoleVoList, Long companyId) {
        // 参数判空
        if (CollectionUtils.isEmpty(uemCompanyRoleVoList) || Objects.isNull(companyId)) {
            return;
        }
        log.info("新增企业权限配置");
        // 获取已选择的权限
        List<UemCompanyRole> companyRoleList = new ArrayList<>();
        for (UemCompanyRoleVO roleVO : uemCompanyRoleVoList) {
            // 未选择，不保存
            if (!Objects.equals(roleVO.getIsSelected(), Boolean.TRUE)) {
                continue;
            }
            // 新增信息
            UemCompanyRole uemCompanyRole = new UemCompanyRole();
            uemCompanyRole.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            // 企业id
            uemCompanyRole.setUemCompanyId(companyId);
            // 应用id
            uemCompanyRole.setSysApplicationId(roleVO.getSysApplicationId());
            // 角色id
            uemCompanyRole.setSysRoleId(roleVO.getSysRoleId());
            // 是否默认
            uemCompanyRole.setIsDefault(Objects.equals(roleVO.getIsDefault(), Boolean.TRUE));
            companyRoleList.add(uemCompanyRole);
        }
        // 新增记录
        QUemCompanyRole.uemCompanyRole.save(companyRoleList);
    }

    @Override
    public CompanyBasicInfoVO queryCompanyBasicInfoByUid() {
        IUser user = defaultUserService.getCurrentLoginUser();
        CompanyBasicInfoVO companyBasicInfoVO = new CompanyBasicInfoVO();
        if (ObjectUtils.isEmpty(user.getUserId())) {
            return companyBasicInfoVO;
        }
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(Long.valueOf(user.getUserId().toString()));
        SysPlatformUser sysPlatformUser = QSysPlatformUser.sysPlatformUser.selectOne().byId(Long.valueOf(user.getUserId().toString()));
        if ((!Objects.isNull(uemUser)) && (!Objects.isNull(uemUser.getBlindCompanny()))) {
            UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(uemUser.getBlindCompanny());
            //企业id
            companyBasicInfoVO.setCompanyId(uemCompany.getUemCompanyId());
            //企业公司名称
            companyBasicInfoVO.setCompanyName(uemCompany.getCompanyNameCn());
            //企业地址
            companyBasicInfoVO.setLocAddress(uemCompany.getLocAddress());
            //企业类型
            companyBasicInfoVO.setOrganizationType(uemCompany.getOrganizationType());
            //法人名称
            companyBasicInfoVO.setLegalName(uemCompany.getLegalName());
            //组织机构代码
            companyBasicInfoVO.setOrganizationCode(uemCompany.getOrganizationCode());
            //公告所在城市
            companyBasicInfoVO.setLocCityCode(uemCompany.getLocCityCode());
            //公告所在区
            companyBasicInfoVO.setLocDistrictCode(uemCompany.getLocDistrictCode());
            //公司所在省
            companyBasicInfoVO.setLocProvinceCode(uemCompany.getLocProvinceCode());
            if (!Objects.isNull(sysPlatformUser)) {
                companyBasicInfoVO.setIdentity("3");
            }
        }
        return companyBasicInfoVO;
    }

    /**
     * 联想控件查询企业名称
     * @Author: cjh
     * @param uemCompanyNameDTO 查询条件
     * @return UemCompanyNameVO 企业名称信息封装类
     */
    @Override
    public Page<UemCompanyNameVO> queryCompanyNameList(UemCompanyNameDTO uemCompanyNameDTO) {
        //权限过滤
        CompanyBasicInfoVO companyBasicInfoVO = this.queryCompanyBasicInfoByUid();
        Long companyId = null;
        if (!ObjectUtil.equal(companyBasicInfoVO.getOrganizationType(),"3")&&!ObjectUtil.equal(companyBasicInfoVO.getIdentity(),"3")){
            companyId = companyBasicInfoVO.getCompanyId();
        }
        // 查询条件
        AndExpression expression = QUemCompany.uemCompanyId.ne$(-1L)
                .and(QUemCompany.companyNameCn.like$(uemCompanyNameDTO.getKeyword()))
                .and(QUemCompany.isValid.eq$(true))
                .and(QUemCompany.auditStatus.eq$("1"))
                .and(QUemCompany.uemCompanyId.eq$(companyId));

        // 排序方法
        Iterable<Expression> sorting = new ArrayList<>();
        // 字母（A-Z）升序。
        ((ArrayList<Expression>) sorting).add(QUemCompany.companyNameCn.asc());

        // 执行查询，返回结果
        Page<UemCompanyNameVO> uemCompanyNameVOPage =  QUemCompany.uemCompany.select()
                .where(expression)
                .paging(uemCompanyNameDTO.getCurrentPage(),uemCompanyNameDTO.getPageSize())
                .sorting(sorting)
                .mapperTo(UemCompanyNameVO.class)
                .execute();

        return uemCompanyNameVOPage;
    }

    /**
     * 获取所有企业（非机关单位）的管理人信息列表
     * @Author: cjh
     * @return UemUser
     */
    @Override
    public List<UemUserVO> queryAdministratorList(Long companyId) {

        HashMap<String, Object> paramMa = new HashMap<>();
        if (!ObjectUtil.equal(-1L,companyId)){
            paramMa.put("companyId",companyId);
        }
        return DSContext.customization("CZT_queryAdministratorList").select()
                .mapperTo(UemUserVO.class)
                .execute(paramMa);
    }

    @Override
    public CompanyBasicInfoVO queryCompanyBasicInfoByCompany(Long companyId){
        CompanyBasicInfoVO companyBasicInfoVO = new CompanyBasicInfoVO();
        UemCompany uemCompany = QUemCompany.uemCompany.selectOne().byId(companyId);
        if(uemCompany==null){
            return companyBasicInfoVO;
        }
        List<SysDictCode> result = QSysDictCode.sysDictCode.select(QSysDictCode.dictName).
                where(QSysDictCode.dictCode.eq$(uemCompany.getOrganizationType())
                .and(QSysDictCode.sysDictCode.chain(QSysDictType.sysDictTypeId))
                .and(QSysDictType.dictTypeCode.eq$(CodeFinal.DICT_LOGIN_ORGANIZATION_TYPE))).execute();

        //企业id
        companyBasicInfoVO.setCompanyId(uemCompany.getUemCompanyId());
        //企业公司名称
        companyBasicInfoVO.setCompanyName(uemCompany.getCompanyNameCn());
        //企业地址
        companyBasicInfoVO.setLocAddress(uemCompany.getLocAddress());
        if(CollectionUtils.isNotEmpty(result)){
            //企业类型
//            companyBasicInfoVO.setOrganizationType(uemCompany.getOrganizationType());
            companyBasicInfoVO.setOrganizationType(result.get(0).getDictName());
        }

        //法人名称
        companyBasicInfoVO.setLegalName(uemCompany.getLegalName());
        //组织机构代码
        companyBasicInfoVO.setOrganizationCode(uemCompany.getOrganizationCode());
        return companyBasicInfoVO;
    }

    /**
     *
     * @author  panzw
     * @create  2021/10/6 15:24
     * @param    ids
     * @return Page<UemCompanyNameVO>
     **/
    @Override
    public List<UemCompanyVO>  selectAuthCompany(List<Long> ids) {
        List<UemCompanyVO> uemCompanyList =  QUemCompany.uemCompany.select().where(QUemCompany.uemCompanyId.in$(ids)).mapperTo(UemCompanyVO.class).execute();

        return uemCompanyList;
    }

    /**
     *
     * @author  Linja
     * @create  2021/10/25 17:00
     * @param
     * @return Page<UemCompanyNameVO>
     **/
    @Override
    public List<UemCompanyVO> queryComName(String companyNameCn) {
        List<UemCompanyVO> uemCompanyVOList = QUemCompany.uemCompany.select().
                where(QUemCompany.companyNameCn.eq$(companyNameCn)
                        .and(QUemCompany.auditStatus.ne$("2")))
                .mapperTo(UemCompanyVO.class).execute();

        return uemCompanyVOList;
    }

    @Override
    public List<UemCompanyVO> queryOrgCode(String code) {
        List<UemCompanyVO> uemCompanyVOList = QUemCompany.uemCompany.select().
                where(QUemCompany.organizationCode.eq$(code)
                        .and(QUemCompany.auditStatus.ne$("2")))
                .mapperTo(UemCompanyVO.class).execute();

        return uemCompanyVOList;
    }

    @Override
    public void updateCompanyValid(QueryCompanyTreeTableDTO queryCompanyTreeTableDTO) {
        QUemCompany.uemCompany.save(queryCompanyTreeTableDTO);
    }

    /**
     * @return
     * @Author cec
     * @Description 获取companyCode
     * @Date 2021/10/25 15:36
     * @Param
     **/
    private String getMaxCompanyCode() {
        String dateStr = DateUtils.getDateStr(DateUtils.getNowDate()).replaceAll("-","");
        //取出最后一条
        UemCompanyHistory uemCompanyHistory = DSContext.customization("CZT_getMaxCompanyCode").selectOne()
                .mapperTo(UemCompanyHistory.class)
                .execute();
        //判断是否为空
        if (ObjectUtils.isEmpty(uemCompanyHistory) || ObjectUtils.isEmpty(uemCompanyHistory.getCompanyCode())) {
            return "JHDM" + dateStr.substring(2,8) + "00001";
        }
        String oldCode = uemCompanyHistory.getCompanyCode();
        int newCode = Integer.valueOf(oldCode.substring(10)) + 1;
        String companyCode = "JHDM" + dateStr.substring(2,8) + String.format("%05d", newCode);
        log.info("生成公司物流交换代码：{}", companyCode);
        return companyCode;
    }
}
