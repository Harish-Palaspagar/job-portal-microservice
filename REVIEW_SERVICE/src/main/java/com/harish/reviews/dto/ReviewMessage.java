package com.harish.reviews.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewMessage {

    private Long reviewId;
    private String reviewTitle;
    private String reviewDescription;
    private double ratings;
    private Long companyId;

}
