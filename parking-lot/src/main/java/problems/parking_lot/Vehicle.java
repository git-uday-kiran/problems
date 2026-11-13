package problems.parking_lot;

import java.util.Optional;

public interface Vehicle {
    Optional<String> getVehicleNumber();

    VehicleType getType();
}
