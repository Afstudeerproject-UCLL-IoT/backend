package infrastructure.persistence;

import core.domain.Device;
import core.domain.Game;
import core.domain.GameSession;
import core.domain.Puzzle;
import core.interfaces.repositories.GameRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public void addGamePuzzleSubscription(Device subscriber, Puzzle puzzle, Game game) {
        var ps = PUZZLE_SUBSCRIBER.as("ps");

        context.insertInto(ps, ps.GAME_NAME, ps.SUBSCRIBED_TO_PUZZLE_NAME, ps.SUBSCRIBER_DEVICE_ID)
                .values(game.getName(), puzzle.getName(), subscriber.getId())
                .execute();
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
