package com.spring.clearsolutions.domain;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {
    private int id;
    private int age;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    @Override
    public String toString() {
        return "User: " +
                "id = " + id +
                ", age = " + age +
                ", email = '" + email + '\'' +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", birthDate = " + birthDate +
                ", address = '" + address + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'';
    }
}
