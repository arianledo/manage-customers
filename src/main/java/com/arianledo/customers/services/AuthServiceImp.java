package com.arianledo.customers.services;

import com.arianledo.customers.entities.UserEntity;
import com.arianledo.customers.repository.UserRepository;
import com.arianledo.customers.utils.Constants;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity login(String username, String password) {
        String hashPassword = Hashing.sha256()
                .hashString(password + Constants.SECRET_KEY, StandardCharsets.UTF_8)
                .toString();

        List<UserEntity> users = userRepository.findByUsernameAndPassword(username, hashPassword);

        if(users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }
}
