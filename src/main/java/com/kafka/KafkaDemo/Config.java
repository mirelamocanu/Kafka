package com.kafka.KafkaDemo;

import com.kafka.KafkaDemo.objects.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableKafka
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class Config {
    public final KafkaProperties kafkaProperties;

    @Autowired
    public Config(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    //Producer
    @Bean
    public ProducerFactory<String, Payment> specificRecordProducer() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, Payment> personKafkaTemplate() {
        return new KafkaTemplate<>(specificRecordProducer());
    }

    //Consumer
    @Bean
    public ConsumerFactory<String, Payment> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Payment> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Payment> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    //Listener
    @Bean
    List<Payment> paymentList() {
        return new ArrayList<>();
    }

    @KafkaListener(topics = "${spring.kafka.topics.pending-payments}")
    public void paymentTopicList(Payment payment) {
        paymentList().add(payment);
    }
}
