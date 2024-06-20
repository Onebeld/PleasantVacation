package com.onebeld.pleasantvacation.dtos.trip;

import com.onebeld.pleasantvacation.entities.Trip;
import jakarta.validation.constraints.Positive;

import java.sql.Date;

/**
 * Класс, представляющий сокращенные данные о путевке
 */
public class TripReducedDto {
    private long id;

    private String name;
    private String city;
    private String country;
    private Date startDate;
    private Date endDate;
    @Positive
    private double price;
    private String image;

    /**
     * Конструктор для создания объекта {@link TripReducedDto} на основе объекта {@link Trip}.
     *
     * @param trip Объект {@link Trip}
     */
    public TripReducedDto(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.city = trip.getCity();
        this.country = trip.getCountry();
        this.startDate = trip.getStartDate();
        this.endDate = trip.getEndDate();
        this.price = trip.getPrice();
    }

    /**
     * Конструктор с параметром.
     *
     * @param id        Идентификатор путешествия
     * @param name      Название путешествия
     * @param city      Город путешествия
     * @param country   Страна путешествия
     * @param startDate Дата начала путешествия
     * @param endDate   Дата окончания путешествия
     * @param price     Цена путешествия
     */
    public TripReducedDto(long id, String name, String city, String country, Date startDate, Date endDate, double price) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    /**
     * Получает идентификатор путешествия.
     *
     * @return Идентификатор путешествия
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор путешествия.
     *
     * @param id Идентификатор путешествия
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Получает имя путевки.
     *
     * @return Имя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя путевки.
     *
     * @param name Имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получает город.
     *
     * @return Город
     */
    public String getCity() {
        return city;
    }

    /**
     * Устанавливает город.
     *
     * @param city Город
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Получает страну.
     *
     * @return Страна
     */
    public String getCountry() {
        return country;
    }

    /**
     * Устанавливает страну.
     *
     * @param country Страна
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Получает дату начала.
     *
     * @return Дата начала
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Устанавливает дату начала.
     *
     * @param startDate Дата начала
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Получает дату окончания.
     *
     * @return Дата окончания
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Устанавливает дату окончания.
     *
     * @param endDate Дата окончания
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Получает цену.
     *
     * @return Цена
     */
    public double getPrice() {
        return price;
    }

    /**
     * Устанавливает цену.
     *
     * @param price Цена
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Получает ссылку на изображение.
     *
     * @return Ссылка на изображение
     */
    public String getImage() {
        return image;
    }

    /**
     * Устанавливает ссылку на изображение.
     *
     * @param image Ссылка на изображение
     */
    public void setImage(String image) {
        this.image = image;
    }
}
