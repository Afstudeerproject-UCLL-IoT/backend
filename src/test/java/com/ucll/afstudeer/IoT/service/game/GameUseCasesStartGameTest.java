package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesStartGameTest extends GameServiceBase {

    // TODO fix tests
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

        when(gameRepository.getAllDevicesInAGame(any(Game.class)))
                .thenReturn(List.of(device));

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
        verify(gameRepository).getAllDevicesInAGame(any(Game.class));
        verify(notificationService).send(eq(device), eq(Event.STARTPZL), eq(device.getPuzzle().getName()));

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
        verify(gameRepository, never()).getAllDevicesInAGame(any(Game.class));
        verify(notificationService, never()).send(any(Device.class), any(Event.class), anyString());

        assertNull(response.getValue());
        assertEquals(ServiceError.GAME_DOES_NOT_EXIST, response.getError());
    }

    @Test
    public void startingANullGameExitsEarly() {
        assertThrows(IllegalArgumentException.class, () -> gameService.startGame(null));

        verify(gameRepository, never()).exists(anyString());
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getAllDevicesInAGame(any(Game.class));
        verify(notificationService, never()).send(any(Device.class), any(Event.class), anyString());
    }
}
