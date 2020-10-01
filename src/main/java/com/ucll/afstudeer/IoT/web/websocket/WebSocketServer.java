package com.ucll.afstudeer.IoT.web.websocket;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketServer extends TextWebSocketHandler {

    private final DeviceService deviceService;
    private final NotificationService notificationService;

    public WebSocketServer(DeviceService deviceService, NotificationService notificationService) {
        super();
        this.deviceService = deviceService;
        this.notificationService = notificationService;
    }

    // websocket methods
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("\n" + "Message: " + message.getPayload() + "\n");

        var payloadSplit = message.getPayload().split("\\.");

        var device = new Device.Builder()
                .withoutId()
                .fromDeviceName(payloadSplit[0])
                .build();

        var event = Event.valueOf(payloadSplit[1]);

        switch (event){
            case REGISTER_DEVICE:
                notificationService.addDeviceConnection(device, session);
                //deviceService.registerDeviceWithPuzzle(device);
                break;
            case PUZZLE_COMPLETED:
                deviceService.puzzleIsCompleted(device.getPuzzle());
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
