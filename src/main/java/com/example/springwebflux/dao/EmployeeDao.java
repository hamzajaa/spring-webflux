package com.example.springwebflux.dao;

import com.example.springwebflux.bean.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeDao extends ReactiveMongoRepository<Employee, String> {
}
