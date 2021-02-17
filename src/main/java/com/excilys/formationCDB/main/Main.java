package com.excilys.formationCDB.main;

import com.excilys.formationCDB.controller.CLIHandler;
import com.excilys.formationCDB.controller.CompanyController;
import com.excilys.formationCDB.controller.ComputerController;
import com.excilys.formationCDB.ui.CLI;

public class Main {

	public static void main(String[] args) {
		CLI cli = new CLI();
		CLIHandler cliHandler = new CLIHandler();
		CompanyController companyController = new CompanyController();
		ComputerController computerController = new ComputerController();
		cliHandler.setCompanyController(companyController);
		cliHandler.setComputerController(computerController);
		cli.addCliHandler(cliHandler);
		cli.startReadingUserInput();
	}
}
