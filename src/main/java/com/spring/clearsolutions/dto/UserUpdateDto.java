package com.spring.clearsolutions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateDto {
    private static final String FIRST_NAME_AND_LAST_NAME_REGEX = "[A-Z][a-z]*";
    private static final String ADDRESS_REGEX = "^(\\d{1,}) [a-zA-Z0-9\\s]+(,)? [a-zA-Z]+(,)? [A-Z]{2} [0-9]{5,6}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$\" \n" +
            "+ \"|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$\" \n" +
            "+ \"|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    @JsonProperty("age")
    private String age;

    @JsonProperty("email")
    @Email(message = "Invalid email")
    private String email;

    @JsonProperty("firstName")
    @Size(min = 2, max = 30, message = "First name size must be between 2 and 30")
    @Pattern(regexp = FIRST_NAME_AND_LAST_NAME_REGEX, message = "First name isn't correct. " +
            "Must be start with uppercase letter")
    private String firstName;

    @JsonProperty("lastName")
    @Size(min = 2, max = 30, message = "Last name size must be between 2 and 30")
    @Pattern(regexp = FIRST_NAME_AND_LAST_NAME_REGEX, message = "Last name isn't correct. " +
            "Must be start with uppercase letter")
    private String lastName;

    @JsonProperty("birthDate")
    @Past(message = "date of birth must be less than today")
    private LocalDate birthDate;

    @JsonProperty("address")
    @Pattern(regexp = ADDRESS_REGEX, message = "Address is invalid")
    private String address;

    @JsonProperty("phoneNumber")
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "Phone number is incorrect")
    private String phoneNumber;
}
