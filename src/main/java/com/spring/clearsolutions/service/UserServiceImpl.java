package com.spring.clearsolutions.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.clearsolutions.domain.User;
import com.spring.clearsolutions.dto.UserSaveDto;
import com.spring.clearsolutions.dto.UserUpdateDto;
import com.spring.clearsolutions.exception.DateFromLessThanDateToException;
import com.spring.clearsolutions.exception.UserAgeLessThanMinimalException;
import com.spring.clearsolutions.exception.UserDoesntExistException;
import com.spring.clearsolutions.mapper.UserMapper;
import com.spring.clearsolutions.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public int saveUser(UserSaveDto userSaveDto) {
        log.info("Service level. Saving user: {}", userSaveDto);
        int userAge = userSaveDto.getAge();
        validateAge(userAge);
        User user = UserMapper.mapUserSaveDtoToUser(userSaveDto);
        return userRepository.saveUser(user);
    }

    @Override
    public List<User> searchByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        log.info("Service level. Searching by birthDate from: {}, to: {}", fromDate.toString(), toDate.toString());
        validateDates(fromDate, toDate);
        return userRepository.searchByBirthDateRange(fromDate, toDate);
    }

    @Override
    public int updateUser(int userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.get(userId)
                .orElseThrow(() ->
                    new UserDoesntExistException("User with id " + userId + " does not exist"));
        try {
            objectMapper.updateValue(user, userUpdateDto);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }
        return userRepository.saveUser(user);
    }

    @Override
    public boolean delete(int userId) {
        log.info("Service level. Deleting user by id: {}", userId);
        userRepository.get(userId)
                .orElseThrow(() ->
                        new UserDoesntExistException("User with id " + userId + " does not exist"));
        return userRepository.delete(userId);
    }

    private boolean validateDates(LocalDate fromDate, LocalDate toDate) {
        log.info("Service level. Validating if dates from: {} less than to: {}", fromDate, toDate);
        if (fromDate.isBefore(toDate)) {
            return true;
        } else {
            log.error("Date from is less than date to");
            throw new DateFromLessThanDateToException("Date from is less than date to");
        }
    }

    private boolean validateAge(int inputAge) {
        log.info("Service level. Validating age: {}", inputAge);
        final String MINIMAL_AGE_FOR_CREATE_USER = environment.getProperty("user.minAge");
        int propertiesAge = Integer.parseInt(MINIMAL_AGE_FOR_CREATE_USER);
        if (propertiesAge <= inputAge) {
            return true;
        } else {
            log.error("Age is less than 18");
            throw new UserAgeLessThanMinimalException("Minimal age is 18");
        }
    }
}
