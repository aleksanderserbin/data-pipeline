package me.aserbin.datapipeline.api.service.dummy.listener;

import me.aserbin.datapipeline.api.producer.JpaRepositoryProducer;
import me.aserbin.datapipeline.api.producer.WebSocketProducer;
import me.aserbin.datapipeline.core.JobFailActions;
import me.aserbin.datapipeline.core.Pipeline;
import me.aserbin.datapipeline.core.PipelineBuilder;
import me.aserbin.datapipeline.model.Dummy;
import me.aserbin.datapipeline.repositories.DummyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

/**
 * Kafka Consumer listening to specific topic. Pushes
 * received messages to websocket.
 */
@Component
public class KafkaDummyListener {

    /**
     * Pipeline to edit, persist and push to WS Dummy instances:
     * 1. Set created date
     * 2. Persist to DB
     * 3. Push to WebSocker
     */
    private Pipeline<Dummy, Dummy> pipeline;

    @Autowired
    public KafkaDummyListener(DummyRepository dummyRepository,
                              SimpMessagingTemplate messagingTemplate,
                              @Value("${websocket.topic}") String wsTopic) {

        pipeline = new PipelineBuilder<Dummy, Dummy>()
                .mutate(d -> d.setCreatedDate(LocalDate.now()), JobFailActions.STOP)
                .then(new JpaRepositoryProducer<>(dummyRepository))
                .then(new WebSocketProducer<>(messagingTemplate, wsTopic))
                .build();

    }



    @KafkaListener(topics = "${kafka.topic:topic}")
    public void receive(@Payload Dummy dummy) {
        pipeline.run(dummy);
    }

}
