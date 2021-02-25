package com.excilys.formationCDB.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formationCDB.controller.CompanyController;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Computer;

/**
 * Servlet implementation class addComputerServlet
 */
//@WebServlet("/addComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATT_COMPUTER = "computer";
	public static final String ATT_FORM = "form";
	public static final String ATT_COMPANYLIST = "companyList";

	public static final String VIEW = "/WEB-INF/views/addComputer.jsp";

	private CompanyController companyController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
		companyController = CompanyController.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
		addCompanyList(request);
		try {
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		AddComputerForm form = new AddComputerForm();
		Computer computer = form.addComputer(request);

		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_COMPUTER, computer);
		handleRequest(request, response);
	}

	private void addCompanyList(HttpServletRequest request) {
		try {
			request.setAttribute(ATT_COMPANYLIST, companyController.getCompanyList());
		} catch (CustomSQLException e) {
			e.printStackTrace();
		}
	}
}
