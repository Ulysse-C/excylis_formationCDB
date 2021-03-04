package com.excilys.formationCDB.logger;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class CDBLogger {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Exception.class);

	//je ne sais pas si la granularité apporte quelque chose
	/*
	private static final Logger invalidWebInputLogger = (Logger) LoggerFactory.getLogger(InvalidWebInputException.class);

	private static final Logger customSQLLogger = (Logger) LoggerFactory.getLogger(CustomSQLException.class);
	private static final Logger invalidInputCLILogger = (Logger) LoggerFactory.getLogger(InvalidInputCLIHandlerException.class);
	private static final Logger companyKeyInvalidLogger = (Logger) LoggerFactory.getLogger(CompanyKeyInvalidException.class);
	private static final Logger noComputerSelectedLogger = (Logger) LoggerFactory.getLogger(NoComputerSelectedException.class);
	*/
	public static void logError(Exception exception) {
		logger.error(exception.getMessage());
	}
	
	public static void logInfo(Exception exception) {
		logger.info(exception.getMessage());
	}
}
