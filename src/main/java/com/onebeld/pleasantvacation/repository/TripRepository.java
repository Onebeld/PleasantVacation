package com.onebeld.pleasantvacation.repository;

import com.onebeld.pleasantvacation.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
