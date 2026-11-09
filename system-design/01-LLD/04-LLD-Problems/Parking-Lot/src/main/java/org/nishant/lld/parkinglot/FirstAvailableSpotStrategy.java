package org.nishant.lld.parkinglot;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Prefer the smallest compatible spot, then lowest floor, then spot ID.
 * Motorcycle: motorcycle > compact > large; car: compact > large; truck: large.
 */
public final class FirstAvailableSpotStrategy implements SpotAllocationStrategy {
    @Override
    public Optional<ParkingSpot> allocate(Vehicle vehicle, List<ParkingFloor> floors) {
        return floors.stream()
                .flatMap(floor -> floor.spots().stream())
                .filter(ParkingSpot::isAvailable)
                .filter(spot -> rank(vehicle.type(), spot.type()) >= 0)
                .min(Comparator.comparingInt((ParkingSpot s) -> rank(vehicle.type(), s.type()))
                        .thenComparingInt(ParkingSpot::floorNumber)
                        .thenComparing(ParkingSpot::id));
    }

    private int rank(VehicleType vehicle, SpotType spot) {
        return switch (vehicle) {
            case MOTORCYCLE -> switch (spot) {
                case MOTORCYCLE -> 0;
                case COMPACT -> 1;
                case LARGE -> 2;
            };
            case CAR -> switch (spot) {
                case MOTORCYCLE -> -1;
                case COMPACT -> 0;
                case LARGE -> 1;
            };
            case TRUCK -> spot == SpotType.LARGE ? 0 : -1;
        };
    }
}
