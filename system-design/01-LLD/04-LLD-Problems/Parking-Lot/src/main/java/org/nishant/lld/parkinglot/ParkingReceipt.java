package org.nishant.lld.parkinglot;

import java.time.Instant;
import java.util.UUID;

/** Exit result: amount is in whole rupees for this educational project. */
public record ParkingReceipt(UUID ticketId, String licensePlate,
                             Instant exitTime, long amountInRupees) {}
