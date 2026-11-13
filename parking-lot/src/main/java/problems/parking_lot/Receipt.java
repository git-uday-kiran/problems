package problems.parking_lot;

public record Receipt(
        int slotNumber,
        int floor,
        int startingTimeMillis,
        String vehicleNumber,
        VehicleType vehicleType,
        int slotPricePerHour
) {
}
