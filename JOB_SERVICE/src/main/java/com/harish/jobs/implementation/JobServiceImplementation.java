package com.harish.jobs.implementation;

import com.harish.jobs.dto.JobDTO;
import com.harish.jobs.entity.Job;
import com.harish.jobs.external.Company;
import com.harish.jobs.external.Review;
import com.harish.jobs.feign.CompanyClient;
import com.harish.jobs.feign.ReviewClient;
import com.harish.jobs.mapper.JobMapper;
import com.harish.jobs.repository.JobRepository;
import com.harish.jobs.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImplementation implements JobService {

    @Autowired
    private RestTemplate restTemplate;

    private final CompanyClient companyClient;

    private final JobRepository jobRepository;

    private final ReviewClient reviewClient;

    int attempt = 0; // just to check retry module

    public JobServiceImplementation(JobRepository jobRepository
            , CompanyClient companyClient
            , ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId).orElse(null);
        return convertToDTO(job);
    }

    @Override
  //  @CircuitBreaker(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
   // @Retry(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
     @RateLimiter(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> getAllJobs() {
        System.out.println("Attempt : " + ++attempt);
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();
        return jobs
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e)
    {
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    private JobDTO convertToDTO(Job job)
    {

        // we made new above
       // RestTemplate restTemplate = new RestTemplate();

         //   JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();

          //  jobWithCompanyDTO.setJob(job);

//            Company company = restTemplate
//                    .getForObject("http://COMPANY-SERVICE/companies/"+job
//                                    .getCompanyId()
//                    , Company.class);

        // we will use feign instead of rest template

        Company company = companyClient.getCompany(job.getCompanyId());

            // when there is generic type use exchange rather than object

//            ResponseEntity<List<Review>> responseEntity =  restTemplate
//                         .exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId()
//                    , HttpMethod.GET
//                    , null
//                    , new ParameterizedTypeReference<List<Review>>() {
//                    });

            // fetch review also we are using feign which are by default load balanced

            List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

          //  List<Review> reviews = responseEntity.getBody();

            JobDTO jobDTO = JobMapper
                    .mapToJobWithCompanyDTO(job,company, reviews);
            //jobDTO.setCompany(company);
            return jobDTO;

    }

    @Override
    public void deleteJobById(Long jobId) {
        jobRepository.deleteById(jobId);
    }

    @Override
    public Job updateJobById(Long jobId, Job updatedJob) {
        Job job = jobRepository.findById(jobId).orElse(null);
        System.out.println(job);
        if (job != null) {
            job.setJobDescription(updatedJob.getJobDescription());
            job.setJobLocation(updatedJob.getJobLocation());
            job.setJobMaxSalary(updatedJob.getJobMaxSalary());
            job.setJobMinSalary(updatedJob.getJobMinSalary());
            job.setJobTitle(updatedJob.getJobTitle());
            return jobRepository.save(job);
        } else {
            return null;
        }
    }

}

