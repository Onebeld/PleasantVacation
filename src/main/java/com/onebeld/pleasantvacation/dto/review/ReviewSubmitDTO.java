package com.onebeld.pleasantvacation.dto.review;

public class ReviewSubmitDTO {
    private String text;

    public ReviewSubmitDTO() {

    }

    public ReviewSubmitDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
