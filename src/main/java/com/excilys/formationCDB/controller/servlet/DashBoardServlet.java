package com.excilys.formationCDB.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formationCDB.controller.CompanyController;
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

    public static final String VIEW         = "/WEB-INF/views/dashboard.jsp";

	private CompanyController companyController;
	private ComputerController computerController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashBoardServlet() {
		super();
		this.companyController = CompanyController.getInstance();
		this.computerController = ComputerController.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			int pageNumber;
			try  {
			pageNumber  = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException numberFormatExceptoin) {
				pageNumber = 1;
			}
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("numberComputers", computerController.getComputerNumber() );
			request.setAttribute("computerPage", computerController.getPage(new Page<Computer>(10, pageNumber, "computer")).getContent());
		} catch (CustomSQLException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
