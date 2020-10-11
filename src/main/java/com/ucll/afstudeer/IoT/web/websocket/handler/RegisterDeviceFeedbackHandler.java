package com.ucll.afstudeer.IoT.web.websocket.handler;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.constant.DeviceType;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;

public class RegisterDeviceFeedbackHandler {

    public static void handle(LocalDateTime onlineAt,
                              DeviceType deviceType,
                              DeviceService deviceService,
                              NotificationService notificationService,
                              WebSocketSession session) {

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
    }
}
