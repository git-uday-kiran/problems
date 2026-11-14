package problems.splitwise;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class ManualAmountSplitStrategy implements SplitStrategy {
    @Override
    public List<Share> split(List<Participant> participants, long amountPaise) {
        assertAtLeastOneParticipant(participants, amountPaise);
        participants.forEach(Participant::assertValueNotNull);
        assertAmountsSumsTo(participants, amountPaise);
        assertAmountsAreWholeNumbers(participants);

        List<Share> shares = new ArrayList<>();
        for (Participant participant : participants) {
            shares.add(participant.toShare(participant.value().longValue()));
        }
        return shares;
    }

    private static void assertAmountsAreWholeNumbers(List<Participant> participants) {
        for (Participant participant : participants) {
            if (!isWholeNumber(participant.value())) {
                throw new IllegalArgumentException("Participant amount must be an whole number");
            }
        }
    }

    private void assertAmountsSumsTo(List<Participant> participants, long amountPaise) {
        long participantsTotalShareSum = participants.stream()
                .map(Participant::value)
                .mapToLong(Double::longValue)
                .sum();
        if (amountPaise != participantsTotalShareSum) {
            throw new IllegalArgumentException("All participants manual amounts must be equals to the total amount");
        }
    }

    private static boolean isWholeNumber(double value) {
        return BigDecimal.valueOf(value).remainder(BigDecimal.ONE).equals(BigDecimal.ZERO);
    }
}
