package com.onebeld.pleasantvacation.dto.trip;

import com.onebeld.pleasantvacation.entity.Trip;
import jakarta.validation.constraints.Positive;

import java.sql.Date;
import java.util.List;

public class TripDto {
    private long id;

    private String name;
    private String description;
    private String city;
    private String country;
    private Date startDate;
    private Date endDate;
    @Positive
    private double price;
    private boolean allInclusive;
    private List<String> imageUrls;

    public TripDto() {}

    public TripDto(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.description = trip.getDescription();
        this.city = trip.getCity();
        this.country = trip.getCountry();
        this.startDate = trip.getStartDate();
        this.endDate = trip.getEndDate();
        this.price = trip.getPrice();
        this.allInclusive = trip.isAllInclusive();
    }

    public TripDto(long id, String name, String description, String city, String country, Date startDate, Date endDate, double price, boolean allInclusive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.allInclusive = allInclusive;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
