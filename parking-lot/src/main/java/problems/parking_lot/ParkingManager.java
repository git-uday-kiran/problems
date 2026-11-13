package problems.parking_lot;

import problems.parking_lot.exception.InvalidReceiptSlotNotFoundException;
import problems.parking_lot.exception.SlotNotAvailable;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;

public final class ParkingManager {

    private final Map<VehicleType, Set<Slot>> slotsByType;
    private final Map<VehicleType, ReentrantLock> locks;

    public ParkingManager() {
        this.slotsByType = new ConcurrentHashMap<>();
        this.locks = new ConcurrentHashMap<>();

        for (VehicleType vehicleType : VehicleType.values()) {
            slotsByType.put(vehicleType, new HashSet<>());
            locks.put(vehicleType, new ReentrantLock());
        }
    }

    public Receipt park(Vehicle vehicle) {
        VehicleType vehicleType = requireNonNull(vehicle.getType());
        ReentrantLock locker = locks.get(vehicleType);

        try {
            locker.lock();
            Slot availableSlot = getAvailableSlot(vehicleType).orElseThrow(slotNotAvailable(vehicleType));
            availableSlot.park(vehicle);
            return availableSlot.prepareReceipt();
        } finally {
            locker.unlock();
        }
    }

    public long unpark(Receipt receipt) {
        VehicleType vehicleType = requireNonNull(receipt.vehicleType());
        ReentrantLock locker = locks.get(vehicleType);
        try {
            locker.lock();
            Slot occupiedSlot = findSlot(receipt).orElseThrow(invalidReceiptSlotNotFoundException());
            long totalParkedTimeMillis = occupiedSlot.unpark();
            return calculateFee(totalParkedTimeMillis, occupiedSlot.getPricePerHour());
        } finally {
            locker.unlock();
        }
    }


    private static Supplier<InvalidReceiptSlotNotFoundException> invalidReceiptSlotNotFoundException() {
        return () -> new InvalidReceiptSlotNotFoundException("Invalid receipt: The slot was not found or has not been registered.");
    }

    private long calculateFee(long totalParkedTimeMillis, int pricePerHour) {
        long seconds = totalParkedTimeMillis / 1000;
        long hours = (long) Math.ceil(seconds / 3600.00);
        return pricePerHour * hours;
    }

    private Optional<Slot> findSlot(Receipt receipt) {
        VehicleType vehicleType = receipt.vehicleType();
        Set<Slot> slots = slotsByType.get(vehicleType);
        for (Slot slot : slots) {
            if (slot.isValidRecept(receipt)) {
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }

    private static Supplier<SlotNotAvailable> slotNotAvailable(VehicleType vehicleType) {
        return () -> new SlotNotAvailable("Slot is currently not available for " + vehicleType);
    }

    private Optional<Slot> getAvailableSlot(VehicleType vehicleType) {
        return slotsByType.get(vehicleType).stream()
                .filter(not(Slot::isOccupied))
                .findAny();
    }

    public int registerNewSlot(int floor, VehicleType vehicleType) {
        Set<Slot> slots = slotsByType.get(vehicleType);
        int newSlotNumber = slots.size();

        Slot newSlot = new Slot(floor, newSlotNumber, vehicleType);
        slots.add(newSlot);
        return newSlotNumber;
    }
}
