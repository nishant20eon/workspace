package org.nishant.lld.parkinglot;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Facade / application service for entry and exit operations.
 *
 * A single lock (synchronized methods) makes allocation + occupancy + ticket
 * bookkeeping atomic for this in-memory JVM. A production multi-instance
 * deployment requires database transactions / distributed coordination.
 */
public final class ParkingLot {
    private final List<ParkingFloor> floors;
    private final SpotAllocationStrategy allocation;
    private final FeeCalculationStrategy fees;
    private final Clock clock;
    private final Map<UUID, ParkingTicket> activeTickets = new HashMap<>();
    private final Map<String, UUID> activeByPlate = new HashMap<>();
    private final Map<String, ParkingSpot> spotsById = new HashMap<>();

    public ParkingLot(List<ParkingFloor> floors, SpotAllocationStrategy allocation,
                      FeeCalculationStrategy fees, Clock clock) {
        if (floors == null || floors.isEmpty()) throw new IllegalArgumentException("Floors required");
        this.floors = List.copyOf(floors);
        this.allocation = java.util.Objects.requireNonNull(allocation);
        this.fees = java.util.Objects.requireNonNull(fees);
        this.clock = java.util.Objects.requireNonNull(clock);
        Set<Integer> floorNumbers = new HashSet<>();
        for (ParkingFloor floor : floors) {
            if (!floorNumbers.add(floor.number())) throw new IllegalArgumentException("Duplicate floor number");
            for (ParkingSpot spot : floor.spots()) {
                if (spotsById.putIfAbsent(spot.id(), spot) != null)
                    throw new IllegalArgumentException("Duplicate spot ID: " + spot.id());
                if (!spot.isAvailable()) throw new IllegalArgumentException("Spot already occupied: " + spot.id());
            }
        }
    }

    /** Reserve one compatible space and create a ticket in one critical section. */
    public synchronized ParkingTicket park(Vehicle vehicle) {
        java.util.Objects.requireNonNull(vehicle);
        if (activeByPlate.containsKey(vehicle.licensePlate()))
            throw new IllegalStateException("Vehicle already parked: " + vehicle.licensePlate());
        ParkingSpot spot = allocation.allocate(vehicle, floors)
                .orElseThrow(() -> new IllegalStateException("No compatible spot available"));
        if (spotsById.get(spot.id()) != spot || !spot.isAvailable())
            throw new IllegalStateException("Allocation strategy returned invalid spot");
        spot.occupy(vehicle.licensePlate());
        ParkingTicket ticket = new ParkingTicket(UUID.randomUUID(), vehicle,
                spot.id(), spot.floorNumber(), Instant.now(clock));
        activeTickets.put(ticket.id(), ticket);
        activeByPlate.put(vehicle.licensePlate(), ticket.id());
        return ticket;
    }

    /** Fee is calculated before freeing the spot; invalid time leaves the ticket active. */
    public synchronized ParkingReceipt exit(UUID ticketId) {
        ParkingTicket ticket = activeTickets.get(ticketId);
        if (ticket == null) throw new IllegalArgumentException("Unknown or closed ticket");
        Instant exitTime = Instant.now(clock);
        long price = fees.calculateInRupees(ticket.vehicle().type(), ticket.entryTime(), exitTime);
        spotsById.get(ticket.spotId()).release(ticket.vehicle().licensePlate());
        activeTickets.remove(ticketId);
        activeByPlate.remove(ticket.vehicle().licensePlate());
        return new ParkingReceipt(ticket.id(), ticket.vehicle().licensePlate(), exitTime, price);
    }

    public synchronized int availableSpots() {
        return (int) spotsById.values().stream().filter(ParkingSpot::isAvailable).count();
    }

    public synchronized List<ParkingTicket> activeTickets() {
        return List.copyOf(new ArrayList<>(activeTickets.values()));
    }
}
