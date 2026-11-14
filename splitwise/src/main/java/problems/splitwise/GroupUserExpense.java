package problems.splitwise;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class GroupUserExpense {

    private final String id;
    private final String groupUserId;
    private final long amountPaise;

    public GroupUserExpense(String groupUserId, long amountPaise) {
        this.id = IdGenerator.generate(groupUserId);
        this.groupUserId = Objects.requireNonNull(groupUserId);
        this.amountPaise = amountPaise;
    }
}
