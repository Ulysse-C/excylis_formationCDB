package com.excilys.formationCDB.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBConnection {

	private static final DBConnection INSTANCE = new DBConnection();
	private Connection connection;
	
	private String URL = "jdbc:mysql://localhost/computer-database-db";
			private String user = "admincdb";
			private String password = "qwerty1234";

	private DBConnection() {
	}  

	public static DBConnection getInstance() {
		return INSTANCE;
	}

	protected Connection getconnection() {
		try {
			if (connection == null || connection.isClosed()) {
				//Class.forName(java.sql.cj)
				connection = DriverManager.getConnection(URL, user,password);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	protected ResultSet executeQuery(String request) {
		ResultSet resultSet = null;
		Statement statement;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

}
