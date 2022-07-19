package com.share.message.service.impl;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.message.enums.GlobalEnum;
import com.share.message.model.entity.MsgMessageTemplate;
import com.share.message.model.querymodels.QMsgMessageTemplate;
import com.share.message.service.MsgMessageTemplateService;
import com.share.message.vo.MsgMessageTemplateVO;
import com.share.message.vo.QueryMsgMessageTemplateDTO;
import com.share.support.constants.ApiConstant;
import com.share.support.result.ApiResultHelper;
import com.share.support.result.CommonApiReturn;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @author wangzicheng
 * @description 消息模板
 * @date 2021年05月25日 15:00
 */
@Service
public class MsgMessageTemplateServiceImpl implements MsgMessageTemplateService {

    /**
     * 查询消息模板
     *
     * @param queryMeetingConditionVO
     * @return ResultHelper<Page < MsgMessageTemplate>>
     * @author wangzicheng
     * @date 2021/5/25 15:27
     */
    @Override
    public ResultHelper<Page<MsgMessageTemplateVO>> queryMsgMessageTemplate(QueryMsgMessageTemplateDTO queryMeetingConditionVO) {
        String msgTemplateCode = queryMeetingConditionVO.getMsgTemplateCode() == null ? "" : queryMeetingConditionVO.getMsgTemplateCode();
        String msgTemplateName = queryMeetingConditionVO.getMsgTemplateName() == null ? "" : queryMeetingConditionVO.getMsgTemplateName();
        String businessSystemId = queryMeetingConditionVO.getBusinessSystemId() == null ? "" : queryMeetingConditionVO.getBusinessSystemId();
        // 分页查询,返回page对象
        Page<MsgMessageTemplateVO> page = QMsgMessageTemplate.msgMessageTemplate
                .select(QMsgMessageTemplate.msgMessageTemplate.fieldContainer())
                .where(QMsgMessageTemplate.msgTemplateCode.like$("%" + msgTemplateCode + "%")
                        .and(QMsgMessageTemplate.msgTemplateName.like$("%" + msgTemplateName + "%")
                                .and(QMsgMessageTemplate.businessSystemId.eq$(businessSystemId))))
                .paging(queryMeetingConditionVO.getPageNow(), queryMeetingConditionVO.getPageSize())
                .sorting(QMsgMessageTemplate.createTime.desc())
                .mapperTo(MsgMessageTemplateVO.class)
                .execute();
        return CommonResult.getSuccessResultData(page);
    }

    /**
     * 新增消息模板
     *
     * @param msgMessageTemplateVO
     * @return ResultHelper
     * @author wangzicheng
     * @date 2021/5/25 15:55
     */
    @Override
    public ResultHelper addMsgMessageTemplate(MsgMessageTemplateVO msgMessageTemplateVO) {
        MsgMessageTemplate msgMessageTemplate = new MsgMessageTemplate();
        BeanUtils.copyProperties(msgMessageTemplateVO, msgMessageTemplate);
        msgMessageTemplate.setIsValid(Boolean.TRUE);
        msgMessageTemplate.setMsgTemplateCode(msgMessageTemplateVO.getMsgTemplateCode() == null ? "" : msgMessageTemplateVO.getMsgTemplateCode());
        msgMessageTemplate.setRowStatus(GlobalEnum.DataOperRowStatus.ADDITION.getCode());
        if (!checkMsgTemplateName(msgMessageTemplate.getMsgTemplateName(), msgMessageTemplate.getBusinessSystemId())) {
            return CommonResult.getFaildResultData("模板名称不能重复！");
        }
        checkMsgTemplateName(msgMessageTemplate.getMsgTemplateName(), msgMessageTemplate.getMsgTemplateName());
        int result = -1;
        try {
            result = QMsgMessageTemplate.msgMessageTemplate.tag("MsgTemplateCode").save(msgMessageTemplate);
        } catch (Exception e) {
            log.print(e.getMessage());
        }
        if (result != 1) {
            return CommonResult.getFaildResultData();
        }
        return CommonResult.getSuccessResultData();
    }

    /**
     * 模板名称唯一校验
     *
     * @param templateName
     * @param businessSystemId
     * @return boolean
     * @author wangzicheng
     * @date 2021/6/10 19:43
     */
    private boolean checkMsgTemplateName(String templateName, String businessSystemId) {
        List<MsgMessageTemplate> execute = QMsgMessageTemplate.msgMessageTemplate.select()
                .where(QMsgMessageTemplate.msgTemplateName.eq$(templateName)
                        .and(QMsgMessageTemplate.businessSystemId.eq$(businessSystemId))).execute();
        return CollectionUtils.isEmpty(execute);
    }

    /**
     * 更新消息模板
     *
     * @param msgMessageTemplateVO
     * @return ResultHelper
     * @author wangzicheng
     * @date 2021/5/25 15:55
     */
    @Override
    public ResultHelper updateMsgMessageTemplate(MsgMessageTemplateVO msgMessageTemplateVO) {
        List<MsgMessageTemplate> list = QMsgMessageTemplate.msgMessageTemplate.select().where(QMsgMessageTemplate.businessSystemId.eq$(msgMessageTemplateVO.getBusinessSystemId())).execute();
        Map<Boolean, List<MsgMessageTemplate>> map = list.stream().collect(Collectors.partitioningBy(m -> msgMessageTemplateVO.getMsgMessageTemplateId().equals(m.getMsgMessageTemplateId())));
        List<String> nameList = map.get(false).stream().map(MsgMessageTemplate::getMsgTemplateName).collect(Collectors.toList());
        nameList.add(msgMessageTemplateVO.getMsgTemplateName());
        HashSet<String> set = new HashSet<>(nameList);
        if (nameList.size() != set.size()) {
            return CommonResult.getFaildResultData("模板名称不能重复！");
        }
        int result = QMsgMessageTemplate.msgMessageTemplate.update(QMsgMessageTemplate.msgTemplateName, QMsgMessageTemplate.description, QMsgMessageTemplate.content)
                .where(QMsgMessageTemplate.msgMessageTemplateId.eq(":msgMessageTemplateId"))
                .execute(msgMessageTemplateVO.getMsgTemplateName(),
                        msgMessageTemplateVO.getDescription(),
                        msgMessageTemplateVO.getContent(),
                        msgMessageTemplateVO.getMsgMessageTemplateId());
        if (result != 1) {
            return CommonResult.getFaildResultData();
        }
        return CommonResult.getSuccessResultData();
    }

    /**
     * 禁用/启用消息模板
     *
     * @param msgMessageTemplateId
     * @return ResultHelper
     * @author wangzicheng
     * @date 2021/5/25 15:55
     */
    @Override
    public ResultHelper updateIsValid(Long msgMessageTemplateId) {
        MsgMessageTemplate msgMessageTemplate = QMsgMessageTemplate.msgMessageTemplate.selectOne().byId(msgMessageTemplateId);
        Boolean isValid = msgMessageTemplate.getIsValid();
        msgMessageTemplate.setIsValid(Boolean.TRUE.equals(isValid) ? GlobalEnum.IsValid.INVALID.getCode() : GlobalEnum.IsValid.VALID.getCode());
        msgMessageTemplate.setRowStatus(GlobalEnum.DataOperRowStatus.UPDATE.getCode());
        int result = QMsgMessageTemplate.msgMessageTemplate.save(msgMessageTemplate);
        if (result != 1) {
            return CommonResult.getFaildResultData();
        }
        return CommonResult.getSuccessResultData();
    }

    /**
     * 删除消息模板
     *
     * @param msgMessageTemplateId
     * @return ResultHelper
     * @author wangzicheng
     * @date 2021/5/25 15:55
     */
    @Override
    public ResultHelper deleteMsgMessageTemplate(Long msgMessageTemplateId) {
        int result = QMsgMessageTemplate.msgMessageTemplate.deleteById(msgMessageTemplateId);
        if (result != 1) {
            return CommonResult.getFaildResultData();
        }
        return CommonResult.getSuccessResultData();
    }

    /**
     * 根据模板编码获取消息模板
     *
     * @param msgMessageTemplateCode
     * @return MsgMessageTemplateVO
     * @author wangzicheng
     * @date 2021/5/28 18:09
     */
    @Override
    public ApiResultHelper<Object> getMsgMessageTemplateByCode(String msgMessageTemplateCode) {
        MsgMessageTemplate messageTemplate = QMsgMessageTemplate.msgMessageTemplate.selectOne().where(QMsgMessageTemplate.msgTemplateCode.eq$(msgMessageTemplateCode)).execute();
        MsgMessageTemplateVO msgMessageTemplateVO = new MsgMessageTemplateVO();
        BeanUtils.copyProperties(messageTemplate, msgMessageTemplateVO);
        return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, null, msgMessageTemplateVO);
    }
}
