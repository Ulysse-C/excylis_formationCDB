package com.excilys.formationcdb.controller.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formationcdb.controller.cli.CliComputerController;
import com.excilys.formationcdb.controller.web.request.DashBoardRequestVariable;
import com.excilys.formationcdb.controller.web.session.DashBoardSessionVariable;
import com.excilys.formationcdb.controller.web.validator.AddComputerValidator;
import com.excilys.formationcdb.controller.web.validator.EditComputerValidator;
import com.excilys.formationcdb.dto.web.AddComputerDTO;
import com.excilys.formationcdb.dto.web.EditComputerDTO;
import com.excilys.formationcdb.dto.web.mapper.WebCompanyMapper;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;
import com.excilys.formationcdb.service.CompanyService;
import com.excilys.formationcdb.service.ComputerService;

@Controller
public class ComputerController {

	private static final long serialVersionUID = 1L;

	public static final String ATT_COMPUTER_DTO = "computerDTO";
	public static final String ATT_COMPUTERDTO = "computer";
	public static final String ATT_ERRORS = "errors";
	public static final String ATT_COMPANYLIST = "companyList";
	public static final String ATT_PAGE_NB = "pageNumber";
	public static final String ATT_COMPUTER_NB = "numberComputers";
	public static final String ATT_COMPUTERDTO_LIST = "computerList";
	public static final String ATT_PAGEINDEX_FROM = "pageIndexFrom";
	public static final String ATT_PAGEINDEX_TO = "pageIndexTo";
	public static final String ATT_COMPUTER_NAME = "computerSearch";
	public static final String INPUT_PAGE = "page";
	public static final String INPUT_SEARCH = "search";
	public static final String INPUT_PAGE_SIZE = "pageSize";
	public static final String INPUT_ID = "computerId";

	public static final String VIEW_ADD = "views/addComputer";
	public static final String VIEW_EDIT = "/views/editComputer";
	public static final String VIEW_DASHBOARD = "views/dashboard";

	@Autowired
	private DashBoardSessionVariable sessionVariable;

	@Autowired
	private DashBoardRequestVariable requestVariable;

	private ComputerService computerService;
	private CompanyService companyService;
	private AddComputerValidator addValidator;
	private EditComputerValidator editValidator;

	public ComputerController(CompanyService companyService, ComputerService computerService,
			AddComputerValidator addValidator, EditComputerValidator editValidator) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.addValidator = addValidator;
		this.editValidator = editValidator;
	}

	@GetMapping(value = "/dashboard")
	public ModelAndView getDashBoard(@RequestParam(required = false) String page,
			@RequestParam(required = false) String search) {
		setPageNumber(page);
		setSearch(search);
		handleDashBoard(page);
		return requestVariable.getModelAndView();
	}

	private void setSearch(String search) {
		if (search != null) {
			requestVariable.setSearch(search);
		}
	}

	private void setPageNumber(String page) {
		if (page != null) {
			try {
				requestVariable.setPageNumber(Integer.parseInt(page));
			} catch (NumberFormatException numberFormatExceptoin) {
				CDBLogger.logInfo(numberFormatExceptoin);
			}
		}
	}

	@PostMapping(value = "/dashboard")
	public ModelAndView postDashBoard(@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String selection, @RequestParam(required = false) String orderAttribute,
			@RequestParam(required = false) String page) {
		deleteSelected(selection);
		setPageSize(pageSize);
		setPageOrdering(orderAttribute);
		handleDashBoard(page);
		return requestVariable.getModelAndView();

	}

	private void setPageOrdering(String orderAttribute) {
		if (orderAttribute != null) {
			try {
				Page.SortAttribute sortAttribute = Page.SortAttribute.valueOf(orderAttribute);
				if (sortAttribute != sessionVariable.getSortAttribute()) {
					sessionVariable.setSortAttribute(sortAttribute);
				} else {
					sessionVariable.flipSortOrder();
				}
			} catch (NumberFormatException numberFormatExceptoin) {
				CDBLogger.logInfo(numberFormatExceptoin);
			} catch (java.lang.IllegalArgumentException illegal) {
				CDBLogger.logInfo(illegal);
			}
		}

	}

	private void setPageSize(String pageSize) {
		if (pageSize != null) {
			try {
				sessionVariable.setPageSize(Integer.parseInt(pageSize));
			} catch (NumberFormatException numberFormatExceptoin) {
				CDBLogger.logInfo(numberFormatExceptoin);
			}
		}
	}

	private void handleDashBoard(String page) {
		setPageNumber(page);
		setRequestAttributes();
		setGeneralAttributes();
	}

	private void setRequestAttributes() {
		requestVariable.setComputerNumber(computerService.getComputerNumberbyName(requestVariable.getSearch()));
	}

	private void setGeneralAttributes() {
		ModelAndView mv = requestVariable.getModelAndView();
		mv.setViewName(VIEW_DASHBOARD);
		setComputerList();
		mv.getModel().put(ATT_PAGE_NB, requestVariable.getPageNumber());
		mv.getModel().put(ATT_COMPUTER_NB, requestVariable.getComputerNumber());
	}

	private void setIndexAttributes(Page<Computer> page) {
		Map<String, Object> mv = requestVariable.getModelAndView().getModel();
		mv.put(ATT_PAGEINDEX_FROM, page.getPageIndexFrom());
		mv.put(ATT_PAGEINDEX_TO, page.getPageIndexTo(requestVariable.getComputerNumber()));
	}

	private void setComputerList() {
		Map<String, Object> mv = requestVariable.getModelAndView().getModel();
		Page<Computer> page;
		page = new Page<Computer>(sessionVariable.getPageSize(), requestVariable.getPageNumber(),
				CliComputerController.COMPUTER_TABLE_NAME);
		page.setSearch(requestVariable.getSearch());
		setSortAttributes(page);
		page = computerService.getPage(page);
		mv.put(ATT_COMPUTERDTO_LIST, WebComputerMapper.createDashBoardComputerDTOList(page.getContent()));
		computerService.getComputerNumberbyName(requestVariable.getSearch());
		mv.put(ATT_COMPUTER_NAME, requestVariable.getSearch());
		setIndexAttributes(page);
	}

	private void setSortAttributes(Page<Computer> page) {
		page.setSortName(sessionVariable.getSortAttribute());
		page.setSortOrder(sessionVariable.getSortOrder());
	}

	private void deleteSelected(String selection) {
		if (selection != null) {
			List<String> idToDelete = Arrays.asList(selection.split(","));
			for (String id : idToDelete) {
				try {
					computerService.deleteComputerById(Integer.parseInt(id));
				} catch (NumberFormatException numFormat) {
					CDBLogger.logError(numFormat);
				} catch (NothingSelectedException noComputerException) {
					CDBLogger.logError(noComputerException);

				}
			}
		}
	}

	public DashBoardSessionVariable getDashBoardSessionVariable() {
		return this.sessionVariable;
	}

	@GetMapping(value = "/addComputer")
	protected ModelAndView getAddComputer() {
		handleRequestAdd();
		return requestVariable.getModelAndView();
	}

	@PostMapping(value = "/addComputer")
	protected ModelAndView postAddComputer(AddComputerDTO addComputerDTO) {
		Map<String, Object> mv = requestVariable.getModelAndView().getModel();
		Map<String, String> errors = addValidator.validate(addComputerDTO);
		if (errors.isEmpty()) {
			Computer computer = WebComputerMapper.createComputer(addComputerDTO);
			computerService.createComputer(computer);
			addComputerDTO = null;
		}
		mv.put(ATT_ERRORS, errors);
		mv.put(ATT_COMPUTERDTO, addComputerDTO);
		handleRequestAdd();
		System.out.println(addComputerDTO);
		return requestVariable.getModelAndView();
	}

	private void handleRequestAdd() {
		ModelAndView mv = requestVariable.getModelAndView();
		mv.setViewName(VIEW_ADD);
		addCompanyList();
	}

	private void addCompanyList() {
		requestVariable.getModelAndView().getModel().put(ATT_COMPANYLIST,
				WebCompanyMapper.createAddCompanyDTOList(companyService.getCompanyList()));
	}

	@GetMapping(value = "/editComputer")
	protected ModelAndView getEditComputer(@RequestParam(required = false) String computerId) {
		handleRequestEdit(computerId);
		return requestVariable.getModelAndView();
	}

	@PostMapping(value = "/editComputer")
	protected ModelAndView postAddComputer(@RequestParam(required = false) String computerId,
			EditComputerDTO editComputerDTO) {
		editComputer(editComputerDTO);
		handleRequestEdit(computerId);
		return requestVariable.getModelAndView();

	}

	private void editComputer(EditComputerDTO editComputerDTO) {
		Map<String, Object> mv = requestVariable.getModelAndView().getModel();
		Map<String, String> errors = editValidator.validate(editComputerDTO);
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

	private void handleRequestEdit(String computerIdString) {
		addCompanyList();
		addComputerSelected(computerIdString);
		requestVariable.getModelAndView().setViewName(VIEW_EDIT);
	}

	private void addComputerSelected(String computerIdString) {
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
	}
}
