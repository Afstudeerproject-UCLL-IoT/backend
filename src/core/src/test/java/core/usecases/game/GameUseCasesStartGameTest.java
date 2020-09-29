package core.usecases.game;

import core.domain.Device;
import core.domain.Event;
import core.domain.Game;
import core.domain.GameSession;
import core.exceptions.game.GameDoesNotExistException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesStartGameTest extends GameUseCasesBase {

    @Test
    public void existingGameCanBeStartedAndSendsNotification(){
        // device that will be notified
        var device = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-puzzle1")
                .build();

        // stubs
        when(gameRepository.isPresent(any(Game.class)))
                .thenReturn(true);

        when(gameRepository.getFirstDevicePuzzle(any(Game.class)))
                .thenReturn(device);

        // existing game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // start the game
        var success = gameUseCases.startGame(game);

        verify(gameRepository).isPresent(any(Game.class));
        verify(gameRepository).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository).getFirstDevicePuzzle(any(Game.class));
        verify(notificationService).send(device, Event.GAME_STARTED);

        assertTrue(success);
    }

    @Test
    public void gameThatDoesNotExistCannotBeStartedAndThrowsException(){
        // stub
        when(gameRepository.isPresent(any(Game.class)))
                .thenReturn(false);

        // game that does not exist
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        // try to start it
        assertThrows(GameDoesNotExistException.class, () -> gameUseCases.startGame(game));
        verify(gameRepository).isPresent(any(Game.class));
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getFirstDevicePuzzle(any(Game.class));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));
    }

    @Test
    public void startingANullGameExitsEarly(){
        assertThrows(IllegalArgumentException.class, () -> gameUseCases.startGame(null));
        verify(gameRepository, never()).isPresent(any(Game.class));
        verify(gameRepository, never()).addGameSession(any(Game.class), any(GameSession.class));
        verify(gameRepository, never()).getFirstDevicePuzzle(any(Game.class));
        verify(notificationService, never()).send(any(Device.class), any(Event.class));
    }
}
