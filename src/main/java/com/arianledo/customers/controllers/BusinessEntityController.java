package com.arianledo.customers.controllers;

import com.arianledo.customers.entities.BusinessEntity;
import com.arianledo.customers.entities.Customer;
import com.arianledo.customers.services.BusinessEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business-entity")
@CrossOrigin(origins = "*")
public class BusinessEntityController {

    @Autowired
    private BusinessEntityService businessEntityService;

    @GetMapping("/{id}") // Traer todos los clientes
    public ResponseEntity<BusinessEntity> getBusinessEntityWithCustomers(@PathVariable Long id) {

        return new ResponseEntity<>(businessEntityService.getBusinessEntityWithCustomers(id), HttpStatus.OK);
    }

    @PostMapping // Agregar business
    public ResponseEntity<BusinessEntity> addBusinessEntity(@RequestBody BusinessEntity businessEntity) {

        return new ResponseEntity<>(businessEntityService.addBusinessEntity(businessEntity), HttpStatus.CREATED);
    }

}
