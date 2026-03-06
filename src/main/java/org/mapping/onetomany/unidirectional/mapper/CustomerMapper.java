package org.mapping.onetomany.unidirectional.mapper;


import org.mapping.onetomany.unidirectional.dto.CustomerResponse;
import org.mapping.onetomany.unidirectional.dto.OrderResponse;
import org.mapping.onetomany.unidirectional.entities.Customer;
import org.mapping.onetomany.unidirectional.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    private CustomerMapper() { }

    public static CustomerResponse toResponse(Customer c) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order o : c.getOrders()) {
            orderResponses.add(new OrderResponse(o.getId(), o.getItemName(), o.getQuantity()));
        }
        return new CustomerResponse(c.getId(), c.getName(), orderResponses);
    }
}