package com.excilys.formationcdb.controller.cli;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;
import com.excilys.formationcdb.service.CompanyService;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CliCompanyControllerImpl implements CliCompanyController {

	public static final String COMPANY_TABLE_NAME = "company";

	@Autowired
	private CompanyService companyService;

	public <E> Page<E> getPage(Page<E> page) {
		return companyService.getPage(page);
	}

	public List<Optional<Company>> getCompanyList() {
		return companyService.getCompanyList();
	}

	public void deleteCompanyById(int id) throws NothingSelectedException {
		companyService.deleteCompanyById(id);

	}

}
