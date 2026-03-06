package org.mapping.onetomany.bidirectional.controller;

import org.mapping.onetomany.bidirectional.dto.DepartmentDTO;
import org.mapping.onetomany.bidirectional.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Create Department
    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO dto) {
        return departmentService.createDepartment(dto);
    }

    // Get Department
    @GetMapping("/{id}")
    public DepartmentDTO getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }
}