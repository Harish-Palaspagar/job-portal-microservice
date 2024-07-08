package com.harish.jobs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobId;
    private String jobTitle;
    private String jobDescription;
    private String jobMinSalary;
    private String jobMaxSalary;
    private String jobLocation;
    private Long companyId;

}
