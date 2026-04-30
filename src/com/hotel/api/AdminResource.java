package com.hotel.api;

import com.hotel.models.Customer;
import com.hotel.models.IRoom;
import com.hotel.models.Room;
import com.hotel.models.RoomType;
import com.hotel.models.Reservation;
import com.hotel.services.CustomerService;
import com.hotel.services.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static final AdminResource INSTANCE = new AdminResource();

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {}

    public static AdminResource getInstance() {
        return INSTANCE;
    }

    // -------------------- CUSTOMERS --------------------

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // -------------------- ROOMS --------------------

    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    // -------------------- RESERVATIONS --------------------

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }

    public Collection<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
