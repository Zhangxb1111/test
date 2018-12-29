package com.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zxb
 */
@Controller
@RequestMapping("/kafka")
public class ProducerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/send", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sendKafka(@RequestParam("message") String message) {
        try {
            logger.info("kafka的消息={}", message);
            kafkaTemplate.send("kafka_my02", "key", message);
            logger.info("发送kafka成功.");
            return "successs";
        } catch (Exception e) {
            logger.error("发送kafka失败", e);
            return "failure";
        }
    }
}
