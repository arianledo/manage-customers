package com.arianledo.customers.controllers;

import com.arianledo.customers.controllers.dto.AuthResponse;
import com.arianledo.customers.entities.Customer;
import com.arianledo.customers.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/{businessEntityId}") // Traer todos los clientes
    public ResponseEntity<List<Customer>> getAllCustomers(@PathVariable Long businessEntityId) {

        return new ResponseEntity<>(service.getAllCustomers(businessEntityId), HttpStatus.OK);
    }

    @PostMapping("/{businessEntityId}") // Agregar cliente
    public ResponseEntity<Customer> addCustomer(@PathVariable Long businessEntityId, @RequestBody Customer customer) {

        return new ResponseEntity<>(service.addCustomer(businessEntityId, customer), HttpStatus.CREATED);
    }

    @PutMapping("/{businessEntityId}/{id}") // Modificar cliente
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long businessEntityId,
                                                   @PathVariable Long id,
                                                   @RequestBody Customer updateCustomer) {
        return new ResponseEntity<>(service.updateCustomer(businessEntityId, id, updateCustomer), HttpStatus.OK);
    }

    @DeleteMapping("/{businessEntityId}/{id}") // Eliminar un cliente
    public ResponseEntity<Void> removeCustomer(@PathVariable Long businessEntityId,
                                               @PathVariable Long id) {
        service.removeCustomer(businessEntityId, id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/{id}") // Traer un cliente especifico
//    public Customer getCustomer(@PathVariable Long id) {
//        return service.getCustomer(id);
//    }

//    @GetMapping("/search") // Busqueda
//    public List<Customer> searchCustomer(@RequestParam(name = "email", required = false) String email,
//                                         @RequestParam(name = "phone", required = false) String phone,
//                                         @RequestParam(name = "firstname", required = false) String firstname,
//                                         @RequestParam(name = "lastname", required = false) String lastname) {
//        return service.searchCustomer(email, phone, firstname, lastname);
//    }

}
