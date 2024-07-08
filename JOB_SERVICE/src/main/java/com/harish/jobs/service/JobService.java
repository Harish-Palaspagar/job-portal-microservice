package com.harish.jobs.service;


import com.harish.jobs.dto.JobDTO;
import com.harish.jobs.entity.Job;

import java.util.List;

public interface JobService {

    Job createJob(Job job);

    JobDTO getJobById(Long jobId);

    List<JobDTO> getAllJobs();

    void deleteJobById(Long jobId);

    Job updateJobById(Long jobId, Job updatedJob);

}
