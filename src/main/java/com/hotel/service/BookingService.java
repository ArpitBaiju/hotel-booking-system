package com.hotel.service;

import com.hotel.model.Booking;
import java.util.List;

public interface BookingService {
    Booking createBooking(String roomNumber, String guestName);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id); // Added this to use the exception
}