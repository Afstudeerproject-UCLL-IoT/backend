package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.exception.game.GameAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesCreateGameTest extends GameServiceBase {

    @Test
    public void newGameCanBeCreated(){
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        var success = gameService.createGame(game);
        verify(gameRepository).add(any(Game.class));
        assertTrue(success);
    }

    @Test
    public void duplicateGameThrowsException(){
        // stub
        when(gameRepository.get(any(Game.class)))
                .thenReturn(true);

        // duplicate game
        var duplicate = new Game.Builder()
                .withName("Game1")
                .build();

        assertThrows(GameAlreadyExistsException.class, () -> gameService.createGame(duplicate));
        verify(gameRepository).get(any(Game.class));
        verify(gameRepository, never()).add(any(Game.class));
    }

    @Test
    public void nullGameExitsEarly(){
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(null));
        verify(gameRepository, never()).get(any(Game.class));
        verify(gameRepository, never()).add(any(Game.class));
    }
}
