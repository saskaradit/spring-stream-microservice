package com.streamicroservices.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-ko-kafka-service")
public class TwitterToKafkaServiceConfigData {
    private List<String> twitterKeywords;
    private String welcomeMessage;
    private Boolean enableMockTweets;
    private Long mockSleepMs;
    private Integer mockMinTweetLength;
    private Integer mockMaxTweetLength;

    public Boolean getEnableMockTweets() {
        return enableMockTweets;
    }

    public void setEnableMockTweets(Boolean enableMockTweets) {
        this.enableMockTweets = enableMockTweets;
    }

    public Long getMockSleepMs() {
        return mockSleepMs;
    }

    public void setMockSleepMs(Long mockSleepMs) {
        this.mockSleepMs = mockSleepMs;
    }

    public Integer getMockMinTweetLength() {
        return mockMinTweetLength;
    }

    public void setMockMinTweetLength(Integer mockMinTweetLength) {
        this.mockMinTweetLength = mockMinTweetLength;
    }

    public Integer getMockMaxTweetLength() {
        return mockMaxTweetLength;
    }

    public void setMockMaxTweetLength(Integer mockMaxTweetLength) {
        this.mockMaxTweetLength = mockMaxTweetLength;
    }

    public List<String> getTwitterKeywords() {
        return twitterKeywords;
    }

    public void setTwitterKeywords(List<String> twitterKeywords) {
        this.twitterKeywords = twitterKeywords;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }
}
