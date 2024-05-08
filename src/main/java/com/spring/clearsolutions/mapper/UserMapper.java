package com.spring.clearsolutions.mapper;


import com.spring.clearsolutions.domain.User;
import com.spring.clearsolutions.dto.UserSaveDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMapper {

    public static User mapUserSaveDtoToUser(UserSaveDto userSaveDto) {
        User user = new User();
        user.setAge(userSaveDto.getAge());
        user.setEmail(userSaveDto.getEmail());
        user.setFirstName(userSaveDto.getFirstName());
        user.setLastName(userSaveDto.getLastName());
        user.setBirthDate(userSaveDto.getBirthDate());
        return user;
    }

}
