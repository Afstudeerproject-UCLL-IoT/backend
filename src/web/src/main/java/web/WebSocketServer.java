package web;

import core.interfaces.NotificationService;
import org.springframework.stereotype.Component;

import core.domain.Device;
import core.domain.Event;
import core.usecases.device.DeviceUseCases;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketServer extends TextWebSocketHandler {

    private final DeviceUseCases deviceUseCases;
    private final NotificationService<WebSocketSession> notificationService;

    public WebSocketServer(DeviceUseCases deviceUseCases, NotificationService<WebSocketSession> notificationService) {
        super();
        this.deviceUseCases = deviceUseCases;
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
                deviceUseCases.registerDeviceWithPuzzle(device);
                break;
            case PUZZLE_COMPLETED:
                deviceUseCases.puzzleIsCompleted(device.getPuzzle());
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
