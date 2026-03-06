package org.mapping.onetomany.bidirectional.service;

import org.mapping.onetomany.bidirectional.dto.DepartmentDTO;
import org.mapping.onetomany.bidirectional.dto.EmployeeDTO;
import org.mapping.onetomany.bidirectional.dto.EmployeeRequestDTO;
import org.mapping.onetomany.bidirectional.entities.Department;
import org.mapping.onetomany.bidirectional.entities.Employee;
import org.mapping.onetomany.bidirectional.repository.DepartmentRepository;
import org.mapping.onetomany.bidirectional.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public EmployeeDTO createEmployee(EmployeeRequestDTO dto) {

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSalary(dto.getSalary());

        Department department = departmentRepository
                .findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setDepartment(department);
        //This sets the foreign key (dept_id).

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToResponseDTO(savedEmployee);
    }

    private EmployeeDTO mapToResponseDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());

        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
            dto.setDepartmentName(employee.getDepartment().getName());
        }

        return dto;
    }

    public EmployeeDTO getEmployee(Long id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return mapToDTO(employee);
    }

    private EmployeeDTO mapToDTO(Employee employee){

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());

        Department dept = employee.getDepartment();

        if(dept != null){
            DepartmentDTO deptDTO = new DepartmentDTO();
            deptDTO.setId(dept.getId());
            deptDTO.setName(dept.getName());
            dto.setDepartment(deptDTO);
        }

        return dto;
    }

    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private EmployeeDTO convertToDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());

        Department dept = employee.getDepartment();

        if (dept != null) {
            dto.setDepartmentId(dept.getId());
            dto.setDepartmentName(dept.getName());
        }

        return dto;
    }
}