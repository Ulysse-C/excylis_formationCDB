package com.excilys.formationcdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.formationcdb.dao.CompanyDao;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

@Service
@Scope("singleton")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao daoCompany;

	public Optional<Company> getCompanyById(int id) {
		return daoCompany.getCompanyById(id);
	}

	public Page getPage(Page page) {
		return daoCompany.getPage(page);
	}

	public List<Optional<Company>> getCompanyList() {
		return daoCompany.getCompanyList();
	}

	public void deleteCompanyById(int id) throws NothingSelectedException {
		daoCompany.deleteCompanyById(id);

	}

}
