package com.onebeld.pleasantvacation.services.interfaces;

import com.onebeld.pleasantvacation.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс, который предоставляет функциональность для работы с отзывами.
 */
public interface ReviewService {
    /**
     * Сохраняет отзыв.
     *
     * @param review отзыв для сохранения
     */
    void saveReview(Review review);

    /**
     * Находит все отзывы.
     *
     * @param pageable Настройки пагинации
     * @return Страница отзывов
     */
    Page<Review> findAllReviews(Pageable pageable);

    /**
     * Находит отзывы для указанной путевки.
     *
     * @param id       Идентификатор путевки
     * @param pageable Настройки пагинации
     * @return Страница отзывов
     */
    Page<Review> findReviewsByTripId(long id, Pageable pageable);
}
