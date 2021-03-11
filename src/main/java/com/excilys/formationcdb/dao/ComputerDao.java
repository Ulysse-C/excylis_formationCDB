package com.excilys.formationcdb.dao;

import java.util.Optional;

import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

public interface ComputerDao {

	Optional<Computer> getComputerById(int id);

	void createComputer(Computer computer);

	void updateComputer(Computer computer) throws NothingSelectedException;

	void deleteComputerById(int i) throws NothingSelectedException;

	int getComputerNumber();

	int getComputerNumberbyName(String search);

	Page<Computer> getPageByName(Page<Computer> page, String search);

	Page<Computer> getPage(Page<Computer> page);

}
