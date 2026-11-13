package problems.parking_lot;

public class Bike implements Vehicle {
    @Override
    public VehicleType getType() {
        return VehicleType.BIKE;
    }
}
