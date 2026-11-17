package problems.splitwise.models;

import static java.util.Objects.requireNonNull;

public record Participant(String userId, Double value) {
    public Participant {
        requireNonNull(userId);
    }

    public void assertValueNotNull() {
        if (value == null) {
            throw new IllegalArgumentException("Participant value can not be null");
        }
    }

    public Share toShare(long amountPaise) {
        return new Share(userId, amountPaise);
    }

    public static Participant of(String userId) {
        return new Participant(userId, null);
    }

    public static Participant of(String userId, Double value) {
        return new Participant(userId, value);
    }
}