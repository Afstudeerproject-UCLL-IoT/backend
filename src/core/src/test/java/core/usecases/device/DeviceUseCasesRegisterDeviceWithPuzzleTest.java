package core.usecases.device;

import core.domain.device.Device;
import core.domain.puzzle.Puzzle;
import core.exceptions.device.DeviceAlreadyExistsException;
import core.interfaces.DeviceRepository;
import core.interfaces.PuzzleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceUseCasesRegisterDeviceWithPuzzleTest {
    private DeviceUseCases deviceUseCases;
    private DeviceRepository deviceRepository;
    private PuzzleRepository puzzleRepository;

    @BeforeEach
    public void setup(){
        deviceRepository = Mockito.mock(DeviceRepository.class);
        puzzleRepository = Mockito.mock(PuzzleRepository.class);
        deviceUseCases = new DeviceUseCasesImpl(deviceRepository, puzzleRepository );
    }

    @DisplayName("Test device and it's puzzle registration")
    @ParameterizedTest
    @ValueSource(strings = {"AwesomePuzzle1-Owner-1", "AwesomePuzzle1-Subscriber-1"})
    public void validDeviceWithPuzzleCanBeRegistered(String deviceName){
        // device registration
        var device = deviceUseCases.registerDeviceWithPuzzle(deviceName);
        verify(deviceRepository).add(any(Device.class));
        verify(puzzleRepository).add(any(Puzzle.class));

        // null assertions
        assertNotNull(device);
        assertNotNull(device.getPuzzle());
        assertNotNull(device.getName());

        // data assertions
        assertEquals(deviceName, device.getName().toString());
        assertEquals(device.getName().getPuzzleName(), device.getPuzzle().getName());
    }

    @DisplayName("Registering a device that already exists throws an exception")
    @ParameterizedTest
    @ValueSource(strings = {"AwesomePuzzle1-Owner-1", "AwesomePuzzle1-Subscriber-1"})
    public void RegisteringAnExistingDeviceThrowsAnException(String deviceName){
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(true);

        // check if an exception is thrown
        assertThrows(DeviceAlreadyExistsException.class, () -> deviceUseCases.registerDeviceWithPuzzle(deviceName));
        verify(deviceRepository).exists(any(Device.class));
    }
}