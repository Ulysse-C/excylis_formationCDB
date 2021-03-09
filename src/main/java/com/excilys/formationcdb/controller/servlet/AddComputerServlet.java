package com.excilys.formationcdb.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formationcdb.dto.AddComputerDTO;
import com.excilys.formationcdb.dto.mapper.CompanyMapper;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.service.CompanyService;

/**
 * Servlet implementation class addComputerServlet
 */
@Component
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATT_COMPUTERDTO = "computer";
	public static final String ATT_ERRORS = "errors";
	public static final String ATT_COMPANYLIST = "companyList";

	public static final String VIEW = "/WEB-INF/views/addComputer.jsp";

	@Autowired
	private CompanyService serviceCompany;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		handleRequest(request, response);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
	}


	private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
		addCompanyList(request);
		try {
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch (ServletException exception) {
			CDBLogger.logError(exception);
		} catch (IOException exception) {
			CDBLogger.logError(exception);
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
			request.setAttribute(ATT_COMPANYLIST,CompanyMapper.createAddCompanyDTOList(serviceCompany.getCompanyList()) );

	}
}