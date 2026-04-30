package com.hotel.models;

import com.hotel.util.ValidateDate;

import java.util.Objects;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final String checkInDate;
    private final String checkOutDate;

    public Reservation(Customer customer, IRoom room, String checkInDate, String checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (ValidateDate.compareDates(checkOutDate, checkInDate) <= 0) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getFirstName() + " " + customer.getLastName() +
            " | Email: " + customer.getEmail() +
            " | Room: " + room.getRoomNumber() +
            " | Check-In: " + checkInDate +
            " | Check-Out: " + checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) &&
                Objects.equals(room, that.room) &&
                Objects.equals(checkInDate, that.checkInDate) &&
                Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
