package com.harish.companies.service;


import com.harish.companies.dto.ReviewMessage;
import com.harish.companies.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company updateCompanyById(Long companyId, Company updatedCompany);

    Company createCompany(Company company);

    void deleteCompanyById(Long companyId);

    Company getCompanyByCompanyId(Long companyId);

    void updateCompanyRating(ReviewMessage reviewMessage);

}
