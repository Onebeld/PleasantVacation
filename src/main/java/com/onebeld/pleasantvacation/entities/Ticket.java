package com.onebeld.pleasantvacation.entities;

import jakarta.persistence.*;

/**
 * Представляет собой билет на поездку.
 */
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    /**
     * Конструктор по умолчанию
     */
    public Ticket() {
    }

    /**
     * Создает новый билет с указанным пользователем и поездкой.
     *
     * @param user Пользователь, связанный с билетом.
     * @param trip Поездка, связанная с билетом.
     */
    public Ticket(User user, Trip trip) {
        this.user = user;
        this.trip = trip;
    }

    /**
     * Получает идентификатор билета.
     *
     * @return Идентификатор билета.
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор билета.
     *
     * @param id Идентификатор билета.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Получает пользователя, связанного с билетом.
     *
     * @return Пользователь
     */
    public User getUser() {
        return user;
    }

    /**
     * Устанавливает пользователя, связанного с билетом.
     *
     * @param user Пользователь
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Получает поездку, связанную с билетом.
     *
     * @return Поездка
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Устанавливает поездку, связанную с билетом.
     *
     * @param trip Поездка
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Возвращает строковое представление билета.
     *
     * @return Строковое представление билета.
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user_id=" + user.getId() +
                ", trip_id=" + trip.getId() +
                '}';
    }
}
