package com.onebeld.pleasantvacation.dto;

public class UserDto {
    private String surname;
    private String name;
    private String patronymic;
    private String city;
    private String country;
    private String email;
    private String password;

    public UserDto() {

    }

    public UserDto(String surname, String name, String patronymic, String city, String country, String email, String password) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.city = city;
        this.country = country;
        this.email = email;
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
