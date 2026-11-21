package problems.lru_cache;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public final class LRUCache<T> implements Cache<T> {

    private final int capacity;
    private final Clock clock;
    private final Map<String, KeyData<T>> storage;

    private final LinkedHashSet<String> leastRecentUsedKeys;
    private final ReentrantLock lestRecentUsedKeysLock;

    public LRUCache(int capacity, Clock clock) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        Objects.requireNonNull(clock, "Clock must not be null");

        this.capacity = capacity;
        this.clock = clock;
        this.storage = new ConcurrentHashMap<>();
        this.leastRecentUsedKeys = new LinkedHashSet<>();
        lestRecentUsedKeysLock = new ReentrantLock();
    }

    @Override
    public Optional<T> get(String key) {
        Objects.requireNonNull(key, "key cannot be null");
        KeyData<T> value = storage.compute(key, (k, v) -> {
            if (v == null) {
                return null;
            }
            markLastUsed(key);
            return v;
        });
        return Optional.ofNullable(value).map(KeyData::value);
    }

    @Override
    public void set(String key, T value) {
        long expireMillis = Long.MAX_VALUE;
        set(key, value, expireMillis);
    }

    @Override
    public void set(String key, T value, Duration ttl) {
        Objects.requireNonNull(ttl, "ttl must not be null");
        long expireMillis = BigInteger.valueOf(clock.millis()).add(BigInteger.valueOf(ttl.toMillis())).longValue();
        set(key, value, expireMillis);
    }

    private void set(String key, T value, long expireMillis) {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(value, "value must not be null");

        storage.compute(key, (k, v) -> {
            if (v == null) {
                checkStorageSizeAndShrink();
            }
            KeyData<T> keyData = new KeyData<>(value, expireMillis);
            markLastUsed(key);
            return keyData;
        });
    }

    private void checkStorageSizeAndShrink() {
        if (storage.size() == capacity) {
            lestRecentUsedKeysLock.lock();
            try {
                String leastRecentUsedKey = leastRecentUsedKeys.removeFirst();
                storage.remove(leastRecentUsedKey);
            } finally {
                lestRecentUsedKeysLock.unlock();
            }
        }
    }

    private void markLastUsed(String key) {
        lestRecentUsedKeysLock.lock();
        try {
            leastRecentUsedKeys.addLast(key);
        } finally {
            lestRecentUsedKeysLock.unlock();
        }
    }

}
