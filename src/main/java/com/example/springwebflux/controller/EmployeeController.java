package com.example.springwebflux.controller;

import com.example.springwebflux.dto.EmployeeDto;
import com.example.springwebflux.service.facade.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        return employeeService.save(employeeDto);
    }

    @GetMapping("/id/{id}")
    public Mono<EmployeeDto> findByID(@PathVariable String id) {
        return employeeService.findByID(id);
    }

    @GetMapping("/")
    public Flux<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @PutMapping("/")
    public Mono<EmployeeDto> update(@RequestBody EmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return employeeService.delete(id);
    }
}
