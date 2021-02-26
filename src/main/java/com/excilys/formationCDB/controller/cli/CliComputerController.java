package com.excilys.formationCDB.controller.cli;

import java.util.List;

import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
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

	public Computer getComputerById(int id) throws CustomSQLException {
		return computerService.getComputerById(id);
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

	public int getComputerNumber() throws CustomSQLException {
		return computerService.getComputerNumber();
	}

	public List<Computer> getComputerListByName(String search) throws CustomSQLException {
		return computerService.getComputerListByName(search);
	}

	

	public Page<Computer> getPageByName(Page<Computer> page, String search) throws CustomSQLException {
		return computerService.getPageByName(page, search);
	}
	
	public int getComputerNumberbyName(String search) throws CustomSQLException {
		return computerService.getComputerNumberbyName(search);
	}

}
