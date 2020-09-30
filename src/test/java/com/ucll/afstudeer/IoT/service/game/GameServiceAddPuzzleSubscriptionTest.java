package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.exception.subscribe.CannotSubscribeToItselfException;
import com.ucll.afstudeer.IoT.exception.subscribe.DeviceCannotSubscribeToPuzzleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameServiceAddPuzzleSubscriptionTest extends GameServiceBase {

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

        var success = gameService.addPuzzleSubscription(game, subscriber, puzzle, 2);

        verify(gameRepository).GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class), anyInt());
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

        assertThrows(CannotSubscribeToItselfException.class, () -> gameService.addPuzzleSubscription(game, subscriber, puzzle, 2));

        verify(gameRepository, never()).GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository, never()).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class), anyInt());
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

        assertThrows(DeviceCannotSubscribeToPuzzleException.class, () -> gameService.addPuzzleSubscription(game, subscriber, puzzle, 2));

        verify(gameRepository).GamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository, never()).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class), anyInt());
    }
}
