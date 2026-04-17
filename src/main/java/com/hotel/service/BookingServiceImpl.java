package com.hotel.service;

import com.hotel.model.Booking;
import com.hotel.model.Room;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.exception.*; // Imports all exceptions in that folder
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private RoomRepository roomRepo;

    @Override
    public Booking createBooking(String roomNumber, String guestName) {
        Room room = roomRepo.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with number: " + roomNumber));

        if (!room.isAvailable()) {
            throw new RoomAlreadyBookedException("Room " + roomNumber + " is already occupied.");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setGuestName(guestName);
        
        room.setAvailable(false);
        roomRepo.save(room);

        return bookingRepo.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
    }
}