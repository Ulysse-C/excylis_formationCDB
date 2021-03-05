package com.excilys.formationcdb.main;

import com.excilys.formationcdb.controller.cli.CLIHandler;
import com.excilys.formationcdb.ui.cli.CLI;

public class Main {
	public static void main(String[] args) {
		
		 CLI cli = new CLI(); CLIHandler cliHandler = new CLIHandler();
		 cli.addCliHandler(cliHandler); cli.startReadingUserInput();
		 
	}

	static String table;

}
