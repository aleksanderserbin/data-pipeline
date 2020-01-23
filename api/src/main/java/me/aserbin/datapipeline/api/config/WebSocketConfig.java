package me.aserbin.datapipeline.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Provides configuration for websocket related beans.
 */
@Configuration
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${websocket.stomp.endpoint:/dummy}")
    private String stompEmdpoint;

    @Value("${websocket.prefix.broker:/topic}")
    private String brokerPrefix;

    @Value("${websocket.prefix.app:/app}")
    private String appPrefix;

    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint(stompEmdpoint)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(brokerPrefix);
        config.setApplicationDestinationPrefixes(appPrefix);
    }
}
