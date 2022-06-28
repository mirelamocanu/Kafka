package com.kafka.KafkaDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class TestDataProducer {
    static final List<String> currencies = Arrays.asList("EUR", "USD", "GBP", "CHF", "CAD", "AUD", "CNY");
    static final List<String> countries = Arrays.asList("Germany", "France", "Italy", "Poland", "Netherlands", "Belgium", "China", "Australia", "Canada", "USA");

    public String generateUniqueId() {
        long uuidSeq = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        return (DateTimeFormatter.ofPattern("yyMMdd").format(LocalDateTime.now()) + uuidSeq).substring(0,15);
    }

    public String pickRandom(List<String> stringList) {
        return stringList.size() == 1
                ? stringList.get(0)
                : stringList.get(new Random().ints(0, stringList.size()).findFirst().getAsInt());
    }
    public String generateAmount (){
        double amount = new Random().ints(0, 99).findFirst().getAsInt() * 10008.46;
        return BigDecimal.valueOf(amount).toString();
    }
}
