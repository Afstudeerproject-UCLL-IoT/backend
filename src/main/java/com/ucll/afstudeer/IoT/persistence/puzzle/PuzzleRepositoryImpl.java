package com.ucll.afstudeer.IoT.persistence.puzzle;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.domain.constant.DeviceType;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static infrastructure.persistence.Tables.*;


@Repository
public class PuzzleRepositoryImpl implements PuzzleRepository {

    private final DSLContext context;

    public PuzzleRepositoryImpl(@Autowired DSLContext context) {
        this.context = context;
    }

    @Override
    public List<Device> getSubscriptions(Game game, Puzzle puzzle) {
        // aliases
        var d = DEVICE.as("d");
        var p = PUZZLE.as("p");
        var ps = PUZZLE_SUBSCRIBER.as("ps");

        // query
        var records = context
                .select(d.ID, d.TYPE, p.NAME, p.SOLUTION)
                .from(d.innerJoin(p).on(d.ID.eq(p.DEVICE_OWNER_ID)))
                .whereExists(context
                        .selectOne()
                        .from(ps)
                        .where(ps.SUBSCRIBER_DEVICE_ID.eq(d.ID))
                        .and(ps.SUBSCRIBED_TO_PUZZLE_NAME.eq(puzzle.getName()))
                        .and(ps.GAME_NAME.eq(game.getName()))
                )
                .fetch();

        // build devices and return it
        return records.stream()
                .map(record -> new Device.Builder()
                        .withId(record.value1())
                        .withDeviceType(DeviceType.valueOf(record.value2()))
                        .withPuzzle(new Puzzle.Builder()
                                .withName(record.value3())
                                .withSolution(record.value4())
                                .build()
                        )
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Puzzle updatePuzzleSolution(Puzzle puzzle, String newSolution) {
        // query
        var record = context
                .update(PUZZLE)
                .set(PUZZLE.SOLUTION, newSolution)
                .where(PUZZLE.NAME.eq(puzzle.getName()))
                .returningResult(PUZZLE.NAME, PUZZLE.SOLUTION)
                .fetchOne();

        // check if we got the updated puzzle back
        if (record == null)
            return null;

        // create the puzzle with it's new solution and return it
        return new Puzzle.Builder()
                .withName(record.value1())
                .withSolution(record.value2())
                .build();
    }

    @Override
    public Puzzle get(String puzzleName) {
        var record = context
                .select(PUZZLE.NAME, PUZZLE.SOLUTION)
                .from(PUZZLE)
                .where(PUZZLE.NAME.eq(puzzleName))
                .fetchOne();

        if (record != null) {
            return new Puzzle.Builder()
                    .withName(record.value1())
                    .withSolution(record.value2())
                    .build();
        }
        return null;
    }

    @Override
    public boolean exists(String puzzleName) {
        return context.fetchExists(context
                .selectOne()
                .from(PUZZLE)
                .where(PUZZLE.NAME.eq(puzzleName)));
    }
}
