package com.onebeld.pleasantvacation.entities;

import jakarta.persistence.*;

/**
 * Represents an image associated with a trip.
 */
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @Column(name = "url", nullable = false, length = 5000)
    private String url;

    /**
     * Default constructor
     */
    public Image() {
    }

    /**
     * Creates a new image with the specified ID, trip, and URL.
     *
     * @param id   Image Identifier.
     * @param trip The trip associated with the image.
     * @param url  URL of the image.
     */
    public Image(long id, Trip trip, String url) {
        this.id = id;
        this.trip = trip;
        this.url = url;
    }

    /**
     * Gets the image ID.
     *
     * @return The image ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the image ID.
     *
     * @param id The new image ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the trip associated with the image.
     *
     * @return The trip.
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Sets the trip associated with the image.
     *
     * @param trip The new trip.
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Gets the image URL.
     *
     * @return The image URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the image URL.
     *
     * @param url The new URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
