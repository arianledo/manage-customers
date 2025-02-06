package com.arianledo.customers.repository;

import com.arianledo.customers.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntitiesByUsername(String username);
    //HQL
    @Query("SELECT u FROM UserEntity u WHERE username = :username AND password = :password")
    List<UserEntity> findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    UserEntity findByUsername(String username);
}