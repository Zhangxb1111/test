package com.test.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix="spring.kafka")
public class KafkaProperties {

    private String bootstrapServers;

    private Map<String,String> producer;
    private Map<String,String> consumer;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public Map<String, String> getProducer() {
        return producer;
    }

    public void setProducer(Map<String, String> producer) {
        this.producer = producer;
    }

    public Map<String, String> getConsumer() {
        return consumer;
    }

    public void setConsumer(Map<String, String> consumer) {
        this.consumer = consumer;
    }
}
