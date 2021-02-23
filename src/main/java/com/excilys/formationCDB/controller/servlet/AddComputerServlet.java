package com.excilys.formationCDB.controller.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formationCDB.controller.CompanyController;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Company;
import com.excilys.formationCDB.model.Computer;

/**
 * Servlet implementation class addComputerServlet
 */
@WebServlet("/addComputerServlet")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		addCompanyList(request);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AddComputerForm form = new AddComputerForm();
		Computer computer = form.addComputer(request);
		
		request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_COMPUTER, computer );
        addCompanyList(request);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	private void addCompanyList(HttpServletRequest request) {
		try {
			request.setAttribute(ATT_COMPANYLIST, companyController.getCompanyList());
		} catch (CustomSQLException e) {
			e.printStackTrace();
		}
	}
}
