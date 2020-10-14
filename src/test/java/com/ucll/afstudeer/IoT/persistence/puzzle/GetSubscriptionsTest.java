package com.ucll.afstudeer.IoT.persistence.puzzle;

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
public class GetSubscriptionsTest extends PersistenceBase {

    private Game game;
    private Device device1, device2, device3, device4;

    public GetSubscriptionsTest(@Autowired DSLContext context) {
        super(context);
    }

    @BeforeEach
    public void initEach() {
        game = gameRepository.add(new Game.Builder().withName("Game").build());

        device1 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle1").build());
        device2 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle2").build());
        device3 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle3").build());
        device4 = deviceRepository.addDeviceWithPuzzle(new Device.Builder().fromDeviceName("ARDUINO-Puzzle4").build());
    }


    @Test
    public void singleSubscriberCorrectQuery() {
        // do subscription
        gameRepository.addGamePuzzleSubscription(device2, device1.getPuzzle(), game, 2);
        gameRepository.addGamePuzzleSubscription(device1, null, game, 1);

        // check size
        var subscriptions = puzzleRepository.getSubscriptions(game, device1.getPuzzle());
        assertEquals(1, subscriptions.size());

        // check data
        assertEquals(device2, subscriptions.get(0));

        assertFalse(subscriptions.contains(device1));
        assertFalse(subscriptions.contains(device3));
        assertFalse(subscriptions.contains(device4));

    }

    @Test
    public void ThreeSubscriberCorrectQuery() {
        // do subscription
        gameRepository.addGamePuzzleSubscription(device4, device1.getPuzzle(), game, 2);
        gameRepository.addGamePuzzleSubscription(device3, device1.getPuzzle(), game, 2);
        gameRepository.addGamePuzzleSubscription(device2, device1.getPuzzle(), game, 2);
        gameRepository.addGamePuzzleSubscription(device1, null, game, 1);

        // check size
        var subscriptions = puzzleRepository.getSubscriptions(game, device1.getPuzzle());
        assertEquals(3, subscriptions.size());

        // check data
        assertTrue(subscriptions.contains(device2));
        assertTrue(subscriptions.contains(device3));
        assertTrue(subscriptions.contains(device4));

        assertFalse(subscriptions.contains(device1));

    }

    @Test
    public void noSubscriberCorrectQuery() {
        gameRepository.addGamePuzzleSubscription(device1, null, game, 1);

        // check size
        var subscriptions = puzzleRepository.getSubscriptions(game, device1.getPuzzle());
        assertEquals(0, subscriptions.size());

    }
}
