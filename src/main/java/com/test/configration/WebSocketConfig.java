package com.test.configration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by sang on 16-12-22.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    /**
     *  服务器要监听的端口，message会从这里进来，要对这里加一个Handler
     *  这样在网页中就可以通过websocket连接上服务了
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册stomp的节点，映射到指定的url,并指定使用sockjs协议./endpointChat"是前台连接的端点url
        stompEndpointRegistry.addEndpoint("/endpointChat").withSockJS();
    }

    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // queue、topic、user代理
        //registry.enableSimpleBroker("/queue");//配置客户端订阅前缀,一对一发送
        registry.enableSimpleBroker("/topic");//
       // registry.setApplicationDestinationPrefixes("/app");//配置客户端发送消息路径前缀
    }
}
