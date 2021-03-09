package com.excilys.formationcdb.main;

import com.excilys.formationcdb.controller.cli.CliHandler;
import com.excilys.formationcdb.ui.cli.CLI;

public class Main {
	public static void main(String[] args) {
		CLI cli = new CLI();
		CliHandler cliHandler = new CliHandler();
		cli.addCliHandler(cliHandler);
		cli.startReadingUserInput();
	}
}
