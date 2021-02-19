package com.excilys.formationCDB.controller;

import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;
import com.excilys.formationCDB.service.ComputerService;

public class ComputerController {
	private ComputerService computerService;

	public ComputerController() {
		computerService = ComputerService.getInstance();
	}

	public Computer getComputerById(int id) throws CustomSQLException {
		return computerService.getComputerById(id);
	}

	public Computer getComputerByName(String computerName) throws CustomSQLException {
		return computerService.getComputerByName(computerName);
	}

	public void createComputer(Computer computer) throws CustomSQLException, CompanyKeyInvalidException {
		computerService.createComputer(computer);

	}

	public void updateComputerName(Computer computer) throws CustomSQLException, NoComputerSelectedException {
		computerService.updateComputerName(computer);
	}

	public void deleteComputerById(Computer computer) throws CustomSQLException, NoComputerSelectedException {
		computerService.deleteComputerById(computer);
	}

	public Page getPage(Page page) throws CustomSQLException {
		return computerService.getPage(page);
	}

	public void updateComputerNameAndDate(Computer computer) throws CustomSQLException, NoComputerSelectedException {
		computerService.updateComputerNameAndDate(computer);
		
	}
}
