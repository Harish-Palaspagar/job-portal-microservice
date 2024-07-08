package com.harish.jobs.mapper;

import com.harish.jobs.dto.JobDTO;
import com.harish.jobs.entity.Job;
import com.harish.jobs.external.Company;
import com.harish.jobs.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO mapToJobWithCompanyDTO(
            Job job,
            Company company,
            List<Review> reviews

    )
    {
          JobDTO jobDTO = new JobDTO();
          jobDTO.setJobId(job.getJobId());
          jobDTO.setJobTitle(job.getJobTitle());
          jobDTO.setJobLocation(job.getJobLocation());
          jobDTO.setJobMaxSalary(job.getJobMaxSalary());
          jobDTO.setJobMinSalary(job.getJobMinSalary());
          jobDTO.setJobDescription(job.getJobDescription());
          jobDTO.setCompany(company);
          jobDTO.setReview(reviews);

          return jobDTO;
    }

}
