package problems.splitwise;

import java.util.ArrayList;
import java.util.List;

public final class EvenlySplitStrategy implements SplitStrategy {
    @Override
    public List<Share> split(List<Participant> participants, long amountPaise) {
        assertAtLeastOneParticipant(participants, amountPaise);
        long eventShare = (amountPaise / participants.size());
        long leftOverShare = (amountPaise - (eventShare * participants.size()));

        List<Share> shares = new ArrayList<>();
        for (Participant participant : participants) {
            shares.add(participant.toShare(eventShare));
        }

        if (!shares.isEmpty()) {
            Share lastShare = shares.removeLast().add(leftOverShare);
            shares.add(lastShare);
        }

        return shares;
    }
}
