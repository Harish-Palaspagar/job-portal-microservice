package com.harish.jobs.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // to make it load balanced we are going to use this
    // otherwise rest template will not fetch the service by project name

    @LoadBalanced
    @Bean // telling spring to manage this object
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
