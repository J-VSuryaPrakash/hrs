package com.hotel.services;

import com.hotel.models.*;
import com.hotel.util.ValidateDate;

import java.util.*;

public class ReservationService {

    private static final ReservationService INSTANCE = new ReservationService();

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<IRoom, List<Reservation>> reservations = new HashMap<>();

    private String lastSearchCheckIn;
    private String lastSearchCheckOut;

    private ReservationService() {}

    public static ReservationService getInstance() {
        return INSTANCE;
    }

    // -------------------- ROOM METHODS --------------------

    public void addRoom(IRoom room) {
        if (room == null || room.getRoomNumber() == null || room.getRoomNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }

        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room with no " + room.getRoomNumber() + " already exists");
        }

        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    // -------------------- RESERVATION METHODS --------------------

    public Reservation reserveARoom(Customer customer, IRoom room,
                                    String checkInDate, String checkOutDate) {

        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("Room is not available for selected dates");
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        reservations.computeIfAbsent(room, k -> new ArrayList<>()).add(reservation);

        return reservation;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> result = new ArrayList<>();

        for (List<Reservation> reservationList : reservations.values()) {
            for (Reservation r : reservationList) {
                if (r.getCustomer().equals(customer)) {
                    result.add(r);
                }
            }
        }

        return result;
    }

    public void printAllReservation() {
        for (List<Reservation> reservationList : reservations.values()) {
            for (Reservation r : reservationList) {
                System.out.println(r);
            }
        }
    }

    public Collection<Reservation> getAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();

        for (List<Reservation> reservationList : reservations.values()) {
            allReservations.addAll(reservationList);
        }

        return allReservations;
    }

    public String getLastSearchCheckIn() {
        return lastSearchCheckIn;
    }

    public String getLastSearchCheckOut() {
        return lastSearchCheckOut;
    }

    // -------------------- FIND ROOMS --------------------

    public Collection<IRoom> findRooms(String checkInDate, String checkOutDate) {

        this.lastSearchCheckIn = checkInDate;
        this.lastSearchCheckOut = checkOutDate;

        List<IRoom> availableRooms = new ArrayList<>();

        for (IRoom room : rooms.values()) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            return findRecommendedRooms(checkInDate, checkOutDate);
        }

        return availableRooms;
    }

    // -------------------- HELPER METHODS --------------------

    private boolean isRoomAvailable(IRoom room, String checkIn, String checkOut) {

        List<Reservation> roomReservations = reservations.get(room);

        if (roomReservations == null) {
            return true;
        }

        for (Reservation r : roomReservations) {
            if (datesOverlap(checkIn, checkOut, r.getCheckInDate(), r.getCheckOutDate())) {
                return false;
            }
        }

        return true;
    }

    private boolean datesOverlap(String start1, String end1, String start2, String end2) {
        return !(ValidateDate.compareDates(end1, start2) < 0 || ValidateDate.compareDates(start1, end2) > 0);
    }

    private Collection<IRoom> findRecommendedRooms(String checkInDate, String checkOutDate) {

        String newCheckIn = ValidateDate.addDays(checkInDate, 7);
        String newCheckOut = ValidateDate.addDays(checkOutDate, 7);

        this.lastSearchCheckIn = newCheckIn;
        this.lastSearchCheckOut = newCheckOut;

        List<IRoom> recommended = new ArrayList<>();

        for (IRoom room : rooms.values()) {
            if (isRoomAvailable(room, newCheckIn, newCheckOut)) {
                recommended.add(room);
            }
        }

        return recommended;
    }
}
