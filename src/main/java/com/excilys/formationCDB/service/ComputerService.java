package com.excilys.formationCDB.service;

import com.excilys.formationCDB.dao.DAOComputer;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
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

	public Computer getComputerById(int id) throws CustomSQLException {
		return daoComputer.getComputerById(id);
	}

	public Computer getComputerByName(String computerName) throws CustomSQLException {
		return daoComputer.getComputerByName(computerName);
	}

	public void createComputer(Computer computer) throws CustomSQLException, CompanyKeyInvalidException {
		daoComputer.createComputer(computer);

	}

	public void updateComputerName(Computer computer) throws CustomSQLException, NoComputerSelectedException {
		daoComputer.updateComputerName(computer);
	}

	public void deleteComputerById(Computer computer) throws CustomSQLException, NoComputerSelectedException {
		daoComputer.deleteComputerById(computer);
	}

	public Page getPage(Page page) throws CustomSQLException {
		return daoComputer.getPage(page);
	}

	public void updateComputerNameAndDate(Computer computer) throws CustomSQLException, NoComputerSelectedException  {
		daoComputer.updateComputerNameAndDate(computer);
		
	}

}
