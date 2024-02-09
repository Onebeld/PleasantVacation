package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.dto.UserDto;
import com.onebeld.pleasantvacation.entity.User;
import com.onebeld.pleasantvacation.entity.enums.Role;
import com.onebeld.pleasantvacation.repository.UserRepository;
import com.onebeld.pleasantvacation.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User(
                userDto.getSurname(),
                userDto.getName(),
                userDto.getPatronymic(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getCity(),
                userDto.getCountry(),
                Role.USER);

        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setSurname(user.getSurname());
        userDto.setName(user.getName());
        userDto.setPatronymic(user.getPatronymic());
        userDto.setCity(user.getCity());
        userDto.setCountry(user.getCountry());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }
}
