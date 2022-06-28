package com.kafka.KafkaDemo.objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Payment {

    private String id;

    private String paymentState;

    private String timestamp;

    private String amount;

    private String currency;

    private String creditorCountry;

    private String debitorCountry;

}
