package com.arianledo.customers.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;

@Data
@Entity
@Table(name = "business_entities")
public class BusinessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String address;
    private String website;
    @CreationTimestamp
    private String creationDate;

    @OneToMany(mappedBy = "businessEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Customer> customers;
}
