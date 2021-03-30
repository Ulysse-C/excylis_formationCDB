package com.excilys.formationcdb.logger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class CDBLogger {
	private static Map<String, Logger> loggers = new HashMap<>();

	public static void logError(Class<?> c, Exception exception) {
		Logger logger = getLogger(c);
		logger.error(exception.getClass() + " : " + exception.getMessage());
	}

	public static void logInfo(Class<?> c, Exception exception) {
		Logger logger = getLogger(c);
		logger.info(exception.getClass() + " : " + exception.getMessage());
	}

	public static void logInfo(Class<?> c, String message) {
		Logger logger = getLogger(c);
		logger.info(message);

	}

	public static void logDebug(Class<?> c, String message) {
		Logger logger = getLogger(c);
		logger.debug(message);

	}

	public static void logWarn(Class<?> c, Exception exception) {
		Logger logger = getLogger(c);
		logger.warn(exception.getClass() + " : " + exception.getMessage());
	}

	public static void logDebug(Class<?> c, Exception exception) {
		Logger logger = getLogger(c);
		logger.debug(exception.getClass() + " : " + exception.getMessage());
	}

	public static void logTrace(Class<?> c, Exception exception) {
		Logger logger = getLogger(c);
		logger.trace(exception.getClass() + " : " + exception.getMessage());
	}

	private static Logger getLogger(Class<?> c) {
		if (!loggers.containsKey(c.toString())) {
			loggers.put(c.toString(), (Logger) LoggerFactory.getLogger(c));
		}
		return loggers.get(c.toString());
	}
}
