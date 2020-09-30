package com.ucll.afstudeer.IoT.service.notification;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Event;
import org.springframework.web.socket.WebSocketSession;

public interface NotificationService {
    // send the notification of the occurrence of an event to devices that listen for all events
    void send(Event event);

    // send the notification of the occurrence of a device to 1 actor
    void send(Device device, Event event);

    void addDeviceConnection(Device device, WebSocketSession session);

    void removeDeviceConnection(Device device, WebSocketSession session);
}
