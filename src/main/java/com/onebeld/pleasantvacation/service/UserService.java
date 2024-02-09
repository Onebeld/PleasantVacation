package com.onebeld.pleasantvacation.service;

import com.onebeld.pleasantvacation.dto.UserDto;
import com.onebeld.pleasantvacation.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);

    Optional<User> findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
