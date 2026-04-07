package com.example.flight_demo.controller;

import com.example.flight_demo.exception.FlightNotFoundException;
import com.example.flight_demo.model.Booking;
import com.example.flight_demo.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    // Test case 1: valid booking success (201)
    @Test
    void createBooking_validRequest_returns201() throws Exception {
        Booking booking = new Booking("booking-uuid", "AA123", "Xin", LocalDateTime.now());
        when(flightService.createBooking("AA123", "Xin")).thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightNumber\":\"AA123\",\"passengerName\":\"Xin\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value("booking-uuid"))
                .andExpect(jsonPath("$.flightNumber").value("AA123"))
                .andExpect(jsonPath("$.passengerName").value("Xin"));
    }

    // Test case 2: invalid request — blank flightNumber (400)
    @Test
    void createBooking_blankFlightNumber_returns400() throws Exception {
        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightNumber\":\"\",\"passengerName\":\"Xin\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(containsString("Invalid request")));
    }

    // Test case 3: flight not found (404)
    @Test
    void createBooking_flightNotFound_returns404() throws Exception {
        when(flightService.createBooking("XX999", "Xin")).thenThrow(new FlightNotFoundException("XX999"));

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightNumber\":\"XX999\",\"passengerName\":\"Xin\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(containsString("XX999")));
    }
}
