package com.excilys.formationCDB.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formationCDB.controller.cli.CliComputerController;
import com.excilys.formationCDB.dto.mapper.ComputerMapper;
import com.excilys.formationCDB.exception.CustomSQLException;
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
	private static final String ATT_PAGE_SIZE = "pageSize";

	private ComputerService serviceController = ComputerService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			handleRequest(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int pageNumber = 1;
			if (request.getParameter("page") != null) {
				try {
					pageNumber = Integer.parseInt(request.getParameter("page"));
				} catch (NumberFormatException numberFormatExceptoin) {
				}
			}
			setGeneralAttributes(request, response, pageNumber);
		} catch (CustomSQLException e) {
			e.printStackTrace();
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
		if (request.getParameter("search") != null) {
			page = serviceController 
					.getPageByName(new Page<Computer>(pageSize, pageNumber, CliComputerController.COMPUTER_TABLE_NAME),
							request.getParameter("search"));
			request.setAttribute(ATT_COMPUTERDTO_LIST, ComputerMapper.createDashBoardComputerDTOList(page.getContent()));
			computerNb = serviceController.getComputerNumberbyName(request.getParameter("search"));
			request.setAttribute(ATT_COMPUTER_NAME, request.getParameter("search"));
		} else {
			page = serviceController 
					.getPage(new Page<Computer>(pageSize, pageNumber, CliComputerController.COMPUTER_TABLE_NAME));
					request.setAttribute(ATT_COMPUTERDTO_LIST, ComputerMapper.createDashBoardComputerDTOList(page.getContent()));
			computerNb = serviceController.getComputerNumber();
		}
		setIndexAttributes(request, page, computerNb);
		return computerNb;
	}

	private int getPageSize(HttpServletRequest request) {
		int pageSize = -1;
		if (request.getParameter("pageSize") != null) {
			try {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
				request.setAttribute(ATT_PAGE_SIZE, pageSize);
			} catch (NumberFormatException numberFormatExceptoin) {
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
		handleRequest(request, response);
	}

}
