package problems.parking_lot;

public class Car implements Vehicle {
    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }
}
