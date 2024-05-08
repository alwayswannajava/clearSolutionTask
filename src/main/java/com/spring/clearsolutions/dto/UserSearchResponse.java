package com.spring.clearsolutions.dto;

import com.spring.clearsolutions.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Jacksonized
public class UserSearchResponse {

    private List<User> searchUsers = new ArrayList<>();
}
