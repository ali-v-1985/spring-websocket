package me.valizadeh.practices.springwebsocket.ws.config;

import me.valizadeh.practices.springwebsocket.interceptors.HttpHandshakeInterceptor;
import me.valizadeh.practices.springwebsocket.ws.handler.SocketHandler;
import me.valizadeh.practices.springwebsocket.ws.scheduleresponse.ResponseScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableScheduling
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean("webSocketResponseScheduler")
    public ResponseScheduler responseScheduler() {
        return new ResponseScheduler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/wsNames")
                .addInterceptors(new HttpHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}