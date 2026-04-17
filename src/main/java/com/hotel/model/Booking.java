package com.hotel.model;
import jakarta.persistence.*;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String guestName;
    @ManyToOne
    private Room room;

    public void setRoom(Room room) { this.room = room; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
}