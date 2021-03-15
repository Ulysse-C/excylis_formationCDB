package com.excilys.formationcdb.controller.servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formationcdb.controller.cli.CliComputerController;
import com.excilys.formationcdb.dto.web.mapper.WebComputerMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;
import com.excilys.formationcdb.service.ComputerService;

@Controller
public class DashBoardServlet {

	private static final long serialVersionUID = 1L;

	public static final String VIEW = "views/dashboard";
	public static final String ATT_PAGE_NB = "pageNumber";
	public static final String ATT_COMPUTER_NB = "numberComputers";
	public static final String ATT_COMPUTERDTO_LIST = "computerList";
	public static final String ATT_PAGEINDEX_FROM = "pageIndexFrom";
	public static final String ATT_PAGEINDEX_TO = "pageIndexTo";
	public static final String ATT_COMPUTER_NAME = "computerSearch";
	public static final String INPUT_PAGE = "page";
	public static final String INPUT_SEARCH = "search";
	public static final String INPUT_PAGE_SIZE = "pageSize";

	private ComputerService computerService;

	@Autowired
	private DashBoardSessionVariable sessionVariable;

	@Autowired
	private DashBoardRequestVariable requestVariable;
	
	public DashBoardServlet(ComputerService computerService) {
		this.computerService = computerService;
	}

	@GetMapping(value = "/dashboard")
	@ResponseBody
	public ModelAndView getDashBoard(@RequestParam(required = false) String page,
			@RequestParam(required = false) String search) {
		setPageNumber(page);
		setSearch(search);
		handleRequest();
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
	@ResponseBody
	public ModelAndView postDashBoard(@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String selection, @RequestParam(required = false) String orderAttribute) {
		deleteSelected(selection);
		setPageSize(pageSize);
		setPageOrdering(orderAttribute);
		handleRequest();
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

	private void handleRequest() {
		setRequestAttributes();
		setGeneralAttributes();
	}

	private void setRequestAttributes() {
		requestVariable.setComputerNumber(computerService.getComputerNumberbyName(requestVariable.getSearch()));
	}

	private void setGeneralAttributes() {
		ModelAndView mv = requestVariable.getModelAndView();
		mv.setViewName(VIEW);
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

}
