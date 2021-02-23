package com.excilys.formationCDB.controller.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formationCDB.controller.ComputerController;
import com.excilys.formationCDB.controller.servlet.validator.addComputerValidator;
import com.excilys.formationCDB.exception.CompanyKeyInvalidException;
import com.excilys.formationCDB.exception.CustomSQLException;
import com.excilys.formationCDB.model.Computer;

public class AddComputerForm {
	private ComputerController computerController;

	
	public static final String INPUT_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANYID = "companyId";
	public static final String ATT_ERREURS = "errors";
	public static final String ATT_RESULTAT = "result";

	private String result;
	private Map<String, String> errors;

	public AddComputerForm() {
		computerController = ComputerController.getInstance();
		errors = new HashMap<String, String>();
	}
	
	public String getResultat() {
		return result;
	}

	public Map<String, String> getErreurs() {
		return errors;
	}

	public Computer addComputer(HttpServletRequest request) {
		String name = request.getParameter(INPUT_NAME);
		String introduced = request.getParameter(INPUT_INTRODUCED);
		String discontinued = request.getParameter(INPUT_DISCONTINUED);
		String companyID = request.getParameter(INPUT_COMPANYID);

		addComputerValidator validator = new addComputerValidator();
		Computer computer = new Computer();
		try {
			validator.validateName(name);
			computer.setName(name);
		} catch (Exception e) {
			errors.put(INPUT_NAME, e.getMessage());
		}

		try {
			validator.validateDates(introduced, discontinued);
			computer.setDiscontinued(discontinued);
			computer.setIntroduced(introduced);
		} catch (Exception e) {
			// erreurs.put( CHAMP_PASS, e.getMessage() );
		}

		try {
			validator.validateCompanyID(companyID);
			computer.setCompanyID(companyID);
		} catch (Exception e) {
			//errors.put(INPUT_COMPANYID, e.getMessage());
		}
		
		if (errors.isEmpty()) {
			try {
				computerController.createComputer(computer);
			} catch (CustomSQLException | CompanyKeyInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = "Computer added";
		} else {
			result = "Computer not added";
		}

		/* Stockage du résultat et des messages d'erreur dans l'objet request */
		request.setAttribute(ATT_ERREURS, errors);
		request.setAttribute(ATT_RESULTAT, result);
		return computer;
	}
}
