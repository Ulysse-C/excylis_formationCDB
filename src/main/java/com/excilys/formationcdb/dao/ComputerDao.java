package com.excilys.formationcdb.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.formationcdb.dao.rowmapper.ComputerRowMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;

@Repository
public class ComputerDao {

	private DataSource dataSource;

	private static final String UPDATE_COMPUTER_QUERY = "UPDATE computer "
			+ "SET company_id = :companyId, discontinued = :discontinued, introduced = :introduced, name = :name WHERE id = :id";
	private static final String GET_COMPUTER_BY_ID_QUERY = "SELECT computer.id,"
			+ " computer.name, company.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id FROM computer LEFT JOIN company "
			+ "ON company.id = computer.company_id WHERE computer.id = :id";
	private static final String CREATE_COMPUTER_QUERY = "INSERT INTO computer"
			+ " (name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :companyId)";
	private static final String DELETE_COMPUTER_BY_ID_QUERY = "DELETE FROM computer" + " WHERE id = :id";
	private static final String GET_PAGE_BY_NAME_QUERY = "SELECT computer.id, computer.name,"
			+ " company.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id FROM computer LEFT JOIN company "
			+ "ON company.id = computer.company_id WHERE computer.name "
			+ "LIKE :searchQuery ORDER BY :sortNameString :sortOrderString LIMIT :size OFFSET :offset";
	private static final String GET_COMPUTER_NUMBER_BY_NAME_QUERY = "SELECT COUNT(name) AS nbComputer"
			+ " FROM computer WHERE computer.name LIKE :search";

	public ComputerDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Optional<Computer> getComputerById(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		Optional<Computer> computer = Optional
				.ofNullable(jdbcTemplate.query(GET_COMPUTER_BY_ID_QUERY, params, new ComputerRowMapper()).get(0));
		return computer;
	}
	
	public void createComputer(Computer computer) {
		if (computer != null) {
			SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			jdbcTemplate.update(CREATE_COMPUTER_QUERY, params);
		}
	}

	public void deleteComputerById(int id) throws NothingSelectedException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplate.update(DELETE_COMPUTER_BY_ID_QUERY, params);
	}

	public int getComputerNumberbyName(String search) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("search", "%" + search + "%");
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(GET_COMPUTER_NUMBER_BY_NAME_QUERY, params, Integer.class);
	}

	public Page<Computer> getPage(Page<Computer> page) {
		if (page != null) {
			String query = GET_PAGE_BY_NAME_QUERY.replaceFirst(":sortNameString", page.getSortNameString())
					.replaceFirst(":sortOrderString", page.getSortOrderString());
			SqlParameterSource params = new BeanPropertySqlParameterSource(page);
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			List<Computer> computerList = jdbcTemplate.query(query, params, new ComputerRowMapper());
			page.setContent(computerList.stream().map(Optional::ofNullable).collect(Collectors.toList()));
		}
		return page;
	}

	public void updateComputer(Computer computer) throws NothingSelectedException {
		if (computer != null) {
			SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			int row = jdbcTemplate.update(UPDATE_COMPUTER_QUERY, params);
			if (row == 0) {
				throw new NothingSelectedException("Update");
			}
		}
	}

}
