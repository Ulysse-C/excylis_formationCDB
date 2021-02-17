package com.excilys.formationCDB.controller;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Page;
import com.excilys.formationCDB.service.CompanyService;

public class CompanyController {
	
	private CompanyService companyService;
	
	public CompanyController() {
		companyService = CompanyService.getInstance();
	}

	public Page getPage(Page page) throws CustomSQLException {
		return companyService.getPage(page);
	}

}
