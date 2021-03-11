package com.excilys.formationcdb.service;

import java.util.Optional;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

public interface ComputerService {

	Page<Computer> getPageByName(Page<Computer> page, String parameter);

	int getComputerNumberbyName(String parameter);

	Page<Computer> getPage(Page page);

	int getComputerNumber();

	void deleteComputerById(int parseInt) throws NothingSelectedException;

	Optional<Computer> getComputerById(int computerId);

	void createComputer(Computer computer);

	void updateComputer(Computer computer) throws NothingSelectedException;

}
