package problems.lru_cache;

public record KeyData<T>(T value, long expireMillis) {
}
