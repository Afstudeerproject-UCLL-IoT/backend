package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@JooqTest
public class GetAllGamesTest extends PersistenceBase {

    public GetAllGamesTest(@Autowired DSLContext context) {
        super(context);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 1000})
    public void nGamesGivesAListWithNItems(int number) {
        // do inserts
        IntStream.rangeClosed(1, number)
                .forEach(i -> gameRepository.add(new Game.Builder().withName(String.format("Game%d", i)).build()));

        // check size
        assertEquals(number, gameRepository.getAllGames().size());
    }

    @Test
    public void correctQuery() {
        // create 3 games
        var game1 = gameRepository.add(new Game.Builder().withName("Game1").build());
        var game2 = gameRepository.add(new Game.Builder().withName("Game2").build());
        var game3 = gameRepository.add(new Game.Builder().withName("Game3").build());

        // all games
        var games = gameRepository.getAllGames();
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
        assertTrue(games.contains(game3));

        // 2 more games
        var game4 = gameRepository.add(new Game.Builder().withName("Game4").build());
        var game5 = gameRepository.add(new Game.Builder().withName("Game5").build());

        // all games
        games = gameRepository.getAllGames();
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
        assertTrue(games.contains(game3));
        assertTrue(games.contains(game4));
        assertTrue(games.contains(game5));

        // sanity
        assertFalse(games.contains(new Game.Builder().withName("Game6").build()));
    }

}
