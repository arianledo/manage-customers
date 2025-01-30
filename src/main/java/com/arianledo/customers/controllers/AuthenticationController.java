package com.arianledo.customers.controllers;

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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

//    @PostMapping("/auth/login")
//    public String login(@RequestBody RequestLogin requestLogin) {
//        UserEntity user = authService.login(requestLogin.getUsername(), requestLogin.getPassword());
//
//        return JwtUtils.generateToken(user);
//    }
}
