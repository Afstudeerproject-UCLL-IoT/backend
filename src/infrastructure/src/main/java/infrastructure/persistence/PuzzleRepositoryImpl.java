package infrastructure.persistence;

import core.domain.Device;
import core.domain.Puzzle;
import core.interfaces.repositories.PuzzleRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static infrastructure.persistence.Tables.PUZZLE;
import static infrastructure.persistence.Tables.PUZZLE_SUBSCRIBER;

@Repository
public class PuzzleRepositoryImpl implements PuzzleRepository {

    private final DSLContext context;

    public PuzzleRepositoryImpl( @Autowired DSLContext context) {
        this.context = context;
    }

    @Override
    public List<Device> getSubscriptions(Puzzle puzzle) {

        // TODO fix query, we do not join because I know we only have ARDUINOS and don't need the puzzle solution
        // also want to test performance for getting subscribers if it's not good we will cache subscriptions
        var records = context
                .select(PUZZLE_SUBSCRIBER.SUBSCRIBER_DEVICE_ID, PUZZLE_SUBSCRIBER.SUBSCRIBED_TO_PUZZLE_NAME)
                .from(PUZZLE_SUBSCRIBER)
                .where(PUZZLE_SUBSCRIBER.SUBSCRIBED_TO_PUZZLE_NAME.eq(puzzle.getName()))
                .fetch();

        // TODO take a look at a mapper provided by jooq
        return records.stream()
                .map(record -> new Device.Builder()
                        .withId(record.value1())
                        .fromDeviceName(String.format("ARDUINO-%s", record.value2()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPresent(Puzzle puzzle) {
        return context.fetchExists(
                context.selectOne()
                .from(PUZZLE)
                .where(PUZZLE.NAME.eq(puzzle.getName()))
        );
    }
}
