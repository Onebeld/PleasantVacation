package com.onebeld.pleasantvacation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onebeld.pleasantvacation.dtos.trip.CreateTripDto;
import com.onebeld.pleasantvacation.dtos.trip.TripDto;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Представляет информацию о поездке.
 */
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR")
    private String description;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "all_inclusive", nullable = false)
    private boolean allInclusive;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Ticket> tickets = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Image> images = new ArrayList<>();

    /**
     * Конструктор по умолчанию.
     */
    public Trip() {
    }

    // From TripDTO to Trip
    public Trip(TripDto tripDto) {
        this.name = tripDto.getName();
        this.description = tripDto.getDescription();
        this.city = tripDto.getCity();
        this.country = tripDto.getCountry();
        this.startDate = tripDto.getStartDate();
        this.endDate = tripDto.getEndDate();
        this.price = tripDto.getPrice();
        this.allInclusive = tripDto.isAllInclusive();
    }


    public Trip(CreateTripDto createTripDto) {
        this.name = createTripDto.getName();
        this.description = createTripDto.getDescription();
        this.city = createTripDto.getCity();
        this.country = createTripDto.getCountry();
        this.startDate = createTripDto.getStartDate();
        this.endDate = createTripDto.getEndDate();
        this.price = createTripDto.getPrice();
        this.allInclusive = createTripDto.isAllInclusive();
    }

    /**
     * Создает новую поездку с указанными параметрами.
     *
     * @param name         Название поездки.
     * @param description  Описание поездки.
     * @param city         Город, в котором проходит поездка.
     * @param country      Страна, в которой проходит поездка.
     * @param startDate    Дата начала поездки.
     * @param endDate      Дата окончания поездки.
     * @param price        Стоимость поездки.
     * @param allInclusive Признак того, что поездка все включено.
     */
    public Trip(String name, String description, String city, String country, Date startDate, Date endDate, double price, boolean allInclusive) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.allInclusive = allInclusive;
    }

    /**
     * Получает идентификатор поездки.
     *
     * @return Идентификатор поездки.
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор поездки.
     *
     * @param id Идентификатор для установки.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Получает название поездки.
     *
     * @return Название поездки.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название поездки.
     *
     * @param name Название для установки.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получает описание поездки.
     *
     * @return Описание поездки.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание поездки.
     *
     * @param description Описание для установки.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Получает город, в котором проходит поездка.
     *
     * @return Город.
     */
    public String getCity() {
        return city;
    }

    /**
     * Устанавливает город, в котором проходит поездка.
     *
     * @param city Город для установки.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Получает страну, в которой проходит поездка.
     *
     * @return Страна
     */
    public String getCountry() {
        return country;
    }

    /**
     * Устанавливает страну, в которой проходит поездка.
     *
     * @param country Страна для установки
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Получает дату начала поездки.
     *
     * @return Дата начала поездки.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Устанавливает дату начала поездки.
     *
     * @param startDate Дата начала для установки.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Получает дату окончания поездки.
     *
     * @return Дата окончания поездки.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Устанавливает дату окончания поездки.
     *
     * @param endDate Дата окончания для установки.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Получает стоимость поездки.
     *
     * @return Стоимость поездки.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Устанавливает стоимость поездки.
     *
     * @param price Стоимость для установки.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Получает признак того, что в поездку включены все услуги.
     *
     * @return Признак.
     */
    public boolean isAllInclusive() {
        return allInclusive;
    }

    /**
     * Устанавливает признак того, что в поездку включены все услуги.
     *
     * @param allInclusive Признак для установки.
     */
    public void setAllInclusive(boolean allInclusive) {
        this.allInclusive = allInclusive;
    }

    /**
     * Получает пользователя, создавшего поездку.
     *
     * @return Пользователь.
     */
    public User getUser() {
        return user;
    }

    /**
     * Устанавливает пользователя, создавшего поездку.
     *
     * @param user Пользователь для установки.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Получает список билетов на эту путевку
     *
     * @return Список билетов
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Устанавливает список билетов на эту путевку
     *
     * @param tickets Список билетов
     */
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * Получает список отзывов
     *
     * @return Список отзывов
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Устанавливает список отзывов
     *
     * @param reviews Список отзывов
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Получает список изображений
     *
     * @return Список изображений
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * Устанавливает список изображений
     *
     * @param images Список изображений
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * Получает строковое представление путевки
     *
     * @return Строковое представление путевки
     */
    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", allInclusive=" + allInclusive +
                '}';
    }
}
