package com.spring.clearsolutions.service;

import com.spring.clearsolutions.domain.User;
import com.spring.clearsolutions.dto.UserSaveDto;
import com.spring.clearsolutions.dto.UserUpdateDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    int saveUser(UserSaveDto userSaveDto);

    List<User> searchByBirthDateRange(LocalDate fromDate, LocalDate toDate);

    int updateUser(int userId, UserUpdateDto userUpdateDto);

    boolean delete(int userId);
}
