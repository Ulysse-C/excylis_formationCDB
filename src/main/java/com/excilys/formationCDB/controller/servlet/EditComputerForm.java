package com.excilys.formationCDB.controller.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationCDB.controller.servlet.validator.EditComputerValidator;
import com.excilys.formationCDB.dto.EditComputerDTO;
import com.excilys.formationCDB.dto.mapper.ComputerMapper;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.logger.CDBLogger;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.service.ComputerService;

public class EditComputerForm {

	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String ATTR_COMPUTERID = "computerId";
	
	private ComputerService computerService = ComputerService.getInstance();
	private Map<String, String> errors = new HashMap<>();

	public EditComputerDTO editComputer(HttpServletRequest request) {
		EditComputerDTO computerDTO = new EditComputerDTO();
		computerDTO.computerName = request.getParameter(INPUT_NAME);
		computerDTO.companyId = request.getParameter(INPUT_COMPANYID);
		computerDTO.introducedDate = request.getParameter(INPUT_INTRODUCED);
		computerDTO.discontinuedDate = request.getParameter(INPUT_DISCONTINUED);
		computerDTO.computerId = request.getParameter(ATTR_COMPUTERID);

		EditComputerValidator validator = new EditComputerValidator();
		errors = validator.validate(computerDTO);
		if (errors.isEmpty()) {
			Computer computer = ComputerMapper.createComputer(computerDTO);
			computerService.createComputer(computer);
			computerDTO = null;
		}
		return computerDTO;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
