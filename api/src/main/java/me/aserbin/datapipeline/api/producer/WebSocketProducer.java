package me.aserbin.datapipeline.api.producer;

import me.aserbin.datapipeline.core.Producer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Custom pipeline producer to write messages to websocket.
 * Uses {@link SimpMessagingTemplate} as it conforms application needs.
 * @param <I> Data type
 */
public class WebSocketProducer<I> implements Producer<I> {

    private SimpMessagingTemplate template;
    private String topic;

    public WebSocketProducer(SimpMessagingTemplate simpMessagingTemplate,
                             String topic) {
        this.template = simpMessagingTemplate;
        this.topic = topic;
    }

    @Override
    public void produce(I input) {
        this.template.convertAndSend(topic, input);
    }

}
