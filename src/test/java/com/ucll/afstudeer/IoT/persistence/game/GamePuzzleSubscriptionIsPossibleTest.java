package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@JooqTest
public class GamePuzzleSubscriptionIsPossibleTest extends PersistenceBase {

    private Game game;
    private Device device1, device2;

    public GamePuzzleSubscriptionIsPossibleTest(@Autowired DSLContext context) {
        super(context);
    }

    @BeforeEach
    public void initEach(){
        // create all entities
        game = new Game.Builder()
                .withName("Game1")
                .build();

        game = gameRepository.add(game);

        device1 = new Device.Builder().fromDeviceName("ARDUINO-Puzzle1").build();
        device2 = new Device.Builder().fromDeviceName("ARDUINO-Puzzle2").build();

        device1 = deviceRepository.addDeviceWithPuzzle(device1);
        device2 = deviceRepository.addDeviceWithPuzzle(device2);
    }

    @Test
    public void whenAllEntitiesExistTheSubscriptionIsPossible(){
        // puzzle1 <- puzzle2
        var result1 = gameRepository.gamePuzzleSubscriptionIsPossible(device2, device1.getPuzzle(), game);
        var result2 = gameRepository.gamePuzzleSubscriptionIsPossible(device1, null, game);

        // puzzle2 <- puzzle1
        var result3 = gameRepository.gamePuzzleSubscriptionIsPossible(device1, device2.getPuzzle(), game);
        var result4 = gameRepository.gamePuzzleSubscriptionIsPossible(device2, null, game);

        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertTrue(result4);
    }

    @Test
    public void subscriptionWithoutAValidGameReturnsFalse(){
        var nonExistingGame = new Game.Builder()
                .withName("NotRealGame")
                .build();

        var result = gameRepository.gamePuzzleSubscriptionIsPossible(device2, device1.getPuzzle(), nonExistingGame);
        assertFalse(result);
    }

    @Test
    public void subscriptionWithoutAValidPuzzleReturnsFalse(){
        var nonExistingPuzzle = new Puzzle.Builder()
                .withName("PuzzleNotThere")
                .withSolution("candy-man")
                .build();

        var result = gameRepository.gamePuzzleSubscriptionIsPossible(device2, nonExistingPuzzle, game);
        assertFalse(result);
    }

    @Test
    public void subscriptionWithoutAValidSubscriberDeviceReturnsFalse(){
        var nonExistingDevice = new Device.Builder()
                .fromDeviceName("ARDUINO-Puzzle007")
                .build();

        var result = gameRepository.gamePuzzleSubscriptionIsPossible(nonExistingDevice, device1.getPuzzle(), game);
        assertFalse(result);
    }

    @Test
    public void subscriptionWithANullPuzzleAndWithoutAValidGameReturnsFalse(){
        var nonExistingGame = new Game.Builder()
                .withName("NotRealGame")
                .build();

        var result = gameRepository.gamePuzzleSubscriptionIsPossible(device1, null, nonExistingGame);
        assertFalse(result);
    }
    @Test
    public void subscriptionWithANullPuzzleANdWithoutAValidSubscriberDeviceReturnsFalse(){
        var nonExistingDevice = new Device.Builder()
                .fromDeviceName("ARDUINO-Puzzle007")
                .build();

        var result = gameRepository.gamePuzzleSubscriptionIsPossible(nonExistingDevice, null, game);
        assertFalse(result);
    }
}
