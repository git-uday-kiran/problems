package problems.parking_lot;

import lombok.Getter;
import problems.parking_lot.exception.InvalidReceiptException;
import problems.parking_lot.exception.SlotException;
import problems.parking_lot.exception.VehicleTypeNotSupported;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Getter
public final class Slot {

    private final int floor;
    private final int slotNumber;
    private final VehicleType supportedVehicleType;
    private final Set<String> usedReceipts;

    private OccupiedVehicle occupiedVehicle;

    public Slot(int floor, int slotNumber, VehicleType supportedVehicleType) {
        this.floor = floor;
        this.slotNumber = slotNumber;
        this.supportedVehicleType = requireNonNull(supportedVehicleType);
        this.usedReceipts = new HashSet<>();
    }

    public synchronized void park(Vehicle vehicle, long nowMillis) {
        assertVehicleTypeSupported(vehicle.getType());
        assertSlotIsNotOccupied();
        occupiedVehicle = new OccupiedVehicle(nowMillis, vehicle);
    }

    public synchronized long unpark(Receipt receipt, long nowMillis) {
        assertSlotIsOccupied();
        assertReceiptNotUsedAlready(receipt);
        assertValidReceptToUnpark(receipt);
        long startTimeMillis = occupiedVehicle.startTimeMillis();
        occupiedVehicle = null;
        usedReceipts.add(generateReceiptHash(receipt));
        return (nowMillis - startTimeMillis);
    }

    public void assertReceiptNotUsedAlready(Receipt receipt) {
        if (isAlreadyUsedReceipt(receipt)) {
            throw new InvalidReceiptException("Receipt is already used");
        }
    }

    private void assertValidReceptToUnpark(Receipt receipt) {
        if (!isValidReceptToUnpark(receipt)) {
            throw new InvalidReceiptException("Receipt is not valid to unpark");
        }
    }

    public boolean isOccupied() {
        return (occupiedVehicle != null);
    }

    private void assertSlotIsOccupied() {
        if (!isOccupied()) {
            throw new SlotException("Slot is not occupied by any vehicle");
        }
    }

    private void assertVehicleTypeSupported(VehicleType vehicleType) {
        if (supportedVehicleType != vehicleType) {
            throw new VehicleTypeNotSupported("Vehicle type is not supported: " + vehicleType);
        }
    }

    private void assertSlotIsNotOccupied() {
        if (isOccupied()) {
            throw new SlotException("Slot is not available");
        }
    }

    public int getPricePerHour() {
        return supportedVehicleType.getPricePerHour();
    }

    public Receipt prepareReceipt() {
        assertSlotIsOccupied();
        Vehicle vehicle = occupiedVehicle.vehicle();
        return new Receipt(
                slotNumber,
                floor,
                occupiedVehicle.startTimeMillis(),
                vehicle.getVehicleNumber().orElse("Not Available"),
                vehicle.getType(),
                getPricePerHour()
        );
    }

    public boolean isValidReceptToUnpark(Receipt receipt) {
        return isOccupied()
                && receipt.floor() == floor
                && receipt.slotNumber() == slotNumber
                && supportedVehicleType == receipt.vehicleType()
                && occupiedVehicle.startTimeMillis() == receipt.startingTimeMillis();
    }

    public boolean isAlreadyUsedReceipt(Receipt receipt) {
        String receiptHash = generateReceiptHash(receipt);
        return usedReceipts.contains(receiptHash);
    }

    private static String generateReceiptHash(Receipt receipt) {
        return hashCode(receipt.floor(), receipt.slotNumber(), receipt.vehicleType(), receipt.startingTimeMillis());
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

    private static String hashCode(int floor, int slotNumber, VehicleType vehicleType, long startTimeMillis) {
        return floor + "#" + slotNumber + "#" + vehicleType + "#" + startTimeMillis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, slotNumber, supportedVehicleType);
    }
}
