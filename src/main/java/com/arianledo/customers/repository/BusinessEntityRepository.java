package com.arianledo.customers.repository;

import com.arianledo.customers.entities.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessEntityRepository extends JpaRepository<BusinessEntity, Long> {

    @Query("SELECT b FROM BusinessEntity b LEFT JOIN FETCH b.customers WHERE b.id = :id")
    BusinessEntity findByIdWithCustomers(@Param("id") Long id);

}
