package com.onebeld.pleasantvacation.service;

import com.onebeld.pleasantvacation.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<Review> findAllReviews(Pageable pageable);

    Page<Review> findReviewsByTripId(long id, Pageable pageable);
}
