package problems.splitwise;

import static java.util.Objects.requireNonNull;

public record Participant(String groupUserId, Double value) {
    public Participant {
        requireNonNull(groupUserId);
    }

    public void assertValueNotNull() {
        if (value == null) {
            throw new IllegalArgumentException("Participant value can not be null");
        }
    }

    public Share toShare(long amountPaise) {
        return new Share(groupUserId, amountPaise);
    }
}