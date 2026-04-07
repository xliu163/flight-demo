package com.example.flight_demo.controller;

import com.example.flight_demo.model.Booking;
import com.example.flight_demo.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final FlightService flightService;

    public BookingController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Map<String, String> request) {
        String flightNumber = request.get("flightNumber");
        String passengerName = request.get("passengerName");
        Booking booking = flightService.createBooking(flightNumber, passengerName);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
}
