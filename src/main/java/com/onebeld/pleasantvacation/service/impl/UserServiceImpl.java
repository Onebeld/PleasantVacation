package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.dto.UserDto;
import com.onebeld.pleasantvacation.entity.Role;
import com.onebeld.pleasantvacation.entity.User;
import com.onebeld.pleasantvacation.repository.RoleRepository;
import com.onebeld.pleasantvacation.repository.UserRepository;
import com.onebeld.pleasantvacation.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();

        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setPatronymic(userDto.getPatronymic());
        user.setEmail(userDto.getEmail());
        user.setCity(userDto.getCity());
        user.setCountry(userDto.getCountry());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = checkRoleExists();
        }

        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
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

    private Role checkRoleExists() {
        Role role = new Role();
        role.setName("USER");

        return roleRepository.save(role);
    }
}
