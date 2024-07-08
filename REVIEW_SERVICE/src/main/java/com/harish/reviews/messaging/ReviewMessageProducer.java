package com.harish.reviews.messaging;

import com.harish.reviews.dto.ReviewMessage;
import com.harish.reviews.entity.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;


    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review)
    {
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setReviewId(review.getReviewId());
        reviewMessage.setReviewTitle(review.getReviewTitle());
        reviewMessage.setReviewDescription(review.getReviewDescription());
        reviewMessage.setRatings(review.getRatings());
        reviewMessage.setCompanyId(review.getCompanyId());
        rabbitTemplate.convertAndSend("companyRatingQueue"
                , reviewMessage);
    }

}
