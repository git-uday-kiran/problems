package problems.splitwise;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PercentageSplitStrategy implements SplitStrategy {
    @Override
    public List<Share> split(List<Participant> participants, long amountPaise) {
        assertAtLeastOneParticipant(participants, amountPaise);
        participants.forEach(Participant::assertValueNotNull);
        assertParticipantsPercentagesSumUpTo100(participants);

        List<Share> shares = new ArrayList<>();
        for (Participant participant : participants) {
            Double percentage = participant.value();
            long sharePaise = share(amountPaise, percentage);
            shares.add(participant.toShare(sharePaise));
        }

        return shares;
    }

    private static long share(long amountPaise, double percentage) {
        return BigDecimal.valueOf(amountPaise)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(percentage))
                .longValueExact();
    }

    private static void assertParticipantsPercentagesSumUpTo100(Collection<Participant> participants) {
        BigDecimal percentageSum = BigDecimal.ZERO;
        for (Participant participant : participants) {
            percentageSum = percentageSum.add(BigDecimal.valueOf(participant.value()));
        }
        if (percentageSum.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new IllegalArgumentException("All participants percentages should be sum up to 100");
        }
    }

}
