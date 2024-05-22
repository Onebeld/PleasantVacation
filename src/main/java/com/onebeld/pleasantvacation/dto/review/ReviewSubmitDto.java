package com.onebeld.pleasantvacation.dto.review;

public class ReviewSubmitDto {
    private String text;

    public ReviewSubmitDto() {

    }

    public ReviewSubmitDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
