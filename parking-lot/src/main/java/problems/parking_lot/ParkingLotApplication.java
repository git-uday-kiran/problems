package problems.parking_lot;

import java.util.concurrent.TimeUnit;

public final class ParkingLotApplication {
    public static void main(String[] args) {
        ParkingManager manager = new ParkingManager();

        int carSlotNumber = manager.registerNewSlot(1, VehicleType.CAR);
        int autoSlotNumber = manager.registerNewSlot(1, VehicleType.AUTO);
        int bikeSlotNumber = manager.registerNewSlot(2, VehicleType.BIKE);

        System.out.println("New Car Slot Number: " + carSlotNumber);
        System.out.println("New Auto Slot Number: " + autoSlotNumber);
        System.out.println("New Bike Slot Number: " + bikeSlotNumber);
        System.out.println();

        Vehicle car = new Car();
        Vehicle auto = new Auto();
        Vehicle bike = new Bike();
        Vehicle bycycle = new Bicycle();

        Receipt carReceipt = manager.park(car);
        Receipt autoReceipt = manager.park(auto);
        Receipt bikeReceipt = manager.park(bike);

        System.out.println("Car Receipt: " + carReceipt);
        System.out.println("Auto Receipt: " + autoReceipt);
        System.out.println("Bike Receipt: " + bikeReceipt);
        System.out.println();

        throat(1);
        long carFee = manager.unpark(carReceipt);
        System.out.println("Car Fee: " + carFee);

        throat(2);
        long autoFee = manager.unpark(autoReceipt);
        System.out.println("Auto Fee: " + autoFee);

        throat(4);
        long bikeFee = manager.unpark(bikeReceipt);
        System.out.println("Bike Fee: " + bikeFee);

        manager.park(bycycle); // Throw slot not available
    }

    private static void throat(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }
}
