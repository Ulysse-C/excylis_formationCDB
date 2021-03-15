package com.excilys.formationcdb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.excilys.formationcdb.dao", "com.excilys.formationcdb.service",
		"com.excilys.formationcdb.controller.servlet", "com.excilys.formationcdb.controller.cli",
		"com.excilys.formationcdb.ui.cli", "com.excilys.formationcdb.config" })
public class SpringConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();

		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/");
		bean.setSuffix(".jsp");

		return bean;
	}
	
	@Override
    public void configureDefaultServletHandling(
      DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

	@Bean
	public DataSource getDataSource() {
		return new HikariDataSource(new HikariConfig("/config/hikariConfig.properties"));
	}

}
