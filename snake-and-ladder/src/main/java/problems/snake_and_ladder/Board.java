package problems.snake_and_ladder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public final class Board {

    private final Set<Snake> snakes;
    private final Set<Ladder> ladders;

    public Board(Set<Snake> snakes, Set<Ladder> ladders) {
        Objects.requireNonNull(snakes, "snakes");
        Objects.requireNonNull(ladders, "ladders");

        assertSnakeHeadsAndLadderTopsAreNotSame(snakes, ladders);
        assertAllSnakeHeadsUnique(snakes);
        assertAllLadderBottomsUnique(ladders);
        this.snakes = snakes;
        this.ladders = ladders;
    }

    private void assertAllLadderBottomsUnique(Set<Ladder> ladders) {
        Map<Position, Integer> ladderBottomCounts = ladders.stream().collect(toMap(Ladder::bottom, ladder -> 1, Integer::sum));
        for (Map.Entry<Position, Integer> ladderBottomCount : ladderBottomCounts.entrySet()) {
            if (ladderBottomCount.getValue() > 1) {
                throw new IllegalStateException("Ladder bottoms must be unique");
            }
        }
    }

    private void assertAllSnakeHeadsUnique(Set<Snake> snakes) {
        Map<Position, Integer> snakeHeadCounts = snakes.stream().collect(toMap(Snake::head, snake -> 1, Integer::sum));
        for (Map.Entry<Position, Integer> snakeHeadCount : snakeHeadCounts.entrySet()) {
            if (snakeHeadCount.getValue() > 1) {
                throw new IllegalStateException("Snake heads must be unique");
            }
        }
    }

    private static void assertSnakeHeadsAndLadderTopsAreNotSame(Set<Snake> snakes, Set<Ladder> ladders) {
        for (Snake snake : snakes) {
            Position head = snake.head();
            boolean ladderHasThisSnakeHead = ladders.stream().map(Ladder::bottom).anyMatch(head::equals);
            if (ladderHasThisSnakeHead) {
                throw new IllegalStateException("Snake " + snake + " has same head as one of the ladder " + ladders);
            }
        }
    }

    public void turn(Player player, Dice dice) {
        Position position = player.takeTurn(dice);
        while (anySnakeOrLadderPresent(position)) {
            Optional<Snake> snake = findAnySnake(position);
            Optional<Ladder> ladder = findAnyLadder(position);

            snake.ifPresent(Snake::logFoundSnake);
            ladder.ifPresent(Ladder::logLadderFound);

            snake.map(Snake::tail).ifPresent(player::move);
            ladder.map(Ladder::top).ifPresent(player::move);

            position = player.getPosition();
        }
    }

    private boolean anySnakeOrLadderPresent(Position position) {
        return findAnySnake(position).isPresent() || findAnyLadder(position).isPresent();
    }

    private Optional<Ladder> findAnyLadder(Position position) {
        for (Ladder ladder : ladders) {
            if (ladder.bottom().equals(position)) {
                return Optional.of(ladder);
            }
        }
        return Optional.empty();
    }

    private Optional<Snake> findAnySnake(Position position) {
        for (Snake snake : snakes) {
            if (snake.head().equals(position)) {
                return Optional.of(snake);
            }
        }
        return Optional.empty();
    }
}
