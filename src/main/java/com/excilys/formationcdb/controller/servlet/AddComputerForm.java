package com.excilys.formationcdb.controller.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationcdb.controller.servlet.validator.AddComputerValidator;
import com.excilys.formationcdb.dto.AddComputerDTO;
import com.excilys.formationcdb.dto.mapper.ComputerMapper;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.service.ComputerService;

public class AddComputerForm {

	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String SQL_ERRORS = "sqlErrors";

	private static ComputerService computerService = ComputerService.getInstance();

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
			Computer computer = ComputerMapper.createComputer(computerDTO);
			computerService.createComputer(computer);
			computerDTO = null;

		}
		return computerDTO;
	}
}
