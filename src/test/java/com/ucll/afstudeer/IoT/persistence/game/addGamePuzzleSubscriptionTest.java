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

import static infrastructure.persistence.Tables.PUZZLE_SUBSCRIBER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@JooqTest
public class addGamePuzzleSubscriptionTest extends PersistenceBase {

    private Device device1, device2, device3;
    private Game game;

    public addGamePuzzleSubscriptionTest(@Autowired DSLContext context) {
        super(context);
    }

    @BeforeEach
    public void initEach() {
        // game with puzzles
        game = new Game.Builder()
                .withName("Game1")
                .build();

        device1 = new Device.Builder().fromDeviceName("ARDUINO-Puzzle1").build();
        device2 = new Device.Builder().fromDeviceName("ARDUINO-Puzzle2").build();
        device3 = new Device.Builder().fromDeviceName("ARDUINO-Puzzle3").build();

        // insert them
        game = gameRepository.add(game);
        device1 = deviceRepository.addDeviceWithPuzzle(device1);
        device2 = deviceRepository.addDeviceWithPuzzle(device2);
        device3 = deviceRepository.addDeviceWithPuzzle(device3);
    }

    @Test
    public void puzzleSubscriptionIsInserted() {
        // puzzle subscriptions (puzzle1 <- puzzle2 <- puzzle3)
        gameRepository.addGamePuzzleSubscription(device3, device2.getPuzzle(), game, 3);
        gameRepository.addGamePuzzleSubscription(device2, device1.getPuzzle(), game, 2);
        gameRepository.addGamePuzzleSubscription(device1, null, game, 1);

        // check subscription table size
        var size = context.fetchCount(context.selectFrom(PUZZLE_SUBSCRIBER));
        assertEquals(3, size);

        // check if the device can be found by there position and their data is valid
        var foundDevice1 = gameRepository.getDeviceInGameByPosition(game, 1);
        var foundDevice2 = gameRepository.getDeviceInGameByPosition(game, 2);
        var foundDevice3 = gameRepository.getDeviceInGameByPosition(game, 3);

        assertEquals(device1, foundDevice1);
        assertEquals(device2, foundDevice2);
        assertEquals(device3, foundDevice3);
    }


}
