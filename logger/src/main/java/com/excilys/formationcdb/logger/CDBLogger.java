package com.excilys.formationcdb.logger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class CDBLogger {
	private static Map<String, Logger> loggers = new HashMap<>();

	public static void logError(Exception exception) {
		Logger logger = getLogger(exception);
		logger.error(exception.getMessage());
	}

	public static void logInfo(Exception exception) {
		Logger logger = getLogger(exception);
		logger.info(exception.getMessage());
	}

	private static Logger getLogger(Exception exception) {
		if (!loggers.containsKey(exception.getClass().toString())) {
			loggers.put(exception.getClass().toString(), (Logger) LoggerFactory.getLogger(exception.getClass()));
		}
		return loggers.get(exception.getClass().toString());
	}
}
