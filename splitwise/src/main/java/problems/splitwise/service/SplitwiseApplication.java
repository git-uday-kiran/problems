package problems.splitwise.service;

import problems.splitwise.exception.GroupException;
import problems.splitwise.exception.GroupUserException;
import problems.splitwise.exception.ParticipantException;
import problems.splitwise.exception.UserException;
import problems.splitwise.models.GroupUser;
import problems.splitwise.models.Participant;
import problems.splitwise.models.Share;
import problems.splitwise.repository.AllInOneRepository;
import problems.splitwise.service.splitstrategy.SplitStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class SplitwiseApplication {

    private final AllInOneRepository repo;

    public SplitwiseApplication(AllInOneRepository repo) {
        this.repo = repo;
    }

    public void addExpense(String userId, String groupId, long amountPaidPaise, List<Participant> participants, SplitStrategy splitStrategy) {
        repo.findUser(userId).orElseThrow(UserException.userNotFound(userId));
        repo.findGroup(groupId).orElseThrow(GroupException.groupNotFound(groupId));

        GroupUser groupUser = repo.findGroupUser(userId, groupId).orElseThrow(GroupUserException.groupUserNotFound(groupId, userId));
        String groupUserId = groupUser.getId();

        assertAllParticipantsShouldBeInTheGroup(participants, groupId);
        String expenseId = repo.createGroupUserExpense(groupUserId, amountPaidPaise);

        List<Share> splits = splitStrategy.split(participants, amountPaidPaise);
        for (Share split : splits) {
            String participantUserId = split.userId();
            GroupUser participantGroupUser = repo.findGroupUser(groupId, participantUserId).orElseThrow(GroupUserException.groupUserNotFound(groupId, participantUserId));
            repo.createGroupUserExpenseShare(expenseId, participantGroupUser.getId(), split.amountPaise());
        }
    }

    private void assertAllParticipantsShouldBeInTheGroup(Collection<Participant> participants, String groupId) {
        for (Participant participant : participants) {
            String participantUserId = participant.userId();
            Set<String> participantGroupIds = repo.getGroupIds(participantUserId);
            if (!participantGroupIds.contains(groupId)) {
                throw new ParticipantException("Participant " + participantUserId + " does not belong to group " + groupId);
            }
        }
    }

}
