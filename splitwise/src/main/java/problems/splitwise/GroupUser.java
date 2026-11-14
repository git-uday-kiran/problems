package problems.splitwise;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public final class GroupUser {

    private final String id;
    private final String groupId;
    private final String userId;

    public GroupUser(String groupId, String userId) {
        this.id = IdGenerator.generate(groupId, userId);
        this.groupId = requireNonNull(groupId);
        this.userId = requireNonNull(userId);
    }
}
