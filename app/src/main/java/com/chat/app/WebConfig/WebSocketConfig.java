package com.chat.app.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // ✅ WebSocket endpoint (connect from frontend with SockJS)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // allow all (for localhost, future deployment, etc.)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // ✅ Topic for real-time updates (frontend subscribes here)
        registry.enableSimpleBroker("/topic");

        // ✅ Prefix for messages sent from frontend to backend
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(1024 * 1024); // 1MB
        registration.setSendBufferSizeLimit(1024 * 1024); // 1MB
        registration.setSendTimeLimit(20000); // 20 seconds
    }
}
