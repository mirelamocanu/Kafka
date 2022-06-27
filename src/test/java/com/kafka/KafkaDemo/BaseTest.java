package com.kafka.KafkaDemo;

import com.kafka.KafkaDemo.objects.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
@Slf4j
class BaseTest {

    @Autowired
    TestDataProducer testDataProducer;

    @Value("${spring.kafka.topics.pending-payments}")
    protected String pendingPayments;

    @Autowired
    protected List<Payment> paymentList;

    @Autowired
    protected KafkaTemplate<String, Payment> paymentKafkaTemplateProducer;

    protected void publishMessage(String topic, Payment payment) {
        paymentKafkaTemplateProducer.send(topic, payment);
    }

    protected Optional<Payment> getPaymentFromTopic(String id, List<Payment> topicList) {
        await().atMost(5, TimeUnit.SECONDS).until(() -> topicList.stream()
                .anyMatch(payment -> id.equals(payment.getId())));
        return topicList.stream()
                .filter(payment -> id.equals(payment.getId()))
                .findFirst();
    }

}
