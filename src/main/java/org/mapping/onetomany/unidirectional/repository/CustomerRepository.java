package org.mapping.onetomany.unidirectional.repository;

import org.mapping.onetomany.unidirectional.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}