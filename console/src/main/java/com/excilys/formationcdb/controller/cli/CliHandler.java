package com.excilys.formationcdb.controller.cli;

import java.util.Optional;

import com.excilys.formationcdb.exception.CompanyKeyInvalidException;
import com.excilys.formationcdb.exception.CustomSQLException;
import com.excilys.formationcdb.exception.InvalidInputCLIHandlerException;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

public interface CliHandler {

	Optional<Computer> getSingleComputer(String[] inputList) throws InvalidInputCLIHandlerException, CustomSQLException;

	void createComputer(String[] inputList) throws InvalidInputCLIHandlerException, CustomSQLException, CompanyKeyInvalidException;

	void updateComputer(String[] inputList) throws InvalidInputCLIHandlerException, CustomSQLException, NothingSelectedException;

	void deleteComputer(String[] inputList) throws InvalidInputCLIHandlerException, CustomSQLException, NothingSelectedException;

	void deleteCompany(String[] inputList) throws InvalidInputCLIHandlerException;

	<T> Page<T> getPage(Page<T> page);

}
