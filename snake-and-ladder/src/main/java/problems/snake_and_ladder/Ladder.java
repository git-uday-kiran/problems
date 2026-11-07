package problems.snake_and_ladder;

import java.util.Objects;

public record Ladder(Position top, Position bottom) {
    public Ladder {
        Objects.requireNonNull(top, "top");
        Objects.requireNonNull(bottom, "bottom");
    }
}
