package com.xxxxxxxqqqqqqq.collectors.quartz.config;

import com.xxxxxxxqqqqqqq.collectors.quartz.job.QuartzJob;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * @Description Quartz配置类
 *  三要素：Job、Trigger(simple, cron)、Schedule
 * @Author xiao qi
 * @Date 1:51 2020/7/17
 **/
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetailFactoryBean getJobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(QuartzJob.class);
        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean getSimpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
        simpleTriggerFactoryBean.setRepeatInterval(5 * 1000);
        return simpleTriggerFactoryBean;
    }

//    @Bean
//    public CronTriggerFactoryBean getCronTriggerFactoryBean(@Qualifier("simpleJobDetail") JobDetailFactoryBean jobDetailFactoryBean) {
//        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
//        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
//        cronTriggerFactoryBean.setCronExpression("0/2 * * * * *");
//        return cronTriggerFactoryBean;
//    }

//    /**
//     * 解决Job中bean注入问题的写法一
//     *
//     * @param simpleTriggerFactoryBean
//     * @param myAdaptableJobFactory
//     * @return
//     */
//    @Bean
//    public SchedulerFactoryBean getSchedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory) {
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
//        schedulerFactoryBean.setJobFactory(myAdaptableJobFactory);
//        return schedulerFactoryBean;
//    }

    /**
     * 解决Job中bean注入问题的写法二
     *
     * @param simpleTriggerFactoryBean trigger触发器
     * @param jobFactory 自定义Job工厂
     * @return
     */
    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean, JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        return schedulerFactoryBean;
    }

    /**
     * 解决Job中bean注入问题的写法二
     *
     * @param applicationContext 上下文
     * @return
     */
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
