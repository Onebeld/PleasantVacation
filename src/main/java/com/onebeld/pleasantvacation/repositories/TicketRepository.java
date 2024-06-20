package com.onebeld.pleasantvacation.repositories;

import com.onebeld.pleasantvacation.entities.Ticket;
import com.onebeld.pleasantvacation.entities.Trip;
import com.onebeld.pleasantvacation.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository for working with tickets.
 * This interface extends {@link JpaRepository}, which provides a default implementation
 * for most CRUD operations (basic database operations: create,
 * read, edit, and delete).
 *
 * @see JpaRepository
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    /**
     * Checks whether a ticket exists for the specified user and trip.
     *
     * @param user User
     * @param trip Trip
     * @return {@code true}, если билет существует, иначе {@code false}
     */
    boolean existsTicketByUserAndTrip(User user, Trip trip);

    /**
     * Finds the ticket page for the specified user.
     *
     * @param user     User
     * @param pageable Page Options
     * @return Ticket page
     */
    Page<Ticket> findTicketsByUser(User user, Pageable pageable);

    /**
     * Returns the number of tickets for the specified trip.
     *
     * @param trip Trip
     * @return Number of tickets
     */
    long countByTrip(Trip trip);
}
