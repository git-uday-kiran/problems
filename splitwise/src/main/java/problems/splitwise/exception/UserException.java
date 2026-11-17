package problems.splitwise.exception;

import java.util.function.Supplier;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public static Supplier<UserException> userNotFound(String userId) {
        return () -> new UserException("User not found: " + userId);
    }
}
