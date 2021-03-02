package com.excilys.formationCDB.service;

import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.dao.DAOCompany;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Page;

public final class CompanyService {
	
	private final static CompanyService INSTANCE = new CompanyService();
	private DAOCompany daoCompany;
	
	private CompanyService() {
		daoCompany = DAOCompany.getInstance();
	}
	
	public static CompanyService getInstance() {
		return INSTANCE;
	}


	public Optional<Company> getCompanyById(int id) throws CustomSQLException {
		return daoCompany.getCompanyById(id);
	}

	public Page getPage(Page page) throws CustomSQLException {
		return daoCompany.getPage(page);
	}

	public List<Optional<Company>> getCompanyList() throws CustomSQLException {
		return daoCompany.getCompanyList();
	}

}
