package com.example.flight_demo.model;

import java.time.LocalDateTime;

public class Booking {

    private String bookingId;
    private String flightNumber;
    private String passengerName;
    private LocalDateTime bookingTime;

    public Booking() {}

    public Booking(String bookingId, String flightNumber, String passengerName, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.bookingTime = bookingTime;
    }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
}
