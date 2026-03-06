package org.mapping.onetomany.unidirectional.dto;

import java.util.List;

public class CreateCustomerRequest {
    private String name;
    private List<OrderRequest> orders; // optional

    public CreateCustomerRequest() { }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<OrderRequest> getOrders() { return orders; }
    public void setOrders(List<OrderRequest> orders) { this.orders = orders; }
}