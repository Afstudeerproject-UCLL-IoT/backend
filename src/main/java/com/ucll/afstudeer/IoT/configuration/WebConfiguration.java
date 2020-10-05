package com.ucll.afstudeer.IoT.configuration;

import com.ucll.afstudeer.IoT.web.websocket.WebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;

@Configuration
@EnableWebSocket
public class WebConfiguration {

    private final WebSocketServer webSocketServer;

    public WebConfiguration(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            // TODO fix cors
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public WebSocketConfigurer webSocketConfigurer() {
        return registry -> registry.addHandler(webSocketServer, "server")
                .setAllowedOrigins("*");
    }
}
