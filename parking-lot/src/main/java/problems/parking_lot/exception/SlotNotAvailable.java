package problems.parking_lot.exception;

public class SlotNotAvailable extends RuntimeException {
    public SlotNotAvailable(String message) {
        super(message);
    }
}
