package com.test.service.impl;

import com.test.dao.MonUnitMapper;
import com.test.entity.MonUnit;
import com.test.service.MonUnitServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MonUnitServiceImpl implements MonUnitServiceI {

    private static final Logger logger = LoggerFactory.getLogger(MonUnitServiceImpl.class);

    private DateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

    @Autowired
    private MonUnitMapper monUnitMapper;

    public void insert() {
        MonUnit monUnit = new MonUnit();
        monUnit.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        monUnit.setUnitName("m");
        monUnit.setAggregateType("温度");
        monUnit.setTypeCode(monUnit.getId().substring(1, 20));
        monUnit.setCreateTime(df.format(new Date()));
        monUnit.setCreateUser("zhangxubin");
        monUnit.setModifyTime(df.format(new Date()));
        monUnit.setModifyUser("zhangxubin");
        monUnitMapper.insert(monUnit);
    }

    @Async("asyncServiceExecutor")
    public void executeAsync(){
        logger.info("start executeAsync");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }

    @Override
    public List<MonUnit> getList() {
        return monUnitMapper.getList();
    }

}
