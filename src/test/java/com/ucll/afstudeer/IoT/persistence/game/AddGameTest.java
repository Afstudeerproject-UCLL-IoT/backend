package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
public class AddGameTest extends PersistenceBase {

    public AddGameTest(@Autowired DSLContext context) {
        super(context);
    }
}
