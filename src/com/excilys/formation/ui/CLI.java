package com.excilys.formation.ui;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CLI {
	
	PrintStream outPut;
	BufferedReader br;
	
	public CLI() {
		//br = new BufferedReader(new InputStreamReader(System.in));
		outPut = System.out;
	}
	
	public CLI(PrintStream ps) {
		if (ps == null) {
			throw new IllegalArgumentException();
		}
		outPut = ps;
	}
	
	public void printResultSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
	    	outPut.println(rs.getNString("name"));
	    	//System.out.println(rs.getArray(1));
	    }
	}
	
	/*
	public void start() {
		try {
			String s = br.readLine();
			System.out.println("entered " + s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
