package problems.splitwise;

import static java.util.Objects.requireNonNull;

public record Participant(String groupUserId, Double value) {
    public Participant {
        requireNonNull(groupUserId);
    }

    public Share toShare(long amountPaise) {
        return new Share(groupUserId, amountPaise);
    }
}