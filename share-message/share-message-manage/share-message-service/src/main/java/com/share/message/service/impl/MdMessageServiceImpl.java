package com.share.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.DaoServiceClient;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.utils.DateUtils;
import com.share.auth.domain.QueryApplicationDTO;
import com.share.message.enums.GlobalEnum;
import com.share.message.model.entity.MsgMessageNotifier;
import com.share.message.model.entity.MsgMessageRecord;
import com.share.message.model.querymodels.QMsgMessageNotifier;
import com.share.message.model.querymodels.QMsgMessageRecord;
import com.share.message.service.MdMessageService;
import com.share.message.service.SysApplicationService;
import com.share.message.vo.MsgMessageVO;
import com.share.message.vo.QueryMsgMessageDTO;
import com.share.message.vo.UserVO;
import com.share.message.vo.mdmessage.MdMessageVO;
import com.share.message.vo.mdmessage.MdMsgStatusDTO;
import com.share.message.vo.mdmessage.QueryMessageByUserDTO;
import com.share.message.vo.mdmessage.UnReadCountDTO;
import com.share.support.constants.ApiConstant;
import com.share.support.result.ApiResultHelper;
import com.share.support.result.CommonApiReturn;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月28日 13:47
 */
@Service
@Slf4j
public class MdMessageServiceImpl implements MdMessageService {

    @Autowired
    DaoServiceClient client;

    private static final String QUERY = "query";

    @Autowired
    private SysApplicationService sysApplicationService;

    /**
     * 查询消息统计
     *
     * @param queryMeetingConditionVO
     * @return ResultHelper<Page < MsgMessageTemplateVO>>
     * @author wangzicheng
     * @date 2021/5/27 14:14
     */
    @Override
    public ResultHelper<Page<MsgMessageVO>> queryMsgMessage(QueryMsgMessageDTO queryMeetingConditionVO) {
        if (Objects.nonNull(queryMeetingConditionVO.getCreateTimeMax())) {
            Date meetStartTimeMax = queryMeetingConditionVO.getCreateTimeMax();
            queryMeetingConditionVO.setCreateTimeMax(DateUtils.parseDate(meetStartTimeMax.getTime() + 1000 * 60 * 60 * 16 - 1000));
        }
        if (Objects.nonNull(queryMeetingConditionVO.getCreateTimeMin())) {
            Date meetStartTimeMin = queryMeetingConditionVO.getCreateTimeMin();
            queryMeetingConditionVO.setCreateTimeMin(DateUtils.parseDate(meetStartTimeMin.getTime() - 1000 * 60 * 60 * 8));
        }
        Page<MsgMessageVO> page = client.customSelect("queryMsgMessageTest")
                .tag(QUERY)
                .paging(queryMeetingConditionVO.getPageNow(), queryMeetingConditionVO.getPageSize())
                .mapperTo(MsgMessageVO.class)
                .needTotalCount(true)
                .execute(queryMeetingConditionVO);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return CommonResult.getSuccessResultData(page);
        }
        List<Long> msgIdList = page.getRecords().stream().map(MsgMessageVO::getMsgMessageId).collect(Collectors.toList());
        List<MsgMessageNotifier> messageNotifierList = QMsgMessageNotifier.msgMessageNotifier.select().where(QMsgMessageNotifier.msgMessageId.in$(msgIdList)).execute();
        Map<Long, List<MsgMessageNotifier>> messageNotifierMap = messageNotifierList.stream().collect(Collectors.groupingBy(MsgMessageNotifier::getMsgMessageId));
        page.getRecords().forEach(m -> {
            String targetSystemId = m.getTargetSystemId();
            if (Objects.nonNull(targetSystemId)) {
                m.setTargetSystemId(handleIds(targetSystemId));
            }
            List<MsgMessageNotifier> list = messageNotifierMap.get(m.getMsgMessageId());
            List<UserVO> userVOList = list.stream().map(n -> {
                UserVO u = new UserVO();
                u.setIsReaded(n.getIsReaded());
                u.setUserId(n.getUserId());
                u.setUserName(n.getUserName());
                return u;
            }).collect(Collectors.toList());
            m.setUserVOList(userVOList);
        });
        return CommonResult.getSuccessResultData(page);
    }

    /**
     * 多个接收系统处理
     *
     * @param targetSystemId
     * @return java.lang.String
     * @author wangzicheng
     * @date 2021/6/15 19:31
     */
    private String handleIds(String targetSystemId) {
        ResultHelper<List<QueryApplicationDTO>> resultHelper = sysApplicationService.getSystemList();
        List<QueryApplicationDTO> list = new ArrayList<>();
        if (resultHelper.getSuccess()) {
            list = resultHelper.getData();
        }
        Map<String, QueryApplicationDTO> codeMap = list.stream().collect(Collectors.toMap(QueryApplicationDTO::getApplicationCode, v -> v, (v2, v1) -> v1));
        final String flag = ",";
        if (targetSystemId.contains(flag)) {
            String[] ids = targetSystemId.split(flag);
            Set<String> nameSet = Arrays.stream(ids).map(code -> {
                QueryApplicationDTO dto = codeMap.get(code);
                if (Objects.nonNull(dto)) {
                    return codeMap.get(code).getApplicationName();
                }
                return "";
            }).collect(Collectors.toSet());
            StringBuilder sb = new StringBuilder();
            for (String s : nameSet) {
                sb.append(s + flag);
            }
            return StringUtils.removeEnd(sb.toString(), flag);
        }
        return codeMap.get(targetSystemId).getApplicationName();
    }

    /**
     * 保存消息
     *
     * @param msgMessageVO
     * @return ResultHelper
     * @author wangzicheng
     * @date 2021/5/28 18:12
     */
    @Override
    public ApiResultHelper<Object> saveMsgMessage(MsgMessageVO msgMessageVO) {
        log.info("-----------开始保存信息-------------");
        log.info("-----------入参msgMessageVO:"+JSON.toJSONString(msgMessageVO));
        MsgMessageRecord msgMessageRecord = new MsgMessageRecord();
        BeanUtils.copyProperties(msgMessageVO, msgMessageRecord);
        msgMessageRecord.setRowStatus(GlobalEnum.DataOperRowStatus.ADDITION.getCode());
        //true 发送成功
        msgMessageRecord.setIsSuccess(Boolean.TRUE);
        int result = QMsgMessageRecord.msgMessageRecord.save(msgMessageRecord);
        if (result != 1) {
            return CommonApiReturn.getFailedResultData(ApiConstant.FAILURE_CODE, "消息保存失败");
        }
        Map<String, MsgMessageNotifier> map = new HashMap<>(16);
        List<UserVO> userVOList = msgMessageVO.getUserVOList();
        userVOList.forEach(u -> {
            MsgMessageNotifier n = new MsgMessageNotifier();
            n.setMsgMessageId(msgMessageRecord.getMsgMessageId());
            n.setUserId(u.getUserId());
            n.setUserName(u.getUserName());
            //false未读
            n.setIsReaded(Boolean.FALSE);
            //true有效
            n.setIsValid(Boolean.TRUE);
            n.setRowStatus(GlobalEnum.DataOperRowStatus.ADDITION.getCode());
            String key = u.getUserName() + u.getUserId();
            map.put(key, n);
        });
        Collection<MsgMessageNotifier> values = map.values();
        QMsgMessageNotifier.msgMessageNotifier.save(values);
        log.info("保存message成功");
        return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, "消息保存成功");
    }

    /**
     * 查询消息
     *
     * @param queryMessageByUserDTO
     * @return ResultHelper<MdMessageVO>>
     * @author wangzicheng
     * @date 2021/5/28 18:04
     */
    @Override
    public ApiResultHelper<Object> queryMessageByUser(QueryMessageByUserDTO queryMessageByUserDTO) {
        Page<MdMessageVO> page = client.customSelect("queryMessageByUserTest")
                .tag(QUERY)
                .paging(queryMessageByUserDTO.getCurrentPage(), queryMessageByUserDTO.getPageSize())
                .mapperTo(MdMessageVO.class)
                .needTotalCount(true)
                .execute(queryMessageByUserDTO);
        return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, null, page);
    }

    /**
     * 查询消息
     *
     * @param queryMessageByUserDTO
     * @return ResultHelper<MdMessageVO>>
     * @author wangzicheng
     * @date 2021/5/28 18:04
     */
    @Override
    public ApiResultHelper<Object> queryMessageByUserId(QueryMessageByUserDTO queryMessageByUserDTO) {
        Page<MdMessageVO> page = DSContext.customization("CZT_selectUserMessage").select()
                .mapperTo(MdMessageVO.class)
                .paging(queryMessageByUserDTO.getCurrentPage(), queryMessageByUserDTO.getPageSize())
                .execute(queryMessageByUserDTO);
        return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, null, page);
    }

    /**
     * 查询消息详情
     *
     * @param queryMessageByUserDTO
     * @return MdMessageVO
     * @author wangzicheng
     * @date 2021/5/28 18:05
     */
    @Override
    public ApiResultHelper<Object> getMessageDetailByUser(QueryMessageByUserDTO queryMessageByUserDTO) {
        List<MsgMessageRecord> list = QMsgMessageRecord.msgMessageRecord.select()
                .where(QMsgMessageRecord.msgMessageId.eq$(queryMessageByUserDTO.getMdMessageId())
                        .and(QMsgMessageRecord.msgMessageRecord.chain(QMsgMessageNotifier.userId).eq$(queryMessageByUserDTO.getCurrentUserId()))
                        .and(QMsgMessageRecord.targetSystemId.eq$(queryMessageByUserDTO.getTargetSystemId())))
                .execute();
        MdMessageVO mdMessageVO = new MdMessageVO();
        mdMessageVO.setMdMessageId(list.get(0).getMsgMessageId());
        mdMessageVO.setMessageContent(list.get(0).getMessageContent());
        mdMessageVO.setNotifyTime(list.get(0).getCreateTime());
        return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, null, mdMessageVO);
    }

    /**
     * 未读消息统计
     *
     * @param unReadCountDTO
     * @return ApiResultHelper<Integer>
     * @author wangzicheng
     * @date 2021/5/31 11:18
     */
    @Override
    public ApiResultHelper<Object> unReadCount(UnReadCountDTO unReadCountDTO) {
        List<MsgMessageNotifier> execute = client.customSelect("unReadCountTest")
                .tag(QUERY)
                .mapperTo(MsgMessageNotifier.class)
                .execute(unReadCountDTO);
        return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, null, execute.size());
    }

    /**
     * 消息已读
     *
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Integer>
     * @author wangzicheng
     * @date 2021/5/31 11:44
     */
    @Override
    public ApiResultHelper<Object> updateStatus2Read(MdMsgStatusDTO mdMsgStatusDTO) {
        List<MsgMessageNotifier> messageNotifierList = QMsgMessageNotifier.msgMessageNotifier.select().where(QMsgMessageNotifier.userId.eq$(mdMsgStatusDTO.getCurrentUserId())
                .and(QMsgMessageNotifier.msgMessageNotifier.chain(QMsgMessageRecord.msgMessageId).in$(mdMsgStatusDTO.getMdMessageIdList()))
        ).execute();
        messageNotifierList.forEach(m -> {
            m.setIsReaded(Boolean.TRUE);
            m.setReadedTime(new Date());
        });
        int count = QMsgMessageNotifier.msgMessageNotifier.selective(QMsgMessageNotifier.isReaded, QMsgMessageNotifier.readedTime).update(messageNotifierList);
        if (count > 0) {
            return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, null, count);
        }
        return CommonApiReturn.getFailedResultData(ApiConstant.FAILURE_CODE, null);
    }

    /**
     * 删除消息
     *
     * @param mdMsgStatusDTO
     * @return ApiResultHelper<Object>
     * @author wangzicheng
     * @date 2021/5/31 14:19
     */
    @Override
    public ApiResultHelper<Object> update2IsValid(MdMsgStatusDTO mdMsgStatusDTO) {
        List<MsgMessageNotifier> msgMessageNotifierList = QMsgMessageNotifier.msgMessageNotifier.select()
                .where(QMsgMessageNotifier.msgMessageId.in$(mdMsgStatusDTO.getMdMessageIdList())
                        .and(QMsgMessageNotifier.userId.eq$(mdMsgStatusDTO.getCurrentUserId()))
                        .and(QMsgMessageNotifier.msgMessageNotifier.chain(QMsgMessageRecord.targetSystemId).eq$(mdMsgStatusDTO.getTargetSystemId()))).execute();
        msgMessageNotifierList.forEach(m -> m.setIsValid(Boolean.FALSE));
        int count = QMsgMessageNotifier.msgMessageNotifier.selective(QMsgMessageNotifier.isValid).update(msgMessageNotifierList);
        if (count > 0) {
            return CommonApiReturn.getSuccessResultData(ApiConstant.SUCCESS_CODE, null, count);
        }
        return CommonApiReturn.getFailedResultData(ApiConstant.FAILURE_CODE, null);
    }
}
