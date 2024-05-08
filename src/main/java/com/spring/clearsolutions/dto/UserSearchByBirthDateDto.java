package com.spring.clearsolutions.dto;

import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Jacksonized
public class UserSearchByBirthDateDto {

    private LocalDate fromDate;

    @Past(message = "To date of birth must be less than today")
    private LocalDate toDate;
}
