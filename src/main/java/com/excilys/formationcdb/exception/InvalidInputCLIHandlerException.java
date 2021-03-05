package com.excilys.formationcdb.exception;

public class InvalidInputCLIHandlerException extends Exception {
	
	public static final String WRONG_NUMBER_OF_ARGS = "Wrong number of arguments";
	public static final String WRONG_ARGUMENT_TYPE = "Wrong argument type";

	
	public InvalidInputCLIHandlerException(String message) {
		super(message);
	}
}
