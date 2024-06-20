package com.onebeld.pleasantvacation.services.interfaces;

import com.onebeld.pleasantvacation.dtos.user.UserDto;
import com.onebeld.pleasantvacation.entities.User;

import java.util.Optional;

/**
 * The interface provides methods for managing user data.
 */
public interface UserService {
    /**
     * Saves the user in the database.
     *
     * @param userDto User data to be saved
     * @return {@code true} if the user was successfully saved, {@code false} otherwise
     */
    boolean saveUser(UserDto userDto);

    /**
     * Saves the tour manager in the database.
     *
     * @param userDto Tour manager data to be saved
     * @return {@code true} if the tour manager was successfully saved, {@code false} otherwise
     */
    boolean saveTourmanager(UserDto userDto);

    /**
     * Finds a user by their username.
     *
     * @param username The name of the user you want to find
     * @return {@link Optional} containing the {@link User} object if found, empty otherwise
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Retrieves user data by username.
     *
     * @param username User Name
     * @return A {@link UserDto} object containing user data
     */
    UserDto getUserByUsername(String username);
}
