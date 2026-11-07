package problems.snake_and_ladder;

public final class Player {

    private final int identification;
    private Position position = Position.INITIAL;

    public Player(int identification) {
        this.identification = identification;
    }

    public Position takeTurn(Dice dice) {
        int diceNumber = dice.roll();
        this.position = position.increase(diceNumber);
        return position;
    }

    public void move(Position position) {
        this.position = position;
    }

    public int getIdentification() {
        return identification;
    }

    public Position getPosition() {
        return position;
    }

    public static Player withIdentification(int identification) {
        return new Player(identification);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id" + identification +
                ", position=" + position +
                '}';
    }
}
