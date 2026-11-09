package org.nishant.lld.parkinglot;

/** A vehicle is uniquely identified by its license plate. */
public record Vehicle(String licensePlate, VehicleType type) {
    public Vehicle {
        if (licensePlate == null || licensePlate.isBlank())
            throw new IllegalArgumentException("License plate is required");
        if (type == null) throw new IllegalArgumentException("Vehicle type is required");
        licensePlate = licensePlate.trim().toUpperCase(java.util.Locale.ROOT);
    }
}
