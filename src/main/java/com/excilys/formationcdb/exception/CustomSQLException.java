package com.excilys.formationcdb.exception;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class CustomSQLException extends Exception {
	

	public CustomSQLException(String message) {
		super(message);
	}
}
