package com.arianledo.customers.config;

import com.arianledo.customers.config.filter.JWTTokenValidator;
import com.arianledo.customers.services.UserDetailServiceImp;
import com.arianledo.customers.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(authorize -> {
            /*public*/
            /*frontend*/
            authorize.requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/scss/**",
                    "/vendor/**",
                    "/html_components/**",
                    "/",
                    "index.html",
                    "login.html",
                    "register.html",
                    "business_entity.html",
                    "customers.html").permitAll();
            //authorize.requestMatchers("/**").permitAll();

            /*backend*/
            authorize.requestMatchers("/api/auth/**").permitAll();

            /*private*/

            /*backend*/
            authorize.requestMatchers("/api/user/**").hasRole("USER");
            authorize.requestMatchers("/api/business-entity/**").hasRole("USER");
            authorize.requestMatchers("/api/customer/**").hasRole("USER");

            authorize.requestMatchers("/api/**").authenticated();
         });

        httpSecurity.addFilterBefore(new JWTTokenValidator(jwtUtils), BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImp userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
