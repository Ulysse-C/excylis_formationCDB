package com.excilys.formationcdb.controller.web.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.excilys.formationcdb.dto.web.AddComputerDTO;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.exception.InvalidWebInputException;
import com.excilys.formationcdb.logger.CDBLogger;

@Component
public class AddComputerValidator {

	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_COMPANYID = "companyId";

	private static final int MAX_COMPANYID = 43;
	private String nameRegex = "^[a-zA-Z].*";

	public Map<String, String> validate(AddComputerDTO computerDTO) {
		Map<String, String> errors = new HashMap<>();
		try {
			validateName(computerDTO.computerName);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(INPUT_NAME, invalidInput.getMessage());
			CDBLogger.logInfo(AddComputerValidator.class, invalidInput);
		}
		try {
			validateCompanyID(computerDTO.companyId);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(INPUT_COMPANYID, invalidInput.getMessage());
			CDBLogger.logInfo(AddComputerValidator.class, invalidInput);
		}
		try {
			validateDates(computerDTO.introducedDate, computerDTO.discontinuedDate);
		} catch (InvalidWebInputException invalidInput) {
			errors.put(INPUT_INTRODUCED, invalidInput.getMessage());
			CDBLogger.logInfo(AddComputerValidator.class, invalidInput);
		} catch (DateTimeParseException invalidInput) {
			errors.put(INPUT_INTRODUCED, "invalid dates");
			CDBLogger.logInfo(AddComputerValidator.class, invalidInput);
		}

		return errors;
	}

	private void validateName(String name) throws InvalidWebInputException {
		if (!"".equals(name)) {
			if (!name.matches(nameRegex)) {
				throw new InvalidWebInputException("the name format is not valid");
			}
		} else {
			throw new InvalidWebInputException("the name may not be empty");
		}
	}

	private void validateDates(String introduced, String discontinued) throws InvalidWebInputException {
		if (!"".equals(introduced) && !"".equals(discontinued)) {
			System.out.println();
			LocalDate dateDiscontinued = LocalDate.parse(discontinued,
					DateTimeFormatter.ofPattern(WebComputerMapper.DATE_FORMAT));
			LocalDate dateIntroduced = LocalDate.parse(introduced,
					DateTimeFormatter.ofPattern(WebComputerMapper.DATE_FORMAT));
			if (dateDiscontinued != null && dateIntroduced != null) {
				if (dateIntroduced.isAfter(dateDiscontinued)) {
					throw new InvalidWebInputException("the computer may not be introduced after being discontined");
				}
			}
		} else if (!"".equals(introduced)) {
			LocalDate.parse(introduced, DateTimeFormatter.ofPattern(WebComputerMapper.DATE_FORMAT));
		} else if (!"".equals(discontinued)) {
			LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(WebComputerMapper.DATE_FORMAT));
		}
	}

	private void validateCompanyID(String companyID) throws InvalidWebInputException {
		if (companyID != null && !"".equals(companyID)) {
			int companyId = Integer.parseInt(companyID);
			if (companyId <= 0 || companyId > MAX_COMPANYID) {
				throw new InvalidWebInputException("invalid company");
			}
		} else {
			throw new InvalidWebInputException("please pick a company");
		}
	}
}
