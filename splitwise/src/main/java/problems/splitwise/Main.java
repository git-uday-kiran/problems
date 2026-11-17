package problems.splitwise;

import problems.splitwise.models.Participant;
import problems.splitwise.repository.AllInOneRepository;
import problems.splitwise.service.SplitwiseApplication;
import problems.splitwise.service.splitstrategy.EvenlySplitStrategy;
import problems.splitwise.service.splitstrategy.SplitStrategy;

import java.util.List;

public final class Main {

    public static void main(String[] args) {
        AllInOneRepository repo = AllInOneRepository.getInstance();
        SplitwiseApplication app = new SplitwiseApplication(repo);

        String groupId = repo.createGroup();

        String userId1 = repo.createUser();
        String userId2 = repo.createUser();
        String userId3 = repo.createUser();

        String groupUserId1 = repo.createGroupUser(groupId, userId1);
        String groupUserId2 = repo.createGroupUser(groupId, userId2);
        String groupUserId3 = repo.createGroupUser(groupId, userId3);

        List<Participant> participants = List.of(
                Participant.of(userId2),
                Participant.of(userId3)
        );

        SplitStrategy evenlySplitStrategy = new EvenlySplitStrategy();
        app.addExpense(userId1, groupId, 100, participants, evenlySplitStrategy);


    }
}
