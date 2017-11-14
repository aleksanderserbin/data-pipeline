package me.aserbin.datapipeline.producer;

import me.aserbin.datapipeline.core.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.function.Function;

/**
 * Custom pipeline producer to write message to Kafka distributed log.
 * Uses preconfigured {@link org.apache.kafka.clients.producer.Producer} to
 * identify proper serializer and deserializer.
 *
 * Key is considered to be of type String.
 *
 * @param <V> Data type
 * @param <K> Key type
 * @see Producer
 */
public class KafkaPipelineProducer<K, V> implements Producer<V> {

    private org.apache.kafka.clients.producer.Producer<K, V> producer;
    private String topic;
    private Function<V, K> keyExtractor;

    /**
     * Preferable constructor with {@link this#keyExtractor}.
     * @param producer Kafka producer
     * @param topic Topic name
     * @param keyExtractor Function to extract key from input
     */
    public KafkaPipelineProducer(org.apache.kafka.clients.producer.Producer<K, V> producer,
                                 String topic,
                                 Function<V, K> keyExtractor) {
        this.producer = producer;
        this.topic = topic;
        this.keyExtractor = keyExtractor;
    }

    /**
     * Short constructor without {@link this#keyExtractor}. Provides default behavior
     * of casting input to key type. Therefore is not safe.
     * @param producer Kafka producer
     * @param topic Topic name
     */
    public KafkaPipelineProducer(org.apache.kafka.clients.producer.Producer<K, V> producer,
                                 String topic) {
        this(producer, topic, (input) -> (K) input);
    }

    @Override
    public void produce(V input) {
        this.producer.send(new ProducerRecord<>(topic, keyExtractor.apply(input), input));
    }
}
