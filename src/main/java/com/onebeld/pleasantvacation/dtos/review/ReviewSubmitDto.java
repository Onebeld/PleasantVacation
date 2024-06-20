package com.onebeld.pleasantvacation.dtos.review;

/**
 * A class representing the data to send the feedback.
 */
public class ReviewSubmitDto {
    private String text;

    /**
     * Default Constructor.
     */
    public ReviewSubmitDto() {
    }

    /**
     * Constructor with parameter.
     *
     * @param text Testimonial Text
     */
    public ReviewSubmitDto(String text) {
        this.text = text;
    }

    /**
     * Receives the text of the review.
     *
     * @return Testimonial Text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the review.
     *
     * @param text Testimonial Text
     */
    public void setText(String text) {
        this.text = text;
    }
}
