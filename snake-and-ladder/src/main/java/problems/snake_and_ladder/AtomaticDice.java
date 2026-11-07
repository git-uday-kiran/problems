package problems.snake_and_ladder;

import java.util.Random;

public class AtomaticDice implements Dice {
    private static final int MAX_POSSIBILITIES = 6;
    private static final Random RANDOM = new Random();

    public int roll() {
        return RANDOM.nextInt(1, MAX_POSSIBILITIES);
    }
}
