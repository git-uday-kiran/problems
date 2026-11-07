package problems.snake_and_ladder;

public final class ManualDice implements Dice {
    @Override
    public int roll() {
        return InputUtil.takeNumericInput("Enter the dic number (1-6): ", 1, 6);
    }
}
