package com.ucll.afstudeer.IoT.service.notification;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Event;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

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
    public void addDeviceConnection(Device device, WebSocketSession session) {
        deviceConnections.put(device, session);
    }

    @Override
    public void removeDeviceConnection(Device device, WebSocketSession session) {
        var key = deviceConnections.entrySet()
                .stream()
                .filter(entry -> session.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(NullPointerException::new); // TODO fix this exception

        deviceConnections.remove(key, session);
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
