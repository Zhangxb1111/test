package com.test.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobDetail oneJob = JobBuilder.newJob(OneJob.class).withIdentity("job1", "group1").build();
        JobDetail secondJob = JobBuilder.newJob(SecondJob.class).withIdentity("job2", "group2").build();
        JobDetail threeJob = JobBuilder.newJob(ThreeJob.class).withIdentity("job3","group3").build();
        TriggerBuilder<Trigger> newTrigger = trigger();

        newTrigger.withIdentity("trigger1", "group1");
        newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"));//cron表达式 一分钟一次
        //newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"));//10秒一次
        Trigger oneTrigger = newTrigger.build();

        newTrigger.withIdentity("trigger2", "group2");
        newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"));
        Trigger secondTrigger = newTrigger.build();

        newTrigger.withIdentity("trigger3", "group3");
        newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0 0 17 * * ?"));
        Trigger threeTrigger = newTrigger.build();
        //作业绑定触发器
        scheduler.scheduleJob(oneJob, oneTrigger);//展示实时数据
        scheduler.scheduleJob(secondJob, secondTrigger);//定时存储进数据仓库
        scheduler.scheduleJob(threeJob,threeTrigger);//定时删除MYSQL数据库数据
        //调度器开始
        scheduler.start();
        return scheduler;
    }

    @Bean
    public TriggerBuilder<Trigger> trigger() {
        return TriggerBuilder.newTrigger();
    }

}