package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.domain.*;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static infrastructure.persistence.Tables.DEVICE;
import static infrastructure.persistence.tables.Game.GAME;
import static infrastructure.persistence.tables.GameSession.GAME_SESSION;
import static infrastructure.persistence.tables.Puzzle.PUZZLE;
import static infrastructure.persistence.tables.PuzzleSubscriber.PUZZLE_SUBSCRIBER;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private final DSLContext context;

    public GameRepositoryImpl(@Autowired DSLContext context) {
        this.context = context;
    }

    @Override
    public void add(Game game) {
        context.insertInto(GAME, GAME.NAME)
                .values(game.getName())
                .execute();
    }

    @Override
    public void addGameSession(Game game, GameSession session) {
        context.insertInto(GAME_SESSION, GAME_SESSION.GAME_NAME, GAME_SESSION.START)
                .values(game.getName(), session.getStart())
                .execute();
    }

    @Override
    public Device getFirstDevicePuzzle(Game game) {
        // TODO fix query, we do not join because I know we only have ARDUINOS and don't need the puzzle solution
        var record = context.
                select(PUZZLE_SUBSCRIBER.SUBSCRIBER_DEVICE_ID, PUZZLE_SUBSCRIBER.SUBSCRIBED_TO_PUZZLE_NAME)
                .from(PUZZLE_SUBSCRIBER)
                .where(PUZZLE_SUBSCRIBER.SUBSCRIBED_TO_PUZZLE_NAME.isNull())
                .fetchOne();

        // TODO take a look at a mapper provided by jooq
        return new Device.Builder()
                .withId(record.value1())
                .fromDeviceName(String.format("ARDUINO-%s", record.value2()))
                .build();
    }

    @Override
    public boolean GamePuzzleSubscriptionIsPossible(Device subscriber, Puzzle puzzle, Game game) {
        return context.fetchExists(
                context
                        .selectOne()
                        .whereExists(context.selectOne().from(DEVICE).where(DEVICE.ID.eq(subscriber.getId()))
                        .andExists(context.selectOne().from(PUZZLE).where(PUZZLE.NAME.eq(puzzle.getName())))
                        .andExists(context.selectOne().from(GAME).where(GAME.NAME.eq(game.getName()))))
        );
    }

    @Override
    public void addGamePuzzleSubscription(Device subscriber, Puzzle puzzle, Game game, int  position) {
        var ps = PUZZLE_SUBSCRIBER.as("ps");

        context.insertInto(ps, ps.GAME_NAME, ps.SUBSCRIBED_TO_PUZZLE_NAME, ps.SUBSCRIBER_DEVICE_ID, ps.POSITION)
                .values(game.getName(), puzzle == null ? null : puzzle.getName(), subscriber.getId(), position)
                .execute();
    }

    @Override
    public boolean firstDevicePuzzleIsPossible(Device device, Game game) {
        return context.fetchExists(
                context
                        .selectOne()
                        .whereExists(context.selectOne().from(DEVICE).where(DEVICE.ID.eq(device.getId()))
                        .andExists(context.selectOne().from(GAME).where(GAME.NAME.eq(game.getName()))))
        );
    }

    @Override
    public List<Game> getAllGames() {
        return context
                .select(GAME.fields())
                .from(GAME)
                .fetch()
                .stream()
                .map(record -> new Game.Builder()
                        .withName(record.getValue(GAME.NAME))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Device> getAllDevicesInAGame(Game game) {
        // aliases
        var d = DEVICE.as("d");
        var p = PUZZLE.as("p");
        var ps = PUZZLE_SUBSCRIBER.as("ps");

        return context
                .select(d.ID, d.TYPE, p.NAME, p.SOLUTION)
                .from(
                        ps.innerJoin(d).on(d.ID.eq(ps.SUBSCRIBER_DEVICE_ID))
                        .innerJoin(p).on(p.DEVICE_OWNER_ID.eq(d.ID))
                )
                .where(ps.GAME_NAME.eq(game.getName()))
                .fetch()
                .stream()
                .map(record -> new Device.Builder()
                        .withId(record.value1())
                        .withDeviceType(DeviceType.valueOf(record.value2()))
                        .withPuzzle(new Puzzle.Builder()
                                .withName(record.value3())
                                .withSolution(record.value4())
                                .build()
                ).build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPresent(Game game) {
        return context.fetchExists(
                context.selectOne()
                .from(GAME)
                .where(GAME.NAME.eq(game.getName()))
        );
    }
}