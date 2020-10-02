package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.exception.game.GameDoesNotExistException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesStartGameTest extends GameServiceBase {

    @Test
    public void existingGameCanBeStartedAndSendsNotification(){
        // device that will be notified
        var device = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-puzzle1")
                .build();

        // stubs
        when(gameRepository.exists(anyString()))
                .thenReturn(true);

        when(gameRepository.getDeviceInGameByPosition(any(Game.class), anyInt()))
                .thenReturn(device);

        // existing game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // start the game
        var response = gameService.startGame(game);

        verify(gameRepository).exists(anyString());
        verify(gameRepository).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository).getDeviceInGameByPosition(any(Game.class), eq(1));
        verify(notificationService).send(eq(device), eq(Event.GAME_STARTED));

        assertTrue(response.isSucceeded());
    }

    @Test
    public void gameThatDoesNotExistCannotBeStartedAndReturnsFailedResponse(){
        // stub
        when(gameRepository.exists(anyString()))
                .thenReturn(false);

        // game that does not exist
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // try to start it
        var response =  gameService.startGame(game);

        verify(gameRepository).exists(anyString());
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getDeviceInGameByPosition(any(Game.class), eq(1));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));

        assertFalse(response.isSucceeded());
        assertEquals("The game that is going to start does not exist", response.getErrorMessage());
    }

    @Test
    public void startingANullGameExitsEarly(){
        assertThrows(IllegalArgumentException.class, () -> gameService.startGame(null));

        verify(gameRepository, never()).exists(anyString());
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getDeviceInGameByPosition(any(Game.class), eq(1));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));
    }
}
