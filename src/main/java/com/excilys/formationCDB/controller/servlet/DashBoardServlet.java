package com.excilys.formationCDB.controller.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formationCDB.controller.cli.CliComputerController;
import com.excilys.formationCDB.dto.mapper.ComputerMapper;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.exception.NoComputerSelectedException;
import com.excilys.formationCDB.logger.CDBLogger;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;
import com.excilys.formationCDB.service.ComputerService;

/**
 * Servlet implementation class DashBoardServlet
 */
//@WebServlet("/DashBoardServlet")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String VIEW = "/WEB-INF/views/dashboard.jsp";
	public static final String ATT_PAGE_NB = "pageNumber";
	public static final String ATT_COMPUTER_NB = "numberComputers";
	public static final String ATT_COMPUTERDTO_LIST = "computerList";
	public static final String ATT_PAGEINDEX_FROM = "pageIndexFrom";
	public static final String ATT_PAGEINDEX_TO = "pageIndexTo";
	public static final String ATT_COMPUTER_NAME = "computerSearch";
	public static final String INPUT_PAGE = "page";
	public static final String INPUT_SEARCH = "search";
	public static final String INPUT_PAGE_SIZE = "pageSize";
	private static final String ATT_PAGE_SIZE = "pageSize";

	private ComputerService serviceComputer = ComputerService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			handleRequest(request, response);
		} catch (ServletException exception) {
			CDBLogger.logError(exception);
		} catch (IOException exception) {
			CDBLogger.logError(exception);
		}
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int pageNumber = 1;
			if (request.getParameter(INPUT_PAGE) != null) {
				try {
					pageNumber = Integer.parseInt(request.getParameter(INPUT_PAGE));
				} catch (NumberFormatException numberFormatExceptoin) {
					CDBLogger.logInfo(numberFormatExceptoin);
				}
			}
			setGeneralAttributes(request, response, pageNumber);
		} catch (CustomSQLException exception) {
			CDBLogger.logError(exception);
		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	private void setGeneralAttributes(HttpServletRequest request, HttpServletResponse response, int pageNumber)
			throws CustomSQLException {
		request.setAttribute(VIEW, response);
		request.setAttribute(ATT_PAGE_NB, pageNumber);
		int computerNb;
		computerNb = setComputerList(request, pageNumber);
		request.setAttribute(ATT_COMPUTER_NB, computerNb);
	}

	private void setIndexAttributes(HttpServletRequest request, Page page, int computerNb) {
		request.setAttribute(ATT_PAGEINDEX_FROM, page.getPageIndexFrom());
		request.setAttribute(ATT_PAGEINDEX_TO, page.getPageIndexTo(computerNb));
	}

	private int setComputerList(HttpServletRequest request, int pageNumber) throws CustomSQLException {
		int computerNb;
		int pageSize = getPageSize(request);
		Page<Computer> page;
		if (request.getParameter(INPUT_SEARCH) != null) {
			page = serviceComputer.getPageByName(
					new Page<Computer>(pageSize, pageNumber, CliComputerController.COMPUTER_TABLE_NAME),
					request.getParameter(INPUT_SEARCH));
			request.setAttribute(ATT_COMPUTERDTO_LIST,
					ComputerMapper.createDashBoardComputerDTOList(page.getContent()));
			computerNb = serviceComputer.getComputerNumberbyName(request.getParameter(INPUT_SEARCH));
			request.setAttribute(ATT_COMPUTER_NAME, request.getParameter(INPUT_SEARCH));
		} else {
			page = serviceComputer
					.getPage(new Page<Computer>(pageSize, pageNumber, CliComputerController.COMPUTER_TABLE_NAME));
			request.setAttribute(ATT_COMPUTERDTO_LIST,
					ComputerMapper.createDashBoardComputerDTOList(page.getContent()));
			computerNb = serviceComputer.getComputerNumber();
		}
		setIndexAttributes(request, page, computerNb);
		return computerNb;
	}

	private int getPageSize(HttpServletRequest request) {
		int pageSize = -1;
		if (request.getParameter(INPUT_PAGE_SIZE) != null) {
			try {
				pageSize = Integer.parseInt(request.getParameter(INPUT_PAGE_SIZE));
				request.setAttribute(ATT_PAGE_SIZE, pageSize);
			} catch (NumberFormatException numberFormatExceptoin) {
				CDBLogger.logInfo(numberFormatExceptoin);
			}
		}
		return pageSize;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		deleteSelected(request);

		handleRequest(request, response);
	}

	private void deleteSelected(HttpServletRequest request) {
		if (request.getParameter("selection") != null) {
			List<String> idToDelete = Arrays.asList(request.getParameter("selection").split(","));
			for (String id : idToDelete) {
				try {
					serviceComputer.deleteComputerById(Integer.parseInt(id));
				} catch (NumberFormatException numFormat) {
						CDBLogger.logError(numFormat);
				} catch (CustomSQLException sqlException) {
					CDBLogger.logError(sqlException);
				} catch (NoComputerSelectedException noComputerException) {
					CDBLogger.logError(noComputerException);

				}
			}
		}
	}

}
