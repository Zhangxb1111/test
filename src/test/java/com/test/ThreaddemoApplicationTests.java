package com.test;

import com.test.service.impl.MonUnitServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreaddemoApplicationTests {

    //@Autowired
    //private MonUnitServiceImpl monUnitService;

    @Test
    public void contextLoads() {
        //monUnitService.insert();
    }

}
