package com.excilys.formation.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.controller.CLIHandler;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class CLI {

	private BufferedReader bufferedReader;
	private PrintStream output;
	private CLIHandler cliHandler;

	public CLI() {
		output = System.out;
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public CLI(PrintStream ps) {
		if (ps == null) {
			throw new IllegalArgumentException();
		}
		output = ps;
	}

	public void printResultSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			output.println(rs.getNString("name"));
			// System.out.println(rs.getArray(1));
		}
	}
 
	public void startReadingUserInput() {
		try {
			String input = bufferedReader.readLine();
			if (cliHandler != null) {
				parseInput(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseInput(String input) {
		if (input != null) {
			String[] inputList = input.split(" ");
			if (inputList.length > 0) {
				handleRequest(inputList);
			}
		}
	}

	private void handleRequest(String[] inputList) {
		switch (inputList[0]) {
		case "1":
			printListComputers(cliHandler.listComputer());
			break;
		case "2":
			printListCompanies(cliHandler.listCompany());
			break;
		case "3":
			if (inputList.length > 1) {
				printSingleComputer(cliHandler.singleComputer(inputList[1]));
			}
			break;
		case "4":
			if (inputList.length > 1) {
				printResult(cliHandler.createComputer(inputList), "created");
			}
			break;
		case "5":
			if (inputList.length > 1) {
				printResult(cliHandler.updateComputer(inputList), "updated");
			}
			break;
		case "6":
			if (inputList.length > 1) {
				printResult(cliHandler.deleteComputer(inputList), "deleted");
			}
			break;
		}
	}
 
	private void printResult(boolean isSuccess, String operation) {
		if (isSuccess) {
			System.out.println("the computer was succesfully "+operation);
		} else {
			System.out.println("the computer was not succesfully "+operation);
		}
		
	}

	private void printSingleComputer(Computer singleComputer) {
		if (singleComputer != null) {
			System.out.println(singleComputer.toString());	
		} else {
			System.out.println("the computer you asked for is not available");
		}
		

	}

	private void printListComputers(List<Computer> computerList) {
		for (Computer computer : computerList) {
			System.out.println(computer.getName());
		}
	}

	private void printListCompanies(List<Company> companyList) {
		for (Company company : companyList) {
			System.out.println(company.getName() + ", id: " + company.getId());
		}

	}

	public void addCliHandler(CLIHandler cliHandler) {
		this.cliHandler = cliHandler;
	}

}
