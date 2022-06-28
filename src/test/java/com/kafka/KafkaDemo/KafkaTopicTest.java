package com.kafka.KafkaDemo;

import com.kafka.KafkaDemo.objects.Payment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.JsonMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class KafkaTopicTest extends BaseTest {
    Payment payment;

    @BeforeEach
    public void setupPayment() {
        payment = JsonMapper.getPaymentFromFile("src/test/resources/payment.json");
        payment.setId(testDataGenerator.generateUniqueId());
    }

    @Test
    void testSendMessage() {
        payment.setTimestamp(LocalDateTime.now().toString());
        TestDataGenerator.generatePaymentValues(payment);

        log.info("Sending message: {}", payment);
        publishMessage(pendingPayments, payment);

        Optional<Payment> processedPaymentFromTopic = getPaymentFromTopic(payment.getId(), paymentList);
        Assertions.assertTrue(processedPaymentFromTopic.isPresent());

        Payment paymentFromTopic = processedPaymentFromTopic.get();
        log.info("Reading from [{}] message {}", pendingPayments, paymentFromTopic);
        Assertions.assertEquals(payment.getId(), paymentFromTopic.getId());

        //checker methods and Assertions according to service specs
        checkMappingInToOutMessage(payment, paymentFromTopic);

    }

    @RepeatedTest(5)
    void testPublishMessage() {
        payment.setTimestamp(LocalDateTime.now().toString());
        TestDataGenerator.generatePaymentValues(payment);

        log.info("Sending message: {}", payment);
        publishMessage(pendingPayments, payment);

        Optional<Payment> processedPaymentFromTopic = getPaymentFromTopic(payment.getId(), paymentList);
        Assertions.assertTrue(processedPaymentFromTopic.isPresent());

        Payment paymentFromTopic = processedPaymentFromTopic.get();
        log.info("Reading from [{}] message {}", pendingPayments, paymentFromTopic);
        Assertions.assertEquals(payment.getId(), paymentFromTopic.getId());

        //checker methods and Assertions according to service specs
        checkMappingInToOutMessage(payment, paymentFromTopic);
    }

}
