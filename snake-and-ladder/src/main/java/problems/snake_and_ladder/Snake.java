package problems.snake_and_ladder;

import java.util.Objects;

public record Snake(Position head, Position tail) {
    public Snake {
        Objects.requireNonNull(head, "head");
        Objects.requireNonNull(tail, "tail");
    }

    public void logFoundSnake() {
        System.out.println("🐍 SNAKE FOUND! Sliding down... 🐍");
    }

    public static Snake at(Position head, Position tail) {
        return new Snake(head, tail);
    }
}
