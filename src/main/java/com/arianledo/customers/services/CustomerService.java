package com.arianledo.customers.services;

import com.arianledo.customers.entities.Customer;

import java.util.List;

public interface CustomerService {

    Customer getCustomer(Long id);
    List<Customer> getAllCustomers();
    void removeCustomer(Long id);
    void addCustomer(Customer customer);
    void updateCustomer(Long id, Customer updateCustomer);
    List<Customer> searchCustomer(String email, String phone, String firstname,String lastname);

}
