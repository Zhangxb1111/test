package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.influxdb.InfluxDBTemplate;
import com.test.properties.InfluxDBProperties;
import com.test.properties.ThreadPoolExecutorProperties;
import com.test.service.MonUnitServiceI;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/influxDBCtl")
public class InfluxdbController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InfluxDBTemplate influxDBTemplate;
    @Autowired
    private InfluxDBProperties influxDBProperties;
    @Autowired
    private MonUnitServiceI monUnitServiceI;
    @Autowired
    private ThreadPoolExecutorProperties taskConfig;

    @RequestMapping(value = "/remoteAddData")
    @ResponseBody
    public void addRemoteDataToInfluxDB(@RequestParam("map") String lists){
        logger.info(lists);
        Map tags = null;
        Map fields = null;
        //List pointList = new ArrayList();
        //'List list =  (List) JSON.parseObject(lists);
        //for (Object obj:list){
            Map map = JSONObject.parseObject(lists);
            tags = new HashMap();
            fields = new HashMap();
            tags.put("point_code",map.get("point_code"));
            fields.put("point_name",map.get("point_name"));
            fields.put("id",map.get("id"));
            fields.put("eq_code",map.get("eq_code"));
            fields.put("eq_name",map.get("eq_name"));
            fields.put("data_unit",map.get("data_unit"));
            fields.put("monitor_value",Double.valueOf(map.get("monitor_value").toString()));
            fields.put("ts",map.get("datetime"));
            fields.put("type_code",map.get("type_code"));
            fields.put("warn_type",map.get("state"));
            Point point = null;
            Point.Builder builder = Point.measurement(influxDBProperties.getTableName())
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag(tags);
            builder.fields(fields);
            point = builder.build();
            //pointList.add(point);
        //}
        //logger.info(pointList.toString());
        /*存入influxDB数据库*/
        influxDBTemplate.write(point);
    }

    @RequestMapping(value = "/executeAsync")
    @ResponseBody
    public String submit(){
        logger.info("submit start");
        logger.info("corePoolSize:"+taskConfig.getCorePoolSize()+";maxPoolSize"+taskConfig.getMaxPoolSize()+";keepAliveSeconds:"+taskConfig.getKeepAliveSeconds()+";queueCapacity:"+taskConfig.getQueueCapacity());
        monUnitServiceI.executeAsync();
        logger.info("submit end");
        return "success";
    }
}
