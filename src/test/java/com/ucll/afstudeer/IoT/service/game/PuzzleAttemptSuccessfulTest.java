package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.service.device.DeviceServiceBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


// TODO Fix tests
public class PuzzleAttemptSuccessfulTest extends GameServiceBase {

    @DisplayName("Test that when a puzzle is completed a notification is send to it's subscribers")
    public void completingAPuzzleSendsANotificationOfTheEventToSubscribedDevices() {
        // create subscriber
        var subscriber = new Device.Builder()
                .withoutId()
                .fromDeviceName("ARDUINO-Puzzle2")
                .build();

        // stub
        when(puzzleRepository.getSubscriptions(any(Puzzle.class)))
                .thenReturn(Collections.singletonList(subscriber));

        // create device
        var device = new Device.Builder()
                .withoutId()
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        //var response = gameService.puzzleAttemptSuccessful(device.getPuzzle())
        verify(puzzleRepository).getSubscriptions(any(Puzzle.class));
        verify(notificationService, atLeastOnce()).send(eq(subscriber), eq(Event.STARTPZL), eq(subscriber.getPuzzle().getName()));
        //assertTrue(response.getValue());
    }

    // TODO sent if puzzle attempt is done
}
