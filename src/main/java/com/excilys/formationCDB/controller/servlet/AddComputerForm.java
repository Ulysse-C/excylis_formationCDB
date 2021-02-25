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

	private Map<String, String> errors;

	public AddComputerForm() {
		computerController = ComputerController.getInstance();
		errors = new HashMap<String, String>();
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
		validate(name, introduced, discontinued, companyID, validator);
		if (errors.isEmpty()) {
			try {
				//dtoComputer = newDtocomputer(params)
				// computer = mapper.getComputer(dtoComputer);
				computer.setCompanyID(companyID);
				computer.setIntroduced(introduced, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				computer.setDiscontinued(discontinued, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				computer.setName(name);
				computerController.createComputer(computer);
			} catch (CustomSQLException | CompanyKeyInvalidException sqlError) {
				errors.put(INPUT_COMPANYID, sqlError.getMessage());
			}
		}
		return computer;
	}

	private void validate(String name, String introduced, String discontinued, String companyID,
			addComputerValidator validator) {
		validateName(name, validator);
		validateDates(introduced, discontinued, validator);
		validateCompanyId(companyID, validator);
	}

	private void validateCompanyId(String companyID, addComputerValidator validator) {
		try {
			validator.validateCompanyID(companyID);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(INPUT_COMPANYID, invalidInput.getMessage());
		}
	}

	private void validateDates(String introduced, String discontinued, addComputerValidator validator) {
		try {
			validator.validateDates(introduced, discontinued);
		} catch (InvalidWebInputException invalidInput) {
			errors.put( INPUT_INTRODUCED, invalidInput.getMessage() );
		} catch (DateTimeParseException dateParseException) {
			errors.put( INPUT_INTRODUCED, "bad input format");
		}
	}

	private void validateName(String name, addComputerValidator validator) {
		try {
			validator.validateName(name);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(INPUT_NAME, invalidInput.getMessage());
		}
	}
}
