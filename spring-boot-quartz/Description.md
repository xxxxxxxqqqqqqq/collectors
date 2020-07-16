# 定时任务

---

## @Schedule注解——Spring自带的定时任务

1.maven依赖

```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
        </dependency>
```

2.使用```@Scheduled```注解，使用IOC容器管理.

``` java
@Component
public class ScheduleDemo {

    /**
     * @Scheduled 支持cron表达式和时区的定时任务注解
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void demo() {
        System.out.println("====================execute demo======================" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}
```

3.启动类上加注解```@EnableScheduling```注解

```java
@SpringBootApplication
@EnableScheduling
public class SpringBootQuartZApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuartZApplication.class, args);
    }

}
```

---

Quzrtz框架

1.maven依赖

```xml
		<!-- quartz依赖包 https://mvnrepository.com/artifact/org.quartz-scheduler/quartz -->
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz</artifactId>
            <version>2.3.2</version>
            <!-- 去除日志依赖，web包中已经包含 -->
            <exclusions>
                <exclusion>
                    <groupId>slf4j-api</groupId>
                    <artifactId>org.slf4j</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
        </dependency>
```

2.Job作业类

```java
public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("====================QuartzJob execute======================" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        componentTest.test();
    }
}
```

3.配置类。（启动类同样需要使用```@EnableScheduling```注解，不展示）

```java
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
	
    // 简单触发器
    @Bean
    public SimpleTriggerFactoryBean getSimpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
        simpleTriggerFactoryBean.setRepeatInterval(5 * 1000);
        return simpleTriggerFactoryBean;
    }
    
	// Cron触发器 
//    @Bean
//    public CronTriggerFactoryBean getCronTriggerFactoryBean(@Qualifier("simpleJobDetail") JobDetailFactoryBean jobDetailFactoryBean) {
//        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
//        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
//        cronTriggerFactoryBean.setCronExpression("0/2 * * * * *");
//        return cronTriggerFactoryBean;
//    }

    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 如果使用cron触发器，方法参数和此处的set方法做对应的改变
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        return schedulerFactoryBean;
    }

}
```



解决job类无法使用IOC容器中的实例问题：在实例化Job时，把Job添加到IOC容器。

写法一：私有AdaptableJobFactory

```java
/**
 * @Description 私有AdaptableJobFactory
 *  继承AdaptableJobFactory，重写createJobInstance，再利用AutowireCapableBeanFactory将Job实例添加到IOC容器当中。
 * @Author xiao qi
 * @Date 2:28 2020/7/17
 **/
@Component
public class MyAdaptableJobFactory extends AdaptableJobFactory {
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        // 将Job实例添加到IOC容器当中
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
```

修改SchedulerFactoryBean的Bean配置：

```java
    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        // 此处设置私有的JobFactory
        schedulerFactoryBean.setJobFactory(myAdaptableJobFactory);
        return schedulerFactoryBean;
    }
```

写法二：利用applicationContext装载

```java
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
```

配置类添加JobFactory的配置Bean，设置到SchedulerFactoryBean。

```java
    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean, JobFactory jobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
```



























