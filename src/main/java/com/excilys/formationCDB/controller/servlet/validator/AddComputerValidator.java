package com.excilys.formationCDB.controller.servlet.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.excilys.formationCDB.controller.servlet.AddComputerForm;
import com.excilys.formationCDB.dto.AddComputerDTO;
import com.excilys.formationCDB.dto.mapper.ComputerMapper;
import com.excilys.formationCDB.exception.InvalidWebInputException;
import com.excilys.formationCDB.logger.CDBLogger;

public class AddComputerValidator {
	private static final AddComputerValidator INSTANCE = new AddComputerValidator();
	private static final int MAX_COMPANYID = 43;
	private String nameRegex = "^[a-zA-Z].*";

	private AddComputerValidator() {

	}

	public static AddComputerValidator getInstance() {
		return INSTANCE;
	}

	public Map<String, String> validate(AddComputerDTO computerDTO) {
		Map<String, String> errors = new HashMap<>();
		try {
			validateName(computerDTO.computerName);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(AddComputerForm.INPUT_NAME, invalidInput.getMessage());
			CDBLogger.logInfo(invalidInput);
		}
		try {
			validateCompanyID(computerDTO.companyId);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(AddComputerForm.INPUT_COMPANYID, invalidInput.getMessage());
			CDBLogger.logInfo(invalidInput);
		}
		try {
			validateDates(computerDTO.introducedDate, computerDTO.discontinuedDate);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(AddComputerForm.INPUT_INTRODUCED, invalidInput.getMessage());
			CDBLogger.logInfo(invalidInput);
		} catch (DateTimeParseException invalidInput) {
			errors.put(AddComputerForm.INPUT_INTRODUCED, "invalid dates");
			CDBLogger.logInfo(invalidInput);
		}
		
		return errors;
	}

	private void validateName(String name) throws InvalidWebInputException {
		if (name != "") {
			if (!name.matches(nameRegex)) {
				throw new InvalidWebInputException("the name format is not valid");
			}
		} else {
			throw new InvalidWebInputException("the name may not be empty");
		}
	}

	private void validateDates(String introduced, String discontinued) throws InvalidWebInputException {
		if (introduced != "" && discontinued != "") {
			System.out.println();
			LocalDate dateDiscontinued = LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
			LocalDate dateIntroduced = LocalDate.parse(introduced, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
			if (dateDiscontinued != null && dateIntroduced != null) {
				if (dateIntroduced.isAfter(dateDiscontinued)) {
					throw new InvalidWebInputException("the computer may not be introduced after being discontined");
				}
			}
		} else if (introduced != "") { 
			LocalDate.parse(introduced, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
		} else if (discontinued != "") {
			LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
		}
	}

	private void validateCompanyID(String companyID) throws InvalidWebInputException {
		if (companyID != "") {
			int companyId = Integer.parseInt(companyID);
			if (companyId <= 0 || companyId > MAX_COMPANYID) {
				throw new InvalidWebInputException("invalid company");
			}
		} else {
			throw new InvalidWebInputException("please pick a company");
		}
	}
}
