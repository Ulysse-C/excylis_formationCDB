package com.excilys.formationcdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.formationcdb.logger.CDBLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DBConnection {

	private static final DBConnection INSTANCE = new DBConnection();
	private HikariDataSource dataSource;

	private DBConnection() {
		try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("config/dataBase.properties")) {
			Properties properties = new Properties();
			properties.load(input);
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(properties.getProperty("url"));
			config.setUsername(properties.getProperty("username"));
			config.setPassword(properties.getProperty("password"));
			config.setDriverClassName(properties.getProperty("driver"));
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			dataSource = new HikariDataSource(config);
		} catch (IOException exception) {
			CDBLogger.logError(exception);
		}
	}

	public static DBConnection getInstance() {
		return INSTANCE;
	}

	protected Connection getconnection() throws SQLException {
		return dataSource.getConnection();
	}

}
