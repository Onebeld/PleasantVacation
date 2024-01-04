package com.onebeld.pleasantvacation.service;

import com.onebeld.pleasantvacation.dto.UserDto;
import com.onebeld.pleasantvacation.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
