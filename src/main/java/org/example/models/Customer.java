package org.example.models;

import java.util.List;

public class Customer extends User{

    int customerId;
    String customerName;
    int accountBalance;

    List<Customer> customers;

    public List<Customer> getCustomers() {
        return  List.of(
            new Customer("test_user_1@gmail.com", "test_user_1", 1, "test_user_1", 1000),
            new Customer("test_user_2@gmail.com", "test_user_2", 2, "test_user_2", 1500),
            new Customer("test_user_3@example.com", "test_user_3", 3, "test_user_3", 2000)
        );
    }

    public Customer() {

    }

    public Customer(String email, String password, int customerId, String customerName, int accountBalance) {
        this.email = email;
        this.password = password.concat("@");
        this.customerId = customerId;
        this.customerName = customerName;
        this.accountBalance = accountBalance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
}
