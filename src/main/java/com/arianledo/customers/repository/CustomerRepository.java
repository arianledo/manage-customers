package com.arianledo.customers.repository;

import com.arianledo.customers.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

//   Search By name or lastname, such that starting with
 //   List<Customer> findByFirstnameStartingWithOrLastnameStartingWith(String firstname, String lastname);

    //HQL
    @Query("SELECT c FROM Customer c WHERE email LIKE  %:email% OR address LIKE %:address%")
    List<Customer> findByEmailOrAddress(String email, String address);
}
