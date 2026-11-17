package problems.splitwise.models;

import lombok.Getter;
import problems.splitwise.IdGenerator;

import static java.util.Objects.requireNonNull;

@Getter
public final class GroupUserExpenseShare {

    private final String id;
    private final String groupUserExpenseId;
    private final String groupUserId;
    private final long amountOwedPaise;

    public GroupUserExpenseShare(String groupUserExpenseId, String groupUserId, long amountOwedPaise) {
        this.id = IdGenerator.generate(groupUserExpenseId);
        this.groupUserExpenseId = requireNonNull(groupUserExpenseId);
        this.groupUserId = groupUserId;
        this.amountOwedPaise = amountOwedPaise;
    }
}
