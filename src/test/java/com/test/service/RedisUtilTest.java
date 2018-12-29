package com.test.service;

import com.test.FatherConfigTest;
import com.test.util.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class RedisUtilTest extends FatherConfigTest {

    @Autowired
    private RedisUtil redisUtil;
    @Test  //测试redis
    public void testRedisDataMethod(){
        System.out.println("--------String--------");
        redisUtil.set("test1","test1");
        redisUtil.expire("test1",10);
        System.out.println("测试-test1:"+redisUtil.get("test1"));
        System.out.println("--------List--------");
        List list = new ArrayList();
        list.add("huangbo");
        list.add("xuzheng");
        list.add("wangbaoqiang");
        redisUtil.lSet("meumber",list);
        /*for (int i=0;i<redisUtil.lGetListSize("lGetListSize");i++){redisUtil.l
            System.out.println("lGetListSize:"+redisUtil.lGetListSize("meumber"));
            System.out.println("meumber-value:"+redisUtil.lGetIndex("meumber",i));
        }*/
        System.out.println("++++++迭代list集合++++++");
        Iterator iterator = redisUtil.lGet("meumber",0,-1).iterator();
        while (iterator.hasNext()){
            System.out.println("meumber-value2:"+iterator.next());
        }
        System.out.println("------------Set------------");
        redisUtil.sSet("sets","w1","x2","h3");
        System.out.println("遍历指定key的set集合");
        Iterator iterator2 = redisUtil.sGet("sets").iterator();
        while(iterator2.hasNext()){
            System.out.println("set集合中的值:"+iterator2.next());
        }
        System.out.println("set中是否有key value:"+redisUtil.sHasKey("sets","w1"));
        System.out.println("对应key对应的set集合长度:"+redisUtil.sGetSetSize("sets"));

        System.out.println("--------------Map-------------");
        Map map = new HashMap();
        map.put("one","zhangsan");
        map.put("two","lisi");
        map.put("three","wangwu");
//        redisUtil.hmset("map",map,10);
        redisUtil.hmset("map",map);
        Set<Map.Entry<Object, Object>> entries = redisUtil.hmget("map").entrySet();
        Iterator iterator3 = entries.iterator();
        while(iterator3.hasNext()){
            System.out.println("map对应key的value值:"+iterator3.next());
        }
        System.out.println("key对应的map中item对应value:"+redisUtil.hget("map","one"));
    }

    @Test
    public  void testRedisDataPrint(){
        System.out.println("测试-test1:"+redisUtil.get("test1"));
    }
}
