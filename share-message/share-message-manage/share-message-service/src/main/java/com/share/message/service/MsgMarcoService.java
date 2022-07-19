package com.share.message.service;

import com.share.message.dto.MsgMarcoDto;

import java.util.Map;

/**
 * @Description 消息宏
 * @Author nily
 * @Date 2021/1/14
 * @Time 上午9:36
 */
public interface MsgMarcoService {

    /**
     * 保存宏
     * @param msgMarcoDTO
     * @return
     * @author nily
     */
    Map<String, Object> saveMsgMarco(MsgMarcoDto msgMarcoDTO);

    /**
     * 编辑宏
     * @param msgMarcoDTO
     * @return
     * @author nily
     */
    Map<String, Object> updateMsgMarco(MsgMarcoDto msgMarcoDTO);

    /**
     * 删除宏
     * @param msgMarcoId
     * @return
     * @author nily
     */
    Map<String, Object> deleteMsgMarcoById(Long msgMarcoId);

    /**
     * 禁用/启用宏
     * @param msgMarcoId
     * @return
     * @author nily
     */
    Map<String, Object> updateMsgMarcoStatusById(Long msgMarcoId);

    /**
     * 用英文名 去获取 marco 对象 field 属性
     * @param enName
     * @param businessSystemId
     * @return
     * @author nily
     */
    MsgMarcoDto getMarcoByEnName(String enName, String businessSystemId);


}
