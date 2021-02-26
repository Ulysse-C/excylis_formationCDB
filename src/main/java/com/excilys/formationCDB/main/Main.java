package com.excilys.formationCDB.main;

import com.excilys.formationCDB.controller.cli.CLIHandler;
import com.excilys.formationCDB.ui.cli.CLI;

public class Main {
	public static void main(String[] args) {
		
		 CLI cli = new CLI(); CLIHandler cliHandler = new CLIHandler();
		 cli.addCliHandler(cliHandler); cli.startReadingUserInput();
		 
	}

	static String table;

}
