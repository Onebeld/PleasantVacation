package com.onebeld.pleasantvacation.dtos.trip;

import java.util.List;

/**
 * Class representing trip data
 */
public class TripsDto {
    private int totalPages;
    private int elementsOnPage;
    private int currentPage;
    private long totalElements;

    private List<TripReducedDto> trips;

    /**
     * Default constructor
     */
    public TripsDto() {

    }

    /**
     * Constructor with parameters.
     *
     * @param totalPages     Number of pages
     * @param elementsOnPage Number of elements on the page
     * @param currentPage    Current page
     * @param totalElements  Total number of elements
     * @param trips          List of trips
     */
    public TripsDto(int totalPages, int elementsOnPage, int currentPage, long totalElements, List<TripReducedDto> trips) {
        this.totalPages = totalPages;
        this.elementsOnPage = elementsOnPage;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.trips = trips;
    }

    /**
     * Gets the number of pages.
     *
     * @return Number of pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the number of pages.
     *
     * @param totalPages Number of pages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Gets the number of elements on the page.
     *
     * @return Number of elements per page
     */
    public int getElementsOnPage() {
        return elementsOnPage;
    }

    /**
     * Sets the number of elements on the page.
     *
     * @param elementsOnPage Number of elements per page
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
     * Gets a list of trips.
     *
     * @return List of trips
     */
    public List<TripReducedDto> getTrips() {
        return trips;
    }

    /**
     * Sets a list of trips.
     *
     * @param trips List of trips
     */
    public void setTrips(List<TripReducedDto> trips) {
        this.trips = trips;
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
}
