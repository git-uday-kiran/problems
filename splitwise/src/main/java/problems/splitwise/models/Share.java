package problems.splitwise.models;

import static java.util.Objects.requireNonNull;

public record Share(String userId, long amountPaise) {
    public Share {
        requireNonNull(userId);
    }

    public Share add(long amountPaise) {
        return new Share(userId, this.amountPaise + amountPaise);
    }
}
