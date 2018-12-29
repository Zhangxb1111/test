package com.test.quartz;


import com.alibaba.fastjson.JSONObject;
import com.test.properties.InfluxDBProperties;
import com.test.util.HttpDeal;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 数据存储到数据仓库
 */
@Component//关键一
public class SecondJob implements Job {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InfluxDBProperties influxDBProperties;

    private static SecondJob secondJob;//关键二
    private HttpDeal httpDeal = new HttpDeal();

    @PostConstruct     //关键三
    public void init(){
        secondJob = this;
    }
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        int i = (int) (Math.random()*(10-1+1));//1-10的个位正数
        int sensor_code = (int) ((Math.random()*9+1)*100000);//6位数正数
        StringBuffer monitor_value = new StringBuffer();
        for(int j=0;j<3;j++){
            monitor_value.append((int) (Math.random()*(10-1+1)));
        }
        Double warn_value = (Math.random()*9+1)*10;
        Map<String,String> map = new HashMap<String,String>();
        Map<String,String> sendmap = new HashMap<String,String>();
        map.put("EQ_CODE",String.valueOf(sensor_code));
        map.put("SENSOR_CODE","sensor"+i);
        map.put("CODE", UUID.randomUUID().toString().replaceAll("-",""));
        map.put("DATA_UNIT","℃");
        map.put("MONITOR_VALUE",String.valueOf(monitor_value));
        map.put("TS",String.valueOf(System.currentTimeMillis()));
        map.put("WARN_VALUE",String.valueOf(warn_value));
        sendmap.put("message", JSONObject.toJSONString(map));
        //httpDeal.post(secondJob.influxDBProperties.getHttp(),sendmap);
    }
}