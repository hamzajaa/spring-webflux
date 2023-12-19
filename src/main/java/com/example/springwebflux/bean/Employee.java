package com.example.springwebflux.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "employees")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
