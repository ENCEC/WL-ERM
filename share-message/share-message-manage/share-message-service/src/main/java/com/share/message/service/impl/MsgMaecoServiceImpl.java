package com.share.message.service.impl;

import com.gillion.ds.utils.ResultUtils;
import com.share.message.constants.GlobalConstant;
import com.share.message.dto.MsgMarcoDto;
import com.share.message.model.entity.MsgMarco;
import com.share.message.model.querymodels.QMsgMarco;
import com.share.message.service.MsgMarcoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 消息宏实现类
 * @Author nily
 * @Date 2020/11/2
 * @Time 2:27 下午
 */
@Service
@Slf4j
public class MsgMaecoServiceImpl implements MsgMarcoService {


    private static final Logger logger = LoggerFactory.getLogger(MsgMaecoServiceImpl.class);

    /**
     * @param msgMarcoDTO
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 保存宏
     */
    @Override
    public Map<String, Object> saveMsgMarco(MsgMarcoDto msgMarcoDTO) {
        Map<String, Object> resultData = null;
        resultData = validateMsgMarcoValue(msgMarcoDTO);
        if (resultData != null) {
            return resultData;
        }
        MsgMarco msgMarco = convertToEntity(msgMarcoDTO);
        msgMarco.setIsValid(true);
        msgMarco.setRowStatus(GlobalConstant.SAVE);

        List<MsgMarco> list = QMsgMarco.msgMarco.select(QMsgMarco.msgMarco.fieldContainer()).where(QMsgMarco.businessSystemId.eq$(msgMarco.getBusinessSystemId())).execute();
        for (MsgMarco listMarco : list) {
            if (listMarco.getMarcoNameCn().equals(msgMarco.getMarcoNameCn())) {
                resultData = ResultUtils.getFailedResultData("宏中文名值具有唯一性，不能重复");
                return resultData;
            }
            if (listMarco.getMarcoNameEn().equals(msgMarco.getMarcoNameEn())) {
                resultData = ResultUtils.getFailedResultData("宏英文名值具有唯一性，不能重复");
                return resultData;
            }
            if (listMarco.getFieldName().equals(msgMarco.getFieldName())) {
                resultData = ResultUtils.getFailedResultData("属性值具有唯一性，不能重复");
                return resultData;
            }
        }

        int save = -1;
        try {
            save = QMsgMarco.msgMarco.save(msgMarco);
        } catch (Exception e) {
            log.error("saveMsgMarco 出现异常", e);
            resultData = ResultUtils.getFailedResultData("saveMsgMarco 出现异常");
            return resultData;
        }

        if (save == 1) {
            resultData = ResultUtils.getSuccessResultData(msgMarco);
        } else {
            resultData = ResultUtils.getFailedResultData("saveMsgMarco 保存失败");
        }
        return resultData;
    }

    /**
     * @param msgMarcoDTO
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 编辑宏
     */
    @Override
    public Map<String, Object> updateMsgMarco(MsgMarcoDto msgMarcoDTO) {
        Map<String, Object> resultData = null;
        resultData = validateMsgMarcoValue(msgMarcoDTO);
        if (resultData != null) {
            return resultData;
        }
        MsgMarco msgMarco = convertToEntity(msgMarcoDTO);
        msgMarco.setRowStatus(GlobalConstant.UPDATE);


        List<MsgMarco> list = QMsgMarco.msgMarco.select(QMsgMarco.msgMarco.fieldContainer()).where(QMsgMarco.businessSystemId.eq$(msgMarco.getBusinessSystemId()).and(QMsgMarco.msgMarcoId.ne$(msgMarco.getMsgMarcoId()))).execute();
        for (MsgMarco listMarco : list) {
            if (!listMarco.getMsgMarcoId().equals(msgMarco.getMsgMarcoId())) {
                if (listMarco.getMarcoNameCn().equals(msgMarco.getMarcoNameCn())) {
                    resultData = ResultUtils.getFailedResultData("宏中文名值具有唯一性，不能重复");
                    return resultData;
                }
                if (listMarco.getMarcoNameEn().equals(msgMarco.getMarcoNameEn())) {
                    resultData = ResultUtils.getFailedResultData("宏英文名值具有唯一性，不能重复");
                    return resultData;
                }
                if (listMarco.getFieldName().equals(msgMarco.getFieldName())) {
                    resultData = ResultUtils.getFailedResultData("属性值具有唯一性，不能重复");
                    return resultData;
                }
            }
        }

        MsgMarco msgMarco1 = QMsgMarco.msgMarco.selectOne().byId(msgMarco.getMsgMarcoId());
        int update = -1;
        if (msgMarco1 != null) {
            try {
                update = QMsgMarco.msgMarco.save(msgMarco);
            } catch (Exception e) {
                resultData = ResultUtils.getFailedResultData("updateMsgMarco 更新出现异常");
                return resultData;
            }
            if (update == 1) {
                resultData = ResultUtils.getSuccessResultData(msgMarco);
            } else {
                resultData = ResultUtils.getFailedResultData("updateMsgMarco 更新失败");
            }
        } else {
            return ResultUtils.getFailedResultData("更新失败 没有查到对应 MsgMarco对象 ");

        }
        return resultData;
    }

    /**
     * @param msgMarcoId
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 删除宏
     */
    @Override
    public Map<String, Object> deleteMsgMarcoById(Long msgMarcoId) {
        Map<String, Object> resultData = null;
        MsgMarco msgMarco = QMsgMarco.msgMarco.selectOne().byId(msgMarcoId);
        int delete = 0;
        if (msgMarco != null) {
            msgMarco.setRowStatus(GlobalConstant.DELETE);
            delete = QMsgMarco.msgMarco.deleteById(msgMarcoId);
        } else {
            ResultUtils.getFailedResultData("deleteMsgMarcoById 查询失败");
        }

        if (delete == 1) {
            resultData = ResultUtils.getSuccessResultData(msgMarco);
        } else {
            resultData = ResultUtils.getFailedResultData("deleteMsgMarcoById 删除失败");
        }
        return resultData;
    }

    /**
     * @param msgMarcoId
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Description 禁用/启用宏
     */
    @Override
    public Map<String, Object> updateMsgMarcoStatusById(Long msgMarcoId) {
        Map<String, Object> resultData = null;
        MsgMarco msgMarco = QMsgMarco.msgMarco.selectOne().byId(msgMarcoId);
        int update = 0;
        if (msgMarco != null) {
            msgMarco.setRowStatus(GlobalConstant.UPDATE);
            if (Boolean.FALSE.equals(msgMarco.getIsValid()) || msgMarco.getIsValid() == null) {
                msgMarco.setIsValid(true);
                logger.info("启用宏  updateMsgMarcoStatusById 成功");
            } else {
                msgMarco.setIsValid(false);
                logger.info("禁用宏  updateMsgMarcoStatusById 成功");
            }

            update = QMsgMarco.msgMarco.save(msgMarco);
        } else {
            ResultUtils.getFailedResultData("updateMsgMarcoStatusById 启用/禁用宏 失败 msgMarco 为空");
        }
        if (update == 1) {
            resultData = ResultUtils.getSuccessResultData(msgMarco);
        } else {
            resultData = ResultUtils.getFailedResultData("updateMsgMarcoStatusById 更新失败");
        }
        return resultData;
    }


    /**
     * dto 转化成 entity
     *
     * @param msgMarcoDTO
     * @return
     */

    private MsgMarco convertToEntity(MsgMarcoDto msgMarcoDTO) {
        MsgMarco msgMarco = new MsgMarco();
        if (msgMarcoDTO != null) {
            msgMarco.setMsgMarcoId(msgMarcoDTO.getMsgMarcoId());
            msgMarco.setBusinessSystemId(msgMarcoDTO.getBusinessSystemId());
            msgMarco.setMarcoNameCn(msgMarcoDTO.getMarcoNameCn());
            msgMarco.setMarcoNameEn(msgMarcoDTO.getMarcoNameEn());
            msgMarco.setFieldName(msgMarcoDTO.getFieldName());
            msgMarco.setIsValid(msgMarcoDTO.getStatus());
        }
        return msgMarco;
    }

    /**
     * entity 转化成 dto
     *
     * @param msgMarco
     * @return
     */
    private MsgMarcoDto convertToDto(MsgMarco msgMarco) {
        MsgMarcoDto msgMarcoDto = new MsgMarcoDto();
        if (msgMarco != null) {
            msgMarcoDto.setMsgMarcoId(msgMarco.getMsgMarcoId());
            msgMarcoDto.setBusinessSystemId(msgMarco.getBusinessSystemId());
            msgMarcoDto.setMarcoNameCn(msgMarco.getMarcoNameCn());
            msgMarcoDto.setMarcoNameEn(msgMarco.getMarcoNameEn());
            msgMarcoDto.setFieldName(msgMarco.getFieldName());
            msgMarcoDto.setStatus(msgMarco.getIsValid());
        }
        return msgMarcoDto;
    }


    /**
     * @param enName
     * @param businessSystemId
     * @return com.share.message.dto.MsgMarcoDto
     * @Description 用英文名 去获取 marco 对象 field 属性
     */
    @Override
    public MsgMarcoDto getMarcoByEnName(String enName, String businessSystemId) {
        MsgMarco msgMarco = QMsgMarco.msgMarco.selectOne().where(QMsgMarco.marcoNameEn.eq$(enName).and(QMsgMarco.businessSystemId.eq$(businessSystemId))).execute();
        MsgMarcoDto msgMarcoDto = null;
        if (msgMarco != null) {
            msgMarcoDto = convertToDto(msgMarco);
        }

        return msgMarcoDto;
    }


    /**
     * @Description 验证必填参数
     * @Author nily
     * @Date 2020/11/12
     * @Time 3:15 下午
     */
    private Map<String, Object> validateMsgMarcoValue(MsgMarcoDto msgMarcoDTO) {
        int length = 50;
        if (StringUtils.isEmpty(msgMarcoDTO.getMarcoNameCn()) || msgMarcoDTO.getMarcoNameCn().length() > length) {
            return ResultUtils.getFailedResultData("消息宏对象 msgMarcoDTO MarcoNameCn 必填字段为空 或大于50字符 ");
        }
        if (StringUtils.isEmpty(msgMarcoDTO.getMarcoNameEn()) || msgMarcoDTO.getMarcoNameEn().length() > length) {
            return ResultUtils.getFailedResultData("消息宏对象 msgMarcoDTO MarcoNameEn 必填字段为空 或大于50字符");
        }

        if (StringUtils.isEmpty(msgMarcoDTO.getFieldName()) || msgMarcoDTO.getFieldName().length() > length) {
            return ResultUtils.getFailedResultData("消息宏对象 msgMarcoDTO FieldName 必填字段为空 或大于50字符");
        }
        return null;
    }


}
