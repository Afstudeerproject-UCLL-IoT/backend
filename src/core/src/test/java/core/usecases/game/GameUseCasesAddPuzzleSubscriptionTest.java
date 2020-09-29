package core.usecases.game;

import core.domain.Device;
import core.domain.Game;
import core.domain.Puzzle;
import core.exceptions.subscribe.CannotSubscribeToItselfException;
import core.exceptions.subscribe.DeviceCannotSubscribeToPuzzleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesAddPuzzleSubscriptionTest extends GameUseCasesBase{

    @Test
    public void deviceCanSubscribeToPuzzleForAGame(){
        // stub
        when(gameRepository.GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class)))
                .thenReturn(true);

        // subscriber
        var subscriber = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        // puzzle
        var puzzle = new Puzzle.Builder()
                .withName("Puzzle2")
                .build();

        // game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        var success = gameUseCases.addPuzzleSubscription(game, subscriber, puzzle);

        verify(gameRepository).GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class));
        assertTrue(success);
    }

    @Test
    public void deviceCannotSubscribeToItself(){
        // subscriber
        var subscriber = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        // same puzzle as subscriber but other mem address
        var puzzle = new Puzzle.Builder()
                .withoutSolution()
                .withName("Puzzle1")
                .build();

        // game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        assertThrows(CannotSubscribeToItselfException.class, () -> gameUseCases.addPuzzleSubscription(game, subscriber, puzzle));

        verify(gameRepository, never()).GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository, never()).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class));
    }

    @Test
    public void whenSubscriptionIsNotPossibleAnExceptionIsThrown(){
        // stub
        when(gameRepository.GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class)))
                .thenReturn(false);

        // subscriber
        var subscriber = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        // puzzle
        var puzzle = new Puzzle.Builder()
                .withName("Puzzle2")
                .build();

        // game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        assertThrows(DeviceCannotSubscribeToPuzzleException.class, () -> gameUseCases.addPuzzleSubscription(game, subscriber, puzzle));

        verify(gameRepository).GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository, never()).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class));
    }
}
