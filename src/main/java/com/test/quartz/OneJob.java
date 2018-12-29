package com.test.quartz;

import com.alibaba.fastjson.JSON;
import com.test.influxdb.InfluxDBTemplate;
import com.test.properties.InfluxDBProperties;
import com.test.websocket.WebSocketTemplate;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实时数据展示任务
 */
@Component//关键一
public class OneJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(OneJob.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Autowired
    private WebSocketTemplate webSocketTemplate;
    @Autowired
    private InfluxDBTemplate influxDBTemplate;
    @Autowired
    private InfluxDBProperties influxDBProperties;

    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    private static OneJob oneJob;//关键二

    @PostConstruct      //关键三
    public void init(){
        oneJob = this;
    }
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //index3测试
        /*List list = new ArrayList<Map>();
        Map map = null;
        for (int i=0;i<10;i++){
            map = this.getMonitorValue("sensor"+i);
            list.add(map);
        }*/
        List list = this.getRealTimeData();
        oneJob.webSocketTemplate.sendMessage(JSON.toJSONString(list));
    }

    public List getRealTimeData(){
        List allList = new ArrayList();
        String sql = "select * from "+oneJob.influxDBProperties.getTableName()+" where time>now()-1m group by point_code tz('Asia/Shanghai')";
        logger.info("sql:"+sql);
        String sql2 = "select mean(monitor_value) from "+oneJob.influxDBProperties.getTableName()+" where time>now()-1m group by point_code tz('Asia/Shanghai')";
        logger.info("饼状图sql:"+sql2);
        Query query = new Query(sql,oneJob.influxDBProperties.getDatabase());
        QueryResult queryResult = oneJob.influxDBTemplate.query(query);
        Query query2 = new Query(sql2,oneJob.influxDBProperties.getDatabase());
        QueryResult queryResult2 = oneJob.influxDBTemplate.query(query2);
        List point_value_list = new ArrayList();
        Map single_point_map = null;
        List ts = null;
        List data = null;
        if (null != queryResult.getResults()){
            QueryResult.Result results = queryResult.getResults().get(0);
            if (null != results.getSeries() && results.getSeries().size() != 0){
                for (QueryResult.Series series:results.getSeries()){
                    ts = new ArrayList();
                    data = new ArrayList();
                    single_point_map = new HashMap();
                    single_point_map.put("point_code",series.getTags().get("point_code"));
                    if (null != series.getValues() && series.getValues().size() != 0){
                        for (int i=0;i<series.getValues().size();i++){
                            ts.add(series.getValues().get(i).get(7));
                            data.add(series.getValues().get(i).get(5));
                        }
                    }
                    single_point_map.put("time",ts);
                    logger.info("ts"+ts.toString());
                    single_point_map.put("data",data);
                    logger.info("data"+data.toString());
                    point_value_list.add(single_point_map);
                }
            }
        }
        List pieList = new ArrayList();
        Map pieMap = null;
        if (null != queryResult2.getResults()){
            List<QueryResult.Series> seriesList = queryResult2.getResults().get(0).getSeries();
            if (null != seriesList && seriesList.size() != 0){
                for (QueryResult.Series series:seriesList){
                    if (null != series.getValues()){
                        pieMap = new HashMap();
                        pieMap.put("value",df.format(series.getValues().get(0).get(1)));
                        pieMap.put("name",series.getTags().get("point_code"));
                        pieList.add(pieMap);
                    }
                }
            }
        }
        allList.add(0,point_value_list);
        allList.add(1,pieList);
        return allList;
    }

    /**
     * 查询时序数据库数据 index3测试
     * @param
     * @return
     */
    public Map getMonitorValue(String sensorCode){
        List timeList = null;
        List valList = null;
        Map map = null;
        Query query = new Query("select MONITOR_VALUE,TS from "+influxDBProperties.getTableName()+" where time > now()-1h and \"SENSOR_CODE\"='"+sensorCode+"'",influxDBProperties.getDatabase());
        QueryResult queryResult = influxDBTemplate.query(query);
        if(queryResult.getResults() != null){
            QueryResult.Result result = queryResult.getResults().get(0);
            if (result.getSeries() != null){
                timeList = new ArrayList();
                valList = new ArrayList();
                map = new HashMap();
                List<List<Object>> values = result.getSeries().get(0).getValues();
                for (int j=0;j<values.size();j++){
                    timeList.add(j,values.get(j).get(2));
                    valList.add(j,values.get(j).get(1));
                }
                map.put("SensorCode",sensorCode);
                map.put("timeList",timeList);
                map.put("valList",valList);
            }
        }
        return map;
    }

}