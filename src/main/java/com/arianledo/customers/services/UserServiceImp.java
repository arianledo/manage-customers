package com.arianledo.customers.services;

import com.arianledo.customers.controllers.dto.MeDto;
import com.arianledo.customers.entities.UserEntity;
import com.arianledo.customers.repository.UserRepository;
import com.arianledo.customers.utils.Constants;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository repository;

    public MeDto getMyData(String username) {
        UserEntity user = repository.findByUsername(username);
        return new MeDto(user.getUsername(), user.getEmail(), user.getBusinessEntity().getName(), user.getBusinessEntity().getId());
    }

    public UserEntity getUser(Long id) {
        Optional<UserEntity> user = repository.findById(id);
        return user.orElse(null);
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> result = new ArrayList<>();

        Iterable<UserEntity> iterable = repository.findAll();
        iterable.forEach(result::add);
        return result;
    }

    public void removeUser(Long id) {
        repository.deleteById(id);
    }

    public void addUser(UserEntity user) {
        String hashPassword = Hashing.sha256()
                .hashString(user.getPassword() + Constants.SECRET_KEY, StandardCharsets.UTF_8)
                .toString();

        user.setPassword(hashPassword);
        repository.save(user);
    }

    public void updateUser(Long id, UserEntity updateUser) {
        if(repository.existsById(id)) {
            updateUser.setId(id);
            repository.save(updateUser);
        }
    }

    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username);
    }

//    public List<User> searchUser(String email, String phone, String firstname,String lastname) {
//        List<User> result = new ArrayList<>();
//
//        Iterable<User> iterable = repository.findByEmailOrPhoneOrFirstnameOrLastname(email, phone, firstname, lastname);
//        iterable.forEach(result::add);
//        return result;
//    }
}
