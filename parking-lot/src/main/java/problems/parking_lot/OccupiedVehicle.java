package problems.parking_lot;

import java.util.Objects;

public record OccupiedVehicle(long startTimeMillis, Vehicle vehicle) {
    public OccupiedVehicle {
        Objects.requireNonNull(vehicle);
    }
}
