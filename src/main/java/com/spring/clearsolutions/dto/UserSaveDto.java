package com.spring.clearsolutions.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Builder
@Jacksonized
public class UserSaveDto {
    private static final String FIRST_NAME_AND_LAST_NAME_REGEX = "[A-Z][a-z]*";
    private static final String ADDRESS_REGEX = "^(\\d{1,}) [a-zA-Z0-9\\s]+(,)? [a-zA-Z]+(,)? [A-Z]{2} [0-9]{5,6}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$\" \n" +
            "+ \"|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$\" \n" +
            "+ \"|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    @NotNull(message = "Age must be not null")
    private int age;

    @Email(message = "Invalid email")
    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not blank")
    private String email;

    @Size(min = 2, max = 30, message = "First name size must be between 2 and 30")
    @NotNull(message = "First name is null. Try another one")
    @NotBlank(message = "First name is blank. Try another one")
    @Pattern(regexp = FIRST_NAME_AND_LAST_NAME_REGEX, message = "First name isn't correct. " +
            "Must be start with uppercase letter")
    private String firstName;

    @Size(min = 2, max = 30, message = "Last name size must be between 2 and 30")
    @NotNull(message = "Last name is null. Try another one")
    @NotBlank(message = "Last name is blank. Try another one")
    @Pattern(regexp = FIRST_NAME_AND_LAST_NAME_REGEX, message = "Last name isn't correct. " +
            "Must be start with uppercase letter")
    private String lastName;

    @Past(message = "date of birth must be less than today")
    @NotNull(message = "Birth date must be not null")
    private LocalDate birthDate;

    @Pattern(regexp = ADDRESS_REGEX, message = "Address is invalid")
    private String address;

    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "Phone number is incorrect")
    private String phoneNumber;

}
