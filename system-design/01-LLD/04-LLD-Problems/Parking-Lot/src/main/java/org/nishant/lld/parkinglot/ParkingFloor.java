package org.nishant.lld.parkinglot;

import java.util.List;

/** Floor owns its physical spots; consumers receive immutable list snapshots. */
public final class ParkingFloor {
    private final int number;
    private final List<ParkingSpot> spots;

    public ParkingFloor(int number, List<ParkingSpot> spots) {
        if (number < 0) throw new IllegalArgumentException("Floor number cannot be negative");
        if (spots == null || spots.isEmpty()) throw new IllegalArgumentException("At least one spot required");
        if (spots.stream().anyMatch(spot -> spot.floorNumber() != number))
            throw new IllegalArgumentException("Spot assigned to wrong floor");
        this.number = number;
        this.spots = List.copyOf(spots);
    }
    public int number() { return number; }
    public List<ParkingSpot> spots() { return spots; }
}
