package problems.splitwise;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public final class GroupUserExpenseSplit {

    private final String id;
    private final String groupUserExpenseId;

    public GroupUserExpenseSplit(String groupUserExpenseId) {
        this.id = IdGenerator.generate(groupUserExpenseId);
        this.groupUserExpenseId = requireNonNull(groupUserExpenseId);
    }
}
