//package com.xxxxxxxqqqqqqq.collectors.quartz;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.JobListener;
//import org.springframework.stereotype.Component;
//
///**
// * @Description TODO
// * @Author xiao qi
// * @Date 1:39 2020/7/16
// **/
//@Component
//public class SchedulerListener implements JobListener {
//
//    public static final String LISTENER_NAME = "QuartSchedulerListener";
//
//
//    @Override
//    public String getName() {
//        // must return a name
//        return LISTENER_NAME;
//    }
//
//    //任务被调度前
//    @Override
//    public void jobToBeExecuted(JobExecutionContext context) {
//
//        String jobName = context.getJobDetail().getKey().toString();
//        System.out.println("jobToBeExecuted");
//        System.out.println("Job : " + jobName + " is going to start...");
//
//    }
//
//    //任务调度被拒了
//    @Override
//    public void jobExecutionVetoed(JobExecutionContext context) {
//        System.out.println("jobExecutionVetoed");
//        //可以做一些日志记录原因
//
//    }
//
//    //任务被调度后
//    @Override
//    public void jobWasExecuted(JobExecutionContext context,
//                               JobExecutionException jobException) {
//        System.out.println("jobWasExecuted");
//
//        String jobName = context.getJobDetail().getKey().toString();
//        System.out.println("Job : " + jobName + " is finished...");
//
//        if (jobException!=null&&!jobException.getMessage().equals("")) {
//            System.out.println("Exception thrown by: " + jobName
//                    + " Exception: " + jobException.getMessage());
//        }
//
//    }
//
//}
