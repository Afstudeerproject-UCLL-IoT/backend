package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesStartGameTest extends GameServiceBase {

    @Test
    public void existingGameCanBeStartedAndSendsNotification() {
        // device that will be notified
        var device = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-puzzle1")
                .build();

        // game session that will be returned
        var session = new GameSession.Builder()
                .withId(1)
                .withStartTime(LocalDateTime.now())
                .withoutEndTime()
                .build();

        // stubs
        when(gameRepository.exists(anyString()))
                .thenReturn(true);

        when(gameRepository.getDeviceInGameByPosition(any(Game.class), anyInt()))
                .thenReturn(device);

        when(gameRepository.addGameSession(any(Game.class), any(GameSession.class)))
                .thenReturn(session);

        // existing game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // start the game
        var startedGameSession = gameService
                .startGame(game)
                .getValue();

        verify(gameRepository).exists(anyString());
        verify(gameRepository).addGameSession(eq(game), any(GameSession.class));
        verify(gameRepository).getDeviceInGameByPosition(any(Game.class), eq(1));
        verify(notificationService).send(eq(device), eq(Event.GAME_STARTED));

        assertNotNull(startedGameSession);
        assertNotNull(startedGameSession.getStart());
        assertNull(startedGameSession.getEnd());
        assertEquals(1, startedGameSession.getId());
    }

    @Test
    public void gameThatDoesNotExistCannotBeStartedAndReturnsFailedResponse() {
        // stub
        when(gameRepository.exists(anyString()))
                .thenReturn(false);

        // game that does not exist
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // try to start it
        var response = gameService.startGame(game);

        verify(gameRepository).exists(anyString());
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getDeviceInGameByPosition(any(Game.class), eq(1));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));

        assertNull(response.getValue());
        assertEquals("The game that is going to start does not exist", response.getMessage());
    }

    @Test
    public void startingANullGameExitsEarly() {
        assertThrows(IllegalArgumentException.class, () -> gameService.startGame(null));

        verify(gameRepository, never()).exists(anyString());
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getDeviceInGameByPosition(any(Game.class), eq(1));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));
    }
}
