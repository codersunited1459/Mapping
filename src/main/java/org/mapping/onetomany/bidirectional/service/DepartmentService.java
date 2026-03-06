package org.mapping.onetomany.bidirectional.service;


import org.mapping.onetomany.bidirectional.dto.DepartmentDTO;
import org.mapping.onetomany.bidirectional.dto.EmployeeDTO;
import org.mapping.onetomany.bidirectional.entities.Department;
import org.mapping.onetomany.bidirectional.entities.Employee;
import org.mapping.onetomany.bidirectional.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Create Department with Employees
    public DepartmentDTO createDepartment(DepartmentDTO dto) {

        Department department = new Department();
        department.setName(dto.getName());

        if (dto.getEmployees() != null) {
            dto.getEmployees().forEach(empDTO -> {
                Employee employee = new Employee();
                employee.setName(empDTO.getName());
                employee.setSalary(empDTO.getSalary());
                department.addEmployee(employee);
            });
        }

        Department saved = departmentRepository.save(department);

        return mapToDTO(saved);
    }

    // Get Department by ID
    public DepartmentDTO getDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return mapToDTO(department);
    }

    // Mapping method
    private DepartmentDTO mapToDTO(Department department) {

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());

        dto.setEmployees(
                department.getEmployees()
                        .stream()
                        .map(emp -> {
                            EmployeeDTO e = new EmployeeDTO();
                            e.setId(emp.getId());
                            e.setName(emp.getName());
                            e.setSalary(emp.getSalary());
                            return e;
                        })
                        .collect(Collectors.toList())
        );

        return dto;
    }
}