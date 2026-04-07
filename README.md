# flight-demo

A Spring Boot REST API for flight ticket booking.

## Requirements

- Java 17
- Maven (or use the included `mvnw` wrapper)

## How to Run

```bash
./mvnw spring-boot:run
```

The service starts on `http://localhost:8080`.

## How to Test

```bash
./mvnw test
```

## API

### POST /api/bookings

Book a seat on a flight.

**Request**

```json
{
  "flightNumber": "AA123",
  "passengerName": "Xin"
}
```

**Responses**

| Status | Condition |
|--------|-----------|
| 201 Created | Booking successful |
| 400 Bad Request | Missing or blank field |
| 404 Not Found | Flight does not exist |
| 409 Conflict | Flight is fully booked |

---

**201 Created**

```json
{
  "bookingId": "e3d1c2b0-...",
  "flightNumber": "AA123",
  "passengerName": "Xin",
  "bookingTime": "2026-04-07T10:00:00"
}
```

**400 Bad Request**

```json
{ "error": "Invalid request: passengerName is required" }
```

**404 Not Found**

```json
{ "error": "Flight not found: XX999" }
```

**409 Conflict**

```json
{ "error": "Flight AA123 is fully booked" }
```

## Example curl Commands

```bash
# Successful booking
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"flightNumber":"AA123","passengerName":"Xin"}'

# Missing field (400)
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"flightNumber":"AA123"}'

# Flight not found (404)
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"flightNumber":"XX999","passengerName":"Xin"}'
```

## Notes

- Flight data is stored in-memory and resets on restart.
- Pre-loaded flight: `AA123` with capacity 150.
- Booking is thread-safe via `synchronized` on the flight object.
