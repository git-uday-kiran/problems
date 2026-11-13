package problems.parking_lot;

import java.util.Optional;

public interface Vehicle {
    default Optional<String> getVehicleNumber() {
        return Optional.empty();
    }

    VehicleType getType();
}
