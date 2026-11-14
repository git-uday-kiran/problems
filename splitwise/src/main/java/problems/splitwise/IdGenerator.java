package problems.splitwise;

import java.util.Arrays;
import java.util.UUID;

public final class IdGenerator {

    public static String generate(Object... objects) {
        if (objects == null || objects.length == 0) {
            return UUID.randomUUID().toString();
        }
        return UUID.nameUUIDFromBytes(Arrays.toString(objects).getBytes()).toString();
    }

    private IdGenerator() {
    }
}
