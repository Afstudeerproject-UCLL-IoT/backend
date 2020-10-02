package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.persistence.PersistenceBase;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
public class GetAllDevicesInAGameTest extends PersistenceBase {

    public GetAllDevicesInAGameTest(@Autowired DSLContext context) {
        super(context);
    }
}
