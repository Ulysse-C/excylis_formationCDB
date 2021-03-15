package com.excilys.formationcdb.controller.servlet.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.controller.servlet.validator.AddComputerValidator;
import com.excilys.formationcdb.dto.web.AddComputerDTO;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.service.ComputerService;

@Component
public class AddComputerForm {

	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String SQL_ERRORS = "sqlErrors";

	private ComputerService computerService;
	private AddComputerValidator validator;
	private Map<String, String> errors;

	public AddComputerForm(ComputerService computerService, AddComputerValidator validator) {
		errors = new HashMap<String, String>();
		this.computerService = computerService;
		this.validator = validator;
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
		errors = validator.validate(computerDTO);
		if (errors.isEmpty()) {
			Computer computer = WebComputerMapper.createComputer(computerDTO);
			computerService.createComputer(computer);
			computerDTO = null;

		}
		return computerDTO;
	}
}
