package com.test;

import com.test.interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Component
public class WebMvcAutoConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        super.addResourceHandlers(registry);
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/homeCtl/home").setViewName("index");
//        super.addViewControllers(registry);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        System.out.println("进入拦截器(过滤器Filter)");
        //注册session拦截器
        registry.addInterceptor(new Interceptor()).addPathPatterns("/**").excludePathPatterns(
                "/dtCtl/delData",
                "/kafka/send",
                "/influxDBCtl/*",
                "/index2.html",
                "/view.html**"
        );

        super.addInterceptors(registry);
    }
}
