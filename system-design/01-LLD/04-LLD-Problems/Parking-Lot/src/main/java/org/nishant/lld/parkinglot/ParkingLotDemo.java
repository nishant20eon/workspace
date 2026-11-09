package org.nishant.lld.parkinglot;

import java.time.Clock;
import java.util.List;

/** Run with: mvn compile exec:java */
public class ParkingLotDemo {
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(List.of(
                new ParkingFloor(0, List.of(
                        new ParkingSpot("G-M1", 0, SpotType.MOTORCYCLE),
                        new ParkingSpot("G-C1", 0, SpotType.COMPACT))),
                new ParkingFloor(1, List.of(
                        new ParkingSpot("F1-C1", 1, SpotType.COMPACT),
                        new ParkingSpot("F1-L1", 1, SpotType.LARGE)))
        ), new FirstAvailableSpotStrategy(), new HourlyFeeStrategy(), Clock.systemUTC());

        ParkingTicket car = lot.park(new Vehicle("KA03AB1234", VehicleType.CAR));
        ParkingTicket bike = lot.park(new Vehicle("KA05CD5678", VehicleType.MOTORCYCLE));
        System.out.println("Car ticket: " + car.id() + " -> " + car.spotId());
        System.out.println("Bike spot: " + bike.spotId());
        System.out.println("Available after entry: " + lot.availableSpots());
        ParkingReceipt receipt = lot.exit(car.id());
        System.out.println("Car exit fee: Rs " + receipt.amountInRupees());
        System.out.println("Available after exit: " + lot.availableSpots());
    }
}
