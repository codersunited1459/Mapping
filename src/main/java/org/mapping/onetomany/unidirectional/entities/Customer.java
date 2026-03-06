package org.mapping.onetomany.unidirectional.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Uni-directional Join Column behaviour for OneToMany
    // FK will be created in orders table: orders.customer_id
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id", nullable = false)
    private List<Order> orders = new ArrayList<>();

    public Customer() { }

    public Customer(String name) {
        this.name = name;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrderById(Long orderId) {
        orders.removeIf(o -> o.getId() != null && o.getId().equals(orderId));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}