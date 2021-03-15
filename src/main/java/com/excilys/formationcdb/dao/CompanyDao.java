package com.excilys.formationcdb.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formationcdb.dao.rowmapper.CompanyRowMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;

@Repository
public class CompanyDao {

	private DataSource dataSource;

	private static final String DELETE_COMPUTER_OF_COMPANY_QUERY = "DELETE FROM computer WHERE company_id = :id";
	private static final String DELETE_COMPANY_BY_ID_QUERY = "DELETE FROM company WHERE id = :id";
	private static final String GET_PAGE_QUERY = "SELECT id,name FROM company ORDER BY id LIMIT :size OFFSET :number";
	private static final String GET_COMPANY_LIST_QUERY = "SELECT id,name FROM company ORDER BY id";

	public CompanyDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Optional<Company>> getCompanyList() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Company> companyList = jdbcTemplate.query(GET_COMPANY_LIST_QUERY, new CompanyRowMapper());
		return companyList.stream().map(Optional::ofNullable).collect(Collectors.toList());
	}

	public Page<Company> getPage(Page<Company> page) {
		if (page != null) {
			SqlParameterSource params = new BeanPropertySqlParameterSource(page);
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			List<Company> companyList = jdbcTemplate.query(GET_PAGE_QUERY, params, new CompanyRowMapper());
			page.setContent(companyList.stream().map(Optional::ofNullable).collect(Collectors.toList()));
		}
		return page;
	}

	@Transactional
	public void deleteCompanyById(int id) throws NothingSelectedException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplate.update(DELETE_COMPUTER_OF_COMPANY_QUERY, params);
		jdbcTemplate.update(DELETE_COMPANY_BY_ID_QUERY, params);
	}
}
