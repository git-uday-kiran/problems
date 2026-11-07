package problems.snake_and_ladder;

import java.util.Set;

import static problems.snake_and_ladder.InputUtil.takeNumericInput;

public final class SnakeLadderApplication {

    public static void main(String[] args) {
        int noOfPlayers = takeNumericInput("Enter no of players: ", 2, 10);
        Dice dice = new ManualDice();
        Board board = prepareBoard();

        Game game = new Game(board, dice, Position.at(100), noOfPlayers);
        game.start();
    }

    private static Board prepareBoard() {
        Set<Snake> snakes = Set.of(
                Snake.at(Position.at(20), Position.at(5)),
                Snake.at(Position.at(30), Position.at(10)),
                Snake.at(Position.at(95), Position.at(10)),
                Snake.at(Position.at(96), Position.at(10))
        );
        Set<Ladder> ladders = Set.of(
                Ladder.at(Position.at(100), Position.at(11)),
                Ladder.at(Position.at(90), Position.at(12))
        );
        return new Board(snakes, ladders);
    }
}
