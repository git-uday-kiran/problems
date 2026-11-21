package problems.lru_cache;

import java.io.Closeable;
import java.io.IOException;
import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class ScheduledDataSweeper<T> implements ExpiredDataSweeper<ConcurrentHashMap<String, KeyData<T>>>, Closeable {

    private final Clock clock;
    private final ScheduledExecutorService scheduleService;
    private final Consumer<String> removedKeyListener;

    public ScheduledDataSweeper(Clock clock, ConcurrentHashMap<String, KeyData<T>> data, Consumer<String> removedKeyListener) {
        this.clock = clock;
        this.removedKeyListener = removedKeyListener;

        this.scheduleService = Executors.newScheduledThreadPool(1);
        scheduleService.scheduleWithFixedDelay(() -> sweep(data), 1, 5, TimeUnit.SECONDS);
    }

    @Override
    public void sweep(ConcurrentHashMap<String, KeyData<T>> data) {
        for (String key : data.keySet()) {
            data.compute(key, (k, v) -> {
                if (v == null) {
                    return null;
                }
                if (v.isExpired(clock)) {
                    removedKeyListener.accept(key);
                    return null;
                }
                return v;
            });
        }
    }

    @Override
    public void close() throws IOException {
        scheduleService.shutdownNow();
    }
}
