package com.spring.clearsolutions.controller;


import com.spring.clearsolutions.domain.User;
import com.spring.clearsolutions.dto.*;
import com.spring.clearsolutions.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse addUser(@Valid @RequestBody UserSaveDto userSaveDto) {
        log.info("Controller level. Add user : {}", userSaveDto);
        userService.saveUser(userSaveDto);
        return new RestResponse("User was added successfully");
    }

    @DeleteMapping("/{userId}")
    public RestResponse deleteUser(@PathVariable int userId) {
        log.info("Controller level. Delete user by id: {}", userId);
        userService.delete(userId);
        return new RestResponse("User with id " + userId + " deleted successfully");
    }

    @PatchMapping("/{userId}")
    public RestResponse updateUser(@PathVariable int userId, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        log.info("Controller level. Update user by id: {}", userId);
        userService.updateUser(userId, userUpdateDto);
        return new RestResponse("User with id: " + userId + " was updated successfully");
    }

    @PostMapping("/search")
    public ResponseEntity<UserSearchResponse> searchUsersByBirthDateRange(@RequestBody @Valid UserSearchByBirthDateDto userSearchByBirthDateDto) {
        log.info("Controller level. Searching users by birth date from: {}, to: {}",
                userSearchByBirthDateDto.getFromDate(), userSearchByBirthDateDto.getToDate());

        LocalDate fromDate = userSearchByBirthDateDto.getFromDate();
        LocalDate toDate = userSearchByBirthDateDto.getToDate();
        List<User> searchUsers = userService.searchByBirthDateRange(fromDate, toDate);
        UserSearchResponse userSearchResponse = new UserSearchResponse();
        userSearchResponse.setSearchUsers(searchUsers);
        return ResponseEntity.ok().body(userSearchResponse);
    }

}
