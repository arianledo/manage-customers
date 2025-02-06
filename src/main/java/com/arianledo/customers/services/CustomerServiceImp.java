package com.arianledo.customers.services;

import com.arianledo.customers.entities.BusinessEntity;
import com.arianledo.customers.entities.Customer;
import com.arianledo.customers.repository.BusinessEntityRepository;
import com.arianledo.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private BusinessEntityRepository businessEntityRepository;

    @Transactional
    public Customer addCustomer(Long businessEntityId, Customer customer) {
        BusinessEntity businessEntity = businessEntityRepository.findById(businessEntityId)
                .orElseThrow(() -> new RuntimeException("Business Entity not found"));
        customer.setBusinessEntity(businessEntity);

        return repository.save(customer);
    }

    public List<Customer> getAllCustomers(Long businessEntityId) {
        List<Customer> result = new ArrayList<>();

        Iterable<Customer> iterable = repository.findByBusinessEntityId(businessEntityId);
        iterable.forEach(result::add);
        return result;
    }

    public Customer updateCustomer(Long businessEntityId, Long id, Customer updateCustomer) {
        BusinessEntity businessEntity = businessEntityRepository.findById(businessEntityId)
                .orElseThrow(() -> new RuntimeException("Business Entity not found"));
        updateCustomer.setBusinessEntity(businessEntity);

        if(repository.existsById(id)) {
            updateCustomer.setId(id);
            return repository.save(updateCustomer);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    public void removeCustomer(Long businessEntityId, Long id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        repository.deleteById(id);
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> customer = repository.findById(id);
        return customer.orElse(null);
    }

    public List<Customer> searchCustomer(String email, String phone, String firstname,String lastname) {
        List<Customer> result = new ArrayList<>();

        Iterable<Customer> iterable = repository.findByEmailOrPhoneOrFirstnameOrLastname(email, phone, firstname, lastname);
        iterable.forEach(result::add);
        return result;
    }
}
