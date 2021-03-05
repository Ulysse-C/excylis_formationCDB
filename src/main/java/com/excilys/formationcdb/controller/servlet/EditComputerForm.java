package com.excilys.formationcdb.controller.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationcdb.controller.servlet.validator.EditComputerValidator;
import com.excilys.formationcdb.dto.EditComputerDTO;
import com.excilys.formationcdb.dto.mapper.ComputerMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.service.ComputerService;

public class EditComputerForm {

	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String ATTR_COMPUTERID = "computerId";
	
	private static ComputerService computerService = ComputerService.getInstance();
	private Map<String, String> errors = new HashMap<>();

	public EditComputerDTO editComputer(HttpServletRequest request) {
		EditComputerDTO computerDTO = new EditComputerDTO();
		computerDTO.computerName = request.getParameter(INPUT_NAME);
		computerDTO.companyId = request.getParameter(INPUT_COMPANYID);
		computerDTO.introducedDate = request.getParameter(INPUT_INTRODUCED);
		computerDTO.discontinuedDate = request.getParameter(INPUT_DISCONTINUED);
		computerDTO.computerId = request.getParameter(ATTR_COMPUTERID);
		EditComputerValidator validator = EditComputerValidator.getInstance();
		errors = validator.validate(computerDTO);
		if (errors.isEmpty()) {
			Computer computer = ComputerMapper.createComputer(computerDTO);
			try {
				computerService.updateComputer(computer);
			} catch (NothingSelectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			computerDTO = null;
		}
		return computerDTO;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
