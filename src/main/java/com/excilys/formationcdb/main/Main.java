package com.excilys.formationcdb.main;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formationcdb.SpringConfig;
import com.excilys.formationcdb.ui.cli.Cli;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		Cli cli = context.getBean(Cli.class);
		cli.startReadingUserInput();
		((ConfigurableApplicationContext) context).close();
	}
}
