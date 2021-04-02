package com.excilys.formationcdb.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.formationcdb.dao", 
		"com.excilys.formationcdb.config", "com.excilys.formationcdb.aspects" })
@EnableAspectJAutoProxy
public class DaoConfig  {


	@Bean
	public DataSource getDataSource() {
		return new HikariDataSource(new HikariConfig("/config/hikariConfig.properties"));
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan("com.excilys.formationcdb.dto.dao");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		try (InputStream input = this.getClass().getResourceAsStream("/config/hibernate.properties")) {
    		hibernateProperties.load(input);
		}catch (IOException ex) {
            ex.printStackTrace();
        }
		return hibernateProperties;
	}
}
