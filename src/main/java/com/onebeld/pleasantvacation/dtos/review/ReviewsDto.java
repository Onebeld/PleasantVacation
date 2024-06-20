package com.onebeld.pleasantvacation.dtos.review;

import java.util.List;

/**
 * A class representing information about the testimonials page.
 */
public class ReviewsDto {
    private int totalPages;
    private int elementsOnPage;
    private int currentPage;
    private long totalElements;

    private List<ReviewDto> reviews;

    /**
     * Default Constructor.
     */
    public ReviewsDto() {
    }

    /**
     * Constructor to create an object {@link ReviewDto}
     *
     * @param totalPages     Total number of pages
     * @param elementsOnPage Number of items per page
     * @param currentPage    Current page
     * @param totalElements  Total number of elements
     * @param reviews        List of reviews
     */
    public ReviewsDto(int totalPages, int elementsOnPage, int currentPage, long totalElements, List<ReviewDto> reviews) {
        this.totalPages = totalPages;
        this.elementsOnPage = elementsOnPage;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.reviews = reviews;
    }

    /**
     * Gets the total number of pages.
     *
     * @return Total number of pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the total number of pages.
     *
     * @param totalPages Total number of pages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Gets the number of elements on the page.
     *
     * @return Number of elements on the page
     */
    public int getElementsOnPage() {
        return elementsOnPage;
    }

    /**
     * Sets the number of elements on the page.
     *
     * @param elementsOnPage Number of elements on the page
     */
    public void setElementsOnPage(int elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    /**
     * Gets the current page.
     *
     * @return Current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the current page.
     *
     * @param currentPage Current page
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Gets the total number of elements.
     *
     * @return Total number of elements
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Sets the total number of elements.
     *
     * @param totalElements Total number of elements
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Gets a list of reviews.
     *
     * @return List of references
     */
    public List<ReviewDto> getReviews() {
        return reviews;
    }

    /**
     * Sets up a list of reviews.
     *
     * @param reviews List of references
     */
    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
