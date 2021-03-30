package com.excilys.formationcdb.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.excilys.formationcdb.logger.CDBLogger;

public class Main {
	public static void main(String[] args) {
		String command = "curl --user "
				+ "http://localhost:8080/cdb/computer/count";
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8") );
			System.out.println(br.readLine());
		} catch (IOException e) {
			CDBLogger.logError(Main.class, e);
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
	}
}
