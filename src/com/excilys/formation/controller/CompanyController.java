package com.excilys.formation.controller;

import java.util.List;

import com.excilys.formation.model.Company;
import com.excilys.formation.service.CompanyService;

public class CompanyController {
	
	private CompanyService companyService;
	
	public CompanyController() {
		companyService = CompanyService.getCompanyService();
	}

	public List<Company> listCompany() {
		return companyService.listCompany();
	}

}
