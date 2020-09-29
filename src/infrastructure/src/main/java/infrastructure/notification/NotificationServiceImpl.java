package infrastructure.notification;

import core.domain.Device;
import core.domain.Event;
import core.interfaces.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationServiceImpl implements NotificationService<WebSocketSession> {

    private final Map<Device, WebSocketSession> deviceConnections;

    public NotificationServiceImpl() {
        this.deviceConnections = new HashMap<>();
    }

    // send login
    @Override
    public void send(Event event) {
        deviceConnections.values()
                .forEach(connection -> sendMessage(event.toString(), connection));
    }

    @Override
    public void send(Device device, Event event) {
        if(deviceConnections.containsKey(device)){
            var connection = deviceConnections.get(device);
            sendMessage(event.toString(), connection);
        }

        // TODO handling of connection loss, cache events for later use
    }

    // add and remove connection login
    @Override
    public void addDeviceConnection(Device device, WebSocketSession connection) {
        deviceConnections.put(device, connection);
    }

    @Override
    public void removeDeviceConnection(Device device, WebSocketSession connection) {
        var key = deviceConnections.entrySet()
                .stream()
                .filter(entry -> connection.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(NullPointerException::new); // TODO fix this exception

        deviceConnections.remove(key, connection);
    }

    // helpers
    private void sendMessage(String message, WebSocketSession session){
        var textMessage = new TextMessage(message);

        try {
            session.sendMessage(textMessage);
        } catch (IOException e) {
            // TODO error handling
            e.printStackTrace();
        }
    }
}
