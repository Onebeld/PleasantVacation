package com.onebeld.pleasantvacation.dto.review;

import java.util.List;

public class ReviewsDto {
    private int totalPages;
    private int elementsOnPage;
    private int currentPage;
    private long totalElements;

    private List<ReviewDto> reviews;

    public ReviewsDto() {

    }

    public ReviewsDto(int totalPages, int elementsOnPage, int currentPage, long totalElements, List<ReviewDto> reviews) {
        this.totalPages = totalPages;
        this.elementsOnPage = elementsOnPage;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.reviews = reviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getElementsOnPage() {
        return elementsOnPage;
    }

    public void setElementsOnPage(int elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
