package com.arianledo.customers.controllers;

import com.arianledo.customers.entities.Customer;
import com.arianledo.customers.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/{id}") // Traer un cliente especifico
    public Customer getCustomer(@PathVariable Integer id) {
        return service.getCustomer(id);
    }

    @GetMapping // Traer todos los clientes
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @DeleteMapping("/{id}") // Eliminar un cliente
    public void removeCustomer(@PathVariable Integer id) {
        service.removeCustomer(id);
    }

    @PostMapping // Agregar cliente
    public void addCustomer(@RequestBody Customer customer) {
        service.addCustomer(customer);
    }

    @PutMapping("/{id}") // Modificar cliente
    public void updateCustomer(@PathVariable Integer id,
                               @RequestBody Customer updateCustomer) {
        service.updateCustomer(id, updateCustomer);
    }


//    @GetMapping("/api/customer/search") // Busqueda
//    public List<Customer> searchCustomer(@RequestParam(name = "firstname", required = false) String firstname,
//                                      @RequestParam(name = "lastname", required = false) String lastname) {
//        return service.searchCustomer(firstname, lastname);
//    }

    @GetMapping("/search") // Busqueda
    public List<Customer> searchCustomer(@RequestParam(name = "email", required = false) String email,
                                         @RequestParam(name = "address", required = false) String address) {
        return service.searchCustomer(email, address);
    }

}
