package problems.splitwise;

import static java.util.Objects.requireNonNull;

public record Share(String groupUserId, long amountPaise) {
    public Share {
        requireNonNull(groupUserId);
    }
}
