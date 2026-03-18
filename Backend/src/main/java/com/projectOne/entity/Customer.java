package com.projectOne.entity;

import com.projectOne.entity.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "password")
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    public Customer(Long id, String name, String email, String password,Role role) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }
}
