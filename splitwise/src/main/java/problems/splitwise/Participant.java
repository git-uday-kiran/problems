package problems.splitwise;

import static java.util.Objects.requireNonNull;

public record Participant(String groupUserId, long amountPaise) {
    public Participant {
        requireNonNull(groupUserId);
    }
}