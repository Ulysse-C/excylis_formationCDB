package com.excilys.formationcdb.controller.servlet.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.controller.servlet.validator.EditComputerValidator;
import com.excilys.formationcdb.dto.web.EditComputerDTO;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.service.ComputerService;

@Component
public class EditComputerForm {

	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String ATTR_COMPUTERID = "computerId";

	private ComputerService computerService;
	private EditComputerValidator validator;

	private Map<String, String> errors;

	public EditComputerForm(ComputerService computerService, EditComputerValidator validator) {
		errors = new HashMap<>();
		this.computerService = computerService;
		this.validator = validator;
	}

	public EditComputerDTO editComputer(HttpServletRequest request) {
		EditComputerDTO computerDTO = buildComputerDTO(request);
		errors = validator.validate(computerDTO);
		if (errors.isEmpty()) {
			Computer computer = WebComputerMapper.createComputer(computerDTO);
			try {
				computerService.updateComputer(computer);
			} catch (NothingSelectedException e) {
				CDBLogger.logError(e);
			}
			computerDTO = null;
		}
		return computerDTO;
	}

	private EditComputerDTO buildComputerDTO(HttpServletRequest request) {
		EditComputerDTO computerDTO = new EditComputerDTO();
		computerDTO.computerName = request.getParameter(INPUT_NAME);
		computerDTO.companyId = request.getParameter(INPUT_COMPANYID);
		computerDTO.introducedDate = request.getParameter(INPUT_INTRODUCED);
		computerDTO.discontinuedDate = request.getParameter(INPUT_DISCONTINUED);
		computerDTO.computerId = request.getParameter(ATTR_COMPUTERID);
		return computerDTO;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
