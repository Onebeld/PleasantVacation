package com.onebeld.pleasantvacation.service;

import com.onebeld.pleasantvacation.dto.user.UserDto;
import com.onebeld.pleasantvacation.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean saveUser(UserDto userDto);

    boolean saveTourmanager(UserDto userDto);

    Optional<User> findUserByUsername(String username);

    List<UserDto> findAllUsers();
}
