package com.excilys.formation.main;

import com.excilys.formation.model.LoadDriver;
import com.excilys.formation.ui.CLI;

public class Main {

	public static void main(String[] args) {
		CLI cli = new CLI();
		//cli.start();
		LoadDriver ld = new LoadDriver();
		ld.test();

	}

}
