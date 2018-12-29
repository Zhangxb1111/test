package com.test.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketTemplate {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    //向订阅了 /queue/hello 客户端websocket实例发送数据
    public void sendMessage(String message){
        System.out.println("=====================================");
        messagingTemplate.convertAndSend("/topic/hello",message);
    }
}
