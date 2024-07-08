package com.harish.companies.messaging;

import com.harish.companies.dto.ReviewMessage;
import com.harish.companies.service.CompanyService;
import feign.FeignException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage);
        try {
            companyService.updateCompanyRating(reviewMessage);
        } catch (FeignException e) {
            if (e.status() == 404) {
                // Handle 404 Not Found
                System.err.println("Review service endpoint not found for company ID: " + reviewMessage.getCompanyId());
                // Take appropriate action, e.g., log the error, send a notification, etc.
            } else {
                // Handle other Feign exceptions
                throw e;
            }
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Error processing review message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
