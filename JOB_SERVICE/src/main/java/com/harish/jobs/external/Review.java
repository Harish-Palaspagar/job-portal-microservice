package com.harish.jobs.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {

    private Long reviewId;
    private String reviewTitle;
    private String reviewDescription;
    private double ratings;

}
