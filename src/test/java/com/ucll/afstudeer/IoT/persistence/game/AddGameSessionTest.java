package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static infrastructure.persistence.Tables.GAME_SESSION;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@JooqTest
public class AddGameSessionTest extends PersistenceBase {

    public AddGameSessionTest(@Autowired DSLContext context) {
        super(context);
    }

    @Test
    public void gameSessionIsInserted() {
        // setup for game
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        gameRepository.add(game);

        // game session
        var session = new GameSession.Builder()
                .withoutId()
                .withStartTime(LocalDateTime.now())
                .withoutEndTime()
                .build();

        var addedGameSession = gameRepository.addGameSession(game, session);

        // check if 1 game is inserted
        var amountOfGameSessions = context
                .fetchCount(context.selectFrom(GAME_SESSION));

        assertEquals(1, amountOfGameSessions);

        // check if all the data is inserted
        var record = context
                .select(GAME_SESSION.ID, GAME_SESSION.START, GAME_SESSION.END, GAME_SESSION.GAME_NAME)
                .from(GAME_SESSION)
                .fetchOne();

        assertEquals(addedGameSession.getId(), record.value1());
        assertEquals(addedGameSession.getStart(), record.value2());
        assertEquals(addedGameSession.getEnd(), record.value3());
        assertEquals(game.getName(), record.value4());
    }
}
