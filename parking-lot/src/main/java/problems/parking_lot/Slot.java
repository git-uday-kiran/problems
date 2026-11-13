package problems.parking_lot;

import lombok.Getter;
import problems.parking_lot.exception.SlotNotAvailable;
import problems.parking_lot.exception.VehicleNotFoundException;

import java.util.Objects;

@Getter
public final class Slot {

    private final int floor;
    private final int slotNumber;
    private final VehicleType supportedVehicleType;

    private OccupiedVehicle occupiedVehicle;

    public Slot(int floor, int slotNumber, VehicleType supportedVehicleType) {
        this.floor = floor;
        this.slotNumber = slotNumber;
        this.supportedVehicleType = supportedVehicleType;
    }

    public OccupiedVehicle getOccupiedVehicle() {
        if (occupiedVehicle == null) {
            throw new VehicleNotFoundException("Slot is not occupied by any vehicle");
        }
        return occupiedVehicle;
    }

    public OccupiedVehicle park(Vehicle vehicle) {
        assertSlotIsAvailable();
        long startTimeMillis = System.currentTimeMillis();
        occupiedVehicle = new OccupiedVehicle(startTimeMillis, vehicle);
        return occupiedVehicle;
    }

    public long unpark() {
        occupiedVehicle = null;
        long endTimeMillis = System.currentTimeMillis();
        long startTimeMillis = (occupiedVehicle == null) ? endTimeMillis : occupiedVehicle.startTimeMillis();
        return (endTimeMillis - startTimeMillis);
    }

    public boolean isAvailable() {
        return (occupiedVehicle != null);
    }

    private void assertSlotIsAvailable() {
        if (!isAvailable()) {
            throw new SlotNotAvailable("Slot is not available");
        }
    }

    public int getPrice() {
        return supportedVehicleType.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Slot slot = (Slot) o;
        return floor == slot.floor
                && slotNumber == slot.slotNumber
                && supportedVehicleType == slot.supportedVehicleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, slotNumber, supportedVehicleType);
    }
}
