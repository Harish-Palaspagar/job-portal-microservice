package com.harish.companies.implementation;

import com.harish.companies.clients.ReviewClient;
import com.harish.companies.dto.ReviewMessage;
import com.harish.companies.entity.Company;
import com.harish.companies.repository.CompanyRepository;
import com.harish.companies.service.CompanyService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ReviewClient reviewClient;

    public CompanyServiceImplementation(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompanyById(Long companyId, Company updatedCompany) {
        Company company =  companyRepository.findById(companyId).orElse(null);
        if(company != null)
        {
            company.setCompanyDescription(updatedCompany.getCompanyDescription());
            company.setCompanyName(updatedCompany.getCompanyName());
            companyRepository.save(company);
            return company;
        }
          return null;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompanyById(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    @Override
    public Company getCompanyByCompanyId(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {

        System.out.println(reviewMessage.getReviewDescription());

        System.out.println(reviewMessage.getCompanyId());

         Company company = companyRepository.findById(reviewMessage.getCompanyId())
              .orElseThrow(() ->
                      new NotFoundException("Company with given id not found !!" + reviewMessage
                              .getCompanyId()));

                     double averageRating  = reviewClient
                             .getAverageRatingForCompany(reviewMessage
                             .getCompanyId());

                     System.out.println(averageRating);
                     company.setRatings(averageRating);

                     companyRepository.save(company);

    }


}
