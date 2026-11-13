package problems.parking_lot.exception;

public class InvalidReceiptException extends RuntimeException {
    public InvalidReceiptException(String message) {
        super(message);
    }
}
