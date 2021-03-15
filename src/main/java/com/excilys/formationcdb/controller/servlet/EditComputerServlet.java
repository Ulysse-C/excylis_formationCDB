package com.excilys.formationcdb.controller.servlet;

import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formationcdb.controller.servlet.validator.EditComputerValidator;
import com.excilys.formationcdb.dto.web.EditComputerDTO;
import com.excilys.formationcdb.dto.web.mapper.WebCompanyMapper;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.service.CompanyService;
import com.excilys.formationcdb.service.ComputerService;

@Controller
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATT_ERRORS = "errors";
	public static final String ATT_COMPANYLIST = "companyList";
	public static final String INPUT_ID = "computerId";
	public static final String ATT_COMPUTER_DTO = "computerDTO";
	public static final String VIEW = "/views/editComputer";

	private ComputerService computerService;
	private CompanyService companyService;
	private EditComputerValidator validator;

	@Autowired
	private EditComputerRequestVariable requestVariable;

	public EditComputerServlet(ComputerService computerService, CompanyService companyService,
			EditComputerValidator validator) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.validator = validator;
	}

	@GetMapping(value = "/editComputer")
	@ResponseBody
	protected ModelAndView getEditComputer(@RequestParam(required = false) String computerId) {
		handleRequest(computerId);
		return requestVariable.getModelAndView();
	}

	@PostMapping(value = "/editComputer")
	@ResponseBody
	protected ModelAndView postAddComputer(@RequestParam(required = false) String computerId,
			EditComputerDTO editComputerDTO) {
		editComputer(editComputerDTO);
		handleRequest(computerId);
		return requestVariable.getModelAndView();

	}

	private void editComputer(EditComputerDTO editComputerDTO) {
		Map<String, Object> mv = requestVariable.getModelAndView().getModel();
		Map<String, String> errors = validator.validate(editComputerDTO);
		if (errors.isEmpty()) {
			Computer computer = WebComputerMapper.createComputer(editComputerDTO);
			try {
				computerService.updateComputer(computer);
			} catch (NothingSelectedException e) {
				CDBLogger.logError(e);
			}
			editComputerDTO = null;
		}
		mv.put(ATT_ERRORS, errors);
		mv.put(ATT_COMPUTER_DTO, editComputerDTO);
	}

	private void handleRequest(String computerIdString) {
		addCompanyList();
		if (computerIdString != null) {
			try {
				int computerId = Integer.parseInt(computerIdString);
				EditComputerDTO computerDTO = WebComputerMapper
						.createEditComputerDTO(computerService.getComputerById(computerId));
				requestVariable.getModelAndView().getModel().put(ATT_COMPUTER_DTO, computerDTO);
			} catch (NumberFormatException numberFormatExceptoin) {
				CDBLogger.logInfo(numberFormatExceptoin);
			}
		}
		requestVariable.getModelAndView().setViewName(VIEW);
	}

	private void addCompanyList() {
		requestVariable.getModelAndView().getModel().put(ATT_COMPANYLIST,
				WebCompanyMapper.createAddCompanyDTOList(companyService.getCompanyList()));
	}

}
