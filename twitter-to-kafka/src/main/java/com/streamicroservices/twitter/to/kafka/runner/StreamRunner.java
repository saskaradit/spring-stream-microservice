package com.streamicroservices.twitter.to.kafka.runner;

import twitter4j.TwitterException;

public interface StreamRunner {
    void start() throws TwitterException;
}
