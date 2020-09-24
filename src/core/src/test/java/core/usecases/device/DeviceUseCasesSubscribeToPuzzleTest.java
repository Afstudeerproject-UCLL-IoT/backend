package core.usecases.device;

import core.domain.device.Device;
import core.domain.puzzle.Puzzle;
import core.interfaces.DeviceRepository;
import core.interfaces.PuzzleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeviceUseCasesSubscribeToPuzzleTest extends DeviceUseCasesBase {
    private DeviceUseCases deviceUseCases;
    private DeviceRepository deviceRepository;
    private PuzzleRepository puzzleRepository;

    @DisplayName("Test if a device can subscribe to a puzzle")
    @Test
    public void aDeviceCanSubscribeToAPuzzle(){
        var device = Device.instance("ARDUINO-Puzzle1");
        var puzzle = Puzzle.instance("Puzzle2", "");

        assert(true);
    }
}
