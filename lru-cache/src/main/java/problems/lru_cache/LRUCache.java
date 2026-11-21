package problems.lru_cache;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class LRUCache<T> implements Cache<T> {

    private final int capacity;
    private final Clock clock;
    private final ConcurrentHashMap<String, KeyData<T>> storage;

    private final ReentrantLock lruLock;
    private final LinkedHashSet<String> lruKeys;

    private final ScheduledDataSweeper<T> sweeper;

    public LRUCache(int capacity, Clock clock) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        Objects.requireNonNull(clock, "Clock must not be null");

        this.capacity = capacity;
        this.clock = clock;
        this.storage = new ConcurrentHashMap<>();
        this.lruKeys = new LinkedHashSet<>();
        this.lruLock = new ReentrantLock();
        sweeper = new ScheduledDataSweeper<>(clock, storage, lruKeys::remove);
    }

    @Override
    public Optional<T> get(String key) {
        Objects.requireNonNull(key, "key cannot be null");
        KeyData<T> value = storage.compute(key, (k, v) -> {
            if (v == null) {
                return null;
            }
            if (v.isExpired(clock)) {
                remove(key);
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

    @Override
    public void remove(String key) {
        Objects.requireNonNull(key, "key cannot be null");
        storage.compute(key, (k, v) -> {
            withLRULock(key, lruKeys::remove);
            return null;
        });
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
            String lruKey = withLRULock(lruKeys::removeFirst);
            storage.remove(lruKey);
        }
    }

    private void markLastUsed(String key) {
        withLRULock(key, lruKeys::addLast);
    }

    private void withLRULock(String key, Consumer<String> action) {
        lruLock.lock();
        try {
            action.accept(key);
        } finally {
            lruLock.unlock();
        }
    }

    private <R> R withLRULock(Supplier<R> supplier) {
        lruLock.lock();
        try {
            return supplier.get();
        } finally {
            lruLock.unlock();
        }
    }

    @Override
    public void close() {
        sweeper.close();
    }
}
