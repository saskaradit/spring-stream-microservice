package com.streamicroservices.twitter.to.kafka.init.impl;

import com.streamicroservices.common.config.KafkaConfigData;
import com.streamicroservices.kafka.admin.client.KafkaAdminClient;
import com.streamicroservices.twitter.to.kafka.init.StreamInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.streamicroservices")
public class KafkaStreamInitializer implements StreamInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializer.class);
    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializer(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
        this.kafkaAdminClient = kafkaAdminClient;
        this.kafkaConfigData = kafkaConfigData;
    }

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        LOG.info("Topic '{}' is ready for operations", kafkaConfigData.getTopicNamesToCreate().toArray());
    }

}
