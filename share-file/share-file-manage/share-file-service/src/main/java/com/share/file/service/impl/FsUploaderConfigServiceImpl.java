package com.share.file.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.share.file.enums.GlobalEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.ds.utils.ResultUtils;
import com.share.file.model.entity.FsUploaderConfig;
import com.share.file.model.querymodels.QFsUploaderConfig;
import com.share.file.service.FsUploaderConfigService;
import com.share.file.vo.FsUploaderConfigVo;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author: baill
 * @description: 文件服务接口ServiceImpl
 * @data: 2020-12-03 16:10
 */
@Service
public class FsUploaderConfigServiceImpl implements FsUploaderConfigService {

	/**
	 * 文件配置类保存接口
	 * @param fsUploaderConfigVo
	 * @return
	 * @author bll
	 */
	@Override
	public Map<String, Object> saveUploaderConfig(FsUploaderConfigVo fsUploaderConfigVo) {
		if (StringUtils.isEmpty(fsUploaderConfigVo.getBusinessSystemId())) {
            return ResultUtils.getFailedResultData("businessSystemId不能为空");
        }
        if (fsUploaderConfigVo.getCountLimit() == null || fsUploaderConfigVo.getCountLimit() < 0) {
            return ResultUtils.getFailedResultData("文件数量限制不能为空且不能小于0");
        }
        BigDecimal zero = new BigDecimal(0);
        if (fsUploaderConfigVo.getSizeLimit() == null || (fsUploaderConfigVo.getSizeLimit().compareTo(zero) < 0)) {
            return ResultUtils.getFailedResultData("文件大小限制不能为空且不能小于0");
        }

        List<FsUploaderConfig> fsUploaderConfigs;
        fsUploaderConfigs = QFsUploaderConfig.fsUploaderConfig
                .select(QFsUploaderConfig.fsUploaderConfig.fieldContainer())
                .where(QFsUploaderConfig.businessSystemId.eq(":businessSystemId"))
                .execute(ImmutableMap.of("businessSystemId", fsUploaderConfigVo.getBusinessSystemId()));
        FsUploaderConfig fsUploaderConfig = new FsUploaderConfig();
        int result = 0;
        if (fsUploaderConfigs.size() > 1) {
            return ResultUtils.getFailedResultData("数据库存在多个文件配置类，请清理");
        } else if (CollectionUtils.isEmpty(fsUploaderConfigs)) {
            fsUploaderConfig.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            BeanUtils.copyProperties(fsUploaderConfigVo, fsUploaderConfig);
            result = QFsUploaderConfig.fsUploaderConfig.save(fsUploaderConfig);
        } else if (fsUploaderConfigs.size() == 1) {
            fsUploaderConfig = fsUploaderConfigs.get(0);
            fsUploaderConfig.setCountLimit(fsUploaderConfigVo.getCountLimit());
            fsUploaderConfig.setSizeLimit(fsUploaderConfigVo.getSizeLimit());
            fsUploaderConfig.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            result = QFsUploaderConfig.fsUploaderConfig.save(fsUploaderConfig);
        }
		if (result > 0) {
			return ResultUtils.getSuccessResultData(result);
		} else {
			return ResultUtils.getFailedResultData("保存文件配置失败");
		}
	}


	/**
	 * 根据使用方系统编码查询文件上传配置
	 *
	 * @param businessSystemCode 使用方系统编码
	 * @return Map
	 * @throws
	 * @author tujx
	 */
	@Override
	public Map<String, Object> getUploaderConfigBySystemCode(String businessSystemCode) {
		Map<String,Object> result = new HashMap<>(16);
		String resultCode;
		String resultMsg;
		if(StringUtils.isBlank(businessSystemCode)){
			resultCode = GlobalEnum.InterfaceErrorCode.REQUIRED_PARAMS_EMPTY.getResultCode();
			resultMsg = GlobalEnum.InterfaceErrorCode.REQUIRED_PARAMS_EMPTY.getResultMsg()+"【使用方系统编码不能为空】";
		}else{
			FsUploaderConfigVo fsUploaderConfig = QFsUploaderConfig.fsUploaderConfig.selectOne(QFsUploaderConfig.businessSystemId,QFsUploaderConfig.countLimit,QFsUploaderConfig.sizeLimit).where(QFsUploaderConfig.businessSystemId.eq$(businessSystemCode))
					.mapperTo(FsUploaderConfigVo.class).execute();
			if(Objects.isNull(fsUploaderConfig)){
				resultCode = GlobalEnum.InterfaceErrorCode.FAIL.getResultCode();
				resultMsg = GlobalEnum.InterfaceErrorCode.FAIL.getResultMsg()+"【未查询到有效的文件上传配置】";
			}else{
				resultCode = GlobalEnum.InterfaceErrorCode.SUCCESS.getResultCode();
				resultMsg = GlobalEnum.InterfaceErrorCode.SUCCESS.getResultMsg();
				result.put("data",fsUploaderConfig);
			}
		}
		result.put("resultCode",resultCode);
		result.put("resultMsg",resultMsg);
		return result;
	}
}
