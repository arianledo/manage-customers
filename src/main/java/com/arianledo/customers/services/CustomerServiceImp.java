package com.arianledo.customers.services;

import com.arianledo.customers.entities.Customer;
import com.arianledo.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer getCustomer(Integer id) {
        Optional<Customer> customer = repository.findById(id);
        return customer.orElse(null);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<>();

        Iterable<Customer> iterable = repository.findAll();
        iterable.forEach(result::add);
        return result;
    }

    public void removeCustomer(Integer id) {
        repository.deleteById(id);
    }

    public void addCustomer(Customer customer) {
        repository.save(customer);
    }

    public void updateCustomer(Integer id, Customer updateCustomer) {
        if(repository.existsById(id)) {
           updateCustomer.setId(id);
           repository.save(updateCustomer);
        }
    }


    public List<Customer> searchCustomer(String email, String address) {
        List<Customer> result = new ArrayList<>();

        Iterable<Customer> iterable = repository.findByEmailOrAddress(email, address);
        iterable.forEach(result::add);
        return result;
    }
}
