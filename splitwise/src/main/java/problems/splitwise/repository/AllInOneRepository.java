package problems.splitwise.repository;

import problems.splitwise.models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class AllInOneRepository {

    private final Map<String, User> users;
    private final Map<String, Group> groups;
    private final Map<String, GroupUser> groupUsers;
    private final Map<String, GroupUserExpense> groupUserExpenses;
    private final Map<String, GroupUserExpenseShare> groupUserExpenseShares;

    private static final AllInOneRepository INSTANCE = new AllInOneRepository();

    public AllInOneRepository() {
        users = new HashMap<>();
        groups = new HashMap<>();
        groupUsers = new HashMap<>();
        groupUserExpenses = new HashMap<>();
        groupUserExpenseShares = new HashMap<>();
    }

    public Optional<User> findUser(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Optional<Group> findGroup(String groupId) {
        return Optional.ofNullable(groups.get(groupId));
    }

    public Optional<GroupUser> findGroupUser(String groupId, String userId) {
        return Optional.ofNullable(groupUsers.get(groupId));
    }

    public Optional<GroupUser> findGroupUser(String groupUserId) {
        return Optional.ofNullable(groupUsers.get(groupUserId));
    }

    public List<GroupUserExpense> getGroupUserExpenses(String groupId, String userId) {
        GroupUser groupUser = findGroupUser(groupId, userId).orElseThrow();
        return groupUserExpenses.values().stream()
                .filter(expense -> expense.getGroupUserId().equals(groupUser.getId()))
                .toList();
    }

    public static AllInOneRepository getInstance() {
        return INSTANCE;
    }
}
