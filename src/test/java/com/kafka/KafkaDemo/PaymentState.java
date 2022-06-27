package com.kafka.KafkaDemo;

public enum PaymentState {

    NEW("NEW"),
    PROCESSING("PROCESSING"),
    PAYED("PAYED");

    public String getState() {
        return state;
    }

    private String state;

    PaymentState(String state) {
        this.state = state;
    }
}
