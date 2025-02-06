package com.arianledo.customers.services;

import com.arianledo.customers.entities.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers(Long businessEntityId);
    Customer addCustomer(Long businessEntityId, Customer customer);
    Customer updateCustomer(Long businessEntityId, Long id, Customer updateCustomer);
    void removeCustomer(Long businessEntityId, Long id);
    Customer getCustomer(Long id);
    List<Customer> searchCustomer(String email, String phone, String firstname,String lastname);

}
