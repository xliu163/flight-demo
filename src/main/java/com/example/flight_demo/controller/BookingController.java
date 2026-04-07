package com.example.flight_demo.controller;

import com.example.flight_demo.dto.BookingRequest;
import com.example.flight_demo.model.Booking;
import com.example.flight_demo.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final FlightService flightService;

    public BookingController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest request) {
        Booking booking = flightService.createBooking(request.getFlightNumber(), request.getPassengerName());
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
}
