package com.streamicroservices.kafka.admin.config.client;

import com.streamicroservices.common.config.KafkaConfigData;
import com.streamicroservices.common.config.RetryConfigData;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.support.RetryTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ComponentScan(basePackages = "com.streamicroservices")
public class KafkaAdminClient {

    private static Logger LOG = LoggerFactory.getLogger(KafkaAdminClient.class);

    private KafkaConfigData kafkaConfigData;

    private RetryConfigData retryConfigData;

    private AdminClient adminClient;

    private RetryTemplate retryTemplate;

    public KafkaAdminClient(KafkaConfigData config,
                            RetryConfigData retryConfigData,
                            AdminClient client,
                            RetryTemplate template) {
        this.kafkaConfigData = config;
        this.retryConfigData = retryConfigData;
        this.adminClient = client;
        this.retryTemplate = template;
    }
}