package com.ucll.afstudeer.IoT.web.websocket.handler;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.domain.constant.DeviceType;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;

public class RegisterDevicePuzzleHandler {

    public static void handle(LocalDateTime onlineAt,
                              DeviceType deviceType,
                              String puzzleName,
                              String puzzleSolution,
                              DeviceService deviceService,
                              NotificationService notificationService,
                              WebSocketSession session) {

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
        deviceService.deviceOnline(registeredDevice, onlineAt);
        notificationService.addSession(registeredDevice, session);

        // give the client it's registration details back
        var data = String.format("%d_%s_%s_%s",
                registeredDevice.getId(),
                registeredDevice.getType().toString(),
                registeredDevice.getPuzzle().getName(),
                registeredDevice.getPuzzle().getSolution());

        notificationService.send(registeredDevice, Event.REGDET, data);
    }
}
