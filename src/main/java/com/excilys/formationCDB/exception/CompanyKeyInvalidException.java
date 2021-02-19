package com.excilys.formationCDB.exception;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class CompanyKeyInvalidException extends Exception {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(CompanyKeyInvalidException.class);

	public CompanyKeyInvalidException() {
		logger.error("The key of the company does not correspond to any company in the database");
	}
}
