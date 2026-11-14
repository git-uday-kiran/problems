package problems.splitwise;

import java.util.List;
import java.util.Objects;

public interface SplitStrategy {
    List<Share> split(List<Participant> participants, long amountPaise);

    default void assertAtLeastOneParticipant(List<Participant> participants, long amountPaise) {
        Objects.requireNonNull(participants);
        if (amountPaise != 0 && participants.isEmpty()) {
            throw new IllegalStateException("One participant must exist to split an amount");
        }
    }
}