package com.excilys.formationCDB.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formationCDB.controller.CLIHandler;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.InvalidInputHandlerException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;

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
			if (cliHandler != null) {
				handleGeneralRequest();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] parseInput() throws IOException {
		String input = bufferedReader.readLine();
		String[] inputList = null;
		if (input != null) {
			inputList = input.split(" ");
		}
		return inputList;
	}

	private void handleGeneralRequest() throws IOException {
		System.out.println("type a number, exit or \"help\"");
		String[] inputList = parseInput();
		try {
			switch (inputList[0]) {
			case "1":
				pageNavigagtion(new Page<Computer>(10, 1, "computer"));
				break;
			case "2":
				pageNavigagtion(new Page<Company>(10, 1, "company"));
				break;
			case "3":
				printSingleComputer(cliHandler.getSingleComputer(inputList));
				handleGeneralRequest();
				break;
			case "4":
				cliHandler.createComputer(inputList);
				System.out.println("Computer created");
				handleGeneralRequest();
				break;
			case "5":
				cliHandler.updateComputer(inputList);
				System.out.println("Computer updated");
				handleGeneralRequest();
				break;
			case "6":
				cliHandler.deleteComputer(inputList);
				System.out.println("Computer deleted");
				handleGeneralRequest();
				break;
			case "help":
				printHelp();
				handleGeneralRequest();
				break;
			}
		} catch (InvalidInputHandlerException invalidInputException) {
			System.out.println("Input not valid: " + invalidInputException.getMessage());
			handleGeneralRequest();
		} catch (CustomSQLException customSqlException) {
			System.out.println("Sql Error: " +customSqlException.getMessage());
			handleGeneralRequest();
		}
	}

	private void printHelp() {
		System.out.println("1 - Show the list of the computers");
		System.out.println("2 - Show the list of the companies");
		System.out.println("3 - Show a computer details / 3 [computer_id] or 3 [name]");
		System.out.println("4 - Create a computer / 4 [name] [company_ID] [introduced] [discontinued] dd/mm/yyyy");
		System.out.println("5 - Update a computer / 5 [computer_id] [name] ");
		System.out.println("6 - Delete a computer / 6 [computer_id]");

	}

	private void pageNavigagtion(Page page) throws IOException, CustomSQLException {
		printPage(cliHandler.getPage(new Page<Computer>(10, page.getNumber(), page.getTable())));
		handlePageNavigationRequest(page);
	}

	private void handlePageNavigationRequest(Page page) throws IOException {
		System.out.println("previous p / next n / exit e");
		String[] inputList = parseInput();
		try {
			switch (inputList[0]) {
			case "p":
				pageNavigagtion(page.previousPage());
				break;
			case "n":
				pageNavigagtion(page.nextPage());
				break;
			default:
				handleGeneralRequest();
			}
		} catch (CustomSQLException customSqlException) {
			System.out.println("Sql Error:" +customSqlException.getMessage());
			handleGeneralRequest();
		}
	}

	private void printSingleComputer(Computer singleComputer) {
		if (singleComputer != null) {
			System.out.println(singleComputer.toString());
		} else {
			System.out.println("This computer is not available");
		}
	}

	private void printPage(Page page) {
		System.out.println("Page " + page.getNumber());
		for (Object content : page.getContent()) {
			System.out.println(content.toString());
		}
	}

	public void addCliHandler(CLIHandler cliHandler) {
		this.cliHandler = cliHandler;
	}

}
