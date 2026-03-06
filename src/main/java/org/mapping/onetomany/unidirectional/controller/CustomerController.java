package org.mapping.onetomany.unidirectional.controller;

import org.mapping.onetomany.unidirectional.dto.CreateCustomerRequest;
import org.mapping.onetomany.unidirectional.dto.CustomerResponse;
import org.mapping.onetomany.unidirectional.dto.OrderRequest;
import org.mapping.onetomany.unidirectional.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest req) {
        return customerService.createCustomer(req);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping("/{customerId}/orders")
    public CustomerResponse addOrder(@PathVariable Long customerId, @RequestBody OrderRequest req) {
        return customerService.addOrder(customerId, req);
    }

    @DeleteMapping("/{customerId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrder(@PathVariable Long customerId, @PathVariable Long orderId) {
        customerService.removeOrder(customerId, orderId);
    }
}