package com.excilys.formationcdb;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.formationcdb.dao", "com.excilys.formationcdb.service",
		"com.excilys.formationcdb.controller.servlet", "com.excilys.formationcdb.controller.cli",
		"com.excilys.formationcdb.ui.cli" })
public class SpringConfig extends AbstractContextLoaderInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}

	@Bean
	public DataSource dataSource() {
		return new HikariDataSource(new HikariConfig("/config/hikariConfig.properties"));
	}

}
