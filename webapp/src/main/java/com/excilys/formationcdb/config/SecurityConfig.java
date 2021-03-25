package com.excilys.formationcdb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	DataSource datasource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(datasource).passwordEncoder(passwordEncoder)
//				.usersByUsernameQuery("SELECT username, password, enabled from users where username = ?")
//				.authoritiesByUsernameQuery("SELECT u.username, a.authority" + "FROM user_authorities a, user u "
//						+ "WHERE u.username = ? AND u.id = a.user_id");
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser("user")
				.password(passwordEncoder.encode("123456")).roles("USER").and().withUser("admin")
				.password(passwordEncoder.encode("123456")).roles("USER", "ADMIN");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/js/**").permitAll().antMatchers("/css/**").permitAll().antMatchers("/login").permitAll().antMatchers("/**").hasAnyRole("ADMIN", "USER")
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/dashboard").failureUrl("/login?error=true")
				.permitAll().and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
				.permitAll().and().csrf().disable();
	}
}