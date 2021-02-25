package com.excilys.formationCDB.controller;

import java.util.List;

import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;
import com.excilys.formationCDB.service.ComputerService;

public final class ComputerController {
	private final static ComputerController INSTANCE = new ComputerController();
	
	public final static int PAGE_SIZE = 10;
	public final static String COMPUTER_TABLE_NAME = "computer";

	private final static int PAGEINDEX_SIZE = 7;
	private final static int PAGEINDEX_BEFORE_CURRENT_PAGE = 4;
	private ComputerService computerService;

	private ComputerController() {
		computerService = ComputerService.getInstance();
	}

	public static ComputerController getInstance() {
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

	public int getPageIndexFrom(int pageNumber) {
		int indexFrom = 0;
		while (indexFrom + pageNumber > 1 && indexFrom + PAGEINDEX_BEFORE_CURRENT_PAGE > 0) {
			indexFrom--;
		}
		return indexFrom + pageNumber;
	}

	public int getPageIndexTo(int pageNumber, int computerNB) {
		int indexTo = 0;
		int compensation = 0;
		if (pageNumber <= PAGEINDEX_BEFORE_CURRENT_PAGE) {
			compensation = PAGEINDEX_BEFORE_CURRENT_PAGE - pageNumber +1;
		}
		while (computerNB / PAGE_SIZE >= indexTo + pageNumber && indexTo + 1 + PAGEINDEX_BEFORE_CURRENT_PAGE - compensation < PAGEINDEX_SIZE) {
			indexTo++;
		}
		return indexTo + pageNumber;
	}

	public int getComputerNumberbyName(String search) throws CustomSQLException {
		return computerService.getComputerNumberbyName(search);
	}

	public Page<Computer> getPageByName(Page<Computer> page, String search) throws CustomSQLException {
		return computerService.getPageByName(page, search);
	}

}
