package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.Image;
import com.onebeld.pleasantvacation.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByTrip(Trip trip);
}
