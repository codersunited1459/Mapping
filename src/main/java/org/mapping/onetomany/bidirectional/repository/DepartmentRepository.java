package org.mapping.onetomany.bidirectional.repository;

import org.mapping.onetomany.bidirectional.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}