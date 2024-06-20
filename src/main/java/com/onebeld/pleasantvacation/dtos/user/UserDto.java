package com.onebeld.pleasantvacation.dtos.user;

/**
 * Class representing user data
 */
public class UserDto {
    private String username;
    private String surname;
    private String name;
    private String patronymic;
    private String city;
    private String country;
    private String password;
    private String role;

    /**
     * Default constructor
     */
    public UserDto() {
    }

    /**
     * Constructor with parameters
     *
     * @param username   Username
     * @param surname    Last name
     * @param name       First name
     * @param patronymic Patronymic
     * @param city       City
     * @param country    Country
     * @param password   Password
     * @param role       Role
     */
    public UserDto(String username, String surname, String name, String patronymic, String city, String country, String password, String role) {
        this.username = username;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.city = city;
        this.country = country;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the username
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Specifies the username
     *
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets a last name
     *
     * @return Last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets a last name
     *
     * @param surname Last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets a name
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets a middle name
     *
     * @return Middle name
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Sets the middle name
     *
     * @param patronymic Middle name
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Gets the user's full name
     * @return Full name
     */
    public String getFullName() {
        if (surname == null)
            return surname + " " + name;
        return surname + " " + name + " " + patronymic;
    }

    /**
     * Gets the city
     *
     * @return City
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city
     *
     * @param city City
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the country
     *
     * @return Country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country
     *
     * @param country Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the password
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     *
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets a role
     *
     * @return Role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role
     *
     * @param role Role
     */
    public void setRole(String role) {
        this.role = role;
    }
}
