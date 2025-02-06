package com.arianledo.customers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    @CreationTimestamp
    private String creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_entity_id", nullable = false)
    @JsonIgnore
    private BusinessEntity businessEntity;

}
