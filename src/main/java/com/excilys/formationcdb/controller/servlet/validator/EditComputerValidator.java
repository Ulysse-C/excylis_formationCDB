package com.excilys.formationcdb.controller.servlet.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import com.excilys.formationcdb.controller.servlet.AddComputerForm;
import com.excilys.formationcdb.dto.EditComputerDTO;
import com.excilys.formationcdb.dto.mapper.ComputerMapper;
import com.excilys.formationcdb.exception.InvalidWebInputException;
import com.excilys.formationcdb.logger.CDBLogger;

public class EditComputerValidator {
	
	private static final EditComputerValidator INSTANCE = new EditComputerValidator();
	private static final int MAX_COMPANYID = 43;

	private EditComputerValidator() {

	}
	
	public static EditComputerValidator getInstance() {
		return INSTANCE;
	}
	
	public Map<String, String> validate(EditComputerDTO computerDTO) {
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
	}

	private void validateDates(String introduced, String discontinued) throws InvalidWebInputException {
		if (!"".equals(introduced) && !"".equals(discontinued)) {
			System.out.println();
			LocalDate dateDiscontinued = LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
			LocalDate dateIntroduced = LocalDate.parse(introduced, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
			if (dateDiscontinued != null && dateIntroduced != null) {
				if (dateIntroduced.isAfter(dateDiscontinued)) {
					throw new InvalidWebInputException("the computer may not be introduced after being discontined");
				}
			}
		} else if (!"".equals(introduced)) { 
			LocalDate.parse(introduced, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
		} else if (!"".equals(discontinued)) {
			LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(ComputerMapper.DATE_FORMAT));
		}
	}

	private void validateCompanyID(String companyID) throws InvalidWebInputException {
		if (!"".equals(companyID)) {
			int companyId = Integer.parseInt(companyID);
			if (companyId <= 0 || companyId > MAX_COMPANYID) {
				throw new InvalidWebInputException("invalid company");
			}
		} else {
			throw new InvalidWebInputException("please pick a company");
		}
	}

}