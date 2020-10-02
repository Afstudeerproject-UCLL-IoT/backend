package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.exception.game.GameDoesNotExistException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        when(gameRepository.get(any(Game.class)))
                .thenReturn(true);

        when(gameRepository.getDeviceInGameByPosition(any(Game.class)))
                .thenReturn(device);

        // existing game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // start the game
        var success = gameService.startGame(game);

        verify(gameRepository).get(any(Game.class));
        verify(gameRepository).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository).getDeviceInGameByPosition(any(Game.class));
        verify(notificationService).send(device, Event.GAME_STARTED);

        assertTrue(success);
    }

    @Test
    public void gameThatDoesNotExistCannotBeStartedAndThrowsException(){
        // stub
        when(gameRepository.get(any(Game.class)))
                .thenReturn(false);

        // game that does not exist
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // try to start it
        assertThrows(GameDoesNotExistException.class, () -> gameService.startGame(game));
        verify(gameRepository).get(any(Game.class));
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getDeviceInGameByPosition(any(Game.class));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));
    }

    @Test
    public void startingANullGameExitsEarly(){
        assertThrows(IllegalArgumentException.class, () -> gameService.startGame(null));
        verify(gameRepository, never()).get(any(Game.class));
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getDeviceInGameByPosition(any(Game.class));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));
    }
}
