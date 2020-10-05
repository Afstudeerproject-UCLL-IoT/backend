package com.ucll.afstudeer.IoT.persistence.puzzle;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.DeviceType;
import com.ucll.afstudeer.IoT.domain.Puzzle;
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
    public List<Device> getSubscriptions(Puzzle puzzle) {
        // aliases
        var d = DEVICE.as("d");
        var p = PUZZLE.as("p");
        var ps = PUZZLE_SUBSCRIBER.as("ps");

        var records = context
                .select(d.ID, d.TYPE, p.NAME, p.SOLUTION)
                .from(d.innerJoin(p).on(d.ID.eq(p.DEVICE_OWNER_ID)))
                .whereExists(context
                        .selectOne()
                        .from(ps)
                        .where(ps.SUBSCRIBER_DEVICE_ID.eq(d.ID))
                        .and(ps.SUBSCRIBED_TO_PUZZLE_NAME.eq(puzzle.getName()))
                )
                .fetch();

        // TODO take a look at a mapper provided by jooq
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
