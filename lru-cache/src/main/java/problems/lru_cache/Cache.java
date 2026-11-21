package problems.lru_cache;

import java.time.Duration;
import java.util.Optional;

public interface Cache<T> {
    Optional<T> get(String key);

    void set(String key, T value);

    void set(String key, T value, Duration ttl);

    void remove(String key);
}
