package problems.snake_and_ladder;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Game {

    private final Dice dice;
    private final Board board;
    private final List<Player> players;

    private int activePlayerIndex;

    public Game(Board board, Dice dice, int noOfPlayers) {
        Objects.requireNonNull(board, "board must not be null");
        Objects.requireNonNull(dice, "dice must not be null");

        this.dice = dice;
        this.board = board;
        this.players = preparePlayers(noOfPlayers);
        this.activePlayerIndex = -1;
    }

    private static List<Player> preparePlayers(int noOfPlayers) {
        if (noOfPlayers < 2) {
            throw new IllegalStateException("No of players must be at least 2");
        }
        return IntStream.rangeClosed(1, noOfPlayers)
                .mapToObj(Player::withIdentification)
                .toList();
    }

    public void start() {
        System.out.println("No of players: " + players.size());
        System.out.println("Starting game...");
        while (true) {
            Player activePlayer = nextPlayer();
            System.out.println("Current Player: " + activePlayer.getIdentification() + "\t is at: " + activePlayer.getPosition());
            board.turn(activePlayer, dice);
            System.out.println("Player moved to: " + activePlayer.getPosition());
            throat();
        }
    }

    private void throat() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Player nextPlayer() {
        setNextActivePlayer();
        return players.get(activePlayerIndex);
    }

    private void setNextActivePlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % players.size();
    }
}
