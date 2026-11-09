package org.nishant.lld.parkinglot;

import java.time.Instant;

/** Strategy: fee calculation can vary by vehicle, duration, or rate policy. */
public interface FeeCalculationStrategy {
    long calculateInRupees(VehicleType type, Instant entry, Instant exit);
}
