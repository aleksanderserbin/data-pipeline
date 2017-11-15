package me.aserbin.datapipeline.api.service.dummy;

import me.aserbin.datapipeline.core.Validation;
import me.aserbin.datapipeline.middleware.FunctionalProducer;
import me.aserbin.datapipeline.middleware.Mapper;
import me.aserbin.datapipeline.core.Pipeline;
import me.aserbin.datapipeline.core.PipelineBuilder;
import me.aserbin.datapipeline.model.Dummy;
import me.aserbin.datapipeline.api.producer.JpaRepositoryProducer;
import me.aserbin.datapipeline.api.producer.KafkaPipelineProducer;
import me.aserbin.datapipeline.api.producer.WebSocketProducer;
import me.aserbin.datapipeline.repositories.DummyRepository;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link DummyService} implementation based on {@link Pipeline} API.
 * Provides common {@link Dummy} actions via pipelined set of jobs.
 *
 * @see DummyService
 * @see Pipeline
 * @see me.aserbin.datapipeline.core.PipelineJob
 */
@Component
public class PipelinedDummyService implements DummyService {

    @Value("${kafka.topic:topic}")
    private String kafkaTopic;

    private DummyRepository dummyRepository;

    /**
     * Pipeline to run on Dummy post:
     * 1. Validate required fields are set
     * 2. Push to Kafka
     */
    private Pipeline<Dummy, Dummy> postPipeline;


    @Autowired
    public PipelinedDummyService(DummyRepository dummyRepository,
                                 Producer<Integer, Dummy> kafkaProducer) {
        this.dummyRepository = dummyRepository;

        this.postPipeline = new PipelineBuilder<Dummy, Dummy>()
                .then(new Validation<>(d -> d.getSomeNumber() != null, "someNumber can't be null"))
                .then(new Validation<>(d -> d.getSomeString() != null, "someString can't be null"))
                .then(new KafkaPipelineProducer<Integer, Dummy>(kafkaProducer, kafkaTopic, Dummy::getSomeNumber))
                .build();
    }

    /**
     * Runs {@link this#postPipeline PostPipeline}
     * @param dummy Dummy to save
     */
    @Override
    public void onPost(Dummy dummy) {
        postPipeline.run(dummy);
    }

    /**
     * Explodes batch into single entities and runs {@link this#postPipeline}
     * for each of them.
     * TODO: implement Kafka Batch Producer
     * @param dummies Collection of dummies to save
     */
    @Override
    public void onPostBatch(List<Dummy> dummies) {
        for (Dummy dummy : dummies) {
            postPipeline.run(dummy);
        }
    }


}
