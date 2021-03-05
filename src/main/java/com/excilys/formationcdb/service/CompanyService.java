package com.excilys.formationcdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.formationcdb.dao.DAOCompany;
import com.excilys.formationcdb.exception.CustomSQLException;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

public final class CompanyService {
	
	private final static CompanyService INSTANCE = new CompanyService();
	private DAOCompany daoCompany;
	
	private CompanyService() {
		daoCompany = DAOCompany.getInstance();
	}
	
	public static CompanyService getInstance() {
		return INSTANCE;
	}


	public Optional<Company> getCompanyById(int id) {
		return daoCompany.getCompanyById(id);
	}

	public Page<Company> getPage(Page<Company> page) throws CustomSQLException {
		return daoCompany.getPage(page);
	}

	public List<Optional<Company>> getCompanyList(){
		return daoCompany.getCompanyList();
	}

	public void deleteCompanyById(int id) throws NothingSelectedException {
		daoCompany.deleteCompanyById(id);
		
	}

}
