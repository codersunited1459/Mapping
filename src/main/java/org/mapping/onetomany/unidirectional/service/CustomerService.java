package org.mapping.onetomany.unidirectional.service;

import org.mapping.onetomany.unidirectional.dto.CreateCustomerRequest;
import org.mapping.onetomany.unidirectional.dto.CustomerResponse;
import org.mapping.onetomany.unidirectional.dto.OrderRequest;
import org.mapping.onetomany.unidirectional.entities.Customer;
import org.mapping.onetomany.unidirectional.entities.Order;
import org.mapping.onetomany.unidirectional.mapper.CustomerMapper;
import org.mapping.onetomany.unidirectional.repository.CustomerRepository;
import org.mapping.onetoone.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest req) {
        Customer customer = new Customer(req.getName());

        List<OrderRequest> orders = req.getOrders();
        if (orders != null) {
            for (OrderRequest o : orders) {
                customer.addOrder(new Order(o.getItemName(), o.getQuantity()));
            }
        }

        customer = customerRepository.save(customer); // saves customer + orders (cascade)
        return CustomerMapper.toResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long customerId) {
        Customer c = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
        return CustomerMapper.toResponse(c);
    }

    @Transactional
    public CustomerResponse addOrder(Long customerId, OrderRequest req) {
        Customer c = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));

        c.addOrder(new Order(req.getItemName(), req.getQuantity()));
        c = customerRepository.save(c);
        return CustomerMapper.toResponse(c);
    }

    @Transactional
    public void removeOrder(Long customerId, Long orderId) {
        Customer c = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));

        c.removeOrderById(orderId); // orphanRemoval=true -> deletes the order row
        customerRepository.save(c);
    }
}