package edu.ysu.backend.cron;

import edu.ysu.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
// 定时删除数据库中过期的废弃数据
public class TrashTiming {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void deleteTrashDataUser(){
        log.info("执行删除无效用户的定时任务");
        userService.deleteUserTrash();

    }

}
