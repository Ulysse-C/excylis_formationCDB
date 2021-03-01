package com.excilys.formationCDB.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {

	private static final DBConnection INSTANCE = new DBConnection();
	private Connection connection;

	private String url = "";
	private String username = "";
	private String password = "";

	private DBConnection() {
		try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("config/dataBase.properties")) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			this.url = prop.getProperty("url");
			this.username = prop.getProperty("username");
			this.password = prop.getProperty("password");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static DBConnection getInstance() {
		return INSTANCE;
	}

	protected Connection getconnection() throws SQLException {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url, username, password);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
