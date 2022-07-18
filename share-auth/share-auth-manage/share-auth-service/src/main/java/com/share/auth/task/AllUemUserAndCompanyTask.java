package com.share.auth.task;

//import com.alibaba.fastjson.JSON;
//import com.share.auth.model.vo.UserAndCompanyVo;
//import com.share.auth.service.UemUserService;
//import com.share.meeting.api.ShareMeetingApiInterface;
//import com.share.meeting.domain.MeetingUserAndCompanyVo;
//import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Author:chenxq
 * @Description: 定时同步通讯录信息给会商系统定时器
 * @Date: 11:39 2021/3/19
 *
 */
@Component
@EnableScheduling
@Slf4j
public class AllUemUserAndCompanyTask {

//    @Autowired
//    private UemUserService uemUserService;
//    @Autowired
//    private ShareMeetingApiInterface shareMeetingApiInterface;
//
//    /**
//     * 定时整点，同步通讯录信息
//     *
//     * @return
//     * @throws
//     * @author cxq
//     */
//      @Scheduled(cron = "${task.send.uemUser}")
//      @RedisTryLock(keyName = "send_all_uemUser_ForMeeting_task", expireTime = 60)
//    public void calculate2Commission() {
//        log.info("-------------------------定时同步通讯录信息发送开始-------------------------------");
//        // 获取所有用户通讯录信息，同步至会商系统
//        List<UserAndCompanyVo> userAndCompanyVos = uemUserService.getAllUemUserAndCompanyForMeeting();
//        List<MeetingUserAndCompanyVo> meetingUserAndCompanyVos = JSON.parseArray(JSON.toJSONString(userAndCompanyVos), MeetingUserAndCompanyVo.class);
//        try{
//            // 同步
//            shareMeetingApiInterface.syncDirectContacts(meetingUserAndCompanyVos);
//            log.info("-------------------------定时同步通讯录信息发送结束-------------------------------");
//        } catch (Exception e) {
//            log.error("同步失败", e);
//        }
//
//    }

}
