package edu.ysu.backend.cron;

import edu.ysu.backend.service.ServicesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
// 提醒任务计时器
public class AlertTiming {

    @Autowired
    private ServicesService servicesService;

    @Scheduled(cron = "0 0 12 * * ?")
    private void alertClearTiming(){
        log.info("启动提醒标识清理定时任务");
        servicesService.cleanServiceAlert();
    }

}
