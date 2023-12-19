package com.example.springwebflux.service.impl;

import com.example.springwebflux.bean.Employee;
import com.example.springwebflux.dao.EmployeeDao;
import com.example.springwebflux.dto.EmployeeDto;
import com.example.springwebflux.mapper.EmployeeMapper;
import com.example.springwebflux.service.facade.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Mono<EmployeeDto> save(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployeeMono = employeeDao.save(employee);
        return savedEmployeeMono
                .map((employeeEntity) -> employeeMapper.mapToEmployeeDto(employeeEntity));
    }

    @Override
    public Mono<EmployeeDto> findByID(String id) {
        Mono<Employee> foundedEmployeeMono = employeeDao.findById(id);
        return foundedEmployeeMono
                .map(employee -> employeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Flux<EmployeeDto> findAll() {
        Flux<Employee> employeesFlux = employeeDao.findAll();
        return employeesFlux
                .map(employee -> employeeMapper.mapToEmployeeDto(employee))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> update(EmployeeDto employeeDto) {
        Mono<EmployeeDto> foundedEmployeeMono = findByID(employeeDto.getId());
        return foundedEmployeeMono
                .flatMap((existingEmployee) -> {
                    existingEmployee.setFirstName(employeeDto.getFirstName());
                    existingEmployee.setLastName(employeeDto.getLastName());
                    existingEmployee.setEmail(employeeDto.getEmail());
                    return employeeDao.save(employeeMapper.mapToEmployee(existingEmployee));
                }).map(employee -> employeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Mono<Void> delete(String id) {
        return employeeDao.deleteById(id);
    }

}
