package com.excilys.formationCDB.controller.cli;

import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Page;
import com.excilys.formationCDB.service.CompanyService;

public final class CliCompanyController {
	
	public final static String COMPANY_TABLE_NAME = "company";
	
	private final static CliCompanyController INSTANCE = new CliCompanyController();
	private CompanyService companyService;
	
	private CliCompanyController() {
		companyService = CompanyService.getInstance();
	}
	
	public static CliCompanyController getInstance() {
		return INSTANCE;
	}
	
	
	public Page getPage(Page page) throws CustomSQLException {
		return companyService.getPage(page);
	}

	public List<Optional<Company>> getCompanyList() throws CustomSQLException {
		return companyService.getCompanyList();
	}

}
