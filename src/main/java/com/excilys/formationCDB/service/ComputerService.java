package com.excilys.formationCDB.service;

import java.util.List;
import java.util.Optional;

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

	public Optional<Computer> getComputerById(int id) throws CustomSQLException {
		return daoComputer.getComputerById(id);
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

	public int getComputerNumber() throws CustomSQLException {
		return daoComputer.getComputerNumber();
	}

	public List<Optional<Computer>> getComputerListByName(String search) throws CustomSQLException {
		return daoComputer.getComputerListByName(search);
	}

	public int getComputerNumberbyName(String search) throws CustomSQLException {
		return daoComputer.getComputerNumberbyName(search);
	}

	public Page<Computer> getPageByName(Page<Computer> page, String search) throws CustomSQLException {
		return daoComputer.getPageByName(page, search);
	}

}
