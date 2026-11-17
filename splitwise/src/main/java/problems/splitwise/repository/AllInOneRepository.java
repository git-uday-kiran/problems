package problems.splitwise.repository;

import problems.splitwise.util.IdGenerator;
import problems.splitwise.models.*;

import java.util.*;
import java.util.stream.Collectors;

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

    public String createUser() {
        User user = new User(IdGenerator.generate());
        users.put(user.getId(), user);
        return user.getId();
    }

    public String createGroup() {
        Group group = new Group(IdGenerator.generate());
        groups.put(group.getId(), group);
        return group.getId();
    }

    public String createGroupUser(String groupId, String userId) {
        // Suppressing validations on user id and group id
        GroupUser groupUser = new GroupUser(groupId, userId);
        groupUsers.put(groupUser.getId(), groupUser);
        return groupUser.getId();
    }

    public String createGroupUserExpense(String groupUserId, long amountPaidPaise) {
        GroupUserExpense groupUserExpense = new GroupUserExpense(groupUserId, amountPaidPaise);
        groupUserExpenses.put(groupUserExpense.getId(), groupUserExpense);
        return groupUserExpense.getId();
    }

    public String createGroupUserExpenseShare(String groupUserExpenseId, String groupUserId, long amountOwedPaise) {
        GroupUserExpenseShare groupUserExpenseShare = new GroupUserExpenseShare(groupUserExpenseId, groupUserId, amountOwedPaise);
        groupUserExpenseShares.put(groupUserExpenseShare.getId(), groupUserExpenseShare);
        return groupUserExpenseShare.getId();
    }

    public Optional<User> findUser(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Optional<Group> findGroup(String groupId) {
        return Optional.ofNullable(groups.get(groupId));
    }

    public Set<String> getGroupIds(String userId) {
        return getGroups(userId).stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
    }

    public List<Group> getGroups(String userId) {
        return groupUsers.values().stream()
                .filter(groupUser -> groupUser.getUserId().equals(userId))
                .map(GroupUser::getGroupId)
                .map(this::findGroup)
                .flatMap(Optional::stream)
                .toList();
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
