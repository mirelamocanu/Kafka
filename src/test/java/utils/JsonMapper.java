package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.KafkaDemo.objects.Payment;
import org.apache.avro.specific.SpecificData;

import java.io.File;
import java.io.IOException;

public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Payment getPaymentFromString(String json) {
        mapper.registerModule(new JavaTimeModule());
        mapper.addMixIn(Payment.class, JsonMixin.class);

        try {
            return mapper.readValue(json, Payment.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getStringFromPayment(Payment payment) {
        try {
         return mapper.writeValueAsString(payment);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Payment getPaymentFromFile(String path) {
        mapper.addMixIn(Payment.class, JsonMixin.class);

        try {
            return mapper.readValue(new File(path), Payment.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    abstract static class JsonMixin {
        @JsonIgnore
        abstract SpecificData getSpecificData();
    }
}
