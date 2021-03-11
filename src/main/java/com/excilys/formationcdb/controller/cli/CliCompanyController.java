package com.excilys.formationcdb.controller.cli;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Page;

public interface CliCompanyController {

	void deleteCompanyById(int parseInt) throws NothingSelectedException;

	<E> Page<E> getPage(Page<E> page);

}
