package problems.parking_lot.exception;

public class ParkingNotAvailableException extends RuntimeException {
    public ParkingNotAvailableException(String message) {
        super(message);
    }
}
