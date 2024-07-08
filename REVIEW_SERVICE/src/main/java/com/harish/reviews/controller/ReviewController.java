package com.harish.reviews.controller;

import com.harish.reviews.entity.Review;
import com.harish.reviews.messaging.ReviewMessageProducer;
import com.harish.reviews.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMessageProducer reviewMessageProducer;


    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId)
    {
       return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Review> saveReview(@RequestBody Review review, @RequestParam Long companyId)
    {
        try {
            Review savedReview = reviewService.saveReview(companyId ,review);
            reviewMessageProducer.sendMessage(savedReview);
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewByReviewId(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewByReviewId(reviewId);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReviewId(@PathVariable Long reviewId, @RequestBody Review review) {
        Review existingReview = reviewService.getReviewByReviewId(reviewId);
        if (existingReview == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Review updatedReview = reviewService.updateReviewByReviewId(reviewId, review);
        return ResponseEntity.ok(updatedReview);
    }



    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewById( @PathVariable Long reviewId) {
        boolean deleted = reviewService.deleteReviewByReviewId( reviewId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/averageRatings")
    public Double getAverageReview(@RequestParam Long companyId)
    {
        List<Review> reviewsList = reviewService.getAllReviews(companyId);
        return reviewsList
                .stream()
                .mapToDouble(Review::getRatings)
                .average()
                .orElse(0.0);
    }



}
