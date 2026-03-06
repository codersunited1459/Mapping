package org.mapping.onetomany.bidirectional.repository;

import org.mapping.onetomany.bidirectional.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.id = :id")
    Employee findEmployeeWithDepartment(Long id);
}