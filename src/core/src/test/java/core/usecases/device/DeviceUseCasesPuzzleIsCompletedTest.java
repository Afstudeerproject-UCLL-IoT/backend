package core.usecases.device;

import core.domain.Device;
import core.domain.Event;
import core.domain.Puzzle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyList;


public class DeviceUseCasesPuzzleIsCompletedTest extends DeviceUseCasesBase{

    @DisplayName("Test that when a puzzle is completed a notification is send to it's subscribers")
    @Test
    public void completingAPuzzleSendsANotificationOfTheEventToSubscribedDevices(){
        var device = Device.instance("ARDUINO-Puzzle1");

        deviceUseCases.puzzleIsCompleted(device.getPuzzle().getName());
        verify(puzzleRepository).getSubscriptions(any(Puzzle.class));
        verify(notificationService).send(anyList(), any(Event.class));
    }
}
