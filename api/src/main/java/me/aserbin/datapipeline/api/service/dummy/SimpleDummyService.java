package me.aserbin.datapipeline.api.service.dummy;

import me.aserbin.datapipeline.model.Dummy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleDummyService implements DummyService {

    private KafkaProducer<Integer, Dummy> kafkaProducer;
    private String kafkaTopic;

    public SimpleDummyService(KafkaProducer<Integer, Dummy> kafkaProducer,
                              @Value("${kafka.topic}") String kafkaTopic) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaTopic = kafkaTopic;
    }

    @Override
    public void onPost(Dummy dummy) {
        kafkaProducer.send(new ProducerRecord<Integer, Dummy>(kafkaTopic,
                dummy.getSomeNumber(), dummy));
    }

    @Override
    public void onPostBatch(List<Dummy> dummies) {

    }
}
