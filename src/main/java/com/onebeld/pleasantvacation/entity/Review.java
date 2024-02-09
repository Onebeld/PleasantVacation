package com.onebeld.pleasantvacation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.sql.Timestamp;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "text", nullable = false, length = 5000)
    private String text;

    @Column(name = "rating", nullable = false)
    @Min(1)
    @Max(5)
    private short rating;

    protected Review() {}

    public Review(User user, Trip trip, Timestamp date, String text, short rating) {
        this.user = user;
        this.trip = trip;
        this.date = date;
        this.text = text;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user_id=" + user.getId() +
                ", trip_id=" + trip.getId() +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                '}';
    }
}
