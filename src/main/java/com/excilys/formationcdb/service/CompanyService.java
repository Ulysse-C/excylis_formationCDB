package com.excilys.formationcdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.formationcdb.dao.CompanyDao;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

@Service
@Scope("singleton")
public class CompanyService {

	private CompanyDao daoCompany;

	public CompanyService(CompanyDao daoCompany) {
		this.daoCompany = daoCompany;
	}

	public Page getPage(Page page) {
		return daoCompany.getPage(page);
	}

	public List<Company> getCompanyList() {
		return daoCompany.getCompanyList();
	}

	public void deleteCompanyById(int id) throws NothingSelectedException {
		daoCompany.deleteCompanyById(id);
	}

}
