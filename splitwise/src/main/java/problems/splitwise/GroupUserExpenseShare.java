package problems.splitwise;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public final class GroupUserExpenseShare {

    private final String id;
    private final String groupUserExpenseId;
    private final long amountPaise;

    public GroupUserExpenseShare(String groupUserExpenseId, long amountPaise) {
        this.id = IdGenerator.generate(groupUserExpenseId);
        this.groupUserExpenseId = requireNonNull(groupUserExpenseId);
        this.amountPaise = amountPaise;
    }
}
