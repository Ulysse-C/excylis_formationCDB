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

import com.excilys.formationcdb.dto.EditComputerDTO;
import com.excilys.formationcdb.dto.mapper.CompanyMapper;
import com.excilys.formationcdb.dto.mapper.ComputerMapper;
import com.excilys.formationcdb.logger.CDBLogger;
import com.excilys.formationcdb.service.CompanyService;
import com.excilys.formationcdb.service.ComputerService;

/**
 * Servlet implementation class EditComputerServlet
 */
@Component
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATT_ERRORS = "errors";
	public static final String ATT_COMPANYLIST = "companyList";
	public static final String INPUT_ID = "computerId";
	public static final String ATT_COMPUTER_DTO = "computerDTO";
	public static final String VIEW = "/WEB-INF/views/editComputer.jsp";

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EditComputerForm form = new EditComputerForm();
		EditComputerDTO computerDTO = form.editComputer(request);

		request.setAttribute(ATT_ERRORS, form.getErrors());
		request.setAttribute(ATT_COMPUTER_DTO, computerDTO);
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
		addCompanyList(request);
		if (request.getParameter(INPUT_ID) != null) {
			try {
				int computerId = Integer.parseInt(request.getParameter(INPUT_ID));
				EditComputerDTO computerDTO = ComputerMapper
						.createEditComputerDTO(computerService.getComputerById(computerId));
				request.setAttribute(ATT_COMPUTER_DTO, computerDTO);
			} catch (NumberFormatException numberFormatExceptoin) {
				CDBLogger.logInfo(numberFormatExceptoin);
			}
		}
		try {
			this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		} catch (ServletException | IOException exception) {
			CDBLogger.logError(exception);
		}
	}

	private void addCompanyList(HttpServletRequest request) {
		request.setAttribute(ATT_COMPANYLIST, CompanyMapper.createAddCompanyDTOList(companyService.getCompanyList()));

	}

}
