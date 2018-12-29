package com.test.quartz;


import com.test.properties.InfluxDBProperties;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 定时删除mysql数据库数据
 */
@Component  //关键一
public class ThreeJob implements Job {

    @Autowired
    private InfluxDBProperties influxDBProperties;

    public static ThreeJob threeJob;  //关键二

    @PostConstruct      //关键三
    public void init(){
        threeJob = this;
    }
    /**
     * 此定时任务,用来删除MYSQL数据库数据
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {

        //threeJob.dataTableDao.deleteAll();
    }

}