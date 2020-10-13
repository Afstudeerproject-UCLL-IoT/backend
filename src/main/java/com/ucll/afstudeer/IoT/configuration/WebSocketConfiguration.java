package com.ucll.afstudeer.IoT.configuration;

import com.ucll.afstudeer.IoT.web.websocket.WebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketServer webSocketServer;

    public WebSocketConfiguration(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();

        container.setMaxSessionIdleTimeout(TimeUnit.SECONDS.toMillis(3));
        return container;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketServer, "server")
                .setAllowedOrigins("*");
    }
}
