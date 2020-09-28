package core.usecases.device;

import core.domain.Device;
import core.domain.Event;
import core.domain.Puzzle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class DeviceUseCasesPuzzleIsCompletedTest extends DeviceUseCasesBase{

    @DisplayName("Test that when a puzzle is completed a notification is send to it's subscribers")
    @Test
    public void completingAPuzzleSendsANotificationOfTheEventToSubscribedDevices(){
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

        deviceUseCases.puzzleIsCompleted(device.getPuzzle());
        verify(puzzleRepository).getSubscriptions(any(Puzzle.class));
        verify(notificationService, atLeastOnce()).send(any(Device.class), any(Event.class));
    }
}
