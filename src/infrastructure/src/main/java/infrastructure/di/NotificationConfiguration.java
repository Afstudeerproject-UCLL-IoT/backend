package infrastructure.di;

import core.interfaces.NotificationService;
import infrastructure.notification.NotificationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

@Configuration
public class NotificationConfiguration  {

    @Bean
    public NotificationService<WebSocketSession> notificationService(){
        return new NotificationServiceImpl();
    };
}
