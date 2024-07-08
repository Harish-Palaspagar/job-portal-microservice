package com.harish.jobs.controller;

import com.harish.jobs.dto.JobDTO;
import com.harish.jobs.entity.Job;
import com.harish.jobs.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job)
    {
        try {
            Job savedJob = jobService.createJob(job);
            return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs()
    {
        try {
            List<JobDTO> jobs = jobService.getAllJobs();
            if (jobs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId)
    {
        try {
            JobDTO job = jobService.getJobById(jobId);
            if (job != null) {
                return new ResponseEntity<>(job, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJobById(@PathVariable Long jobId)
    {
        try {
            JobDTO job = jobService.getJobById(jobId);
            if (job != null) {
                jobService.deleteJobById(jobId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<Job> updateJoById(@PathVariable Long jobId,@RequestBody Job updatedJob)
    {
        try {
            Job job = jobService.updateJobById(jobId, updatedJob);
            if (job != null) {
                return new ResponseEntity<>(job, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
