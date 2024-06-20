package com.onebeld.pleasantvacation.repositories;

import com.onebeld.pleasantvacation.entities.Image;
import com.onebeld.pleasantvacation.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * A repository interface for managing {@link Image} entities.
 * This interface extends {@link JpaRepository}, which provides a default implementation
 * for most CRUD operations (basic database operations: create,
 * read, edit, and delete).
 *
 * @see JpaRepository
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
    /**
     * Returns a list of {@link Image} entities associated with this {@link Trip}.
     *
     * @param trip The {@link Trip} entity for which you want to get images
     * @return A list of {@link Image} entities associated with this {@link Trip}.
     */
    List<Image> findAllByTrip(Trip trip);
}
