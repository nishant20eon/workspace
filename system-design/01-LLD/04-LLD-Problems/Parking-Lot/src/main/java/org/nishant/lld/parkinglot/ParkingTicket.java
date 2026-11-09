package org.nishant.lld.parkinglot;

import java.time.Instant;
import java.util.UUID;

/** Immutable receipt of entry. UUID prevents collisions across tickets. */
public record ParkingTicket(UUID id, Vehicle vehicle, String spotId,
                            int floorNumber, Instant entryTime) {}
