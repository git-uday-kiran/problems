package problems.parking_lot;

import problems.parking_lot.exception.InvalidReceiptSlotNotFoundException;
import problems.parking_lot.exception.SlotNotAvailable;

import java.time.Clock;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;

public final class ParkingManager {

    private final Clock clock;
    private final EnumMap<VehicleType, Set<Slot>> slotsByType;
    private final EnumMap<VehicleType, ReentrantLock> locks;

    public ParkingManager(Clock clock) {
        this.slotsByType = new EnumMap<>(VehicleType.class);
        this.locks = new EnumMap<>(VehicleType.class);
        this.clock = requireNonNull(clock);

        for (VehicleType vehicleType : VehicleType.values()) {
            slotsByType.put(vehicleType, new HashSet<>());
            locks.put(vehicleType, new ReentrantLock());
        }
    }

    public Receipt park(Vehicle vehicle) {
        VehicleType vehicleType = requireNonNull(vehicle.getType());
        ReentrantLock locker = locks.get(vehicleType);
        locker.lock();

        try {
            Slot availableSlot = getAvailableSlot(vehicleType).orElseThrow(slotNotAvailable(vehicleType));
            availableSlot.park(vehicle, clock.millis());
            return availableSlot.prepareReceipt();
        } finally {
            locker.unlock();
        }
    }

    public long unpark(Receipt receipt) {
        VehicleType vehicleType = requireNonNull(receipt.vehicleType());
        ReentrantLock locker = locks.get(vehicleType);
        Set<Slot> slots = slotsByType.get(vehicleType);
        locker.lock();

        try {
            assertReceiptNotUsedAlready(receipt, slots);
            Slot occupiedSlot = findSlot(receipt, slots).orElseThrow(invalidReceiptSlotNotFoundForReceiptException());
            long totalParkedTimeMillis = occupiedSlot.unpark(receipt, clock.millis());
            return calculateFee(totalParkedTimeMillis, occupiedSlot.getPricePerHour());
        } finally {
            locker.unlock();
        }
    }

    private void assertReceiptNotUsedAlready(Receipt receipt, Set<Slot> slots) {
        for (Slot slot : slots) {
            slot.assertReceiptNotUsedAlready(receipt);
        }
    }

    private static Supplier<InvalidReceiptSlotNotFoundException> invalidReceiptSlotNotFoundForReceiptException() {
        return () -> new InvalidReceiptSlotNotFoundException("Invalid receipt: The slot was not found or has not been registered for the receipt");
    }

    private long calculateFee(long totalParkedTimeMillis, int pricePerHour) {
        long hours = Math.max(1, Math.ceilDiv(totalParkedTimeMillis, 3_600_000));
        return hours * pricePerHour;
    }

    private Optional<Slot> findSlot(Receipt receipt, Set<Slot> slots) {
        for (Slot slot : slots) {
            if (slot.isValidReceptToUnpark(receipt)) {
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
        ReentrantLock locker = locks.get(vehicleType);
        locker.lock();
        try {
            Set<Slot> slots = slotsByType.get(vehicleType);
            int newSlotNumber = slots.size();

            Slot newSlot = new Slot(floor, newSlotNumber, vehicleType);
            slots.add(newSlot);
            return newSlotNumber;
        } finally {
            locker.unlock();
        }
    }
}
