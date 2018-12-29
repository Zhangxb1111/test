package com.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.FatherConfigTest;
import com.test.util.ToolUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class MonUnitServiceImplTest extends FatherConfigTest {

    @Autowired
    private MonUnitServiceImpl monUnitServiceImpl;

    @Test
    public void insert() {
        //monUnitServiceImpl.insert();
//        String str = "{eq_code=EQ107, monitor_value=39, sensor_code=8477900060, id=1416, warn_type=1, data_unit=℃, ts=2018-10-24_16:33:14, type_code=温度}";
//        Map map = ToolUtil.mapStringToMap(str);
//        System.out.println(map);
//        System.out.println(JSONObject.toJSON(map));
    }
}