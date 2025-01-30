package com.arianledo.customers.services;

import com.arianledo.customers.controllers.dto.AuthResponse;
import com.arianledo.customers.controllers.dto.AuthLoginRequest;
import com.arianledo.customers.entities.UserEntity;
import com.arianledo.customers.repository.UserRepository;
import com.arianledo.customers.utils.Constants;
import com.arianledo.customers.utils.JwtUtils;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

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
}
