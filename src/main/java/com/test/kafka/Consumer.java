package com.test.kafka;

import com.alibaba.fastjson.JSONObject;
import com.test.influxdb.InfluxDBTemplate;
import com.test.properties.InfluxDBProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auth zxb
 * @Date 2018/9/3
 * @Desc
 */
@Component
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InfluxDBTemplate influxDBTemplate;
    @Autowired
    private InfluxDBProperties influxDBProperties;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @KafkaListener(topics = "kafka_my02")
    public void listen(ConsumerRecord<?, ?> record) throws Exception {
        Double upWARN_VALUE = 100.0;
        Double downWARN_VALUE = 60.0;
        String WARN_TYPE = "";
        System.out.println("record:"+record);
        Object obj = JSONObject.parseObject(record.value().toString());
        Double WARN_VALUE = ((JSONObject) obj).getDouble("WARN_VALUE");
        if (WARN_VALUE>upWARN_VALUE){
            WARN_TYPE = "2";
        }else if (WARN_VALUE>downWARN_VALUE && WARN_VALUE<upWARN_VALUE){
            WARN_TYPE = "1";
        }else{
            WARN_TYPE = "0";
        }
        ((JSONObject) obj).remove("WARN_VALUE");
        ((JSONObject) obj).put("WARN_TYPE",WARN_TYPE);
        ((JSONObject) obj).put("TS",record.timestamp());
        Map objMap = (Map)obj;
        Map tags = new HashMap();
        Map fields = new HashMap();
        tags.put("SENSOR_CODE",objMap.get("SENSOR_CODE"));
        fields.put("EQ_CODE",objMap.get("EQ_CODE"));
        fields.put("CODE",objMap.get("CODE"));
        fields.put("DATA_UNIT",objMap.get("DATA_UNIT"));
        fields.put("WARN_TYPE",objMap.get("WARN_TYPE"));
        fields.put("MONITOR_VALUE",Double.valueOf(objMap.get("MONITOR_VALUE").toString()));
        Long lt = new Long(objMap.get("TS").toString());
        Date date = new Date(lt);
        fields.put("TS",simpleDateFormat.format(date));
        Point point = null;
        Point.Builder builder = Point.measurement(influxDBProperties.getTableName())
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag(tags);
        builder.fields(fields);
        point = builder.build();
        influxDBTemplate.write(point);
        System.out.printf("topic = %s, offset = %s, value = %s \n", record.topic(), record.key(), record.value());
    }
}

