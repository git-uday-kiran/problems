package problems.snake_and_ladder;

import java.util.HashSet;
import java.util.Set;

import static problems.snake_and_ladder.InputUtil.takeNumericInput;

public final class SnakeLadderApplication {

    public static void main(String[] args) {
        int noOfPlayers = takeNumericInput("No of players: ", 2, 10);
        Dice dice = new ManualDice();
        Board board = prepareBoard();

        Game game = new Game(board, dice, noOfPlayers);
        game.start();
    }

    private static Board prepareBoard() {
        Set<Snake> snakes = new HashSet<>();
        Set<Ladder> ladders = new HashSet<>();
        return new Board(snakes, ladders);
    }
}
