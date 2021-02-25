package com.excilys.formationCDB.controller.servlet.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.formationCDB.exception.InvalidWebInputException;

public class addComputerValidator {

	private static final int MAX_COMPANYID = 44;
	private String nameRegex = "^[a-zA-Z].*";

	public void validateName(String name) throws InvalidWebInputException {
		if (name != null) {
			if (!name.matches(nameRegex)) {
				throw new InvalidWebInputException("the name format is not valid");
			}
		} else {
			throw new InvalidWebInputException("the name may not be empty");
		}
	}

	public void validateDates(String introduced, String discontinued) throws InvalidWebInputException {
		if (introduced != null && discontinued != null) {
			LocalDate dateDiscontinued = LocalDate.parse(discontinued, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDate dateIntroduced = LocalDate.parse(introduced, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if (dateDiscontinued != null && dateIntroduced != null) {
				if (dateIntroduced.isAfter(dateDiscontinued)) {
					throw new InvalidWebInputException("the computer may not be introduced after being discontined");
				}
			} else if (introduced != null) {
				LocalDate.parse(introduced, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			} else if (discontinued != null)
				LocalDate.parse(discontinued, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
	}

	public void validateCompanyID(String companyID) throws InvalidWebInputException {
		if (companyID != null) {
			int companyId = Integer.parseInt(companyID);
			if (companyId <= 0 || companyId > MAX_COMPANYID) {
				throw new InvalidWebInputException("invalid company");
			}
		} else {
			throw new InvalidWebInputException("please pick a company");
		}
	}

}
