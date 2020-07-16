package com.xxxxxxxqqqqqqq.collectors.quartz.job;

import com.xxxxxxxqqqqqqq.collectors.quartz.component.ComponentTest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description Quartz作业实例
 * @Author xiao qi
 * @Date 1:49 2020/7/17
 **/
public class QuartzJob implements Job {

    @Autowired
    private ComponentTest componentTest;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("====================QuartzJob execute======================" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        componentTest.test();
    }
}
