package com.ucll.afstudeer.IoT.web.websocket;

import com.ucll.afstudeer.IoT.domain.constant.DeviceType;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import com.ucll.afstudeer.IoT.service.game.GameService;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import com.ucll.afstudeer.IoT.web.websocket.handler.PuzzleAttemptHandler;
import com.ucll.afstudeer.IoT.web.websocket.handler.RegisterDeviceFeedbackHandler;
import com.ucll.afstudeer.IoT.web.websocket.handler.RegisterDevicePuzzleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

@Component
public class WebSocketServer extends TextWebSocketHandler {

    private final DeviceService deviceService;
    private final GameService gameService;
    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    public WebSocketServer(DeviceService deviceService, GameService gameService, NotificationService notificationService) {
        super();
        this.deviceService = deviceService;
        this.gameService = gameService;
        this.notificationService = notificationService;
    }

    // websocket methods
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        // parse event
        var messageSplit = message.getPayload().split("_");
        var event = Event.valueOf(messageSplit[0]);

        if (event != Event.ALIVE) {
            // log message that we received
            logger.info("Received message: " + message.getPayload());
        } else {
            logger.debug(message.getPayload());
        }

        switch (event) {
            case ALIVE:
                break;
            case REGDEVP: {
                // get the data
                var onlineAt = LocalDateTime.now(); // not 100% accurate
                var deviceType = DeviceType.valueOf(messageSplit[1]);
                var puzzleName = messageSplit[2];
                var puzzleSolution = messageSplit[3];

                // handle the event
                RegisterDevicePuzzleHandler.handle(onlineAt, deviceType, puzzleName, puzzleSolution, deviceService, notificationService, session);
                break;
            }

            case REGDEVF: {
                // get the data
                var onlineAt = LocalDateTime.now(); // not 100% accurate
                var deviceType = DeviceType.ARDUINO_FEEDBACK;

                // handle the event
                RegisterDeviceFeedbackHandler.handle(onlineAt, deviceType, deviceService, notificationService, session);
                break;
            }

            case PATMPT:
                // needed data
                var puzzleAttemptAt = LocalDateTime.now(); // not 100% accurate
                var puzzleName = messageSplit[1];
                var gameSessionId = Integer.parseInt(messageSplit[2]);
                boolean isSolved = Boolean.parseBoolean(messageSplit[3]);

                // handle the event
                PuzzleAttemptHandler.handle(puzzleAttemptAt, puzzleName, gameSessionId, isSolved, gameService);
                break;
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        logger.info("Connection closed for session: " + session.getId());

        // data
        var offlineAt = LocalDateTime.now();
        var device = notificationService.getDeviceBySession(session);

        // device not found so super and return early
        if (device == null) {
            super.afterConnectionClosed(session, status);
            return;
        }

        // device found so remove session from notification service and set offline time
        notificationService.removeSession(session);
        deviceService.deviceOffline(device, offlineAt);
        super.afterConnectionClosed(session, status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
