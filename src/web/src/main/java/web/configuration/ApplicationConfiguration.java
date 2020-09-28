package web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import web.WebSocketServer;

@Configuration
@EnableWebSocket
public class ApplicationConfiguration implements WebSocketConfigurer {

    private final WebSocketServer webSocketServer;

    public ApplicationConfiguration(@Autowired WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketServer, "app")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
