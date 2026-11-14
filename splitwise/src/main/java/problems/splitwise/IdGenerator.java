package problems.splitwise;

import java.util.Arrays;
import java.util.UUID;

public final class IdGenerator {

    public static String generate(Object... objects) {
        return UUID.nameUUIDFromBytes(Arrays.toString(objects).getBytes()).toString();
    }

    private IdGenerator() {
    }
}
