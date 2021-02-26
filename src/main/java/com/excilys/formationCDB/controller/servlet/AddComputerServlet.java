package com.excilys.formationCDB.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formationCDB.controller.cli.CliCompanyController;
import com.excilys.formationCDB.dto.AddComputerDTO;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Computer;
import com.excilys.formationCDB.service.CompanyService;

/**
 * Servlet implementation class addComputerServlet
 */
//@WebServlet("/addComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATT_COMPUTERDTO = "computer";
	public static final String ATT_ERRORS = "errors";
	public static final String ATT_COMPANYLIST = "companyList";

	public static final String VIEW = "/WEB-INF/views/addComputer.jsp";

	private CompanyService serviceController = CompanyService.getInstance();

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
		AddComputerDTO computerDTO = form.addComputer(request);

		request.setAttribute(ATT_ERRORS, form.getErrors());
		request.setAttribute(ATT_COMPUTERDTO, computerDTO);
		handleRequest(request, response);
	}

	private void addCompanyList(HttpServletRequest request) {
		try {
			request.setAttribute(ATT_COMPANYLIST, serviceController.getCompanyList());
		} catch (CustomSQLException e) {
			e.printStackTrace();
		}
	}
}
