package com.ucll.afstudeer.IoT.persistence.game;

import com.ucll.afstudeer.IoT.domain.*;
import com.ucll.afstudeer.IoT.domain.constant.DeviceType;
import infrastructure.persistence.Tables;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static infrastructure.persistence.Tables.DEVICE;
import static infrastructure.persistence.Tables.PUZZLE_ATTEMPT;
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
    public Game add(Game game) {
        // insert operation
        context.insertInto(GAME, GAME.NAME)
                .values(game.getName())
                .execute();

        return game;
    }

    @Override
    public GameSession addGameSession(Game game, GameSession session) {
        // query
        var record = context.insertInto(GAME_SESSION, GAME_SESSION.GAME_NAME, GAME_SESSION.START)
                .values(game.getName(), session.getStart())
                .returningResult(GAME_SESSION.ID, GAME_SESSION.START)
                .fetchOne();

        // build game session and return it
        return new GameSession.Builder()
                .withId(record.value1())
                .withStartTime(record.value2())
                .withoutEndTime()
                .build();
    }

    @Override
    public GameSession updateLastGameSessionEndTimeInAGame(Game game, LocalDateTime endTime) {
        // query
        var records = context
                .update(GAME_SESSION)
                .set(GAME_SESSION.END, endTime)
                .where(GAME_SESSION.GAME_NAME.eq(game.getName())
                        .and(GAME_SESSION.END.isNull()))
                .returningResult(GAME_SESSION.ID, GAME_SESSION.START, GAME_SESSION.END)
                .fetch();

        // check if a record has been updated
        if (records.isEmpty())
            return null;

        // get the first game session, build it and return it
        var record = records.get(0);
        return new GameSession.Builder()
                .withId(record.value1())
                .withStartTime(record.value2())
                .withEndTime(record.value3())
                .build();
    }

    @Override
    public boolean gamePuzzleSubscriptionIsPossible(Device subscriber, Puzzle puzzle, Game game) {
        // query with no puzzle
        if (puzzle == null) {
            return context.fetchExists(
                    context
                            .selectOne()
                            .whereExists(context.selectOne().from(DEVICE).where(DEVICE.ID.eq(subscriber.getId()))
                                    .andExists(context.selectOne().from(GAME).where(GAME.NAME.eq(game.getName()))))
            );
        }

        // query with puzzle
        return context.fetchExists(
                context
                        .selectOne()
                        .whereExists(context.selectOne().from(DEVICE).where(DEVICE.ID.eq(subscriber.getId()))
                                .andExists(context.selectOne().from(PUZZLE).where(PUZZLE.NAME.eq(puzzle.getName())))
                                .andExists(context.selectOne().from(GAME).where(GAME.NAME.eq(game.getName()))))
        );
    }

    @Override
    public void addGamePuzzleSubscription(Device subscriber, Puzzle puzzle, Game game, int position) {
        // alias
        var ps = PUZZLE_SUBSCRIBER.as("ps");

        // query
        context.insertInto(ps, ps.GAME_NAME, ps.SUBSCRIBED_TO_PUZZLE_NAME, ps.SUBSCRIBER_DEVICE_ID, ps.POSITION)
                .values(game.getName(), puzzle == null ? null : puzzle.getName(), subscriber.getId(), position)
                .execute();
    }

    @Override
    public List<Game> getAllGames() {
        // return query
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

        // return the query
        return context
                .select(d.ID, d.TYPE, p.NAME, p.SOLUTION)
                .from(
                        ps.innerJoin(d).on(d.ID.eq(ps.SUBSCRIBER_DEVICE_ID))
                                .innerJoin(p).on(p.DEVICE_OWNER_ID.eq(d.ID))
                )
                .where(ps.GAME_NAME.eq(game.getName()))
                .orderBy(ps.POSITION.asc())
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
    public List<GameSession> getAllGameSessions(Game game) {
        // the query
        var result = context.
                selectFrom(GAME_SESSION)
                .where(GAME_SESSION.GAME_NAME.eq(game.getName()))
                .orderBy(GAME_SESSION.START.asc());

        // build the game sessions and return it
        return result.stream()
                .map(record -> new GameSession.Builder()
                        .withId(record.getId())
                        .withStartTime(record.getStart())
                        .withEndTime(record.getEnd())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public GameSession getCurrentlyPlayedGameSession(Game game) {
        // alias
        var gs = GAME_SESSION.as("gs");

        var record = context
                .selectFrom(gs)
                .where(gs.END.isNull())
                .fetchOne();

        return new GameSession.Builder()
                .withId(record.getId())
                .withStartTime(record.getStart())
                .withEndTime(record.getEnd())
                .build();
    }

    @Override
    public void addPuzzleAttempt(PuzzleAttempt puzzleAttempt) {
        // alias
        var pa = PUZZLE_ATTEMPT.as("pa");

        // query
        context.insertInto(pa, pa.AT, pa.SUCCESS, pa.PUZZLE_NAME, pa.GAME_SESSION_ID)
                .values(puzzleAttempt.getAt(), puzzleAttempt.isSuccess(), puzzleAttempt.getPuzzleName(), puzzleAttempt.getGameSessionId())
                .execute();
    }

    @Override
    public boolean gameSessionExistsAndIsBeingPlayed(int gameSessionId) {
        // return query
        return context.fetchExists(context
                .selectOne()
                .from(GAME_SESSION)
                .where(GAME_SESSION.ID.eq(gameSessionId)
                        .and(GAME_SESSION.END.isNull()))
        );
    }

    @Override
    public void closeAllGameSessionsBeingPlayed() {
        // do query
        context
                .update(GAME_SESSION)
                .set(GAME_SESSION.END, LocalDateTime.now())
                .where(GAME_SESSION.END.isNull())
                .execute();
    }

    @Override
    public Game get(String gameName) {
        // query
        var record = context
                .select(GAME.NAME)
                .from(Tables.GAME)
                .where(GAME.NAME.eq(gameName))
                .fetchAny();

        // check if the game was found
        if (record == null) {
            return null;
        }

        // build the game and return it
        return new Game.Builder()
                .withName(record.value1())
                .build();
    }

    @Override
    public boolean exists(String gameName) {
        return context.fetchExists(context
                .selectOne()
                .from(GAME)
                .where(GAME.NAME.eq(gameName)));
    }
}
