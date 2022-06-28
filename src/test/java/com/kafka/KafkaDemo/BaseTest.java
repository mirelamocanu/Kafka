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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class BaseTest {

    @Autowired
    TestDataGenerator testDataGenerator;

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
                .anyMatch(payment -> payment.getId().equals(id)));
        return topicList.stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst();
    }

    protected void checkMappingInToOutMessage(Payment publishedPayment, Payment processedPayment) {
        log.info("Validating payment with id {}", publishedPayment.getId());
        assertEquals(publishedPayment.getId(), processedPayment.getId(),"Payment Id is different");
        assertEquals(publishedPayment.getPaymentState(), processedPayment.getPaymentState(),"Payment State is different");
        assertEquals(publishedPayment.getAmount(), processedPayment.getAmount(),"Amount is different");
        assertEquals(publishedPayment.getCurrency(), processedPayment.getCurrency());
        assertEquals(publishedPayment.getCreditorCountry(), processedPayment.getCreditorCountry());
        assertEquals(publishedPayment.getDebitorCountry(), processedPayment.getDebitorCountry());
        log.info("All validations passed");
    }
}
