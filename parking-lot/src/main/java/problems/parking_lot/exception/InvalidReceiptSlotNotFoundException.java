package problems.parking_lot.exception;

public class InvalidReceiptSlotNotFoundException extends RuntimeException {
    public InvalidReceiptSlotNotFoundException(String message) {
        super(message);
    }
}
