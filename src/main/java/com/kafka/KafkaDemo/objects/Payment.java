package com.kafka.KafkaDemo.objects;

import lombok.*;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Payment extends SpecificRecordBase implements SpecificRecord  {

    private String id;

    private String paymentState;

    private String timestamp;

    private BigDecimal amount;

    private String currency;

    private String creditorCountry;

    private String debitorCountry;

    public static final Schema SCHEMA =new Schema.Parser().parse("" +
            "{\n" +
            " \"namespace\": \"com.kafka.KafkaDemo.objects.Payment\",\n" +
            " \"type\": \"record\",\n" +
            " \"name\": \"Payment\",\n" +
            " \"fields\": [\n" +
            "     {\"name\": \"id\", \"type\": \"string\"},\n" +
            "     {\"name\": \"paymentState\", \"type\": \"string\"},\n" +
            "     {\"name\": \"timestamp\", \"type\": \"string\"},\n" +
            "     {\"name\": \"amount\", \"type\": \"string\"},\n" +
            "     {\"name\": \"currency\", \"type\": \"string\"},\n" +
            "     {\"name\": \"creditorCountry\", \"type\": \"string\"},\n" +
            "     {\"name\": \"debitorCountry\", \"type\": \"string\"}\n" +
            " ]\n" +
            "}");

    private static final SpecificData MODEL$=new SpecificData();

    @Override
    public Schema getSchema() {
        return SCHEMA;
    }

    @Override
    public Object get(int field) {
        return null;
    }

    @Override
    public void put(int field, Object value) {

    }
}
