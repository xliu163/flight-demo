package com.example.flight_demo.service;

import com.example.flight_demo.exception.FlightFullException;
import com.example.flight_demo.model.Flight;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test case 4: concurrent bookings must not oversell
class FlightServiceConcurrencyTest {

    @Test
    void concurrentBookings_shouldNotOversell() throws InterruptedException {
        FlightService service = new FlightService();
        service.addFlight(new Flight("TEST001", 5));

        int totalRequests = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(totalRequests);
        ExecutorService executor = Executors.newFixedThreadPool(totalRequests);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger conflictCount = new AtomicInteger();

        for (int i = 0; i < totalRequests; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    startLatch.await(); // all threads wait until released simultaneously
                    service.createBooking("TEST001", "Passenger" + idx);
                    successCount.incrementAndGet();
                } catch (FlightFullException e) {
                    conflictCount.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // release all threads at once
        doneLatch.await();
        executor.shutdown();

        assertEquals(5, successCount.get(), "Successful bookings should equal flight capacity");
        assertEquals(5, conflictCount.get(), "Remaining requests should be rejected with 409 Conflict");
    }
}
