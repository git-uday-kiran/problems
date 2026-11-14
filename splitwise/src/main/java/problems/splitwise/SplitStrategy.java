package problems.splitwise;

import java.util.List;

public interface SplitStrategy {
    List<Share> split(List<Participant> participants, long amountPaise);
}
