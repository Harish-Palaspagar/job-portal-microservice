package com.harish.reviews.service;


import com.harish.reviews.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);

    Review saveReview(Long companyId, Review review);

    Review getReviewByReviewId(Long reviewId);

    Review updateReviewByReviewId( Long reviewId, Review review);

    boolean deleteReviewByReviewId(Long reviewId);

}
