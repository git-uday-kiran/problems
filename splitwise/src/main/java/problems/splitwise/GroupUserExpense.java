package problems.splitwise;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class GroupUserExpense {

    private final String id;
    private final String groupUserId;

    public GroupUserExpense(String groupUserId) {
        this.id = IdGenerator.generate(groupUserId);
        this.groupUserId = Objects.requireNonNull(groupUserId);
    }
}
