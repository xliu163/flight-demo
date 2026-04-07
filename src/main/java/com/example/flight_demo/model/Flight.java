package com.example.flight_demo.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Flight {

    private String flightNumber;
    private int capacity;
    private int bookedSeats;
    private Map<String, Booking> bookings = new ConcurrentHashMap<>();

    public Flight() {}

    public Flight(String flightNumber, int capacity) {
        this.flightNumber = flightNumber;
        this.capacity = capacity;
        this.bookedSeats = 0;
    }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getBookedSeats() { return bookedSeats; }
    public void setBookedSeats(int bookedSeats) { this.bookedSeats = bookedSeats; }

    public Map<String, Booking> getBookings() { return bookings; }
    public void setBookings(Map<String, Booking> bookings) { this.bookings = bookings; }
}
