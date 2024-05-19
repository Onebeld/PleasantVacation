package com.onebeld.pleasantvacation.dto.trip;

import java.util.List;

public class TripsDto {
    private int totalPages;
    private int elementsOnPage;
    private int currentPage;
    private long totalElements;

    private List<TripReducedDto> trips;

    public TripsDto() {

    }

    public TripsDto(int totalPages, int elementsOnPage, int currentPage, long totalElements, List<TripReducedDto> trips) {
        this.totalPages = totalPages;
        this.elementsOnPage = elementsOnPage;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.trips = trips;
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

    public List<TripReducedDto> getTrips() {
        return trips;
    }

    public void setTrips(List<TripReducedDto> trips) {
        this.trips = trips;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
