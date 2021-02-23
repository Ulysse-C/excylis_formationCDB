package com.excilys.formationCDB.controller.ui;

import com.excilys.formationCDB.controller.CompanyController;
import com.excilys.formationCDB.controller.ComputerController;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.InvalidInputHandlerException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;

public class CLIHandler {

	private CompanyController companyController;
	private ComputerController computerController;
	
	public CLIHandler() {
		this.companyController = CompanyController.getInstance();
		this.computerController = ComputerController.getInstance();
	}

	public Computer getSingleComputer(String[] inputList) throws InvalidInputHandlerException, CustomSQLException {
		Computer computer;
		if (inputList.length != 2) {
			throw new InvalidInputHandlerException("Wrong number of arguments");
		}
		if (isInteger(inputList[1], 10)) {
			computer = computerController.getComputerById(Integer.parseInt(inputList[1]));
		} else {
			computer = computerController.getComputerByName(inputList[1]);
		}
		return computer;
	}

	public void createComputer(String[] inputList) throws InvalidInputHandlerException, CustomSQLException, CompanyKeyInvalidException {
		if (inputList.length != 3 && inputList.length != 5) {
			throw new InvalidInputHandlerException("Wrong number of arguments");
		}
		Computer computer = null;
		if (inputList.length == 3 && isInteger(inputList[2], 10)) {
			int companyID = Integer.parseInt(inputList[2]);
			computer = new Computer(inputList[1], 0, companyID);
		} else if (inputList.length == 5 && isInteger(inputList[2], 10)) {
			int companyID = Integer.parseInt(inputList[2]);
			computer = new Computer(inputList[1], 0, companyID);
			computer.setIntroduced(inputList[3]);
			computer.setDiscontinued(inputList[4]);
		} else {
			throw new InvalidInputHandlerException("Wrong argument type");
		}
		computerController.createComputer(computer);
	}

	public void updateComputer(String[] inputList) throws InvalidInputHandlerException, CustomSQLException, NoComputerSelectedException {
		if (inputList.length != 3 && inputList.length != 5) {
			throw new InvalidInputHandlerException("Wrong number of arguments");
		}
		if (inputList.length == 3 && isInteger(inputList[1], 10)) {
			int computerID = Integer.parseInt(inputList[1]);
			computerController.updateComputerName(new Computer(inputList[2], computerID, 0));
		} else if (inputList.length == 5 && isInteger(inputList[1], 10)) {
			int computerID = Integer.parseInt(inputList[1]);
			Computer computer = new Computer(inputList[2], computerID, 0);
			computer.setIntroduced(inputList[3]);
			computer.setDiscontinued(inputList[4]);
			computerController.updateComputerNameAndDate(computer);
		} else {
			throw new InvalidInputHandlerException("Wrong argument type");
		}
		
	}

	public void deleteComputer(String[] inputList) throws InvalidInputHandlerException, CustomSQLException, NoComputerSelectedException {
		Computer computer = null;
		if (inputList.length != 2) {
			throw new InvalidInputHandlerException("Wrong number of arguments");
		} else if (!isInteger(inputList[1], 10)) {
			throw new InvalidInputHandlerException("Wrong argument type");
		}
		int computerID = Integer.parseInt(inputList[1]);
		computer = new Computer("", computerID, 0);
		computerController.deleteComputerById(computer);
	}

	// https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	private boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public Page getPage(Page page) throws CustomSQLException {
		Page pageResult = null;
		if (page.getTable().equals("computer")) {
			pageResult = computerController.getPage(page);
		} else if (page.getTable().equals("company")) {
			pageResult = companyController.getPage(page);
		}
		return pageResult;
	}

}
