package com.excilys.formationcdb.controller.cli;

import java.util.Optional;

import com.excilys.formationcdb.exception.CompanyKeyInvalidException;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

public interface CliComputerController {

	Optional<Computer> getComputerById(int parseInt);

	void createComputer(Computer computer) throws CompanyKeyInvalidException;

	void updateComputer(Computer computer) throws NothingSelectedException;

	void deleteComputerById(int parseInt) throws NothingSelectedException;

	<E> Page<E> getPage(Page<E> page);
}
