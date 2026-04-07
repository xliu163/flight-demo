package com.example.flight_demo.exception;

public class FlightFullException extends RuntimeException {
    public FlightFullException(String flightNumber) {
        super("Flight " + flightNumber + " is fully booked");
    }
}
