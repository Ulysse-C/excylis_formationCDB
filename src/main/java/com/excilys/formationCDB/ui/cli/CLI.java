package com.excilys.formationCDB.ui.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.formationCDB.controller.cli.CLIHandler;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.InvalidInputCLIHandlerException;
import com.excilys.formationCDB.exception.NothingSelectedException;
import com.excilys.formationCDB.logger.CDBLogger;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;
import com.mysql.cj.log.Log;

public class CLI {

	private BufferedReader bufferedReader;
	private PrintStream output;
	private CLIHandler cliHandler;

	public CLI() {
		output = System.out;
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public CLI(PrintStream printStream) {
		this();
		if (printStream == null) {
			throw new IllegalArgumentException();
		}
		output = printStream;
	}

	public CLI(BufferedReader bufferedReader) {
		this();
		if (bufferedReader == null) {
			throw new IllegalArgumentException();
		}
		this.bufferedReader = bufferedReader;
	}

	public void startReadingUserInput() {
		try {
			if (cliHandler != null) {
				handleGeneralRequest();
			}
		} catch (IOException exception) {
		CDBLogger.logError(exception);
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
		output.println("type a number, exit or \"help\"");
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
				output.println("Computer created");
				handleGeneralRequest();
				break;
			case "5":
				cliHandler.updateComputer(inputList);
				output.println("Computer updated");
				handleGeneralRequest();
				break;
			case "6":
				cliHandler.deleteComputer(inputList);
				output.println("Computer deleted");
				handleGeneralRequest();
				break;
			case "7":
				cliHandler.deleteCompany(inputList);
				output.println("Company deleted");
				handleGeneralRequest();
				break;
			case "help":
				printHelp();
				handleGeneralRequest();
				break;
			default:
				output.println("exiting");

			}
		} catch (InvalidInputCLIHandlerException invalidInputException) {
			CDBLogger.logInfo(invalidInputException);
			output.println("Input not valid: " + invalidInputException.getMessage());
			handleGeneralRequest();
		} catch (CustomSQLException customSqlException) {
			CDBLogger.logError(customSqlException);
			output.println("Sql Error: " + customSqlException.getMessage());
			handleGeneralRequest();
		} catch (CompanyKeyInvalidException companyKeyInvalidException) {
			CDBLogger.logInfo(companyKeyInvalidException);
			output.println("Sql Error: The company id is not valid");
			handleGeneralRequest();
		} catch (NothingSelectedException noComputerException) {
			CDBLogger.logError(noComputerException);
			output.println("Sql Error: No computer could be selected");
			handleGeneralRequest();
		}
	}

	private void printHelp() {
		output.println("1 - Show the list of the computers");
		output.println("2 - Show the list of the companies");
		output.println("3 - Show a computer details / 3 [computer_id] or 3 [name]");
		output.println("4 - Create a computer / 4 [name] [company_ID] [[introduced] [discontinued]] yyyy-MM-dd");
		output.println("5 - Update a computer / 5 [computer_id] [name] [[introduced] [discontinued]] yyyy-MM-dd");
		output.println("6 - Delete a computer / 6 [computer_id]");
		output.println("7 - Delete a company / 7 [computer_id]");
	}

	private void pageNavigagtion(Page page) throws IOException, CustomSQLException {
		printPage(cliHandler.getPage(new Page<Computer>(10, page.getNumber(), page.getTable())));
		handlePageNavigationRequest(page);
	}

	private void handlePageNavigationRequest(Page page) throws IOException {
		output.println("previous p / next n / exit e");
		String[] inputList = parseInput();
		try {
			switch (inputList[0]) {
			case "p":
				pageNavigagtion(page.getPreviousPage());
				break;
			case "n":
				pageNavigagtion(page.getNextPage());
				break;
			default:
				handleGeneralRequest();
			}
		} catch (CustomSQLException customSqlException) {
			CDBLogger.logInfo(customSqlException);
			output.println("Sql Error:" + customSqlException.getMessage());
			handleGeneralRequest();
		}
	}

	private void printSingleComputer(Optional<Computer> optional) {
		if (optional.isPresent()) {
			output.println(optional.get());
		} else {
			output.println("This computer is not available");
		}
	}

	private void printPage(Page page) {
		output.println("Page " + page.getNumber());
		for (Object content : page.getContent()) {
			output.println(content.toString());
		}
	}

	public void addCliHandler(CLIHandler cliHandler) {
		this.cliHandler = cliHandler;
	}

}
