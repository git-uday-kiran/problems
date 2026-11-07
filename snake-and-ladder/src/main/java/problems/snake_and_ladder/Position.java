package problems.snake_and_ladder;

public record Position(int position) {

    public static final Position INITIAL = new Position(0);

    public Position increase(int count) {
        return at(position + count);
    }

    public static Position at(int position) {
        return new Position(position);
    }
}
