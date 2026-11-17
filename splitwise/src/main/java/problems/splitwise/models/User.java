package problems.splitwise.models;

import lombok.Getter;

@Getter
public final class User {
    private final String id;

    public User(String id) {
        this.id = id;
    }
}
