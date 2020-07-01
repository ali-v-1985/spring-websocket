package me.valizadeh.practices.springwebsocket;

import me.valizadeh.practices.springwebsocket.interceptors.HttpHandshakeInterceptor;
import me.valizadeh.practices.springwebsocket.scheduleresponse.ResponseScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic/");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/greeting").addInterceptors(new HttpHandshakeInterceptor()).setAllowedOrigins("*");
    }

    @Bean
    public ResponseScheduler responseScheduler(SimpMessagingTemplate messagingTemplate) {
        return new ResponseScheduler(messagingTemplate);
    }

}