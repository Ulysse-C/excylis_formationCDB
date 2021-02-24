package com.excilys.formationCDB.controller.servlet;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationCDB.controller.ComputerController;
import com.excilys.formationCDB.controller.servlet.validator.addComputerValidator;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.InvalidInputCLIHandlerException;
import com.excilys.formationCDB.exception.InvalidWebInputException;
import com.excilys.formationCDB.model.Computer;

public class AddComputerForm {
	private ComputerController computerController;

	
	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String SQL_ERRORS = "sqlErrors";

	private String result;
	private Map<String, String> errors;

	public AddComputerForm() {
		computerController = ComputerController.getInstance();
		errors = new HashMap<String, String>();
	}
	
	public String getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public Computer addComputer(HttpServletRequest request) {
		String name = request.getParameter(INPUT_NAME);
		String introduced = request.getParameter(INPUT_INTRODUCED);
		String discontinued = request.getParameter(INPUT_DISCONTINUED);
		String companyID = request.getParameter(INPUT_COMPANYID);

		addComputerValidator validator = new addComputerValidator();
		Computer computer = new Computer();
		validateName(name, validator, computer);
		validateDates(introduced, discontinued, validator, computer);
		validateCompanyId(companyID, validator, computer);
		if (errors.isEmpty()) {
			try {
				computerController.createComputer(computer);
			} catch (CustomSQLException | CompanyKeyInvalidException sqlError) {
				errors.put(INPUT_COMPANYID, sqlError.getMessage());
			}
			result = "Computer added";
		} else {
			result = "Computer not added";
		}
		return computer;
	}

	private void validateCompanyId(String companyID, addComputerValidator validator, Computer computer) {
		try {
			validator.validateCompanyID(companyID);
			computer.setCompanyID(companyID);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(INPUT_COMPANYID, invalidInput.getMessage());
		}
	}

	private void validateDates(String introduced, String discontinued, addComputerValidator validator,
			Computer computer) {
		try {
			validator.validateDates(introduced, discontinued);
			computer.setIntroduced(introduced, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			computer.setDiscontinued(discontinued, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (InvalidWebInputException invalidInput) {
			errors.put( INPUT_INTRODUCED, invalidInput.getMessage() );
		} catch (InvalidInputCLIHandlerException e) {
		} catch (DateTimeParseException dateParseException) {
			
		}
	}

	private void validateName(String name, addComputerValidator validator, Computer computer) {
		try {
			validator.validateName(name);
			computer.setName(name);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(INPUT_NAME, invalidInput.getMessage());
		}
	}
}
