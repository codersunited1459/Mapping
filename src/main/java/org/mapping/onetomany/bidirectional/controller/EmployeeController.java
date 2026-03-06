package org.mapping.onetomany.bidirectional.controller;

import org.mapping.onetomany.bidirectional.dto.EmployeeDTO;
import org.mapping.onetomany.bidirectional.dto.EmployeeRequestDTO;
import org.mapping.onetomany.bidirectional.entities.Employee;
import org.mapping.onetomany.bidirectional.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeRequestDTO dto) {
        return employeeService.createEmployee(dto);
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployee(@PathVariable Long id){

        return employeeService.getEmployee(id);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}