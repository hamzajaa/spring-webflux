package com.example.springwebflux;

import com.example.springwebflux.dao.EmployeeDao;
import com.example.springwebflux.dto.EmployeeDto;
import com.example.springwebflux.service.facade.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeDao employeeDao;

    @BeforeEach
    public void before() {
        System.out.println("Before Each Test");
        employeeDao.deleteAll().subscribe();
    }

    @Test
    public void testSaveEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("hamza");
        employeeDto.setLastName("jaa");
        employeeDto.setEmail("hamza@gmail.com");

        webTestClient.post().uri("/api/v1/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void testFindEmployeeById() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("salah");
        employeeDto.setLastName("jaa");
        employeeDto.setEmail("salah@gmail.com");

        EmployeeDto savedEmployee = employeeService.save(employeeDto).block();

        webTestClient.get().uri("/api/v1/id/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(savedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(savedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
    }

    @Test
    public void testFindAllEmployees() {

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("omar");
        employeeDto1.setLastName("jaa");
        employeeDto1.setEmail("omar@gmail.com");

        employeeService.save(employeeDto1).block();

        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setFirstName("yassine");
        employeeDto2.setLastName("jaa");
        employeeDto2.setEmail("yassine@gmail.com");

        employeeService.save(employeeDto2).block();

        webTestClient.get().uri("/api/v1/employees/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("ayoub");
        employeeDto.setLastName("ben");
        employeeDto.setEmail("ayoub@gmail.com");

        EmployeeDto savedEmployee = employeeService.save(employeeDto).block();

        EmployeeDto updateEmployee = new EmployeeDto();
        assert savedEmployee != null;
        employeeDto.setId(savedEmployee.getId());
        updateEmployee.setFirstName("othmane");
        updateEmployee.setLastName("elkh");
        updateEmployee.setEmail("othmane@gmail.com");

        webTestClient.put().uri("/api/v1/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updateEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updateEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updateEmployee.getEmail());

    }

    @Test
    public void testDeleteEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("ayoub");
        employeeDto.setLastName("ben");
        employeeDto.setEmail("ayoub@gmail.com");

        EmployeeDto savedEmployee = employeeService.save(employeeDto).block();

        webTestClient.delete().uri("/api/v1/employees/")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
