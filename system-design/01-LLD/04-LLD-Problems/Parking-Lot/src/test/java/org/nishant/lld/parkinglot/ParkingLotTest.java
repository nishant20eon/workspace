package org.nishant.lld.parkinglot;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class ParkingLotTest {
    private final Clock clock = Clock.fixed(Instant.parse("2026-11-09T10:00:00Z"), ZoneOffset.UTC);

    private ParkingLot lot(ParkingSpot... spots) {
        return new ParkingLot(List.of(new ParkingFloor(0, List.of(spots))),
                new FirstAvailableSpotStrategy(), new HourlyFeeStrategy(), clock);
    }

    @Test void allocatesCompatibleSpaceAndReleasesOnExit() {
        ParkingLot lot = lot(new ParkingSpot("M", 0, SpotType.MOTORCYCLE),
                new ParkingSpot("C", 0, SpotType.COMPACT));
        ParkingTicket car = lot.park(new Vehicle("ka123", VehicleType.CAR));
        assertEquals("C", car.spotId());
        assertEquals(1, lot.availableSpots());
        assertEquals(20, lot.exit(car.id()).amountInRupees());
        assertEquals(2, lot.availableSpots());
        assertThrows(IllegalArgumentException.class, () -> lot.exit(car.id()));
    }

    @Test void rejectsDuplicateAndNoSpace() {
        ParkingLot lot = lot(new ParkingSpot("C", 0, SpotType.COMPACT));
        lot.park(new Vehicle("KA1", VehicleType.CAR));
        assertThrows(IllegalStateException.class, () -> lot.park(new Vehicle("ka1", VehicleType.CAR)));
        assertThrows(IllegalStateException.class, () -> lot.park(new Vehicle("KA2", VehicleType.CAR)));
        assertThrows(IllegalStateException.class, () -> lot.park(new Vehicle("TRUCK", VehicleType.TRUCK)));
    }

    @Test void roundsStartedHoursAndUsesVehicleRates() {
        HourlyFeeStrategy strategy = new HourlyFeeStrategy();
        Instant entry = Instant.parse("2026-11-09T00:00:00Z");
        assertEquals(20, strategy.calculateInRupees(VehicleType.CAR, entry, entry));
        assertEquals(40, strategy.calculateInRupees(VehicleType.CAR, entry, entry.plusSeconds(3601)));
        assertEquals(40, strategy.calculateInRupees(VehicleType.TRUCK, entry, entry.plusSeconds(3600)));
        assertThrows(IllegalArgumentException.class, () -> strategy.calculateInRupees(VehicleType.CAR, entry, entry.minusSeconds(1)));
    }

    @Test void twoConcurrentCarsCannotTakeSameSpot() throws InterruptedException {
        ParkingLot lot = lot(new ParkingSpot("C", 0, SpotType.COMPACT));
        CountDownLatch ready = new CountDownLatch(2), go = new CountDownLatch(1);
        AtomicInteger successes = new AtomicInteger();
        Runnable task = () -> {
            ready.countDown();
            try {
                go.await();
                lot.park(new Vehicle(Thread.currentThread().getName(), VehicleType.CAR));
                successes.incrementAndGet();
            } catch (IllegalStateException expected) {
                // The second request correctly receives "No compatible spot available".
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        };
        Thread a = new Thread(task, "CAR-A"), b = new Thread(task, "CAR-B");
        a.start(); b.start(); ready.await(); go.countDown(); a.join(); b.join();
        assertEquals(1, successes.get());
        assertEquals(0, lot.availableSpots());
    }
}
