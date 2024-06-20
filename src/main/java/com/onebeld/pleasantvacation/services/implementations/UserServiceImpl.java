package com.onebeld.pleasantvacation.services.implementations;

import com.onebeld.pleasantvacation.dtos.user.UserDto;
import com.onebeld.pleasantvacation.entities.User;
import com.onebeld.pleasantvacation.repositories.RoleRepository;
import com.onebeld.pleasantvacation.repositories.UserRepository;
import com.onebeld.pleasantvacation.services.interfaces.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Service for working with users.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor for initializing dependencies.
     *
     * @param userRepository        User Repository
     * @param roleRepository        Role Repository
     * @param bCryptPasswordEncoder Password Encoder
     */
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return mapToUserDto(user.get());
    }

    /**
     * {@inheritDoc}
     */
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
        String[] userRoles = new String[]{user.getRole().getName()};
        return AuthorityUtils.createAuthorityList(userRoles);
    }
}
