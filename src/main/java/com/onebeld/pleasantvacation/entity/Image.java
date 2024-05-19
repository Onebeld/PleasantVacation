package com.onebeld.pleasantvacation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @Column(name = "url", nullable = false, length = 5000)
    private String url;

    public Image() { }

    public Image(long id, Trip trip, String url) {
        this.id = id;
        this.trip = trip;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
