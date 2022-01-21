package com.streamicroservices.twitter.to.kafka;

import com.streamicroservices.twitter.to.kafka.init.StreamInitializer;
import com.streamicroservices.twitter.to.kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.streamicroservices")
public class TwitterToKafkaService implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaService.class);

    private final StreamInitializer streamInitializer;

    private final StreamRunner streamRunner;

    public TwitterToKafkaService(StreamInitializer streamInitializer, StreamRunner runner) {
        this.streamInitializer = streamInitializer;
        this.streamRunner = runner;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaService.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("App starts...");
        streamInitializer.init();
        streamRunner.start();
    }
}

