package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameUseCasesCreateGameTest extends GameServiceBase {

    @Test
    public void newGameCanBeCreated() {
        // stub
        when(gameRepository.add(any(Game.class)))
                .thenReturn(new Game.Builder()
                        .withName("Game1")
                        .build());

        // the game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        var createdGame = gameService
                .createGame(game)
                .getValue();

        verify(gameRepository).add(any(Game.class));

        assertNotNull(createdGame);
        assertEquals("Game1", createdGame.getName());
    }

    @Test
    public void whenAGameAlreadyExistsItIsReturned() {
        // stub
        when(gameRepository.get(anyString()))
                .thenReturn(new Game.Builder()
                        .withName("Game1")
                        .build());

        // duplicate game
        var duplicate = new Game.Builder()
                .withName("Game1")
                .build();


        var createdGame = gameService
                .createGame(duplicate)
                .getValue();

        verify(gameRepository).get(anyString());
        verify(gameRepository, never()).add(any(Game.class));

        assertNotNull(createdGame);
        assertEquals("Game1", createdGame.getName());
    }

    @Test
    public void nullGameExitsEarly() {
        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(null));

        verify(gameRepository, never()).get(anyString());
        verify(gameRepository, never()).add(any(Game.class));
    }
}
