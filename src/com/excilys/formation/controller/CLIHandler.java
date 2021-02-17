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
		if (inputList.length > 2 && isInteger(inputList[2], 10)) {
			int companyID = Integer.parseInt(inputList[2]);
			computer = new Computer(inputList[1],0, companyID);
		}
		return computerController.createComputer(computer);
	}
	
	public boolean updateComputer(String[] inputList) {
		Computer computer = null;
		if (inputList.length > 2 && isInteger(inputList[1], 10)) {
			int computerID = Integer.parseInt(inputList[1]);
			computer = new Computer(inputList[2],computerID, 0);
		}
		return computerController.updateComputerName(computer);	
		}
	
	public boolean deleteComputer(String[] inputList) {
		Computer computer = null;
		if (inputList.length > 1 && isInteger(inputList[1], 10)) {
			int computerID = Integer.parseInt(inputList[1]);
			computer = new Computer("",computerID, 0);
		}
		return computerController.deleteComputerById(computer);	
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
