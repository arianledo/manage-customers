package com.arianledo.customers.services;

import com.arianledo.customers.controllers.dto.AuthCreateUserRequest;
import com.arianledo.customers.controllers.dto.AuthResponse;
import com.arianledo.customers.controllers.dto.AuthLoginRequest;
import com.arianledo.customers.entities.RoleEntity;
import com.arianledo.customers.entities.UserEntity;
import com.arianledo.customers.repository.RoleRepository;
import com.arianledo.customers.repository.UserRepository;
import com.arianledo.customers.utils.Constants;
import com.arianledo.customers.utils.JwtUtils;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntitiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())))
        );

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnable(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorities
        );
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateToken(authentication);

        return new AuthResponse(username, "Login Successful", jwtToken, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String hashPassword = Hashing.sha256()
                .hashString(password + Constants.SECRET_KEY, StandardCharsets.UTF_8)
                .toString();

        if(!userDetails.getPassword().equals(hashPassword)) {
            throw new UsernameNotFoundException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, hashPassword, userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String email = authCreateUserRequest.email();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().rolesListName();

        Set<RoleEntity> roleEntitySet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest)
                .stream()
                .collect(Collectors.toSet());

        if(roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("Role not found");
        }

        String hashPassword = Hashing.sha256()
                .hashString(password + Constants.SECRET_KEY, StandardCharsets.UTF_8)
                .toString();

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .email(email)
                .password(hashPassword)
                .isEnable(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roles(roleEntitySet)
                .build();

        UserEntity userCreated = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCreated.getRoles().forEach(
                role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())))
        );

        userCreated.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, hashPassword, authorityList);

        String accessToken = jwtUtils.generateToken(authentication);

        return new AuthResponse(username, "User Created", accessToken, true);
    }
}
