# Parking Lot — Low Level Design (Java + Maven)

**Interview problem:** Design a multi-floor parking lot with multiple vehicle categories, entry/exit tickets, allocation and fees. This is an in-memory demonstration, not a paid parking production backend.

## Requirements

### Functional requirements
1. Support multiple floors, each containing multiple individually identified spots.
2. Support **MOTORCYCLE**, **CAR**, and **TRUCK** vehicles.
3. Support **MOTORCYCLE**, **COMPACT**, and **LARGE** parking spots.
4. Assign a compatible **available** spot to a vehicle on entry.
5. Generate an immutable unique ticket including ID, plate, vehicle, floor, spot, and entry time.
6. Reject duplicate active parking for the same plate and reject entry when no suitable space exists.
7. Calculate fees on exit using started hourly billing; free spot and close ticket.
8. Reject an already-closed/unknown ticket, and show remaining availability.

### Assumptions / scope
- Vehicle fit: motorcycle → motorcycle/compact/large; car → compact/large; truck → large only.
- Allocation preference: smallest compatible category, then lowest floor, then lexicographic spot ID.
- Rates (examples only): motorcycle ₹10/hour, car ₹20/hour, truck ₹40/hour. Every started hour is billed, minimum one hour.
- A successful exit is treated as paid; payment integration is **out of scope**.
- Entry/exit requests are synchronous, in-process operations.
- A single application instance holds all active state in memory. A restart loses tickets and occupancy.
- License plates are normalized to uppercase. A ticket cannot be used twice to exit.
- `Clock` is injected for deterministic tests. Fee values use integer rupees (not floating point).

### Non-functional / interview constraints
- **Correctness under concurrency:** entry and exit are synchronized within one JVM so two cars never receive one spot.
- **Extensibility:** `SpotAllocationStrategy` and `FeeCalculationStrategy` are replaceable (Strategy pattern).
- **Encapsulation:** only `ParkingLot` coordinates occupancy changes; `ParkingTicket` and `ParkingReceipt` are immutable records.
- **Validation:** duplicate floor IDs and spot IDs are rejected at construction.
- **Performance:** initial allocation scans all spots O(S), where S is total number of spots; maps provide average O(1) ticket lookup.

## OO model and class responsibilities

| Class | Responsibility |
| --- | --- |
| Vehicle / VehicleType | Identifies plate and vehicle category |
| ParkingSpot / SpotType | Knows location, capacity, and occupancy |
| ParkingFloor | Owns a list of parking spaces |
| ParkingLot | Orchestrates atomic entry/exit, tickets and availability |
| SpotAllocationStrategy | Decides which compatible free spot to choose |
| FirstAvailableSpotStrategy | Preference-based available spot selection |
| FeeCalculationStrategy | Computes price from entry, exit and vehicle type |
| HourlyFeeStrategy | Demo started-hour billing policy |
| ParkingTicket / ParkingReceipt | Immutable entry/exit records |

### Flow
```
Entry: Vehicle → ParkingLot.park() → SpotAllocationStrategy → ParkingSpot.occupy() → Ticket
Exit: Ticket ID → ParkingLot.exit() → FeeCalculationStrategy → ParkingSpot.release() → Receipt
```

### Pattern choice
- **Strategy** for independent spot allocation and pricing algorithms.
- **Facade-like service** `ParkingLot` provides a simple entry/exit API to its supporting components.
- No Factory is added just for its own sake: it is unnecessary for this minimal implementation.

## Run the project

Requires **Java 17+** and **Maven 3.8+**. Open this folder (the one containing `pom.xml`) as a Maven project.

```bash
mvn clean test
mvn compile exec:java
```

Expected demo behavior: car is assigned G-C1, motorcycle is assigned G-M1, there are two spaces left after both entries, and leaving releases the car space. Fees use a one-hour minimum.

## Interview follow-ups (not yet implemented)
1. Multiple entry/exit gates and nearest-spot selection.
2. Fixed vs dynamic hourly rates; per-floor and holiday pricing.
3. EV chargers and accessible parking permissions.
4. Payment failures, refunds, idempotent exit and receipt persistence.
5. Distributed service instances, database row locking, and transactions.
6. Large lots: maintain indexed available-spot pools rather than O(S) scan.
7. Additional constraints: reserved spots, vehicle dimensions, validation of trusted plates.
8. Explicit reservations, expiry, cancellation and audit trail.
