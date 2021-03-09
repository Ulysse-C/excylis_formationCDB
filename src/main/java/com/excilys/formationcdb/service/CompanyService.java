package com.excilys.formationcdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.dao.DaoCompany;
import com.excilys.formationcdb.exception.CustomSQLException;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyService {
	
	@Autowired
	private DaoCompany daoCompany;

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
