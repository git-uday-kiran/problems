package problems.lru_cache;


import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        Cache<Integer> cache = null;

        cache.set("k1", 1);
        cache.set("k2", 2);
        cache.set("k3", 3, Duration.ofSeconds(3));

        sleep(3);

        Integer k1 = cache.get("k1").orElse(null);
        Integer k2 = cache.get("k2").orElse(null);
        Integer k3 = cache.get("k3").orElse(null);

        System.out.println("Expect 1 for k1: " + k1);
        System.out.println("Expect 2 for k2: " + k2);
        System.out.println("Expect null for k3: " + k3);
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ignored) {
        }
    }
}