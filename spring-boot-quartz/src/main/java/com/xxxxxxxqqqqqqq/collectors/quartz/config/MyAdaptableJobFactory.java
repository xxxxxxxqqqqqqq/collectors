//package com.xxxxxxxqqqqqqq.collectors.quartz.config;
//
//import org.quartz.spi.TriggerFiredBundle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.scheduling.quartz.AdaptableJobFactory;
//import org.springframework.stereotype.Component;
//
///**
// * @Description 私有AdaptableJobFactory   解决Job中bean注入问题的写法一
// *  继承AdaptableJobFactory，重写createJobInstance，再利用AutowireCapableBeanFactory将Job实例添加到IOC容器当中。
// *
// * @Author xiao qi
// * @Date 2:28 2020/7/17
// **/
//@Component
//public class MyAdaptableJobFactory extends AdaptableJobFactory {
//
//    @Autowired
//    private AutowireCapableBeanFactory autowireCapableBeanFactory;
//
//    @Override
//    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
//        Object jobInstance = super.createJobInstance(bundle);
//        // 将Job实例添加到IOC容器当中
//        autowireCapableBeanFactory.autowireBean(jobInstance);
//        return jobInstance;
//    }
//
//}
