package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameServiceAddPuzzleSubscriptionTest extends GameServiceBase {

    @Test
    public void deviceCanSubscribeToPuzzleForAGame(){
        // stub
        when(gameRepository.gamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class)))
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

        var response = gameService.addPuzzleSubscription(game, subscriber, puzzle, 2);

        verify(gameRepository).gamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class), anyInt());
        assertTrue(response.isSucceeded());
    }

    @Test
    public void deviceCannotSubscribeToItselfAnAFailedResponseIsReturned(){
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

        var response = gameService.addPuzzleSubscription(game, subscriber, puzzle, 2);

        verify(gameRepository, never()).gamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository, never()).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class), anyInt());

        assertFalse(response.isSucceeded());
        assertEquals("A device cannot subscribe to it's own puzzle", response.getErrorMessage());
    }

    @Test
    public void whenSubscriptionIsNotPossibleAFailedResponseIsReturned(){
        // stub
        when(gameRepository.gamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class)))
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

        var response = gameService.addPuzzleSubscription(game, subscriber, puzzle, 2);

        verify(gameRepository).gamePuzzleSubscriptionIsPossible(any(Device.class), any(Puzzle.class), any(Game.class));
        verify(gameRepository, never()).addGamePuzzleSubscription(any(Device.class), any(Puzzle.class), any(Game.class), anyInt());

        assertFalse(response.isSucceeded());
        assertEquals("The device cannot subscribe to the puzzle for a game because not all entities exist", response.getErrorMessage());
    }
}
