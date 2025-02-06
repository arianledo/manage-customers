package com.arianledo.customers.services;

import com.arianledo.customers.controllers.dto.MeDto;
import com.arianledo.customers.entities.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getUser(Long id);
    List<UserEntity> getAllUsers();
    void removeUser(Long id);
    void addUser(UserEntity user);
    void updateUser(Long id, UserEntity updateUser);

    MeDto getMyData(String string);

    UserEntity findByUsername(String string);
    //List<User> searchUser(String email, String phone, String firstname,String lastname);

}