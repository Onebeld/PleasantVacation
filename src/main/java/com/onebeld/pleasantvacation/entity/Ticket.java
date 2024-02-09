package com.onebeld.pleasantvacation.entity;

import com.onebeld.pleasantvacation.entity.enums.TicketState;
import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TicketState state;

    protected Ticket() {}

    public Ticket(User user, Trip trip, TicketState state) {
        this.user = user;
        this.trip = trip;
        this.state = state;
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

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user_id=" + user.getId() +
                ", trip_id=" + trip.getId() +
                ", state=" + state +
                '}';
    }
}
