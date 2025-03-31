package com.francode.hotelBackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Habilita un broker simple para los destinos "/topic" y "/queue"
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");  // Prefijo para las solicitudes desde el cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Establece el punto de entrada para WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173/")
                .withSockJS();
    }
}
