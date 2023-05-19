package org.example.services;

import org.example.models.Customer;

public class BalanceService {

    public static int getBalanceForCustomer(int customerId) {
        var customers = new Customer().getCustomers();
        return customers.stream()
                .filter(c -> c.getCustomerId() == customerId)
                .map(Customer::getAccountBalance)
                .findAny().get();
    }
}
