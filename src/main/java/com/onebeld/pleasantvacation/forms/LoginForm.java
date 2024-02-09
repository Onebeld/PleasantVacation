package com.onebeld.pleasantvacation.forms;

import jakarta.persistence.Column;

public class LoginForm {
    private String surname;

    private String name;

    private String patronymic;

    private String email;

    private String password;

    private String city;

    private String country;

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

    public LoginForm(String surname,
                     String name,
                     String patronymic,
                     String email,
                     String password,
                     String city,
                     String country) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.city = city;
        this.country = country;
    }
}
