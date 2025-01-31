package com.arianledo.customers;

import com.arianledo.customers.entities.PermissionEntity;
import com.arianledo.customers.entities.RoleEntity;
import com.arianledo.customers.entities.RoleEnum;
import com.arianledo.customers.entities.UserEntity;
import com.arianledo.customers.repository.RoleRepository;
import com.arianledo.customers.repository.UserRepository;
import com.arianledo.customers.utils.Constants;
import com.google.common.hash.Hashing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CustomersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {
			/*Create permissions*/
			PermissionEntity writeAllPermission = PermissionEntity.builder().name("WRITE_ALL").build();

			PermissionEntity readPermission = PermissionEntity.builder().name("READ").build();

			PermissionEntity adminPermission = PermissionEntity.builder().name("ADMIN").build();

			/*Create roles*/
			RoleEntity adminRole = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN).
					permissions(Set.of(writeAllPermission, adminPermission))
					.build();

			RoleEntity invitedRole = RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED).
					permissions(Set.of(readPermission))
					.build();

			RoleEntity userRole = RoleEntity.builder()
					.roleEnum(RoleEnum.USER).
					permissions(Set.of(writeAllPermission))
					.build();

			/*Create users*/
			String hashPassword = Hashing.sha256()
					.hashString("admin" + Constants.SECRET_KEY, StandardCharsets.UTF_8)
					.toString();

			UserEntity admin = UserEntity.builder()
					.username("admin")
					.email("admin@gmail.com")
					.password(hashPassword)
					.isEnable(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.roles(Set.of(adminRole, invitedRole, userRole))
					.build();

			if(userRepository.findUserEntitiesByUsername("admin").isEmpty()) {
				userRepository.save(admin);
			}
		};
	}

}
