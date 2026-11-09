package org.nishant.lld.parkinglot;

import java.util.List;
import java.util.Optional;

/** Strategy: change allocation rules without modifying ParkingLot. */
public interface SpotAllocationStrategy {
    Optional<ParkingSpot> allocate(Vehicle vehicle, List<ParkingFloor> floors);
}
