package com.example.flight_demo.service;

import com.example.flight_demo.exception.FlightFullException;
import com.example.flight_demo.exception.FlightNotFoundException;
import com.example.flight_demo.model.Booking;
import com.example.flight_demo.model.Flight;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FlightService {

    private final ConcurrentHashMap<String, Flight> flights = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        flights.put("AA123", new Flight("AA123", 150));
    }

    public Booking createBooking(String flightNumber, String passengerName) {
        Flight flight = flights.get(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException(flightNumber);
        }

        synchronized (flight) {
            if (flight.getBookedSeats() >= flight.getCapacity()) {
                throw new FlightFullException(flightNumber);
            }
            Booking booking = new Booking(
                    UUID.randomUUID().toString(),
                    flightNumber,
                    passengerName,
                    LocalDateTime.now()
            );
            flight.getBookings().put(booking.getBookingId(), booking);
            flight.setBookedSeats(flight.getBookedSeats() + 1);
            return booking;
        }
    }
}
