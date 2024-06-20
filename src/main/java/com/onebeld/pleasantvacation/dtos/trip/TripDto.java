package com.onebeld.pleasantvacation.dtos.trip;

import com.onebeld.pleasantvacation.entities.Trip;
import jakarta.validation.constraints.Positive;

import java.sql.Date;
import java.util.List;

/**
 * Class representing travel data
 */
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

    /**
     * Default Constructor.
     */
    public TripDto() {
    }

    /**
     * Constructor to create a {@link TripDto} object based on a {@link Trip} object.
     *
     * @param trip Object {@link Trip}
     */
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

    /**
     * Constructor with parameter.
     *
     * @param id           Trip ID
     * @param name         Trip Name
     * @param description  Trip Description
     * @param city         Trip City
     * @param country      Trip Country
     * @param startDate    Trip Start Date
     * @param endDate      Trip end date
     * @param price        Trip Price
     * @param allInclusive All inclusive
     */
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

    /**
     * Gets the trip identifier.
     *
     * @return Travel ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the travel ID.
     *
     * @param id Travel ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the trip.
     *
     * @return Name
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
     * Gets a description of the trip.
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the trip.
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
     * Sets the city.
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
     * @return Start Date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate Start Date
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
     * Gets the price.
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
     * Gets whether all services are included in the package
     *
     * @return {@code true} - if included, otherwise {@code false}
     */
    public boolean isAllInclusive() {
        return allInclusive;
    }

    /**
     * Determines whether the package includes all services.
     *
     * @param allInclusive Are all services included in the package
     */
    public void setAllInclusive(boolean allInclusive) {
        this.allInclusive = allInclusive;
    }

    /**
     * Gets links to images.
     *
     * @return Image links
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     * Sets links to images.
     *
     * @param imageUrls Image links
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
