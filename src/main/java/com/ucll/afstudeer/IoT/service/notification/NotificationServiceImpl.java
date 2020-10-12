package com.ucll.afstudeer.IoT.service.notification;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.constant.DeviceType;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Map<Device, WebSocketSession> deviceConnections;
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

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
        if (!deviceConnections.containsKey(device)) return;

        // find the session and send the message
        var session = deviceConnections.get(device);
        sendMessage(event, data, session);
    }

    @Override
    public void addSession(Device device, WebSocketSession session) {
        deviceConnections.put(device, session);
        logger.info("Connection open for device " + device.toString());
    }

    @Override
    public void removeSession(WebSocketSession session) {
        // find the device, not found => return early
        var device = getDeviceBySession(session);
        if (device == null) return;

        deviceConnections.remove(device, session);
        logger.info("Connection closed for device: " + device.toString());
    }

    @Override
    public Device getDeviceBySession(WebSocketSession session) {
        return deviceConnections.entrySet()
                .stream()
                .filter(entry -> session.getId().equals(entry.getValue().getId()) || session.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    // helpers
    private void sendMessage(Event event, String data, WebSocketSession session) {
        // create message
        var payload = (data == null || data.isBlank()) ? String.format("%s", event.toString()) : String.format("%s_%s", event.toString(), data);
        var message = new TextMessage(payload);

        // try to send it
        try {
            session.sendMessage(message);
            logger.info(String.format("Message sent to session. Event:%s. Data:%s.", event.toString(), data));
        } catch (IOException e) {
            logger.error(String.format("Could not send message to session! Event: %s. Data:%s.", event.toString(), data));
        }
    }
}
