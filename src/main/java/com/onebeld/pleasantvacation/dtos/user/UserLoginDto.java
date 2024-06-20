package com.onebeld.pleasantvacation.dtos.user;

/**
 * Class representing user login information
 */
public class UserLoginDto {
    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public UserLoginDto() {
    }

    /**
     * Constructor with parameters.
     *
     * @param username User name.
     * @param password User Password.
     */
    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user's username.
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
