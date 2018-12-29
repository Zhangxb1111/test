package com.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.test.dao")
public class ThreaddemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(ThreaddemoApplication.class, args);
        System.out.println("应用启动成功!");
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        String[] names = applicationContext.getBeanDefinitionNames();
//        /*遍历ioc容器中的bean的id*/
//        for (String name:names){
//            System.out.println("ioc容器中的Bean的ID:-->"+name);
//        }
    }
}
