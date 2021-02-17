package com.excilys.formation.service;

import java.util.List;

import com.excilys.formation.dao.DAOCompany;
import com.excilys.formation.model.Company;

public class CompanyService {
	
	private static CompanyService companyService;
	private DAOCompany daoCompany;
	
	private CompanyService() {
		daoCompany = DAOCompany.getDaoCompany();
	}
	
	public static CompanyService getCompanyService() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	public List<Company> listCompany() {
		return daoCompany.listCompany();
	}



	public Company getCompanyById(int id) {
		return daoCompany.getCompanyById(id);
	}

}
