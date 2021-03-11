package com.excilys.formationcdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

public interface CompanyDao {
	void deleteCompanyById(int id) throws NothingSelectedException;

	Optional<Company> getCompanyById(int id);

	List<Optional<Company>> getCompanyList();

	Page<Company> getPage(Page<Company> page);
}
