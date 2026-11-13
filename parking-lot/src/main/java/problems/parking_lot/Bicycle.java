package problems.parking_lot;

public class Bicycle implements Vehicle {
    @Override
    public VehicleType getType() {
        return VehicleType.BYCYCLE;
    }
}
