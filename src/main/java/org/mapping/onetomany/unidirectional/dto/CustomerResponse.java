package org.mapping.onetomany.unidirectional.dto;

import java.util.List;

public class CustomerResponse {
    private Long id;
    private String name;
    private List<OrderResponse> orders;

    public CustomerResponse() { }

    public CustomerResponse(Long id, String name, List<OrderResponse> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<OrderResponse> getOrders() { return orders; }
    public void setOrders(List<OrderResponse> orders) { this.orders = orders; }
}