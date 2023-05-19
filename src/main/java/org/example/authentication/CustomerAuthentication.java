package org.example.authentication;

import org.example.exceptions.CustomerNotFoundException;
import org.example.models.Customer;

public class CustomerAuthentication {

    public static Customer authenticate(String email, String password) {
        var customers = new Customer().getCustomers();

        var customerExistence = customers.stream()
            .filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password))
            .findAny();

        return customerExistence
            .orElseThrow(() -> new CustomerNotFoundException("Unable to authenticate!"));
    }
}
