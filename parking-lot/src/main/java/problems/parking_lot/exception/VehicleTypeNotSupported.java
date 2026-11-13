package problems.parking_lot.exception;

public class VehicleTypeNotSupported extends RuntimeException {
    public VehicleTypeNotSupported(String message) {
        super(message);
    }
}
