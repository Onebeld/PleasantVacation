package com.onebeld.pleasantvacation.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * Presents a review of the trip.
 */
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "text", nullable = false, length = 5000)
    private String text;

    /**
     * Default Constructor.
     */
    public Review() {
    }

    /**
     * Creates a new review with the specified user, trip, date, and text.
     *
     * @param user The user who left the review.
     * @param trip The trip to which the review refers.
     * @param date Date of the review.
     * @param text Text of the review.
     */
    public Review(User user, Trip trip, Timestamp date, String text, short rating) {
        this.user = user;
        this.trip = trip;
        this.date = date;
        this.text = text;
    }

    /**
     * Gets the recall identifier.
     *
     * @return Recall ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the recall identifier.
     *
     * @param id ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the user who left the review.
     *
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who left the review.
     *
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the trip to which the review pertains.
     *
     * @return Trip
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Sets the trip to which the review applies.
     *
     * @param trip Trip
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Gets the date of review.
     *
     * @return Date
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * Sets the review date.
     *
     * @param date Date
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * Receives the text of the review.
     *
     * @return Text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the review.
     *
     * @param text Text
     */
    public void setText(String text) {
        this.text = text;
    }
}
