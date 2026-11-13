package problems.parking_lot.exception;

public class VehicleAlreadyOccupiedException extends RuntimeException {
    public VehicleAlreadyOccupiedException(String message) {
        super(message);
    }
}
