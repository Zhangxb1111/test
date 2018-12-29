package com.test.configration;

import com.test.kafka.KafkaProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory(final KafkaProperties kafkaProperties) {
        // set the producer properties
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getProducer().get("batch-size"));
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getProducer().get("buffer-memory"));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<String, String>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(final KafkaProperties kafkaProperties) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(producerFactory(kafkaProperties));
        return kafkaTemplate;
    }

}
