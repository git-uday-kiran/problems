package problems.parking_lot;

import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

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

    public Optional<OccupiedVehicle> getOccupiedVehicle() {
        return Optional.ofNullable(occupiedVehicle);
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
