package problems.splitwise.exception;

import java.util.function.Supplier;

public class GroupUserException extends RuntimeException {
    public GroupUserException(String message) {
        super(message);
    }

    public static Supplier<GroupUserException> groupUserNotFound(String groupId, String userId) {
        return () -> new GroupUserException(String.format("User with id %s not found in group %s", userId, groupId));
    }
}
