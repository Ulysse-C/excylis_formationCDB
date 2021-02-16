package com.excilys.formation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadDriver {

	public void test() {

		try {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost/computer-database-db", "admincdb", "qwerty1234");
			stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT name FROM company");
		    while (rs.next()) {
		    	System.out.println(rs.getNString("name"));
		    	//System.out.println(rs.getArray(1));
		    }
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			ex.printStackTrace();
		}
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

			/*
			// ResultSet & Statement extends AutoCloseable
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		    */
		}
	}
}
