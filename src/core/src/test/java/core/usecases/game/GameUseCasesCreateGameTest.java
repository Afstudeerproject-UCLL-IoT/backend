package core.usecases.game;

import core.domain.Device;
import core.domain.Event;
import core.domain.Game;
import core.exceptions.game.GameAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesCreateGameTest extends GameUseCasesBase {

    @Test
    public void newGameCanBeCreated(){
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        var success = gameUseCases.createGame(game);
        verify(gameRepository).add(any(Game.class));
        assertTrue(success);
    }

    @Test
    public void duplicateGameThrowsException(){
        // stub
        when(gameRepository.exists(any(Game.class)))
                .thenReturn(true);

        // duplicate game
        var duplicate = new Game.Builder()
                .withName("Game1")
                .build();

        assertThrows(GameAlreadyExistsException.class, () -> gameUseCases.createGame(duplicate));
        verify(gameRepository).exists(any(Game.class));
        verify(gameRepository, never()).add(any(Game.class));
    }

    @Test
    public void nullGameExitsEarly(){
        assertThrows(IllegalArgumentException.class, () -> gameUseCases.createGame(null));
        verify(gameRepository, never()).exists(any(Game.class));
        verify(gameRepository, never()).add(any(Game.class));
    }
}
