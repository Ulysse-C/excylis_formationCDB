package com.excilys.formationCDB.controller.servlet;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationCDB.controller.cli.CliComputerController;
import com.excilys.formationCDB.controller.servlet.validator.AddComputerValidator;
import com.excilys.formationCDB.dto.AddComputerDTO;
import com.excilys.formationCDB.dto.mapper.ComputerMapper;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.InvalidWebInputException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.service.ComputerService;

public class AddComputerForm {
	private ComputerService computerService = ComputerService.getInstance(); 

	
	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String SQL_ERRORS = "sqlErrors";

	private Map<String, String> errors;

	public AddComputerForm() {
		errors = new HashMap<String, String>();
	}


	public Map<String, String> getErrors() {
		return errors;
	}

	public AddComputerDTO addComputer(HttpServletRequest request) {
		AddComputerDTO computerDTO = new AddComputerDTO();
		computerDTO.computerName = request.getParameter(INPUT_NAME);
		computerDTO.companyId = request.getParameter(INPUT_COMPANYID);
		computerDTO.introducedDate = request.getParameter(INPUT_INTRODUCED);
		computerDTO.discontinuedDate = request.getParameter(INPUT_DISCONTINUED);
		
		
		AddComputerValidator validator = AddComputerValidator.getInstance();
		errors = validator.validate(computerDTO);
		if (errors.isEmpty()) {
			try {
				Computer computer = ComputerMapper.createComputer(computerDTO);
				computerService.createComputer(computer);
			} catch (CustomSQLException sqlError) {
				errors.put(SQL_ERRORS, sqlError.getMessage());
			} catch(CompanyKeyInvalidException sqlError) {
				errors.put(SQL_ERRORS, sqlError.getMessage());
			}
		}
		return computerDTO;
	}
}
