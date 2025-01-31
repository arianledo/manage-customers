package com.arianledo.customers.controllers;

import com.arianledo.customers.entities.UserEntity;
import com.arianledo.customers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}") // Traer un usuario especifico
    public UserEntity getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    //Delete this method in the future
    @GetMapping // Traer todos los usuario
    public List<UserEntity> getAllUsers() {
        return service.getAllUsers();
    }

    @DeleteMapping("/{id}") // Eliminar un usuario
    public void removeUser(@PathVariable Long id) {
        service.removeUser(id);
    }

//    @PostMapping ("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest) {
//        return new ResponseEntity<>(userDetailsService.createUser(authCreateUserRequest), HttpStatus.CREATED);
//    }

    @PutMapping("/{id}") // Modificar usuario
    public void updateUser(@PathVariable Long id,
                               @RequestBody UserEntity updateUser) {
        service.updateUser(id, updateUser);
    }

//    @GetMapping("/search") // Busqueda
//    public List<User> searchUser(@RequestParam(name = "email", required = false) String email,
//                                         @RequestParam(name = "phone", required = false) String phone,
//                                         @RequestParam(name = "firstname", required = false) String firstname,
//                                         @RequestParam(name = "lastname", required = false) String lastname) {
//        return service.searchUser(email, phone, firstname, lastname);
//    }

}