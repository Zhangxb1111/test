package com.test.configration;

import com.test.properties.ThreadPoolExecutorProperties;
import com.test.thread.VisiableThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableConfigurationProperties({ThreadPoolExecutorProperties.class})
@EnableAsync
public class ExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Autowired
    private ThreadPoolExecutorProperties taskConfig;

    @Bean
    public Executor asyncServiceExecutor(){
        logger.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(taskConfig.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(taskConfig.getMaxPoolSize());
        //配置线程池中的线程的前缀
        executor.setThreadNamePrefix("async-service-"+Thread.currentThread().getId());
        //配置队列大小
        executor.setQueueCapacity(taskConfig.getQueueCapacity());
        //配置线程活跃时间
        executor.setKeepAliveSeconds(taskConfig.getKeepAliveSeconds());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        logger.info("end asyncServiceExecutor");
        return executor;
    }
}
