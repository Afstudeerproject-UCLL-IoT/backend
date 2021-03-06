package com.ucll.afstudeer.IoT.service.notification;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import org.springframework.web.socket.WebSocketSession;

public interface NotificationService {
    /**
     * Send data to the feedback device
     *
     * @param data The data in text format
     */
    void sendToFeedback(String data);

    /**
     * Send an event with data to the device
     *
     * @param device The device
     * @param event  The event
     * @param data   The date in text format
     */
    void send(Device device, Event event, String data);

    /**
     * Sent an event to all devices
     */
    void sendToAll(Event event, String data);

    /**
     * Add a new session to the connection list, if the session already exists it's replaced
     *
     * @param device  The device that holds the session/connection
     * @param session The session (websocket)
     */
    void addSession(Device device, WebSocketSession session);

    /**
     * Remove an existing session for a device from the connection list
     *
     * @param device The device that holds the session (websocket) and is present in the connection list
     */
    void removeSession(Device device, WebSocketSession session);

    /**
     * Get the device in the connection list based by the session it belongs to
     *
     * @param session The websocket session
     * @return The device or null when it was not found
     */
    Device getDeviceBySession(WebSocketSession session);
}
