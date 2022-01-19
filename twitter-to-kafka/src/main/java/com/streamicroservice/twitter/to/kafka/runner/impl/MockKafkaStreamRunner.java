package com.streamicroservice.twitter.to.kafka.runner.impl;

import com.streamicroservice.twitter.to.kafka.config.TwitterToKafkaServiceConfigData;
import com.streamicroservice.twitter.to.kafka.exception.TwitterToKafkaServiceException;
import com.streamicroservice.twitter.to.kafka.listener.TwitterKafkaStatusListener;
import com.streamicroservice.twitter.to.kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ConditionalOnProperty(name = "twitter-ko-kafka-service.enable-mock-tweets")
public class MockKafkaStreamRunner implements StreamRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    private final TwitterToKafkaServiceConfigData configData;
    private final TwitterKafkaStatusListener listener;

    private static final Random RANDOM = new Random();

    private static final String[] WORDS = new String[]{
            "Lorem", "Ipsum", "dolor", "sit", "amet", "consectetuer", "elit", "Maecenas"
    };

    private static final String tweetRawJson =  "{" +
            "\"created at\":\" {0}\"," +
            "\"text\":\" {1}\"";

    private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public MockKafkaStreamRunner(TwitterToKafkaServiceConfigData configData, TwitterKafkaStatusListener listener) {
        this.configData = configData;
        this.listener = listener;
    }


    public void start() throws TwitterException{
        String[] keywords = configData.getTwitterKeywords().toArray(new String[0]);
        int minTweetLength = configData.getMockMinTweetLength();
        int maxTweetLength = configData.getMockMaxTweetLength();
        long sleepTimeMs = configData.getMockSleepMs();
        LOG.info("Starting mock filtering twitter streams for keywords", Arrays.toString(keywords));
        simulateTwitterStream(keywords, minTweetLength, maxTweetLength, sleepTimeMs);

    }

    private void simulateTwitterStream(String[] keywords, int minTweetLength, int maxTweetLength, long sleepTimeMs) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while(true){
                    String formattedTweetAsJson = getFormattedTweet(keywords, minTweetLength, maxTweetLength);
                    Status status = TwitterObjectFactory.createStatus(formattedTweetAsJson);
                    listener.onStatus(status);
                    sleep(sleepTimeMs);
                }
            }catch (TwitterException e){
               LOG.info("Error creating twitter status:", e);
            }
        });
    }

    private void sleep(long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        }catch (InterruptedException e){
            throw new TwitterToKafkaServiceException("Error while sleeping");
        }
    }

    private String getFormattedTweet(String[] keywords, int minTweetLength, int maxTweetLength) {
        String[] params = new String[]{
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
                getRandomTweetContent(keywords,minTweetLength,maxTweetLength)
        };
        String tweet = tweetRawJson;

        for (int i = 0 ; i < params.length; i++ ){
            tweet = tweet.replace("{" + i + "}" , params[i]);
        }
        return tweet;
    }

    private String getRandomTweetContent(String[] keywords, int minTweetLength, int maxTweetLength) {
        StringBuilder tweet = new StringBuilder();
        int tweetLength = RANDOM.nextInt(maxTweetLength - minTweetLength + 1) + minTweetLength;
        return formatTweetAsJsonParams(keywords, tweet, tweetLength);
    }

    private String formatTweetAsJsonParams(String[] keywords, StringBuilder tweet, int tweetLength) {
        for (int i = 0; i < tweetLength; i++ ){
            tweet.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
            if ( i == tweetLength /2) {
                tweet.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
            }
        }
        return tweet.toString().trim();
    }
}
