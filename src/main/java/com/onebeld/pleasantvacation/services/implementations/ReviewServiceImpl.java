package com.onebeld.pleasantvacation.services.implementations;

import com.onebeld.pleasantvacation.entities.Review;
import com.onebeld.pleasantvacation.repositories.ReviewRepository;
import com.onebeld.pleasantvacation.services.interfaces.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * This class provides an implementation of the {@link ReviewService} interface.
 * He is responsible for managing reviews.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * Creates a new instance of {@link ReviewServiceImpl}.
     *
     * @param reviewRepository A feedback store used to interact with the database.
     */
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Review> findAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Review> findReviewsByTripId(long tripId, Pageable pageable) {
        return reviewRepository.findReviewsByTripId(tripId, pageable);
    }
}
