package com.hotel.services;

import com.hotel.models.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService INSTANCE = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        return INSTANCE;
    }

    // Add Customer
    public void addCustomer(String email, String firstName, String lastName) {
        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Customer already exists with email: " + email);
        }

        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    // Get Customer
    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    // Get All Customers
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
