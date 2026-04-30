package com.hotel.models;

import java.util.Objects;

public class Customer {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String NAME_REGEX = "^[A-Za-z]+$";

    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        if (!isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name format");
        }

        if (!isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name format");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    private boolean isValidName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + " | Email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
