package com.example.consumer;

import com.example.consumer.avro.CustomerRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class SinkConfiguration {
    @Autowired
    StreamBridge streamBridge;

    @Bean
    public Consumer<CustomerData> usSink() {
        return message -> {
            log.info("US broker message <{}>", message);
            streamBridge.send(
                    "customers-out-0",
                    CustomerRecord.newBuilder()
                            .setFirstName(message.getFirstName())
                            .setLastName(message.getLastName())
                            .setOrigin(message.getOrigin())
                            .build()
            );
        };
    }

    @Bean
    public Consumer<CustomerData> irelandSink() {
        return message -> {
            log.info("Ireland Broker message <{}>", message);
            streamBridge.send(
                    "customers-out-0",
                    CustomerRecord.newBuilder()
                            .setFirstName(message.getFirstName())
                            .setLastName(message.getLastName())
                            .setOrigin(message.getOrigin())
                            .build()
            );
        };
    }
}
