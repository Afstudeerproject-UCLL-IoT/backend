package com.ucll.afstudeer.IoT.persistence;

import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepositoryImpl;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.persistence.game.GameRepositoryImpl;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepositoryImpl;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class PersistenceBase {
    protected final DSLContext context;
    protected final DeviceRepository deviceRepository;
    protected final PuzzleRepository puzzleRepository;
    protected final GameRepository gameRepository;

    public PersistenceBase(DSLContext context) {
        this.context = context;

        deviceRepository = new DeviceRepositoryImpl(context);
        puzzleRepository = new PuzzleRepositoryImpl(context);
        gameRepository = new GameRepositoryImpl(context);
    }
}
