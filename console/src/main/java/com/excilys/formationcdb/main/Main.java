package com.excilys.formationcdb.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.formationcdb.config.SpringConfig;
import com.excilys.formationcdb.ui.cli.Cli;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		final Cli cli = context.getBean(Cli.class);
		cli.startReadingUserInput();
		((ConfigurableApplicationContext) context).close();
	}
}
