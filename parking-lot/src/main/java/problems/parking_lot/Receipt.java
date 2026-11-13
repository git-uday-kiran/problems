package problems.parking_lot;

public record Receipt(
        int slotNumber,
        int floor,
        long startingTimeMillis,
        String vehicleNumber,
        VehicleType vehicleType,
        int slotPricePerHour
) {
}
