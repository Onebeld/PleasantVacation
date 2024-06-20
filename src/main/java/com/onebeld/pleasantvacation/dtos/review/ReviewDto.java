package com.onebeld.pleasantvacation.dtos.review;

import com.onebeld.pleasantvacation.entities.Review;
import com.onebeld.pleasantvacation.entities.User;

import java.sql.Timestamp;

/**
 * A class to represent the recall data.
 */
public class ReviewDto {
    private long id;

    private User user;

    private Timestamp date;

    private String text;

    /**
     * Constructor to create a {@link ReviewDto} object based on a {@link Review} object.
     *
     * @param review Object {@link Review}
     */
    public ReviewDto(Review review) {
        this.id = review.getId();
        this.user = review.getUser();
        this.date = review.getDate();
        this.text = review.getText();
    }

    /**
     * Constructor to create a {@link ReviewDto} object with the given parameters.
     *
     * @param id   Review ID
     * @param user User
     * @param date Date of Review
     * @param text Review Text
     */
    public ReviewDto(long id, User user, Timestamp date, String text) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.text = text;
    }

    /**
     * Returns the identifier of the review.
     *
     * @return Review ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the recall identifier.
     *
     * @param id Review ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the user who left the review.
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
     * Returns the date of review.
     *
     * @return Date of Review
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * Sets the review date.
     *
     * @param date Date of Review
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * Returns the text of the review.
     *
     * @return Review Text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the review.
     *
     * @param text Review Text
     */
    public void setText(String text) {
        this.text = text;
    }
}
