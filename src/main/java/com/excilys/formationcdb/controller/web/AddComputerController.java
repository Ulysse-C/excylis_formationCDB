package com.excilys.formationcdb.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formationcdb.controller.web.request.AddComputerRequestVariable;
import com.excilys.formationcdb.controller.web.validator.AddComputerValidator;
import com.excilys.formationcdb.dto.web.AddComputerDTO;
import com.excilys.formationcdb.dto.web.mapper.WebCompanyMapper;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.service.CompanyService;
import com.excilys.formationcdb.service.ComputerService;

@Controller
public class AddComputerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATT_COMPUTERDTO = "computer";
	public static final String ATT_ERRORS = "errors";
	public static final String ATT_COMPANYLIST = "companyList";

	public static final String VIEW = "views/addComputer";

	private CompanyService companyService;
	private AddComputerValidator validator;
	private ComputerService computerService;

	@Autowired
	private AddComputerRequestVariable requestVariable;

	public AddComputerController(CompanyService companyService, ComputerService computerService, AddComputerValidator validator) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.validator = validator;
	}

	@GetMapping(value = "/addComputer")
	@ResponseBody
	protected ModelAndView getAddComputer() {
		handleRequest();
		return requestVariable.getModelAndView();
	}
	
	@PostMapping(value = "/addComputer")
	@ResponseBody
	protected ModelAndView postAddComputer(AddComputerDTO addComputerDTO) {
		Map<String, Object> mv = requestVariable.getModelAndView().getModel();
		Map<String, String> errors = validator.validate(addComputerDTO);
		if (errors.isEmpty()) {
			Computer computer = WebComputerMapper.createComputer(addComputerDTO);
			computerService.createComputer(computer);
			addComputerDTO = null;
		}
		mv.put(ATT_ERRORS, errors);
		mv.put(ATT_COMPUTERDTO, addComputerDTO);
		handleRequest();
		
		System.out.println(addComputerDTO);
		return requestVariable.getModelAndView();
	}
	
	private void handleRequest() {
		ModelAndView mv = requestVariable.getModelAndView();
		mv.setViewName(VIEW);
		addCompanyList();
	}

	

	private void addCompanyList() {
		requestVariable.getModelAndView().getModel().put(ATT_COMPANYLIST, WebCompanyMapper.createAddCompanyDTOList(companyService.getCompanyList()));

	}
}
