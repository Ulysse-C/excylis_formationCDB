package com.excilys.formationcdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

public interface CompanyService {

	List<Optional<Company>> getCompanyList();

	void deleteCompanyById(int id) throws NothingSelectedException;

	<E> Page<E> getPage(Page<E> page);
}
