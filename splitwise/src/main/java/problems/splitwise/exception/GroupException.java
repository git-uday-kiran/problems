package problems.splitwise.exception;

import java.util.function.Supplier;

public class GroupException extends RuntimeException {
    public GroupException(String message) {
        super(message);
    }

    public static Supplier<GroupException> groupNotFound(String groupId) {
        return () -> new GroupException("Group not found: " + groupId);
    }
}
