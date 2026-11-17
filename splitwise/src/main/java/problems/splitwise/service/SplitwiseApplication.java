package problems.splitwise.service;

import problems.splitwise.exception.GroupException;
import problems.splitwise.exception.ParticipantException;
import problems.splitwise.exception.UserException;
import problems.splitwise.models.Group;
import problems.splitwise.models.Participant;
import problems.splitwise.models.User;
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

    public void addExpense(String userId, String groupId, List<Participant> participants, SplitStrategy splitStrategy) {
        User user = repo.findUser(userId).orElseThrow(UserException.userNotFound(userId));
        Group group = repo.findGroup(groupId).orElseThrow(GroupException.groupNotFound(groupId));
        assertAllParticipantsShouldBeInTheGroup(participants, groupId);

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
