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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@JooqTest
public class GetDeviceInGameByPositionTest extends PersistenceBase {

    private Device device1, device2;
    private Game game1, game2;

    public GetDeviceInGameByPositionTest(@Autowired DSLContext context) {
        super(context);
    }

    @BeforeEach
    public void initEach() {
        // register 2 devices
        device1 = new Device.Builder()
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        device2 = new Device.Builder()
                .fromDeviceName("ARDUINO-Puzzle2")
                .build();

        device1 = deviceRepository.addDeviceWithPuzzle(device1);
        device2 = deviceRepository.addDeviceWithPuzzle(device2);

        // create 2 games
        game1 = new Game.Builder()
                .withName("Game1")
                .build();

        game2 = new Game.Builder()
                .withName("Game2")
                .build();

        gameRepository.add(game1);
        game2 = gameRepository.add(game2);

        // subscriptions (Puzzle1 <- Puzzle2)
        gameRepository.addGamePuzzleSubscription(device1, null, game2, 1);
        gameRepository.addGamePuzzleSubscription(device2, device1.getPuzzle(), game2, 2);
    }

    @Test
    public void correctQuery() {
        // get the first device in game 2
        var firstDevice = gameRepository.getDeviceInGameByPosition(game2, 1);

        assertNotNull(firstDevice);
        assertNotNull(firstDevice.getPuzzle());
        assertEquals(device1, firstDevice);

        // get the second device in game 2
        var secondDevice = gameRepository.getDeviceInGameByPosition(game2, 2);

        assertNotNull(firstDevice);
        assertNotNull(secondDevice.getPuzzle());
        assertEquals(device2, secondDevice);

        // get no device from game 1
        var noDevice1 = gameRepository.getDeviceInGameByPosition(game1, 1);
        assertNull(noDevice1);

        // get no device from game 2
        var noDevice2 = gameRepository.getDeviceInGameByPosition(game2, 3);
        assertNull(noDevice2);
    }
}
