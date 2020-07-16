//package com.xxxxxxxqqqqqqq.collectors.controller;
//
//import com.xxxxxxxqqqqqqq.collectors.quartz.ScheduledJob;
//import com.xxxxxxxqqqqqqq.collectors.quartz.SchedulerManager;
//import org.quartz.SchedulerException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Description TODO
// * @Author xiao qi
// * @Date 1:41 2020/7/16
// **/
//@RestController
//@RequestMapping("/quartz")
//public class QuartzController {
//
//    @Autowired
//    public SchedulerManager myScheduler;
//
//    @RequestMapping(value = "/job2",method = RequestMethod.GET)
//    public String scheduleJob2()
//    {
//        try {
//            myScheduler.startJob("0/5 * * * * ?","job2","group2", ScheduledJob.class);//每五秒执行一次
//            //0 0/5 14 * * ?在每天下午2点到下午2:55期间的每5分钟触发
//            //0 50 14 * * ?在每天下午2点50分5秒执行一次
////            myScheduler.startJob("5 50 14 * * ?","job2","group2", ScheduledJob.class);
//            return "start success";
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        return "fail";
//    }
//
//    @RequestMapping(value = "/pause_job2",method = RequestMethod.GET)
//    public String pauseScheduleJob2()
//    {
//        try {
//            myScheduler.pauseJob("job2","group2");
//            return "暂停定时器成功";
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        return "暂停定时器失败";
//    }
//
//    @RequestMapping(value = "/resume_job2",method = RequestMethod.GET)
//    public String resumeScheduleJob2()
//    {
//        try {
//            myScheduler.resumeJob("job2","group2");
//            return "恢复定时器成功";
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        return "恢复定时器失败";
//    }
//
//
//    @RequestMapping(value = "/del_job2",method = RequestMethod.GET)
//    public String deleteScheduleJob2()
//    {
//        try {
//            myScheduler.deleteJob("job2","group2");
//            return "删除定时器成功";
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        return "删除定时器失败";
//    }
//
//
//}
