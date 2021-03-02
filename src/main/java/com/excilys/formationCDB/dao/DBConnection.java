package com.excilys.formationCDB.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.formationCDB.logger.CDBLogger;

public final class DBConnection {

	private static final DBConnection INSTANCE = new DBConnection();
	private Connection connection;

	private String url = "";
	private String username = "";
	private String password = "";
	private String driver = "";

	private DBConnection() {
		try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("config/dataBase.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			this.url = prop.getProperty("url");
			this.username = prop.getProperty("username");
			this.password = prop.getProperty("password");
			this.driver = prop.getProperty("driver");
		} catch (IOException exception) {
			CDBLogger.logError(exception);
		}
	}

	public static DBConnection getInstance() {
		return INSTANCE;
	}

	protected Connection getconnection() throws SQLException {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, password);
			}
		} catch (ClassNotFoundException exception) {
			CDBLogger.logError(exception);
		}
		return connection;
	}

}
