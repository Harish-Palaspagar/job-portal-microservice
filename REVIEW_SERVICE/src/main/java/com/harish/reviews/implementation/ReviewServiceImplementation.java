package com.harish.reviews.implementation;


import com.harish.reviews.entity.Review;
import com.harish.reviews.repository.ReviewRepository;
import com.harish.reviews.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;


    public ReviewServiceImplementation(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review saveReview(Long companyId, Review review) {
        if(companyId != null && review != null) {
            review.setCompanyId(companyId);
            return reviewRepository.save(review);
        }
        return null;
    }

    @Override
    public Review getReviewByReviewId(Long reviewId) {
       return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Review updateReviewByReviewId( Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
           if(review != null)
           {
                 review.setReviewTitle(updatedReview.getReviewTitle());
                 review.setReviewDescription(updatedReview.getReviewDescription());
                 review.setRatings(updatedReview.getRatings());
                 review.setCompanyId(updatedReview.getCompanyId());
                 return  reviewRepository.save(review);
           }
           return null;
    }

    @Override
    public boolean deleteReviewByReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        else
        {
            return false;
        }
    }
}
