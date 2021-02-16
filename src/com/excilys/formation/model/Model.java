package com.excilys.formation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
	
	private static Model thisModel;
	Connection conn;
	
	private Model() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/computer-database-db", "admincdb", "qwerty1234");
	}
	
	public static Model getModel() throws SQLException {
		if (thisModel == null) {
			thisModel = new Model();
		}
		return thisModel;
	}
	
	public ResultSet listCompanies() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT name FROM company");
	    return rs;
	}
}
