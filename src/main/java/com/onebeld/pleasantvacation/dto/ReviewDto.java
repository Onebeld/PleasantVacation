package com.onebeld.pleasantvacation.dto;

import com.onebeld.pleasantvacation.entity.Review;

import java.sql.Timestamp;

public class ReviewDto {
    private long id;

    private String firstName;

    private String lastName;

    private Timestamp date;

    private String text;

    private short rating;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.firstName = review.getUser().getName();
        this.lastName = review.getUser().getSurname();
        this.date = review.getDate();
        this.text = review.getText();
        this.rating = review.getRating();
    }

    public ReviewDto(long id, String firstName, String lastName, Timestamp date, String text, short rating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
