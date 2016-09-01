package com.sf.sfpp.kafka;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/16
 */
public class KafkaConnectionPool {

    private final HashMap<String, KafkaConnection> connMap = new HashMap<>();

    public KafkaConnectionPool(String kafkaTopic, String kafkaUrl, String kafkaClusterName, String kafkaTopicToken, int poolSize) {
        String[] kafkaTopics = kafkaTopic.split(",");
        trimArray(kafkaTopics);
        String[] kafkaUrls = kafkaUrl.split(",");
        trimArray(kafkaUrls);
        String[] kafkaClusterNames = kafkaClusterName.split(",");
        trimArray(kafkaClusterNames);
        String[] kafkaTopicTokens = kafkaTopicToken.split(",");
        trimArray(kafkaTopicTokens);
        int max = Math.max(kafkaTopics.length, Math.max(kafkaUrls.length, Math.max(kafkaClusterNames.length, kafkaTopicTokens.length)));
        for (int i = 0; i < max; i++) {
            KafkaConnection kafkaConnection = new KafkaConnection(poolSize,
                    kafkaTopics[i % kafkaTopics.length],
                    kafkaUrls[i % kafkaUrls.length],
                    kafkaClusterNames[i % kafkaClusterNames.length],
                    kafkaTopicTokens[i % kafkaTopicTokens.length]);
            connMap.put(kafkaTopics[i % kafkaTopics.length], kafkaConnection);
        }
    }

    private void trimArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }
    }

    public KafkaConnection getKafkaConnection(String topic) {
        return connMap.get(topic);
    }
}
