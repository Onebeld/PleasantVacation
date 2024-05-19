package com.onebeld.pleasantvacation.entity;

import com.onebeld.pleasantvacation.dto.trip.CreateTripDto;
import com.onebeld.pleasantvacation.dto.trip.TripDto;
import com.onebeld.pleasantvacation.entity.enums.TripState;
import jakarta.persistence.*;

import java.sql.Date;

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

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TripState state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Trip() { }

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

    public Trip(String name, String description, String city, String country, Date startDate, Date endDate, double price, boolean allInclusive, TripState state) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.allInclusive = allInclusive;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAllInclusive() {
        return allInclusive;
    }

    public void setAllInclusive(boolean allInclusive) {
        this.allInclusive = allInclusive;
    }

    public TripState getState() {
        return state;
    }

    public void setState(TripState state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
                ", state=" + state +
                '}';
    }
}
