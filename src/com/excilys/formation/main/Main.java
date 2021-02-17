package com.excilys.formation.main;

import com.excilys.formation.controller.CLIHandler;
import com.excilys.formation.controller.CompanyController;
import com.excilys.formation.controller.ComputerController;
import com.excilys.formation.ui.CLI;

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
