package com.excilys.formationCDB.exception;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class InvalidWebInputException extends Exception {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CompanyKeyInvalidException.class);

	public InvalidWebInputException(String message) {
		super(message);
		logger.error("Invalid input: " + message);

	}
}
