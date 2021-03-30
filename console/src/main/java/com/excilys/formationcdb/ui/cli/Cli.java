package com.excilys.formationcdb.ui.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.controller.cli.CliHandler;
import com.excilys.formationcdb.controller.cli.CliHandler;
import com.excilys.formationcdb.exception.CompanyKeyInvalidException;
import com.excilys.formationcdb.exception.CustomSQLException;
import com.excilys.formationcdb.exception.InvalidInputCLIHandlerException;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

@Component
@Scope("prototype")
public class Cli {

	private BufferedReader bufferedReader;
	private PrintStream output;

	@Autowired
	private CliHandler cliHandler;

	public Cli() {
		output = System.out;
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public Cli(PrintStream printStream) {
		this();
		if (printStream == null) {
			throw new IllegalArgumentException();
		}
		output = printStream;
	}

	public Cli(BufferedReader bufferedReader) {
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
			CDBLogger.logError(Cli.class, exception);
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
		Page page;
		try {
			if (inputList.length > 0) {
				switch (inputList[0]) {
				case "1":
					page = new Page<Computer>(10, 1);
					page.setTable("computer");
					pageNavigagtion(page);
					break;
				case "2":
					page = new Page<Computer>(10, 1);
					page.setTable("company");
					pageNavigagtion(page);
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
			} else {
				handleGeneralRequest();
			}
		} catch (InvalidInputCLIHandlerException invalidInputException) {
			CDBLogger.logInfo(Cli.class, invalidInputException);
			output.println("Input not valid: " + invalidInputException.getMessage());
			handleGeneralRequest();
		} catch (CustomSQLException customSqlException) {
			CDBLogger.logError(Cli.class, customSqlException);
			output.println("Sql Error: " + customSqlException.getMessage());
			handleGeneralRequest();
		} catch (CompanyKeyInvalidException companyKeyInvalidException) {
			CDBLogger.logInfo(Cli.class, companyKeyInvalidException);
			output.println("Sql Error: The company id is not valid");
			handleGeneralRequest();
		} catch (NothingSelectedException noComputerException) {
			CDBLogger.logError(Cli.class, noComputerException);
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

	private <E> void pageNavigagtion(Page<E> page) throws IOException, CustomSQLException {
		Page pagePrinted = new Page<Computer>(10, page.getNumber());
		page.setTable(page.getTable());
		printPage(cliHandler.getPage(pagePrinted));
		handlePageNavigationRequest(page);
	}

	private <E> void handlePageNavigationRequest(Page<E> page) throws IOException {
		output.println("previous p / next n / exit e");
		String[] inputList = parseInput();
		if (inputList.length > 0) {
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
				CDBLogger.logError(Cli.class, customSqlException);
				output.println("Sql Error:" + customSqlException.getMessage());
				handleGeneralRequest();
			}
		} else {
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

	private <T> void printPage(Page<T> page) {
		output.println("Page " + page.getNumber());
		for (T content : page.getContent()) {
			output.println(content);
		}
	}

	public void addCliHandler(CliHandler cliHandler) {
		this.cliHandler = cliHandler;
	}

}
