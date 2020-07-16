package com.xxxxxxxqqqqqqq.collectors.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description Schedule定时任务demo
 * @Author xiao qi
 * @Date 15:47 2020/7/16
 **/
//@Component
public class ScheduleDemo {

    /**
     * @Scheduled 支持cron表达式和时区的定时任务注解
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void demo() {
        System.out.println("====================ScheduleDemo execute======================" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}
