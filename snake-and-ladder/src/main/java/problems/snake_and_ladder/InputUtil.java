package problems.snake_and_ladder;

import java.util.Scanner;

public final class InputUtil {

    public static final Scanner SCANNER = new Scanner(System.in);

    public static int takeNumericInput(String label, int rangeFrom, int rangeTo) {
        System.out.print(label);
        int input = SCANNER.nextInt();
        while (!isValidNumericInput(input, rangeFrom, rangeTo)) {
            System.out.print("Invalid input. Please try again: ");
            input = SCANNER.nextInt();
        }
        return input;
    }

    private static boolean isValidNumericInput(int input, int rangeFrom, int rangeTo) {
        return input >= rangeFrom && input <= rangeTo;
    }
}
