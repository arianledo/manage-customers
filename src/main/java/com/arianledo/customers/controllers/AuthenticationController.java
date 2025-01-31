package com.arianledo.customers.controllers;

import com.arianledo.customers.controllers.dto.AuthCreateUserRequest;
import com.arianledo.customers.controllers.dto.AuthResponse;
import com.arianledo.customers.controllers.dto.AuthLoginRequest;
import com.arianledo.customers.services.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login (@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping ("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest) {
        return new ResponseEntity<>(userDetailService.createUser(authCreateUserRequest), HttpStatus.CREATED);
    }
}
