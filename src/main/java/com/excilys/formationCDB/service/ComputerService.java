package com.excilys.formationCDB.service;

import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.dao.DAOComputer;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NothingSelectedException;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;

public final class ComputerService {

	private final static ComputerService INSTANCE = new ComputerService();
	private DAOComputer daoComputer;

	private ComputerService() {
		daoComputer = DAOComputer.getInstance();
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	public Optional<Computer> getComputerById(int id) {
		return daoComputer.getComputerById(id);
	}

	public void createComputer(Computer computer) {
		daoComputer.createComputer(computer);

	}

	public void updateComputer(Computer computer) throws  NothingSelectedException {
		daoComputer.updateComputer(computer);
	}

	public void deleteComputerById(int i) throws NothingSelectedException {
		daoComputer.deleteComputerById(i);
	}

	public Page getPage(Page page)  {
		return daoComputer.getPage(page);
	}

	public int getComputerNumber() {
		return daoComputer.getComputerNumber();
	}



	public int getComputerNumberbyName(String search) {
		return daoComputer.getComputerNumberbyName(search);
	}

	public Page<Computer> getPageByName(Page<Computer> page, String search)  {
		return daoComputer.getPageByName(page, search);
	}



}
