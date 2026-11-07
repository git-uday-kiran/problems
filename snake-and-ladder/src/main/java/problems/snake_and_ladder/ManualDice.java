package problems.snake_and_ladder;

import java.util.Scanner;

public final class ManualDice implements Dice {

    private static final int MAX_POSSIBILITIES = 6;
    public static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public int roll() {
        System.out.print("Enter the dic number (1-6): ");
        int input = SCANNER.nextInt();
        while (!isValidInput(input)) {
            System.out.print("Invalid input. Please try again: ");
            input = SCANNER.nextInt();
        }
        return input;
    }

    private boolean isValidInput(int input) {
        return input >= 1 && input <= MAX_POSSIBILITIES;
    }
}
