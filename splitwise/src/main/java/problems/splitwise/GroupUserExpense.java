package problems.splitwise;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class GroupUserExpense {

    private final String id;
    private final String groupUserId;
    private final long amountPaidPaise;

    public GroupUserExpense(String groupUserId, long amountPaidPaise) {
        this.id = IdGenerator.generate(groupUserId);
        this.groupUserId = Objects.requireNonNull(groupUserId);
        this.amountPaidPaise = amountPaidPaise;
    }
}
