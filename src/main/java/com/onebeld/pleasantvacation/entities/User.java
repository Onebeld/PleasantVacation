package com.onebeld.pleasantvacation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Представляет информацию о пользователе.
 */
@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips = new ArrayList<>();

    /**
     * Конструктор по умолчанию
     */
    public User() { }

    /**
     * Конструктор с параметрами
     * @param username Имя пользователя
     * @param surname Фамилия
     * @param name Имя
     * @param patronymic Отчество
     * @param password Пароль
     * @param city Город
     * @param country Страна
     * @param role Роль
     */
    public User(String username,
                String surname,
                String name,
                String patronymic,
                String password,
                String city,
                String country,
                Role role) {
        this.username = username;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.password = password;
        this.city = city;
        this.country = country;
        this.role = role;
    }

    /**
     * Получает идентификатор
     * @return Идентификатор
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор
     * @param id Идентификатор
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Получает фамилию
     * @return Фамилия
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Устанавливает фамилию
     * @param surname Фамилия
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Получает имя
     * @return Имя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя
     * @param name Имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получает отчество
     * @return Отчество
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Устанавливает отчество
     * @param patronymic Отчество
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Получает коллекцию ролей
     * @return Коллекция, имеющая одну роль
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    /**
     * Получает пароль
     * @return Пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Получает имя пользователя
     * @return Имя пользователя
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Устанавливает имя пользователя
     * @param username Имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Задает пароль
     * @param password Пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Получает город
     * @return Город
     */
    public String getCity() {
        return city;
    }

    /**
     * Задает город
     * @param city Город
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Получает страну
     * @return Страна
     */
    public String getCountry() {
        return country;
    }

    /**
     * Задает страну
     * @param country Страна
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Получает роль
     * @return Роль
     */
    public Role getRole() {
        return role;
    }

    /**
     * Задает роль
     * @param role Роль
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Получает список отзывов, отправленных пользователем
     * @return Список отзывов
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Задает список отзывов
     * @param reviews
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Получает список путевок, созданных пользователем
     * @return Список путевок
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Задает список путевок
     * @param trips
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Получает полное имя пользователя
     * @return Полное имя пользователя
     */
    public String getFullName() {
        if (surname == null)
            return surname + " " + name;
        return surname + " " + name + " " + patronymic;
    }

    /**
     * Получает строковое представление пользователя
     * @return Строковое представление пользователя
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", role=" + role +
                '}';
    }
}
