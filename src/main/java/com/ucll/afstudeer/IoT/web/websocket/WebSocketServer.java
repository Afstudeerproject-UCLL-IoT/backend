package com.ucll.afstudeer.IoT.web.websocket;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.DeviceType;
import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import com.ucll.afstudeer.IoT.service.game.GameService;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
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

    public WebSocketServer(DeviceService deviceService, GameService gameService, NotificationService notificationService) {
        super();
        this.deviceService = deviceService;
        this.gameService = gameService;
        this.notificationService = notificationService;
    }

    // websocket methods
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        var now = LocalDateTime.now();
        var hour = now.getHour();
        var minute = now.getMinute();
        var second = now.getSecond();

        var log = String.format("Date [%d:%d:%d], message: %s", hour, minute, second, message.getPayload());
        System.out.println(log);

        // parse event
        var messageSplit = message.getPayload().split("_");
        var event = Event.valueOf(messageSplit[0]);

        switch (event) {

            case REGDEVP: {
                // get the data
                var onlineAt = LocalDateTime.now(); // not 100% accurate
                var deviceType = DeviceType.valueOf(messageSplit[1]);
                var puzzleName = messageSplit[2];
                var puzzleSolution = messageSplit[3];

                // create the device with the puzzle
                var device = new Device.Builder()
                        .withoutId()
                        .withDeviceType(deviceType)
                        .withPuzzle(new Puzzle.Builder()
                                .withName(puzzleName)
                                .withSolution(puzzleSolution)
                                .build())
                        .build();

                // register device
                var registeredDevice = deviceService.registerDeviceWithPuzzle(device)
                        .getValue();

                // log online activity and add session to notification service
                notificationService.addSession(registeredDevice, session);
                deviceService.deviceOnline(registeredDevice, onlineAt);

                // give the client it's registration details back
                var data = String.format("%d_%s_%s_%s",
                        registeredDevice.getId(),
                        registeredDevice.getType().toString(),
                        registeredDevice.getPuzzle().getName(),
                        registeredDevice.getPuzzle().getSolution());

                notificationService.send(registeredDevice, Event.REGDET, data);

                // TODO
                // send it's missed messages

                break;
            }

            case REGDEVF: {
                // get the data
                var onlineAt = LocalDateTime.now(); // not 100% accurate
                var deviceType = DeviceType.ARDUINO_FEEDBACK;

                // create the device with the puzzle
                var feedbackDevice = new Device.Builder()
                        .withoutId()
                        .withDeviceType(deviceType)
                        .withoutPuzzle()
                        .build();

                // register the feedback device
                var feedbackDeviceAdded = deviceService.registerFeedbackDevice(feedbackDevice)
                        .getValue();

                // log online activity and add session to notification service
                notificationService.addSession(feedbackDeviceAdded, session);
                deviceService.deviceOnline(feedbackDeviceAdded, onlineAt);

                break;
            }

            case PATMPT:
                // needed data
                var puzzleAttemptAt = LocalDateTime.now(); // not 100% accurate
                var puzzleName = messageSplit[1];
                var gameSessionId = Integer.parseInt(messageSplit[2]);
                boolean isSolved = Boolean.parseBoolean(messageSplit[3]);

                // create the puzzle
                var puzzle = new Puzzle.Builder()
                        .withName(puzzleName)
                        .withoutSolution()
                        .build();

                // service call
                if (isSolved) {
                    gameService.puzzleAttemptSuccessful(puzzle, gameSessionId, puzzleAttemptAt);
                } else {
                    gameService.puzzleAttemptFailed(puzzle, gameSessionId, puzzleAttemptAt);
                }

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
