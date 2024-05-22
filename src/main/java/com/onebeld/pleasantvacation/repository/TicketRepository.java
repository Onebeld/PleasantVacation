package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.Ticket;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsTicketByUserAndTrip(User user, Trip trip);

    Page<Ticket> findTicketsByUser(User user, Pageable pageable);

    long countByTrip(Trip trip);
}
