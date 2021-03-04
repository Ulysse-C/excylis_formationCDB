package com.excilys.formationCDB.controller.cli;

import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NothingSelectedException;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;
import com.excilys.formationCDB.service.ComputerService;

public final class CliComputerController {
	private final static CliComputerController INSTANCE = new CliComputerController();
	
	public final static int PAGE_SIZE = 10;
	public final static String COMPUTER_TABLE_NAME = "computer";


	private ComputerService computerService;

	private CliComputerController() {
		computerService = ComputerService.getInstance();
	}

	public static CliComputerController getInstance() {
		return INSTANCE;
	}

	public Optional<Computer> getComputerById(int id) throws CustomSQLException {
		return computerService.getComputerById(id);
	}

	public void createComputer(Computer computer) throws CustomSQLException, CompanyKeyInvalidException {
		computerService.createComputer(computer);

	}
	
	public void deleteComputerById(int id) throws CustomSQLException, NothingSelectedException {
		computerService.deleteComputerById(id);
	}

	public Page getPage(Page page) throws CustomSQLException {
		return computerService.getPage(page);
	}

	public int getComputerNumber() {
		return computerService.getComputerNumber();
	}
	

	public Page<Computer> getPageByName(Page<Computer> page, String search) throws CustomSQLException {
		return computerService.getPageByName(page, search);
	}
	
	public int getComputerNumberbyName(String search) throws CustomSQLException {
		return computerService.getComputerNumberbyName(search);
	}

	public void updateComputer(Computer computer) throws NothingSelectedException {
		computerService.updateComputer(computer);
		
	}

}
