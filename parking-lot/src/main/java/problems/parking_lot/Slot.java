package problems.parking_lot;

import lombok.Getter;
import problems.parking_lot.exception.SlotNotAvailable;
import problems.parking_lot.exception.VehicleNotFoundException;
import problems.parking_lot.exception.VehicleTypeNotSupported;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Getter
public final class Slot {

    private final int floor;
    private final int slotNumber;
    private final VehicleType supportedVehicleType;

    private OccupiedVehicle occupiedVehicle;

    public Slot(int floor, int slotNumber, VehicleType supportedVehicleType) {
        this.floor = floor;
        this.slotNumber = slotNumber;
        this.supportedVehicleType = requireNonNull(supportedVehicleType);
    }

    public OccupiedVehicle requireOccupiedVehicle() {
        if (occupiedVehicle == null) {
            throw new VehicleNotFoundException("Slot is not occupied by any vehicle");
        }
        return occupiedVehicle;
    }

    public void park(Vehicle vehicle) {
        assertVehicleTypeSupported(vehicle.getType());
        assertSlotIsNotOccupied();
        long startTimeMillis = System.currentTimeMillis();
        occupiedVehicle = new OccupiedVehicle(startTimeMillis, vehicle);
    }

    public long unpark() {
        long endTimeMillis = System.currentTimeMillis();
        long startTimeMillis = (occupiedVehicle == null) ? endTimeMillis : occupiedVehicle.startTimeMillis();
        occupiedVehicle = null;
        return (endTimeMillis - startTimeMillis);
    }

    public boolean isOccupied() {
        return (occupiedVehicle != null);
    }

    private void assertVehicleTypeSupported(VehicleType vehicleType) {
        if (supportedVehicleType != vehicleType) {
            throw new VehicleTypeNotSupported("Vehicle type is not supported: " + vehicleType);
        }
    }

    private void assertSlotIsNotOccupied() {
        if (isOccupied()) {
            throw new SlotNotAvailable("Slot is not available");
        }
    }

    public int getPricePerHour() {
        return supportedVehicleType.getPricePerHour();
    }

    public Receipt prepareReceipt() {
        Vehicle vehicle = requireOccupiedVehicle().vehicle();
        return new Receipt(
                slotNumber,
                floor,
                occupiedVehicle.startTimeMillis(),
                vehicle.getVehicleNumber().orElse("Not Available"),
                vehicle.getType(),
                getPricePerHour()
        );
    }

    public boolean isValidRecept(Receipt receipt) {
        String receiptHash = hashCode(receipt.floor(), receipt.slotNumber(), receipt.vehicleType());
        String slotHash = hashCode(floor, slotNumber, supportedVehicleType);
        return Objects.equals(slotHash, receiptHash);
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

    private static String hashCode(int floor, int slotNumber, VehicleType vehicleType) {
        return floor + "#" + slotNumber + "#" + vehicleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, slotNumber, supportedVehicleType);
    }
}
