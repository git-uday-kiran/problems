package problems.lru_cache;

public interface ExpiredDataSweeper<T> {
    void sweep(T data);
}
