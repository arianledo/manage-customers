package com.arianledo.customers.services;

import com.arianledo.customers.entities.UserEntity;

public interface AuthService {
    UserEntity login(String username, String password);
}
