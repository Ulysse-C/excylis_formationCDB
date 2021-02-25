package com.excilys.formationCDB.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formationCDB.controller.ComputerController;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.model.Page;


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

	private ComputerController computerController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashBoardServlet() {
		super();
		this.computerController = ComputerController.getInstance();
	}

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
		request.setAttribute(ATT_PAGEINDEX_FROM, computerController.getPageIndexFrom(pageNumber));
		request.setAttribute(ATT_PAGEINDEX_TO, computerController.getPageIndexTo(pageNumber, computerNb));
	}
	
	

	private int setComputerList(HttpServletRequest request, int pageNumber) throws CustomSQLException {
		int computerNb;
		if (request.getParameter("search") != null) {
			request.setAttribute(ATT_COMPUTERDTO_LIST,
					computerController
							.getPageByName(new Page<Computer>(ComputerController.PAGE_SIZE, pageNumber, ComputerController.COMPUTER_TABLE_NAME),
									request.getParameter("search"))
							.getContent());
			computerNb = computerController.getComputerNumberbyName(request.getParameter("search"));
		} else {
			request.setAttribute(ATT_COMPUTERDTO_LIST, computerController
					.getPage(new Page<Computer>(ComputerController.PAGE_SIZE, pageNumber, ComputerController.COMPUTER_TABLE_NAME)).getContent());
			computerNb = computerController.getComputerNumber();
		}
		request.setAttribute(ATT_COMPUTER_NAME, request.getParameter("search"));
		return computerNb;
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
