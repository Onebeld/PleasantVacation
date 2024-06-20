package com.onebeld.pleasantvacation.dtos.trip;

import com.onebeld.pleasantvacation.entities.Trip;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

/**
 * A class representing the data to create a trip.
 */
public class CreateTripDto {
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
    private List<MultipartFile> images;

    /**
     * Default Constructor.
     */
    public CreateTripDto() {
    }


    /**
     * Constructor to create a {@link CreateTripDto} object based on a {@link Trip} object.
     *
     * @param trip Object {@link Trip}
     */
    public CreateTripDto(Trip trip) {
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

    /**
     * Constructor with parameter.
     *
     * @param id           Trip ID
     * @param name         Trip Name
     * @param description  Trip Description
     * @param city         Travel City
     * @param country      Travel Country
     * @param startDate    Trip Start Date
     * @param endDate      Trip end date
     * @param price        Trip Price
     * @param allInclusive All inclusive
     */
    public CreateTripDto(long id, String name, String description, String city, String country, Date startDate, Date endDate, double price, boolean allInclusive) {
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

    /**
     * Gets the trip identifier.
     *
     * @return Trip ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the trip identifier.
     *
     * @param id Trip ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the voucher.
     *
     * @return Имя
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the trip.
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Receives a description of the voucher.
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the voucher.
     *
     * @param description Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the city.
     *
     * @return City
     */
    public String getCity() {
        return city;
    }

    /**
     * Establishes the city.
     *
     * @param city City
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the country.
     *
     * @return Country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the start date.
     *
     * @return Start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate Start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return End date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate End date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets a price.
     *
     * @return Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price Price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets whether the voucher includes all services
     *
     * @return {@code true} - if included, otherwise {@code false}
     */
    public boolean isAllInclusive() {
        return allInclusive;
    }

    /**
     * Establishes whether the voucher includes all services.
     *
     * @param allInclusive Does the voucher include all services
     */
    public void setAllInclusive(boolean allInclusive) {
        this.allInclusive = allInclusive;
    }

    /**
     * Gets the images.
     *
     * @return Images
     */
    public List<MultipartFile> getImages() {
        return images;
    }

    /**
     * Sets the images.
     *
     * @param images Images
     */
    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
