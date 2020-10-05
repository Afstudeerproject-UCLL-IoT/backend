package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@JooqTest
public class GetAllDevicesInAGameTest extends PersistenceBase {

    private Game game1, game2, game3;
    private Device device1, device2, device3, device4, device5, device6;


    public GetAllDevicesInAGameTest(@Autowired DSLContext context) {
        super(context);
    }

    @BeforeEach
    public void initEach(){
        // create 3 games

        // game 1 (dp1 <- dp2 <- dp3)
        game1 = gameRepository.add(new Game.Builder().withName("Game1").build());

        device1 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle1").build());
        device2 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle2").build());
        device3 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle3").build());

        gameRepository.addGamePuzzleSubscription(device3, device2.getPuzzle(), game1, 3);
        gameRepository.addGamePuzzleSubscription(device2, device1.getPuzzle(), game1, 2);
        gameRepository.addGamePuzzleSubscription(device1, null, game1, 1);


        // game 2 (dp4 <- dp5)
        game2 = gameRepository.add(new Game.Builder().withName("Game2").build());

        device4 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle4").build());
        device5 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle5").build());

        gameRepository.addGamePuzzleSubscription(device5, device4.getPuzzle(), game2, 2);
        gameRepository.addGamePuzzleSubscription(device4, null, game2, 1);


        // game 3 (dp6)
        game3 = gameRepository.add(new Game.Builder().withName("Game3").build());

        device6 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle6").build());
        gameRepository.addGamePuzzleSubscription(device6, null, game3, 1);
    }

    @Test
    public void gameWith3DevicesReturns3Devices(){
        var devices = gameRepository.getAllDevicesInAGame(game1);
        assertEquals(3, devices.size());

        assertEquals(device1, devices.get(0));
        assertEquals(device2, devices.get(1));
        assertEquals(device3, devices.get(2));
    }

    @Test
    public void gameWith2DevicesReturns2Devices(){
        var devices = gameRepository.getAllDevicesInAGame(game2);
        assertEquals(2, devices.size());

        assertEquals(device4, devices.get(0));
        assertEquals(device5, devices.get(1));
    }

    @Test
    public void gameWith1DeviceReturns1Device(){
        var devices = gameRepository.getAllDevicesInAGame(game3);
        assertEquals(1, devices.size());

        assertEquals(device6, devices.get(0));
    }
    @Test
    public void gameWithZeroDevicesReturnsEmptyList(){
        var devices = gameRepository.getAllDevicesInAGame(new Game.Builder().withName("NoDevicesGame").build());
        assertEquals(0, devices.size());
    }
}
