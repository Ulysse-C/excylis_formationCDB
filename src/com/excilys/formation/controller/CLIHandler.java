package com.excilys.formation.controller;

import java.util.List;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class CLIHandler {

	private CompanyController companyController;
	private ComputerController computerController;

	public List<Computer> listComputer() {
		List<Computer> computerList = null;
		if (computerController != null) {
			computerList = computerController.listComputer();
		}
		return computerList;
	}

	public List<Company> listCompany() {
		List<Company> companyList = null;
		if (companyController != null) {
			companyList = companyController.listCompany();
		}
		return companyList;
	}

	public void setCompanyController(CompanyController companyController) {
		this.companyController = companyController;
	}

	public void setComputerController(ComputerController computerController) {
		this.computerController = computerController;
	}

	public Computer singleComputer(String computerId) {
		Computer computer;
		if (isInteger(computerId, 10)) {
			computer = computerController.getComputerById(Integer.parseInt(computerId));
		} else {
			computer = computerController.getComputerByName(computerId);
		}
		return computer;
	}

	public boolean createComputer(String[] inputList) {
		Computer computer = null;
		if (inputList.length > 3 && isInteger(inputList[1], 10) && isInteger(inputList[3], 10)) {
			int computerId = Integer.parseInt(inputList[1]);
			int companyID = Integer.parseInt(inputList[3]);
			computer = new Computer(inputList[2],computerId, companyID);
		}
		return computerController.createComputer(computer);
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

}
