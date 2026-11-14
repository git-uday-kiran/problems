package problems.splitwise;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public final class Group {
    private final String id;

    public Group() {
        this(IdGenerator.generate());
    }

    public Group(String id) {
        this.id = requireNonNull(id);
    }
}
