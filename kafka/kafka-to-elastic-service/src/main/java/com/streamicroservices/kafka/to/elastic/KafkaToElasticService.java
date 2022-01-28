package com.streamicroservices.kafka.to.elastic;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.streamicroservices")
public class KafkaToElasticService {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticService.class,args);
    }
}
