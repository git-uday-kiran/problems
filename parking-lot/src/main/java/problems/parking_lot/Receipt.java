package problems.parking_lot;

import java.util.Objects;

public record Receipt(
        int slotNumber,
        int floor,
        long startingTimeMillis,
        String vehicleNumber,
        VehicleType vehicleType,
        int slotPricePerHour
) {
    public Receipt {
        Objects.requireNonNull(vehicleType);
    }
}
