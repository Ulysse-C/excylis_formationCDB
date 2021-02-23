package com.excilys.formationCDB.controller;

import java.util.List;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Page;
import com.excilys.formationCDB.service.CompanyService;

public final class CompanyController {
	
	private final static CompanyController INSTANCE = new CompanyController();
	private CompanyService companyService;
	
	private CompanyController() {
		companyService = CompanyService.getInstance();
	}
	
	public static CompanyController getInstance() {
		return INSTANCE;
	}
	
	
	public Page getPage(Page page) throws CustomSQLException {
		return companyService.getPage(page);
	}

	public List<Company> getCompanyList() throws CustomSQLException {
		return companyService.getCompanyList();
	}

}
