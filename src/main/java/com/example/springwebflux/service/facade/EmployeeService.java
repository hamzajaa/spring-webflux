package com.example.springwebflux.service.facade;

import com.example.springwebflux.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<EmployeeDto> save(EmployeeDto employeeDto);
    Mono<EmployeeDto> findByID(String id);
    Flux<EmployeeDto> findAll();
    Mono<EmployeeDto> update(EmployeeDto employeeDto);
    Mono<Void> delete(String id);
}
