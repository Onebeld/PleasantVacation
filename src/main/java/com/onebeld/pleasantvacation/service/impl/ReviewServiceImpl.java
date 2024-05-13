package com.onebeld.pleasantvacation.service.impl;

import com.onebeld.pleasantvacation.entity.Review;
import com.onebeld.pleasantvacation.repository.ReviewRepository;
import com.onebeld.pleasantvacation.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Page<Review> findAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public Page<Review> findReviewsByTripId(long tripId, Pageable pageable) {
        return reviewRepository.findReviewsByTripId(tripId, pageable);
    }
}
