package com.harish.jobs.dto;

import com.harish.jobs.external.Company;
import com.harish.jobs.external.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobDTO {

   private long jobId;
   private String jobTitle;
   private String jobDescription;
   private String jobMinSalary;
   private String jobMaxSalary;
   private String jobLocation;
   private Company company;
   private List<Review> review;

}
