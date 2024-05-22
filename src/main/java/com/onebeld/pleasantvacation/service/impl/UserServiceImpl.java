package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.dto.user.UserDto;
import com.onebeld.pleasantvacation.entity.User;
import com.onebeld.pleasantvacation.repository.RoleRepository;
import com.onebeld.pleasantvacation.repository.UserRepository;
import com.onebeld.pleasantvacation.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean saveUser(UserDto userDto) {
        Optional<User> userFromDb = userRepository.findByUsername(userDto.getUsername());

        if (userFromDb.isPresent()) {
            return false;
        }

        User user = mapToUser(userDto);
        user.setRole(roleRepository.findByName("USER"));

        userRepository.save(user);
        return true;
    }

    @Override
    public boolean saveTourmanager(UserDto userDto) {
        Optional<User> userFromDb = userRepository.findByUsername(userDto.getUsername());

        if (userFromDb.isPresent()) {
            return false;
        }

        User user = mapToUser(userDto);
        user.setRole(roleRepository.findByName("TOURMANAGER"));

        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return mapToUserDto(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), getAuthorities(user.get()));
    }

    private UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getSurname(),
                user.getName(),
                user.getPatronymic(),
                user.getCity(),
                user.getCountry(),
                user.getPassword(),
                user.getRole().getName()
        );
    }

    private User mapToUser(UserDto userDto) {
        return new User(
                userDto.getUsername(),
                userDto.getSurname(),
                userDto.getName(),
                userDto.getPatronymic(),
                bCryptPasswordEncoder.encode(userDto.getPassword()),
                userDto.getCity(),
                userDto.getCountry(),
                roleRepository.findByName(userDto.getRole()));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = new String[] { user.getRole().getName() };
        return AuthorityUtils.createAuthorityList(userRoles);
    }
}
