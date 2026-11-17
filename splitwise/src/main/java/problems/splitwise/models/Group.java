package problems.splitwise.models;

import lombok.Getter;
import problems.splitwise.util.IdGenerator;

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
