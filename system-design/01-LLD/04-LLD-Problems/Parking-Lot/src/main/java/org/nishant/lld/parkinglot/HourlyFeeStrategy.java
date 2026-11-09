package org.nishant.lld.parkinglot;

import java.time.Duration;
import java.time.Instant;

/**
 * Charges each started hour, minimum one hour.
 * Demo rates: motorcycle ₹10/h, car ₹20/h, truck ₹40/h.
 */
public final class HourlyFeeStrategy implements FeeCalculationStrategy {
    @Override
    public long calculateInRupees(VehicleType type, Instant entry, Instant exit) {
        if (exit.isBefore(entry)) throw new IllegalArgumentException("Exit cannot precede entry");
        long seconds = Duration.between(entry, exit).getSeconds();
        long hours = Math.max(1, Math.addExact(seconds / 3600, seconds % 3600 == 0 ? 0 : 1));
        int rate = switch (type) {
            case MOTORCYCLE -> 10;
            case CAR -> 20;
            case TRUCK -> 40;
        };
        return Math.multiplyExact(hours, rate);
    }
}
