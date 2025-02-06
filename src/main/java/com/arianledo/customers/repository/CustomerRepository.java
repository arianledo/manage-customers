package com.arianledo.customers.repository;

import com.arianledo.customers.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
     List<Customer> findByBusinessEntityId(Long businessEntityId);

    //HQL
    @Query("SELECT c FROM Customer c WHERE email LIKE  %:email% OR phone LIKE %:phone% OR firstname LIKE %:firstname% OR lastname LIKE %:lastname%")
    List<Customer> findByEmailOrPhoneOrFirstnameOrLastname(String email, String phone, String firstname, String lastname);
}
