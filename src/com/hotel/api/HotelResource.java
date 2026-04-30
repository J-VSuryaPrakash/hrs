package com.hotel.api;

import com.hotel.models.Customer;
import com.hotel.models.IRoom;
import com.hotel.models.Reservation;
import com.hotel.services.CustomerService;
import com.hotel.services.ReservationService;

import java.util.Collection;

public class HotelResource {

    private static final HotelResource INSTANCE = new HotelResource();

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        return INSTANCE;
    }

    // -------------------- CUSTOMER --------------------

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    // -------------------- ROOM SEARCH --------------------

    public Collection<IRoom> findARoom(String checkIn, String checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    // -------------------- RESERVATION --------------------

    public Reservation bookARoom(String customerEmail, IRoom room,
                                 String checkIn, String checkOut) {

        Customer customer = customerService.getCustomer(customerEmail);

        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }

        return reservationService.reserveARoom(customer, room, checkIn, checkOut);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);

        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }

        return reservationService.getCustomersReservation(customer);
    }
}
