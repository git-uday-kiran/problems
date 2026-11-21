package problems.lru_cache;

import java.time.Clock;

public record KeyData<T>(T value, long expireMillis) {
    public boolean isExpired(Clock clock) {
        return clock.millis() >= expireMillis;
    }
}
