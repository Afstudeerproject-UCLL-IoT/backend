package com.ucll.afstudeer.IoT.service.notification;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.DeviceType;
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
    
    @Override
    public void sendToFeedback(String data) {
        deviceConnections.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getType().equals(DeviceType.ARDUINO_FEEDBACK))
                .findFirst()
                .ifPresent(entry ->
                        sendMessage(Event.FEEDBACK, data, entry.getValue())
                );
    }

    @Override
    public void send(Device device, Event event, String data) {
        if (deviceConnections.containsKey(device)) {
            // find the session and send the message
            var session = deviceConnections.get(device);
            sendMessage(event, data, session);
        }

        // TODO handling of connection loss, cache events for later use
    }

    // add and remove connection login
    @Override
    public void addSession(Device device, WebSocketSession session) {
        deviceConnections.put(device, session);
    }

    @Override
    public void removeSession(WebSocketSession session) {
        var key = deviceConnections.entrySet()
                .stream()
                .filter(entry -> session.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(NullPointerException::new); // TODO fix this exception

        deviceConnections.remove(key, session);
    }

    // helpers
    private void sendMessage(Event event, String data, WebSocketSession session) {
        // create message
        var payload = (data == null || data.isBlank()) ? String.format("%s", event.toString()) : String.format("%s_%s", event.toString(), data);
        var message = new TextMessage(payload);

        // try to send it
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            // TODO error handling
            e.printStackTrace();
        }
    }
}
