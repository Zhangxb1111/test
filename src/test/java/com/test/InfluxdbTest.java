package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.configration.InfluxDBConfiguration;
import com.test.influxdb.InfluxDBTemplate;
import com.test.properties.InfluxDBProperties;
import com.test.util.RedisUtil;
import org.influxdb.dto.Point;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class InfluxdbTest extends FatherConfigTest {

    private  static final Logger logger = LoggerFactory.getLogger(InfluxdbTest.class);

    @Autowired
    private InfluxDBProperties influxDBProperties;
    @Autowired
    private InfluxDBTemplate influxDBTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void test(){
        if (null == influxDBTemplate){
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(InfluxDBConfiguration.class);
            influxDBTemplate = applicationContext.getBean(InfluxDBTemplate.class);
        }
        String sql = "select * from test_bonc where time>now()-1d";
        List list = influxDBTemplate.query(sql,influxDBProperties.getDatabase(),null);
        logger.info(sql);
        logger.info("list=====>"+list);
        Map map = null;
        Map tags = null;
        Map fields = null;
        if (null != list){
            for (int i=0;i<list.size();i++){
                map = JSONObject.parseObject(JSON.toJSONString(list.get(i)));
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
                fields.put("warn_type",map.get("warn_type"));
                Point point = null;
                Point.Builder builder = Point.measurement(influxDBProperties.getTableName())
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .tag(tags);
                builder.fields(fields);
                point = builder.build();
                influxDBTemplate.write(point);
            }
        }
    }
}
