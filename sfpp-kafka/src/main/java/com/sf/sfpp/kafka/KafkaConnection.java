package com.sf.sfpp.kafka;

import com.sf.kafka.api.produce.IKafkaProducer;
import com.sf.kafka.api.produce.ProduceConfig;
import com.sf.kafka.api.produce.ProducerPool;
import com.sf.kafka.exception.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/16
 */
public class KafkaConnection {
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConnection.class);
    private final int poolSize;
    private final String url;
    private final String clusterName;
    private final String topicTokens;
    private IKafkaProducer kafkaProducer;
    private final String topic;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public KafkaConnection(int poolSize, String topic, String url, String clusterName, String topicTokens) {
        this.poolSize = poolSize;
        this.url = url;
        this.clusterName = clusterName;
        this.topicTokens = topicTokens;
        ProduceConfig produceConfig = new ProduceConfig(poolSize, url, clusterName, topicTokens);
        kafkaProducer = new ProducerPool(produceConfig);
        this.topic = topic;
    }

    public void send(String message) throws KafkaException {
        try {
            kafkaProducer.sendString(topic, message);
        } catch (Exception e) {
            LOGGER.warn("", e);
            //todo 异常处理
            throw new KafkaException("Caught Exception while send kafka message, retried 3 times but still failed!");
        }
    }
}
