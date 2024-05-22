package com.onebeld.pleasantvacation.dto.review;

import com.onebeld.pleasantvacation.entity.Review;
import com.onebeld.pleasantvacation.entity.User;

import java.sql.Timestamp;

public class ReviewDto {
    private long id;

    private User user;

    private Timestamp date;

    private String text;

    private short rating;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.user = review.getUser();
        this.date = review.getDate();
        this.text = review.getText();
    }

    public ReviewDto(long id, User user, Timestamp date, String text, short rating) {
        this.id = id;
        this.user = user;
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
