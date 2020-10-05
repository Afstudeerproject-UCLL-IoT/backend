package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.transaction.annotation.Transactional;

import static infrastructure.persistence.Tables.GAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JooqTest
@Transactional
public class AddGameTest extends PersistenceBase {

    public AddGameTest(@Autowired DSLContext context) {
        super(context);
    }

    @Test
    public void gameIsInserted() {
        // game that is going to be inserted
        var game = new Game.Builder()
                .withName("Game1")
                .build();

        var addedGame = gameRepository.add(game);

        var record = context
                .select(GAME.NAME)
                .from(GAME)
                .fetchOne();

        assertNotNull(addedGame);
        assertNotNull(record);

        assertEquals(addedGame.getName(), record.value1());
    }
}
