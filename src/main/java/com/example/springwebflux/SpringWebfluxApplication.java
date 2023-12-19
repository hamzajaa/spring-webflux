package com.example.springwebflux;

import com.example.springwebflux.dto.EmployeeDto;
import com.example.springwebflux.service.facade.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringWebfluxApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxApplication.class, args);
    }

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("salah");
        employeeDto.setLastName("jaa");
        employeeDto.setEmail("salah@gmail.com");
        Mono<EmployeeDto> saved = employeeService.save(employeeDto);
        System.out.println();
    }
}
