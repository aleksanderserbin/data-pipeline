package me.aserbin.datapipeline.api;

import me.aserbin.datapipeline.api.config.WebSocketConfig;
import me.aserbin.datapipeline.api.config.KafkaConfig;
import me.aserbin.datapipeline.api.config.RepositoryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;


@EnableAutoConfiguration
@EnableWebSocketMessageBroker
@SpringBootApplication
@ComponentScan("me.aserbin.datapipeline")
public class Main {

    // TODO: add logger
    // TODO: test memory consumption
    // maven versions to props
    // package containers module to tar

    public static void main(String[] args) {

        SpringApplication.run(new Class[]{
                KafkaConfig.class,
                Main.class,
                WebSocketConfig.class,
                RepositoryConfig.class}, args);

    }

}
