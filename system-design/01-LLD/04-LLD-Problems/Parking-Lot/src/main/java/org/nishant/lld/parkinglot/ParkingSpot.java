package org.nishant.lld.parkinglot;

import java.util.Objects;

/** One physical space. Only ParkingLot coordinates booking/release of spaces. */
public final class ParkingSpot {
    private final String id;
    private final int floorNumber;
    private final SpotType type;
    private String parkedLicense;

    public ParkingSpot(String id, int floorNumber, SpotType type) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Spot ID required");
        if (floorNumber < 0) throw new IllegalArgumentException("Floor number cannot be negative");
        this.id = id;
        this.floorNumber = floorNumber;
        this.type = Objects.requireNonNull(type);
    }
    public String id() { return id; }
    public int floorNumber() { return floorNumber; }
    public SpotType type() { return type; }
    public boolean isAvailable() { return parkedLicense == null; }

    /** Package-private: all mutations occur inside synchronized ParkingLot operations. */
    void occupy(String licensePlate) {
        if (!isAvailable()) throw new IllegalStateException("Spot occupied: " + id);
        parkedLicense = licensePlate;
    }
    void release(String licensePlate) {
        if (!Objects.equals(parkedLicense, licensePlate))
            throw new IllegalStateException("Spot not occupied by " + licensePlate);
        parkedLicense = null;
    }
}
